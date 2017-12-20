name := "AkkaLocal"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.17",
  "com.typesafe.akka" %% "akka-remote" % "2.4.17",
  "com.diwo" %% "diwo-kmm-lib" % "1.0.0-SNAPSHOT",
  "com.diwo" %% "diwo-error" %  "1.0.0-SNAPSHOT"
)


//unmanagedJars in Compile += file(Path.userHome + "/Projects/Loven/diwo-kmm/microstrategy-helper/target/scala-2.11/microstrategy-helper_2.11-1.0.0-SNAPSHOT.jar")
//unmanagedJars in Compile += file(Path.userHome + "/Projects/Loven/diwo-common/diwo-kmm-lib/target/scala-2.11/diwo-kmm-lib_2.11-1.0.0-SNAPSHOT.jar")
