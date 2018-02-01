package $organisation_domain$.$organisation;format="norm,word"$.$name;format="norm,word"$.core.api.routers

import javax.inject.{Inject, Singleton}

import scala.concurrent.ExecutionContext

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

import $organisation_domain$.$organisation;format="norm,word"$.$name;format="norm,word"$.core.api.controllers._

@Singleton
class CoreRouter @Inject()(
  healthCheckController: HealthCheckController,
  buildInfoController: BuildInfoController,
  assetsController: controllers.Assets,
  defaultController: controllers.Default
)(
  implicit executor: ExecutionContext
) extends SimpleRouter {

  override def routes: Routes =
    exposeSwaggerUIAtPath(
      assetsController,
      "/",
      "$name;format="norm"$.yml",
      "/docs"
    ).orElse(
        swaggerUiRoutes(assetsController, "/", "$name;format="norm"$.yml")(executor)
      )
      .orElse {
        // Base Routes
        case GET(p"/health") => healthCheckController.health
        case GET(p"/version") => buildInfoController.info
      }

}
