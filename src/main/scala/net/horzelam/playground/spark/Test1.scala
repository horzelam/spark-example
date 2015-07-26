package net.horzelam.playground.spark

//import akka.japi.Util

/**
 * @author morele
 */
object Test1 {
  def main(args: Array[String]): Unit = {
    println("start")
    val l1 = List(1, 2, 3, 4, 5).init
    val l2 = l1.flatMap { x => if (x % 2 == 0) Some(x) else None }
     
    val test1 :scala.collection.GenTraversableOnce[Int] = Some(12)
    println("test1: " + test1)
  
    l2.foreach { x => println("a:" + x) }
    
    //val lx = null
    //Util.immutableIndexedSeq(lx)  

    println("end")
  }
}