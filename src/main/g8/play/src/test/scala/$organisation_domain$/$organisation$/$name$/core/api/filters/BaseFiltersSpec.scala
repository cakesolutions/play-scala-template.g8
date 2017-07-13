package $organisation_domain$.$organisation$.$name$.core.api.filters

import org.scalatestplus.play.PlaySpec
import play.api.Play
import play.api.http.Status
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.Action
import play.api.routing.sird._
import play.api.test.FakeRequest
import play.api.test.Helpers.{GET => GET_METHOD, _}

class BaseFiltersSpec extends PlaySpec {

  val application =
    GuiceApplicationBuilder()
      .configure(
        "play.http.filters" ->
          "test_net.test_cakesolutions.playrepo.core.api.filters.BaseFilters"
      )
      .routes({
        case ("GET", "/failure") =>
          Action(_ => throw new RuntimeException("Server error"))
      })
      .build()
  Play.start(application)

  "BaseFilters" should {
    "intercept non-fatal errors" in {
      val request = FakeRequest(GET_METHOD, "/failure")
      val Some(result) = route(application, request)
      status(result) mustBe Status.INTERNAL_SERVER_ERROR
    }
  }
}
