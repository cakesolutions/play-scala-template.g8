ivyLoggingLevel := UpdateLogging.Quiet
scalacOptions in Compile ++= Seq("-feature", "-deprecation")

addSbtPlugin("net.cakesolutions" % "sbt-cake" % "1.0.3")
