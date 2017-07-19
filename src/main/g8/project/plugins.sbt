import $organisation_domain$.$organisation$.Dependencies.SbtPlugins._

// An SBT source generator is used to copy centralised library dependencies
// into this build level
sourceGenerators in Compile += Def.task {
  val deps = (baseDirectory in Compile).value / "project" / "Dependencies.scala"
  val projectDeps = (sourceManaged in Compile).value / "Dependencies.scala"

  IO.copyFile(deps, projectDeps)

  Seq(projectDeps)
}.taskValue

ivyLoggingLevel := UpdateLogging.Quiet
scalacOptions in Compile ++= Seq("-feature", "-deprecation")

addSbtPlugin(gatling)
addSbtPlugin(play)
addSbtPlugin(sbtCake)
addSbtPlugin(sbtHeader)
addSbtPlugin(scalafmt)
addSbtPlugin(scalastyle)

