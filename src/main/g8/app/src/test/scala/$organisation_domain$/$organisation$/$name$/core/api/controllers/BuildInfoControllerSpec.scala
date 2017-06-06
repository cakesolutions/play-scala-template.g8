package $organisation_domain$.$organisation$.$name$.core.api.controllers

import org.scalatestplus.play.PlaySpec
import play.api.http.Status
import play.api.test.FakeRequest
import play.api.test.Helpers._

class BuildInfoControllerSpec extends PlaySpec {

  val controller = new BuildInfoController

  "BuildInfoController" should {
    "return the build info" in {
      val request = FakeRequest("GET", "/version")
      val result  = controller.info.apply(request)
      status(result) mustBe Status.OK
    }
  }

}