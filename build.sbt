name := "simple-chat-app"
organization := "jcoster"

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(dockerSettings)

scalaVersion := "2.11.8"

PlayKeys.playDefaultPort := 8080

libraryDependencies ++= Seq(
  guice,
  "com.typesafe.play" %% "play-json-joda" % "2.7.0",
  "org.xerial" % "sqlite-jdbc" % "3.21.0",
  "com.typesafe.play" %% "play-slick" % "3.0.0",
  "com.typesafe.slick" %% "slick" % "3.2.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % Test
)

lazy val dockerSettings = Seq(
  dockerExposedPorts := Seq(8080),
  dockerBaseImage := "adoptopenjdk/openjdk8:latest",
  dockerEntrypoint := Seq("sh", "-c", "bin/simple-chat-app")
)