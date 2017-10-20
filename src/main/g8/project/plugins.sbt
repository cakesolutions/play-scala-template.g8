import $organisation_domain$.$organisation$.PluginDependencies._

ivyLoggingLevel := UpdateLogging.Quiet
scalacOptions in Compile ++= Seq("-feature", "-deprecation")

addSbtPlugin(gatling)
addSbtPlugin(play)
addSbtPlugin(sbtCake)
addSbtPlugin(sbtHeader)
addSbtPlugin("com.lucidchart" % "sbt-scalafmt" % "1.10")
addSbtPlugin(scalastyle)
