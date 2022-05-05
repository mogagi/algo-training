package sort

import scala.collection.mutable

object Sorts extends App {
  val unsorted = List(3, 12, 43, 23, 7, 1, 2, 0)
  val sort: List[Int] => List[Int] = selectionSort
  assert(unsorted.sorted == sort(unsorted))
  assert(List() == sort(List()))
}

/* selection */
def selectionSort(xs: List[Int]): List[Int] = {
  def minimum(xs: List[Int]): List[Int] = (List(xs.head) foldLeft xs.tail) {
    (ys, x) => if x < ys.head then x :: ys else ys.head :: x :: ys.tail
  }

  if xs.isEmpty then List()
  else {
    val ys = minimum(xs)
    if ys.tail.isEmpty then ys else ys.head :: selectionSort(ys.tail)
  }
}

/* insert */

/* quick */
def quickSort(xs: List[Int]): List[Int] = xs match {
  case Nil => Nil
  case List() => List()
  case head :: tail => val (left, right) = tail.partition(_ < head)
    quickSort(left) ::: head :: quickSort(right)
}

def quickSort0(arr: Array[Int]): Array[Int] = if arr.length < 2 then arr else quickSort0(arr.filter(_ < arr.head)) :+ arr.head :++ quickSort0(arr.filter(_ > arr.head))

/* merge */
def mergedSort[T](less: (T, T) => Boolean)(list: List[T]): List[T] = {
  def merged(xList: List[T], yList: List[T]): List[T] = (xList, yList) match {
    case (Nil, _) => yList
    case (_, Nil) => xList
    case (x :: xTail, y :: yTail) => if (less(x, y)) x :: merged(xTail, yList) else y :: merged(xList, yTail)
  }

  val n = list.length / 2
  if n == 0 then list
  else {
    val (x, y) = list splitAt n
    merged(mergedSort(less)(x), mergedSort(less)(y))
  }
}