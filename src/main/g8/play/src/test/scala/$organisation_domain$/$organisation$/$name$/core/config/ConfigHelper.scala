package $organisation_domain$.$organisation$.$name$.core.config

import scala.util.Try

import com.typesafe.config.{Config, ConfigFactory}

/**
  * TODO:
  */
object ConfigHelper {

  /**
    * Helper function to load Typesafe configuration data with environment
    * variables overridden.
    *
    * @param resourceName resource name that is to be loaded (e.g.
    *   "application.conf")
    * @param required required environment variable map
    * @param optional optional environment variable map
    * @return failure or loaded Typesafe configuration instance with environment
    *   variable overrides applied
    */
  def validateWithEnvironmentOverrides(
    resourceName: String
  )(
    required: Map[String, String],
    optional: Map[String, String]
  ): Try[Config] = {
    Try {
      val requiredObject =
        required.map { case (key, value) => s"env.required.\$key=\$value" }
      val optionalObject =
        optional.map { case (key, value) => s"env.optional.\$key=\$value" }
      val overrideConfig =
        ConfigFactory.parseString(
          (requiredObject ++ optionalObject).mkString("\n")
        )

      overrideConfig
        .withFallback(ConfigFactory.parseResourcesAnySyntax(resourceName))
        .resolve()
    }
  }
}
