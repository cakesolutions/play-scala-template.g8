import de.heikoseeberger.sbtheader.{CommentStyle, FileType, HeaderPlugin}
import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport._
import net.cakesolutions._
import sbt._
import sbt.Keys._
import sbtbuildinfo.BuildInfoPlugin.autoImport._

/**
  * Common project settings.
  */
object ProjectPlugin extends AutoPlugin {

  /** @see [[sbt.AutoPlugin]] */
  override def requires: Plugins =
    CakeBuildInfoPlugin &&
      CakeStandardsPlugin &&
      CakePublishMavenPlugin &&
      ReleaseNotesPlugin &&
      HeaderPlugin

  /** @see [[sbt.AutoPlugin]] */
  override val buildSettings = Seq(
    name := "$name;format="norm,word"$",
    organization := "$organisation_domain$.$organisation;format="norm,word"$",
    buildInfoPackage := "$organisation_domain$.$organisation;format="norm,word"$.$name;format="norm,word"$.build",
    buildInfoKeys := Seq[BuildInfoKey](
      name,
      version,
      scalaVersion,
      sbtVersion
    )
  )

  /** @see [[sbt.AutoPlugin]] */
  override val projectSettings = Seq(
    // This avoids using resources to create a POM file.  We don't need it.
    publishArtifact in makePom := false,
    autoAPIMappings in Global := true,
    // scalastyle:off magic.number
    // FIXME: The following should be tailored to match project requirements
    startYear := Some(2017),
    // scalastyle:on magic.number
    // FIXME: The following should be tailored to match project requirements
    licenses :=
      Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    // FIXME: The following should be tailored to match project requirements
    headerLicense := Some(
      HeaderLicense.Custom(
        """|Copyright: 2017 https://github.com/cakesolutions/play-scala-template.g8/graphs
           |License: http://www.apache.org/licenses/LICENSE-2.0
           |""".stripMargin
      )
    ),
    headerMappings :=
      headerMappings.value ++
        Map(
          FileType("sbt") -> CommentStyle.cppStyleLineComment,
          HeaderFileType.java -> CommentStyle.cppStyleLineComment,
          HeaderFileType.scala -> CommentStyle.cppStyleLineComment
        )
  ) ++ headerSettings(IntegrationTest) ++ addCommandAlias(
    "validate",
    ";reload plugins; sbt:scalafmt::test; scalafmt::test; reload return; " +
      "sbt:scalafmt::test; scalafmt::test; test:scalafmt::test; " +
      "it:scalafmt::test; " +
      "scalastyle; test:scalastyle; it:scalastyle; " +
      "headerCheck; test:headerCheck; it:headerCheck"
  )

}

object ProjectPluginKeys {
  // NOTE: anything in here is automatically visible in build.sbt
  /**
    * Implicitly add extra methods to in scope Projects
    *
    * @param p project that Play application setting should be applied to
    */
  implicit final class PlayOps(val p: Project) extends AnyVal {
    import play.sbt._
    import PlayImport.PlayKeys
    import play.twirl.sbt.Import.TwirlKeys

    /**
      * Enable Play Scala plugin, SBT style layout and a default set of
      * settings.
      *
      * @return project with Play settings and configuration applied
      */
    def enablePlay: Project =
      p.enablePlugins(PlayScala)
        // For consistency we prefer default SBT style layout
        // https://www.playframework.com/documentation/2.5.x/Anatomy
        .disablePlugins(PlayLayoutPlugin)
        .settings(
          // false positives in generated code
          scalacOptions -= "-Ywarn-unused-import",
          PlayKeys.playMonitoredFiles ++=
            (sourceDirectories in (Compile, TwirlKeys.compileTemplates)).value
        )
  }
}
