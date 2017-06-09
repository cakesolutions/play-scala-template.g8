package $organisation_domain$.$organisation$.$name$.core.api.routers

import org.scalatestplus.play.PlaySpec
import play.api.{ Environment, Mode, Play }
import play.api.http.Status
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import play.api.test.Helpers._

class CoreRouterSpec extends PlaySpec {

  val application =
    GuiceApplicationBuilder(environment = Environment.simple(mode = Mode.Dev))
      .configure("play.http.router" -> "$organisation_domain$.$organisation$.$name$.core.api.routers.CoreRouter")
      .configure("play.http.filters" -> "$organisation_domain$.$organisation$.$name$.core.api.filters.BaseFilters")
      .build()
  Play.start(application)

  "CoreRouter" should {
    "route request to correct API endpoint" in {
      val versionRequest = FakeRequest(GET, "/version")
      val Some(version) = route(application , versionRequest)
      status(version) mustBe Status.OK

      val healthRequest = FakeRequest(GET, "/health")
      val Some(healthCheck) = route(application , healthRequest)
      status(healthCheck) mustBe Status.OK
    }
    "serve API Docs" in {
      val specsRequest = FakeRequest(GET, "/specs.yml")
      val Some(specs) = route(application , specsRequest)
      status(specs) mustBe Status.OK

      val docsRequest = FakeRequest(GET, "/docs?url=/specs.yml")
      val Some(docs) = route(application , docsRequest)
      status(docs) mustBe Status.OK

      val indexRequest = FakeRequest(GET, "/docs/index.html?url=/specs.yml")
      val Some(index) = route(application , indexRequest)
      status(index) mustBe Status.OK

      val swaggerUIRequest = FakeRequest(GET, "/docs/swagger-ui-bundle.js")
      val Some(swaggerUI) = route(application , swaggerUIRequest)
      status(swaggerUI) mustBe Status.OK
    }
    "redirect request to API Docs when url parameter is missing" in {
      val redirectDocsRequest = FakeRequest(GET, "/docs")
      val Some(redirectDocs) = route(application, redirectDocsRequest)
      status(redirectDocs) mustBe Status.SEE_OTHER

      val redirectIndexRequest = FakeRequest(GET, "/docs/index.html")
      val Some(redirectIndex) = route(application, redirectIndexRequest)
      status(redirectIndex) mustBe Status.SEE_OTHER
    }
    "redirect request to API Docs when bass route is called" in {
      val redirectDocsRequest = FakeRequest(GET, "/")
      val Some(redirectDocs) = route(application, redirectDocsRequest)
      status(redirectDocs) mustBe Status.SEE_OTHER

      val redirectIndexRequest = FakeRequest(GET, "/docs/index.html")
      val Some(redirectIndex) = route(application, redirectIndexRequest)
      status(redirectIndex) mustBe Status.SEE_OTHER
    }
    "respond with 404 (not found) for unknown resources" in {
      val request = FakeRequest(GET, "/unknown/resource")
      val Some(result) = route(application , request)
      status(result) mustBe Status.NOT_FOUND
    }
  }

}