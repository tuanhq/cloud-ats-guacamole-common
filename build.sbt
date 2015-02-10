name := "cloud-ats-guacamole-common"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.glyptodon.guacamole" % "guacamole-common" % "0.9.4",
  "org.glyptodon.guacamole" % "guacamole-common-js" % "0.9.4"
)     

play.Project.playJavaSettings
