package $organisation_domain$.$organisation$.$name$.core.api.controllers

import org.scalatestplus.play.PlaySpec
import play.api.http.Status
import play.api.test.FakeRequest
import play.api.test.Helpers._

class HealthCheckControllerSpec extends PlaySpec {

  val controller = new HealthCheckController

  "HealthCheckController" should {
    "return the application status" in {
      val request = FakeRequest("GET", "/health")
      val result  = controller.health.apply(request)
      status(result) mustBe Status.OK
      (contentAsJson(result) \ "status").as[String] mustBe "Ok"
    }
  }

}