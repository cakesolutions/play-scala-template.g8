package $organisation_domain$.$organisation$.$name$

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.ws.ahc.AhcWSClient
import org.scalatest.{ FreeSpec, Matchers }

trait RestApiIntegrationTest extends FreeSpec with Matchers {

  protected def dockerHostName = "docker-local"
  implicit val actorSystem: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  val wsClient = AhcWSClient()
}
