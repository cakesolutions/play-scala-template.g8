package $organisation_domain$.$organisation;format="norm,word"$.$name;format="norm,word"$.core

import org.scalatestplus.play.PlaySpec
import org.slf4j.{Logger, LoggerFactory}
import play.api.{LoggerLike, MarkerContext, Play}
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder

import $organisation_domain$.$organisation;format="norm,word"$.$name;format="norm,word"$.core.utils.ValueDiscard

class StartUpLoggingSpec extends PlaySpec {

  object StringLogger extends LoggerLike {
    val logger: Logger = LoggerFactory.getLogger("Test")
    val logged = new StringBuilder()

    override def info(message: => String)(implicit mc: MarkerContext): Unit = {
      logged.append(message)
      ValueDiscard[StringBuilder] {
        logged.append(System.lineSeparator())
      }
    }

    override def info(message: => String, error: => Throwable)(
      implicit mc: MarkerContext
    ): Unit = {
      logged.append(message)
      logged.append(System.lineSeparator())
      logged.append(error.toString)
      ValueDiscard[StringBuilder] {
        logged.append(System.lineSeparator())
      }
    }
  }

  private val application =
    GuiceApplicationBuilder()
      .configure("startuplogging.enabled" -> true)
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

      // If WSClient is being used, it seems to add
      // "sun.nio.ch.bugLevel" in between sys.props
      // being logged and us checking that they have
      // been logged so filter it out.
      sys.props.toList
        .filterNot(_._1 == "sun.nio.ch.bugLevel")
        .sortBy(_._1)
        .foreach {
          case (k, v) =>
            loggedOutput must include(s"property: \$k=\$v")
        }

      val configData = application.configuration.entrySet.toList.sortBy(_._1)
      for ((k, v) <- configData) {
        loggedOutput must include(s"configuration: \$k=\${v.unwrapped}")
      }
    }
  }
}
