package sort

import scala.annotation.tailrec

object Fibonacci {

  /**
   * calculate fib
   *
   * @param n  fib times
   * @param f0 result from n - 2 round
   * @param f1 result from n - 1 round
   * @return
   */
  @tailrec def fib(n: Int, f0: Int = 0, f1: Int = 1): Int = if n > 2 then fib(n - 1, f1, f0 + f1) else f1

  val fibArr: LazyList[Int] = 0 #:: fibArr.scan(1)(_ + _)

  def main(args: Array[String]): Unit = {
//    var rectangle: Rectangle = new Rectangle
//    rectangle = new Rectangle
  }
}
