val middle = project
  .enablePlugins(JavaServerAppPackaging, DockerPlugin)
  .settings(
    mainClass in Compile := Some(s"\${organization.value}.\${name.value}.Server"),
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play" % versions.play // for Play's ActorFlow.actorRef function
    ) ++ deps.AkkaPersistence ++ deps.AkkaCluster ++ deps.KafkaClient ++ deps.AkkaHttp
  )
// TODO: add integration test example

val app = (project in file("."))
  .enablePlugins(PlayScala, BuildInfoPlugin, DockerPlugin)
  .settings(
    libraryDependencies ++= deps.AngularBootstrap,
    // false positives in generated code
    scalacOptions -= "-Ywarn-unused-import",
    pipelineStages := Seq(digest, gzip)
  )
