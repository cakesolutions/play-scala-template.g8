package $organisation_domain$.$organisation$.$name$.core

import org.scalatestplus.play.PlaySpec
import org.slf4j.LoggerFactory
import play.api.{LoggerLike, Play}
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder

import $organisation_domain$.$organisation$.$name$.core.utils.ValueDiscard

class StartUpLoggingSpec extends PlaySpec {

  object StringLogger extends LoggerLike {
    val logger = LoggerFactory.getLogger("Test")
    val logged = new StringBuilder()

    override def info(message: => String): Unit = {
      logged.append(message)
      ValueDiscard[StringBuilder] {
        logged.append(System.lineSeparator())
      }
    }

    override def info(message: => String, error: => Throwable): Unit = {
      logged.append(message)
      logged.append(System.lineSeparator())
      logged.append(error.toString)
      ValueDiscard[StringBuilder] {
        logged.append(System.lineSeparator())
      }
    }
  }

  val application =
    GuiceApplicationBuilder()
      .overrides(
        bind[LoggerLike]
          .qualifiedWith("startUpLogging")
          .toInstance(StringLogger)
      )
      .build()
  Play.start(application)

  "Application" should {
    "log environment/runtime information on startup" in {

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
