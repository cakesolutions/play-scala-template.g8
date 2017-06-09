package $organisation_domain$.$organisation$.$name$.core.api.routers

import javax.inject.{Inject, Singleton}

import $organisation_domain$.$organisation$.$name$.core.api.controllers._
import play.api.routing.Router.Routes
import play.api.routing.{Router, SimpleRouter}
import play.api.routing.sird._

@Singleton
class CoreRouter @Inject()(
    healthCheckController: HealthCheckController,
    buildInfoController: BuildInfoController,
    assetsController: controllers.Assets,
    defaultController: controllers.Default
) extends SimpleRouter {

  override def routes: Routes = {
    // Base Routes
    case GET(p"/health")  => healthCheckController.health
    case GET(p"/version") => buildInfoController.info

    // Swagger Documentation and Specification routes
    case GET(p"/specs.yml") =>
      assetsController.at(path = "/", "$name$.yml")
    case GET(p"/docs" ? q"url=\$url") =>
      assetsController.at(path = "/public/lib/swagger-ui", file = "index.html")
    case GET(p"/docs/index.html" ? q"url=\$specs") =>
      assetsController.at(path = "/public/lib/swagger-ui", file = "index.html")
    case GET(p"/docs") | GET(p"/docs/index.html") =>
      defaultController.redirect("/docs/index.html?url=/specs.yml")
    case GET(p"/docs/\$file*") =>
      assetsController.at(path = "/public/lib/swagger-ui", file)
    case GET(p"/") =>
      defaultController.redirect("/docs")
  }

}
