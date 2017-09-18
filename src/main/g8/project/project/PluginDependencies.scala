package $organisation_domain$.$organisation$

import sbt._

/**
  * Plugin dependencies referenced in project auto-plugin and build code
  */
object PluginDependencies {

  val gatling: ModuleID = "io.gatling" % "gatling-sbt" % "2.2.1"
  val play: ModuleID = "com.typesafe.play" % "sbt-plugin" % "2.6.1"
  val sbtCake: ModuleID = "net.cakesolutions" % "sbt-cake" % "1.1.14"
  val scalafmt: ModuleID = "com.lucidchart" % "sbt-scalafmt" % "1.10"
  val sbtHeader: ModuleID = "de.heikoseeberger" % "sbt-header" % "2.0.0"
  val scalastyle: ModuleID =
    "org.scalastyle" %% "scalastyle-sbt-plugin" % "0.9.0"
}
