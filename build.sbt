organization := "BNB"

name := "MongoTest"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.8"

scalacOptions := Seq("-feature", "-language:postfixOps", "-language:reflectiveCalls")


libraryDependencies ++= Seq(
  "org.reactivemongo" %% "play2-reactivemongo" % "0.18.4-play27"
)


libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.2" % "test"

