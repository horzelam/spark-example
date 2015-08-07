name := "minimal-spark-example-scala"

version := "1.0.0"

//scalaVersion := "2.11.7"
scalaVersion := "2.10.5"
//scalaVersion := "2.10.4"

//libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

// to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"


// Repositories
// ----------------

//resolvers += "Local Maven Repository" at "file:///"+Path.userHome+"/.m2/repository"
resolvers += "Akka Maven Repository" at "http://repo.akka.io/releases"
resolvers += "Spray Maven Repository" at "http://repo.spray.io"
resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

//---------------------------------------------------------------------
// JDK used: "1.8.0_25"
// Updated rpc_address in 
//    dse/nodes/node1/resources/cassandra/conf/cassandra.yaml
// with address seen externally: "10.0.2.15" 
// to access it from external machine
//---------------------------------------------------------------------
// about spark-scala versions : see http://stackoverflow.com/questions/26351338/running-spark-scala-example-fails
//val sparkVer = "1.2.0"
//val sparkVer = "1.1.0"
val sparkVer = "1.4.1"


// most of libs had: ,provided
libraryDependencies ++= Seq(
//"com.github.nscala-time" %% "nscala-time" % "1.8.0",
//"org.apache.spark" %% "spark-core" % "1.4.1" %  "compile,test"

"org.apache.spark" %% "spark-core" % sparkVer % "compile,test",
"org.apache.spark" %% "spark-sql" % sparkVer % "compile,test",
//"org.apache.spark" %% "spark-mllib" % sparkVer % "compile,test",
//"org.apache.hadoop" % "hadoop-client" % "2.4.0" % "compile,test" exclude("javax.servlet", "servlet-api"),

// see https://github.com/apache/spark/blob/master/examples/src/main/scala/org/apache/spark/examples/CassandraTest.scala#L4
"org.apache.spark" %% "spark-streaming" % sparkVer % "compile,test",



//C*client + C* server:
 //guavaExclude // ApacheV2
"org.apache.cassandra"    % "cassandra-clientutil"   % "2.1.3" % "compile,test",       
"com.datastax.cassandra"  % "cassandra-driver-core"  % "2.1.5" % "compile,test",
    

// see https://github.com/datastax/spark-cassandra-connector/tree/21bce3fe1a37169762e6a217a56b34d3739c462a
//version we want to use:
//Connector 	Spark 	Cassandra 	Cassandra Java Driver
//1.3 			1.3 	2.1,2.0 	2.1
//1.2 			1.2 	2.1,2.0 	2.1
"com.datastax.spark" %% "spark-cassandra-connector" % "1.2.4"
// on our C: connector is version: 2.10_1.2.1

,"com.typesafe.play" %% "play-json" % "2.2.1"
//,"com.typesafe.play" %% "play-json" % "2.3.4"
//, "play" % "play_2.10" % "2.1.0"

)


