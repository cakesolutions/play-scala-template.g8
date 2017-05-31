package $organisation_domain$.$organisation$.$name$.core.api.routers

import javax.inject.{ Inject, Singleton }

import $organisation_domain$.$organisation$.$name$.core.api.controllers._
import play.api.routing.Router.Routes
import play.api.routing.{ Router, SimpleRouter }
import play.api.routing.sird._

@Singleton
class CoreRouter @Inject()(
  healthCheckController: HealthCheckController,
  buildInfoController: BuildInfoController,
  assets: controllers.Assets
) extends SimpleRouter {

  override def routes: Routes = {
    // Base Routes
    case GET(p"/health")  => healthCheckController.health
    case GET(p"/version") => buildInfoController.info

    // Swagger Documentation and Specification routes
    case GET(p"/swagger.yaml") =>
      assets.at(path = "/public/specs", "swagger.yaml")
    case GET(p"/docs") =>
      assets.at(path = "/public/swagger-ui", file = "index.html")
    case GET(p"/docs/\$file") =>
      assets.at(path = "/public/swagger-ui", file)
    case GET(p"/specs/\$file") =>
      assets.at(path = "/public/specs", file)
  }

}
