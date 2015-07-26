package net.horzelam.playground.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import com.datastax.spark.connector.cql.CassandraConnector
import com.datastax.spark.connector.toNamedColumnRef
import com.datastax.spark.connector.toSparkContextFunctions
import akka.japi.Util

object SimpleApp {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "c:\\winutil\\") 
    
    val conf = new SparkConf(true)
      .set("spark.cassandra.connection.host", "localhost")
      .setAppName("HiSpark Application")
      .setMaster("spark://localhost:7077")

    val test1: scala.collection.GenTraversableOnce[Int] = Some(12)
    println("GenTraversableOnce visible: " + test1)

    //val list1 = new java.util.ArrayList<String>();
    //    Util.immutableIndexedSeq(list1)
    
    // Connect to the Spark cluster:
    val sc = new SparkContext(conf)

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

  }
}