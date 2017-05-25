package $organisation_domain$.$organisation$.$name$

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.{ Config, ConfigFactory }
import org.scalatest.{ AsyncFreeSpec, Matchers }
import play.api.libs.ws.ahc.AhcWSClient
import scala.concurrent.ExecutionContext

trait RestApiIntegrationTest extends AsyncFreeSpec with Matchers {

  protected val config = ConfigFactory.load()
  protected val targetUrl = {
    val target = config.getConfig("services.target")
    s"\${target.getString("scheme")}://\${target.getString("host")}:\${target.getInt("port")}"
  }
  implicit val actorSystem: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContext = ExecutionContext.global
  val wsClient = AhcWSClient()

}
