import de.heikoseeberger.sbtheader.FileType
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
      CakePlatformPlugin &&
      CakeStandardsPlugin &&
      CakePublishMavenPlugin &&
      ReleaseNotesPlugin

  /** @see [[sbt.AutoPlugin]] */
  override val buildSettings = Seq(
    name := "$name$",
    organization := "$organisation_domain$.$organisation$",
    buildInfoPackage := "net.cakesolutions.akkarepo.build",
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
          FileType("sbt") -> HeaderCommentStyle.CppStyleLineComment,
          HeaderFileType.java -> HeaderCommentStyle.CppStyleLineComment,
          HeaderFileType.scala -> HeaderCommentStyle.CppStyleLineComment
        )
  )
}
