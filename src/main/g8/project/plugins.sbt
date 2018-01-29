import $organisation_domain$.$organisation;format="norm,word"$.PluginDependencies._

ivyLoggingLevel := UpdateLogging.Quiet
scalacOptions in Compile ++= Seq("-feature", "-deprecation")

addSbtPlugin(gatling)
addSbtPlugin(play)
addSbtPlugin(sbtCake)
addSbtPlugin(sbtHeader)
addSbtPlugin(sbtScalafmt)
addSbtPlugin(scalastyle)
