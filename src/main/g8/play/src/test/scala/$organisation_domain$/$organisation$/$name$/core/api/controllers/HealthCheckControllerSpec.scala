package $organisation_domain$.$organisation$.$name$.core.api.controllers

import org.scalatestplus.play.PlaySpec
import play.api.mvc._
import play.api.test.Helpers._
import play.api.test._

class HealthCheckControllerSpec extends PlaySpec with Results {

  val controller = new HealthCheckController(stubControllerComponents())

  "HealthCheckController" should {
    "return the application status" in {
      val request = FakeRequest("GET", "/health")
      val result = controller.health.apply(request)
      status(result) mustBe OK
      (contentAsJson(result) \ "status").as[String] mustBe "Ok"
    }
  }

}
