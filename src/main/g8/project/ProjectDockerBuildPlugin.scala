import com.typesafe.sbt.packager.archetypes.AshScriptPlugin
import net.cakesolutions._
import sbt._

/**
  * Common project Docker build settings.
  */
object ProjectDockerBuildPlugin extends AutoPlugin {

  /** @see [[sbt.AutoPlugin]] */
  override def requires: Plugins =
    ProjectPlugin &&
      CakeJavaAppPlugin &&
      AshScriptPlugin &&
      CakeDockerPlugin &&
      CakeDockerComposePlugin

  /** @see [[sbt.AutoPlugin]] */
  override val buildSettings = Seq(
    CakeDockerComposePluginKeys.dockerComposeFiles :=
      Seq(
        file("docker/docker-compose.yml"),
        file("docker/docker-compose-testing.yml")
      )
  )
}
