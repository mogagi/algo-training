package sort

import scala.annotation.tailrec

object Factorial {
  @tailrec def fact(n: Int, acc: Int): Int = if n <= 1 then acc else fact(n - 1, n * acc)

  def fact(n: Int): Int = (1 to n).product

  fact(1, 10)
}
