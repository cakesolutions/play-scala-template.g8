package $organisation_domain$.$organisation$.$name$.core.api.controllers

import org.scalatestplus.play._
import play.api.mvc._
import play.api.test.Helpers._
import play.api.test._

class BuildInfoControllerSpec extends PlaySpec with Results {

  val controller = new BuildInfoController(stubControllerComponents())

  "BuildInfoController" should {
    "return the build info" in {
      val request = FakeRequest("GET", "/version")
      val result = controller.info.apply(request)
      status(result) mustBe OK
    }
  }

}
