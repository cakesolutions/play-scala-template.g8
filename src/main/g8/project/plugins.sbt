ivyLoggingLevel := UpdateLogging.Quiet
scalacOptions in Compile ++= Seq("-feature", "-deprecation")

addSbtPlugin("com.dwijnand"      % "sbt-dynver"          % "1.1.1")
addSbtPlugin("com.eed3si9n"      % "sbt-buildinfo"       % "0.6.1")
addSbtPlugin("com.geirsson"      % "sbt-scalafmt"        % "0.6.6")
addSbtPlugin("com.jsuereth"      % "sbt-pgp"             % "1.0.0")
addSbtPlugin("com.typesafe.play" % "sbt-plugin"          % "2.5.13")
addSbtPlugin("com.typesafe.sbt"  % "sbt-digest"          % "1.1.1")
addSbtPlugin("com.typesafe.sbt"  % "sbt-git"             % "0.8.5")
addSbtPlugin("com.typesafe.sbt"  % "sbt-gzip"            % "1.0.0")
addSbtPlugin("com.typesafe.sbt"  % "sbt-native-packager" % "1.1.5")
addSbtPlugin("io.get-coursier"   % "sbt-coursier"        % "1.0.0-M15-5")
