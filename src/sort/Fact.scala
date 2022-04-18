package sort

import scala.annotation.tailrec

object Fact {
  @tailrec def fact(n: Int, acc: Int): Int = {
    if n <= 1 then acc else fact(n - 1, n * acc)
  }

  fact(1, 10)
}
