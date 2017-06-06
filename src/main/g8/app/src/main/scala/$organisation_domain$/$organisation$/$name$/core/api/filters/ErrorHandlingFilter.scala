package $organisation_domain$.$organisation$.$name$.core.api.filters

import javax.inject.Inject

import akka.stream.Materializer
import play.api.Logger
import play.api.mvc._
import play.api.mvc.Results._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

class ErrorHandlingFilter @Inject()(
    implicit ec: ExecutionContext,
    val mat: Materializer
) extends Filter {

  private val logger = Logger(getClass.getName)

  override def apply(
    nextFilter: RequestHeader => Future[Result]
  )(request: RequestHeader): Future[Result] =
    nextFilter(request) recover {
      case NonFatal(e) =>
        logger.error(
          s"Internal server error, for (\${request.method}) [\${request.uri}]",
          e
        )
        InternalServerError
    }

}
