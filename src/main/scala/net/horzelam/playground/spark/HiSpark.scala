package net.horzelam.playground.spark

import org.apache.spark.Logging
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

import com.datastax.spark.connector.cql.CassandraConnector
import com.datastax.spark.connector.toNamedColumnRef
import com.datastax.spark.connector.toSparkContextFunctions

//import scala.collection.GenTraversableOnce

//TODO:
//2. check: https://github.com/datastax/spark-cassandra-connector/blob/21bce3fe1a37169762e6a217a56b34d3739c462a/spark-cassandra-connector-demos/simple-demos/src/main/scala/com/datastax/spark/connector/demo/WordCountDemo.scala

object HiSpark extends App with Logging {
  val conf = new SparkConf(true)
    .set("spark.cassandra.connection.host", "127.0.0.1")
    .setAppName("Simple HiSpark Application")
    //.setMaster("spark://127.0.0.1:7077")
    .setMaster("local")

  //C ports:
  //storage port 7000
  //rpc port 9160
  //native transport port 9042

  // Connect to the Spark cluster:
  lazy val sc = new SparkContext(conf)

  CassandraConnector(conf).withSessionDo { session =>
    session.execute("CREATE KEYSPACE IF NOT EXISTS test WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1 }")
    session.execute("CREATE TABLE IF NOT EXISTS test.key_value (key INT PRIMARY KEY, value VARCHAR)")
    session.execute("TRUNCATE test.key_value")
    session.execute("INSERT INTO test.key_value(key, value) VALUES (1, 'first row')")
    session.execute("INSERT INTO test.key_value(key, value) VALUES (2, 'second row')")
    session.execute("INSERT INTO test.key_value(key, value) VALUES (3, 'third row')")
  }

  val rdd = sc.cassandraTable("test", "key_value")
  println(rdd.count)
  println(rdd.first)

  val select = rdd.select("key", "value")
  rdd.collect().foreach(row => log.info(s"Existing Data: $row"))
  //println(rdd.map(_.getInt("value")).sum)

}