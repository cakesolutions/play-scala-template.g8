package $organisation_domain$.$organisation;format="norm,word"$

import sbt._

/**
  * Plugin dependencies referenced in project auto-plugin and build code
  */
object PluginDependencies {
  val gatling: ModuleID = "io.gatling" % "gatling-sbt" % "2.2.2"
  val play: ModuleID = "com.typesafe.play" % "sbt-plugin" % "2.6.11"
  val sbtCake: ModuleID = "net.cakesolutions" % "sbt-cake" % "1.1.20"
  // 1.14 and onwards require scala 2.12
  val sbtScalafmt: ModuleID = "com.lucidchart" % "sbt-scalafmt" % "1.13"
  val sbtHeader: ModuleID = "de.heikoseeberger" % "sbt-header" % "4.1.0"
  val scalastyle: ModuleID =
    "org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0"
}
