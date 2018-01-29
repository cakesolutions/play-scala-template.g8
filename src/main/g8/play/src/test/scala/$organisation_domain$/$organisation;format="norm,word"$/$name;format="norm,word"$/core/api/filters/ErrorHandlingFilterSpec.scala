package $organisation_domain$.$organisation;format="norm,word"$.$name;format="norm,word"$.core.api.filters

import scala.concurrent.ExecutionContext

import akka.stream.Materializer
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.mvc.{DefaultActionBuilder, Filters}
import play.api.test.FakeRequest
import play.api.test.Helpers._

class ErrorHandlingFilterSpec extends PlaySpec with GuiceOneAppPerSuite {

  implicit lazy val materializer: Materializer = app.materializer

  "BaseFilters" should {
    "intercept non-fatal errors" in {
      implicit val ec = app.injector.instanceOf[ExecutionContext]
      val Action = app.injector.instanceOf[DefaultActionBuilder]
      val action = Action(_ => throw new RuntimeException("Server error"))
      val rh = FakeRequest()
      val result = Filters(action, new ErrorHandlingFilter())(rh)
      status(result) mustBe Status.INTERNAL_SERVER_ERROR
    }
  }
}
