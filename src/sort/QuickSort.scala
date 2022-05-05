package sort

import javax.print.attribute.standard.Media
import scala.collection.mutable.PriorityQueue
import scala.util.Random

object QuickSort {

  def main(args: Array[String]): Unit = {
    val unsorted = for _ <- 1 to 32 yield Random.nextInt(32)
    val sorted = qSort1(unsorted.toList)
    print(sorted)
  }

  def qSort0(list: List[Int]): List[Int] = if list.length < 2 then list else qSort0(list.filter(_ < list.head)) ::: list.head :: qSort0(list.filter(_ > list.head))

  def qSort1(list: List[Int]): List[Int] = {
    if list.length < 2 then list
    else {
      val median = Median().add(list.head).add(list(list.length / 2)).add(list.last)
      val pivot = median.find()
      qSort1(list.filter(_ < pivot)) ::: pivot :: qSort1(list.filter(_ > pivot))
    }
  }
}

class Median() {
  val hi = PriorityQueue.empty[Int].reverse
  val lo = PriorityQueue.empty[Int]

  def add(num: Int): Median = {
    lo.enqueue(num)
    val highestLow = lo.dequeue()
    hi.enqueue(highestLow)
    if hi.size > lo.size then {
      val lowestHigh = hi.dequeue()
      lo.enqueue(lowestHigh)
    }
    this
  }

  def find(): Int = {
    if lo.size > hi.size then lo.head else hi.head//(hi.head + lo.head) / 2.0
  }
}
