name := "gentelella-play"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.pac4j" % "play-pac4j" % "2.4.0",
  "org.pac4j" % "pac4j-http" % "1.9.0",
  "com.typesafe.play" % "play-cache_2.11" % "2.5.4",
  "commons-io" % "commons-io" % "2.4",
  "com.google.code.gson" % "gson" % "2.7",
  evolutions,
  javaJdbc,
  cache,
  javaWs
)

resolvers ++= Seq(Resolver.mavenLocal, "Sonatype snapshots repository" at "https://oss.sonatype.org/content/repositories/snapshots/")

routesGenerator := InjectedRoutesGenerator
