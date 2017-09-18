package $organisation_domain$.$organisation$.$name$

import scala.concurrent.duration._
import scala.language.postfixOps

import com.typesafe.config.{Config, ConfigFactory}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.HeaderNames
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import $organisation_domain$.$organisation$.$name$.core.config.ValidatedServerConfig

// scalastyle:off magic.number

class $name;format="Camel"$PerformanceTest extends Simulation {

  private val config: Config = ConfigFactory.load("application.conf")

  private val validatedConfig = {
    ValidatedServerConfig(config)
      .getOrElse(
        sys.error("Failed to validate application.conf")
      )
  }

  val appUrl: String = {
    val scheme = config.getString("services.app.scheme")
    val host = validatedConfig.host
    val port = validatedConfig.port

    s"\$scheme://\$host:\$port"
  }

  val httpConf: HttpProtocolBuilder = http.baseURL(appUrl)

  val readClients = scenario("Clients").exec(HealthCheck.refreshManyTimes)

  val _ = setUp(
    readClients.inject(rampUsers(100) over (10 seconds)).protocols(httpConf)
  ).assertions(
    global.successfulRequests.percent.gt(95)
  )
}

object HealthCheck {

  def refreshAfterOneSecond: ChainBuilder = {
    exec(
      http("Health")
        .get("/health")
        .header(HeaderNames.Host, "localhost")
        .check(status.is(200))
    ).pause(1)
  }

  val refreshManyTimes = repeat(10) {
    refreshAfterOneSecond
  }
}
