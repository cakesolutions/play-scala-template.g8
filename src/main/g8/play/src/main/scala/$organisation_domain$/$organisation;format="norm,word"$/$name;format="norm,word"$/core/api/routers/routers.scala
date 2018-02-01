package $organisation_domain$.$organisation;format="norm,word"$.$name;format="norm,word"$.core.api

import scala.concurrent.ExecutionContext

import _root_.controllers.Assets
import play.api.mvc._
import play.api.routing.Router.Routes
import play.api.routing.sird._

package object routers {

  /**
    * A routing function allowing Play to serve the swagger UI.
    *
    * @param assets the assets controller
    * @param swaggerPath the path of the directory at which the swagger file can be found
    * @param swaggerFile the name of the swagger file
    * @return a routing partial-function that can be composed with other routes
    *         and fed to a Play application
    */
  def swaggerUiRoutes(assets: Assets, swaggerPath: String, swaggerFile: String)(
    implicit ec: ExecutionContext
  ): Routes = {
    case GET(p"/assets/\$file*") =>
      assets.at(path = "/public/", file)
    case GET(p"/docs") =>
      new ActionBuilderImpl(BodyParsers.utils.empty).apply {
        Results.Redirect(
          url = "/assets/lib/swagger-ui/index.html",
          queryString = Map("url" -> Seq(s"/\$swaggerFile"))
        )
      }
    case GET(p"/") =>
      new ActionBuilderImpl(BodyParsers.utils.empty).apply {
        Results.Redirect(
          url = "/assets/lib/swagger-ui/index.html",
          queryString = Map("url" -> Seq(s"/\$swaggerFile"))
        )
      }
  }

  /**
    * Generic function to expose specific swagger specs at specific url path
    * @param assets the assets controller
    * @param swaggerPath the path of the directory at which the swagger file can be found
    * @param swaggerFile the name of the swagger file
    * @param urlToServeSwagger url to server swagger ui
    * @return
    */
  def exposeSwaggerUIAtPath(
    assets: Assets,
    swaggerPath: String,
    swaggerFile: String,
    urlToServeSwagger: String
  )(implicit ec: ExecutionContext): Routes = {
    case GET(p"/\$url") if url == swaggerFile =>
      assets.at(swaggerPath, swaggerFile)
    case GET(p"/\$url*") if url == urlToServeSwagger.dropWhile(_ == '/') =>
      new ActionBuilderImpl(BodyParsers.utils.empty).apply {
        Results.Redirect(
          url = "/assets/lib/swagger-ui/index.html",
          queryString = Map("url" -> Seq(s"/\$swaggerFile"))
        )
      }
  }

}
