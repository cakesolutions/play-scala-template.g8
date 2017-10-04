package $organisation_domain$.$organisation$.$name;format="norm,word"$

import scala.concurrent.duration._
import scala.language.postfixOps

import com.typesafe.config.Config
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.HeaderNames
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import $organisation_domain$.$organisation$.$name;format="norm,word"$.core.config.ConfigHelper._
import $organisation_domain$.$organisation$.$name;format="norm,word"$.core.config.ValidatedServerConfig

// scalastyle:off magic.number

object $name;format="norm,word,Camel"$PerformanceTest {
  val requiredEnvVars: Map[String, String] = {
    // In CI environments, we use the eth0 or local-ipv4 address of the slave
    // instead of localhost
    val appHost = sys.env.getOrElse("CI_HOST", "localhost")

    Map(
      "APP_HOST" -> appHost,
      "APP_PORT" -> "9000",
      "APPLICATION_SECRET" -> "secret"
    )
  }

  val optionalEnvVars: Map[String, String] = Map()
}

class $name;format="norm,word,Camel"$PerformanceTest extends Simulation {

  import $name;format="norm,word,Camel"$PerformanceTest._

  private val config: Config =
    validateWithEnvironmentOverrides(
      "application.conf"
    )(
      requiredEnvVars,
      optionalEnvVars
    ).get

  private val validatedConfig = {
    ValidatedServerConfig(config)
      .getOrElse(
        throw new RuntimeException("Failed to validate application.conf")
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

  setUp(
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
