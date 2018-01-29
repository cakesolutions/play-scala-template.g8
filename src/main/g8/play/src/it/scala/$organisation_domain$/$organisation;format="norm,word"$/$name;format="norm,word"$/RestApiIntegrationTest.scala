package $organisation_domain$.$organisation;format="norm,word"$.$name;format="norm,word"$

import scala.concurrent.ExecutionContext

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.Config
import org.scalatest.{AsyncFreeSpec, Matchers}
import play.api.libs.ws.ahc.AhcWSClient

import $organisation_domain$.$organisation;format="norm,word"$.$name;format="norm,word"$.core.config.{ConfigHelper, ValidatedServerConfig}

object RestApiIntegrationTest {
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

  val optionalEnvVars: Map[String, String] = Map(
    "TEST_ACTOR_CREATOR_SERIALISATION" -> "on",
    "TEST_ACTOR_MESSAGE_SERIALISATION" -> "on"
  )
}

trait RestApiIntegrationTest extends AsyncFreeSpec with Matchers {

  import ConfigHelper._
  import RestApiIntegrationTest._

  private val config: Config =
    validateWithEnvironmentOverrides(
      "application.conf"
    )(
      requiredEnvVars,
      optionalEnvVars
    ).get

  private val validatedConfig = {
    ValidatedServerConfig(config)
      .getOrElse(fail("Failed to validate application.conf"))
  }

  val appUrl: String = {
    val host = validatedConfig.host
    val port = validatedConfig.port

    s"http://\$host:\$port"
  }

  implicit val actorSystem: ActorSystem =
    ActorSystem(actorSystemNameFrom(getClass), config)
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContext = ExecutionContext.global

  val wsClient = AhcWSClient()

  private def actorSystemNameFrom(clazz: Class[_]) =
    clazz.getName
      .replace('.', '-')
      .replace('_', '-')
      .filter(_ != '\$')
}
