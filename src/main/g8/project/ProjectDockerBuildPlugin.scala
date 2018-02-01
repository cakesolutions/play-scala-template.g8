import com.typesafe.sbt.packager.Keys.dockerRepository
import com.typesafe.sbt.packager.archetypes.scripts.AshScriptPlugin
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
    CakeDockerComposeKeys.dockerComposeFiles :=
      Seq(
        file("docker/docker-compose.yml"),
        file("docker/docker-compose-testing.yml")
      )
  )

  /** @see [[sbt.AutoPlugin]] */
  override val projectSettings = Seq(
    dockerRepository := Some("$organisation;format="norm"$")
  )
}
