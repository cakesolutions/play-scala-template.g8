package $organisation_domain$.$organisation$.$name$.core.config

import cakesolutions.config._
import cats.data.{NonEmptyList => NEL, Validated}
import cats.syntax.cartesian._
import com.typesafe.config.Config
import eu.timepit.refined._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric._
import eu.timepit.refined.string._

/**
  * Validated server configuration settings helper and utilities.
  */
object ValidatedServerConfig {
  type PositiveInt = Int Refined Positive

  /**
    * Validated configuration settings for the server.
    *
    * @param host hostname the Play server will listen on
    * @param port port the Play server will listen on
    */
  sealed abstract case class ServerConfig(host: String, port: PositiveInt)

  /**
    * Factory method for creating validated instances of ServerConfig.
    *
    * @return validated configuration object or the validation failures
    */
  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  def apply(
    implicit config: Config
  ): Validated[NEL[ValueFailure], ServerConfig] = {
    via[ServerConfig]("services.app") { implicit config =>
      (unchecked[String](required("host", "NOT_SET")) |@|
        unchecked[PositiveInt](required("port", "NOT_SET")))
        .map(new ServerConfig(_, _) {})
    }
  }
}
