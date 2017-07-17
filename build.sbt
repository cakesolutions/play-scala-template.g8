// Copyright: 2017 https://github.com/cakesolutions/play-scala-template.g8/graphs
// License: http://www.apache.org/licenses/LICENSE-2.0

// TODO: CO-???: SBT Resolvers
lazy val root = (project in file(".")).
  settings(
    resolvers += Resolver.url("typesafe", url("http://repo.typesafe.com/typesafe/ivy-releases/"))(Resolver.ivyStylePatterns)
  )
