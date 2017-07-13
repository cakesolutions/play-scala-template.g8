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
  * TODO:
  */
object ValidatedServerConfig {
  type PositiveInt = Int Refined Positive

  /**
    * TODO:
    *
    * @param host
    * @param port
    */
  sealed abstract case class ServerConfig(host: String, port: PositiveInt)

  /**
    * TODO:
    *
    * @return
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
