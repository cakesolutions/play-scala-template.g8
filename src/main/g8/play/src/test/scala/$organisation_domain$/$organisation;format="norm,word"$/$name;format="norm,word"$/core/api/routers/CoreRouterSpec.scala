package $organisation_domain$.$organisation;format="norm,word"$.$name;format="norm,word"$.core.api.routers

import org.scalatestplus.play.PlaySpec
import play.api.{Environment, Mode, Play}
import play.api.http.Status
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import play.api.test.Helpers._

class CoreRouterSpec extends PlaySpec {

  val application =
    GuiceApplicationBuilder(environment = Environment.simple(mode = Mode.Dev))
      .configure(
        "play.http.router" ->
          "$organisation_domain$.$organisation;format="norm,word"$.$name;format="norm,word"$.core.api.routers.CoreRouter"
      )
      .build()
  Play.start(application)

  "CoreRouter" should {
    "route request to correct API endpoint" in {
      val versionRequest = FakeRequest(GET, "/version")
      val Some(version) = route(application, versionRequest)
      status(version) mustBe Status.OK

      val healthRequest = FakeRequest(GET, "/health")
      val Some(healthCheck) = route(application, healthRequest)
      status(healthCheck) mustBe Status.OK
    }
    "serve API specs" in {
      val specsRequest = FakeRequest(GET, "/$name;format="norm"$.yml")
      val Some(specs) = route(application, specsRequest)
      status(specs) mustBe OK
    }
    "redirect request to API docs when url parameter is missing" in {
      val redirectDocsRequest = FakeRequest(GET, "/docs")
      val Some(redirectDocs) = route(application, redirectDocsRequest)
      status(redirectDocs) mustBe SEE_OTHER

    }
    "redirect request to API docs when base route is called" in {
      val redirectDocsRequest = FakeRequest(GET, "/")
      val Some(redirectDocs) = route(application, redirectDocsRequest)
      status(redirectDocs) mustBe SEE_OTHER

    }
    "respond with 404 (not found) for unknown resources" in {
      val request = FakeRequest(GET, "/unknown/resource")
      val Some(result) = route(application, request)
      status(result) mustBe Status.NOT_FOUND
    }
  }

}
