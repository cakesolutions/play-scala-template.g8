package $organisation_domain$.$organisation$.$name$.core.api.routers

import javax.inject.{Inject, Singleton}

import play.api.routing.Router.Routes
import play.api.routing.{Router, SimpleRouter}

@Singleton
class CoreRouter @Inject()
    extends SimpleRouter {

  override def routes: Routes = Router.empty.routes

}
