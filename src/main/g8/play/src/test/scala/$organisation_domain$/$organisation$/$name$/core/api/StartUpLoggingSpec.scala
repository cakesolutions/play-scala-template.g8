package $organisation_domain$.$organisation$.$name$.core.api

import org.scalatestplus.play.PlaySpec
import org.slf4j.LoggerFactory
import play.api.{ LoggerLike, Play }
import play.api.http.Status
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.Action
import play.api.routing.sird._
import play.api.test.FakeRequest
import play.api.test.Helpers.{ GET => GET_METHOD, _}

class StartUpLoggingSpec extends PlaySpec {

  object StringLogger extends LoggerLike {
    val logger = LoggerFactory.getLogger("Test")
    val logged = new StringBuilder()

    override def info(message: => String): Unit = {
      logged.append(message)
      logged.append(System.lineSeparator())
    }

    override def info(message: => String, error: => Throwable): Unit = {
      logged.append(message)
      logged.append(System.lineSeparator())
      logged.append(error.toString)
      logged.append(System.lineSeparator())
    }
  }

  val application =
    GuiceApplicationBuilder()
      .overrides(bind[LoggerLike].qualifiedWith("startUpLogging").toInstance(StringLogger))
      .build()
  Play.start(application)

  "Application" should {
    "log environtment/runtime information on startup" in {

      val expectedParameters = Seq(
        "java.lang.memory.heap: initial=",
        "java.lang.memory.heap: maximum=",
        "java.lang.memory.heap: used=",
        "java.lang.memory.non-heap: committed=",
        "java.lang.memory.non-heap: initial=",
        "java.lang.memory.non-heap: maximum=",
        "java.lang.memory.non-heap: used=",
        "runtime: available-processors="
      )

      val loggedOutput = StringLogger.logged.toString

      expectedParameters.foreach { expectedParameter =>
        loggedOutput must include(expectedParameter)
      }

      sys.props.toList.sortBy(_._1).foreach {
        case (key, value) =>
          loggedOutput must include(s"property: \$key=\$value")
      }

      val configData = application.configuration.entrySet.toList.sortBy(_._1)
      for ((key, value) <- configData) {
        loggedOutput must include(s"configuration: \${key}=\${value.unwrapped}")
      }

    }
  }
}
