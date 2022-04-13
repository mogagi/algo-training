package sort

object Sorts extends App {
  val unsorted = List(3, 12, 43, 23, 7, 1, 2, 0)
  val sort: List[Int] => List[Int] = quickSort0
  assert(unsorted.sorted == sort(unsorted))
  assert(List() == sort(List()))
}

/* bubble */
def bubbleSort(list: List[Int]): List[Int] = list match {
  case List() => List()
  case head :: tail => compute(head, bubbleSort(tail))
}

def compute(data: Int, dataSet: List[Int]): List[Int] = dataSet match {
  case List() => List(data)
  case head :: tail => if (data <= head) data :: dataSet else head :: compute(data, tail)
}

/* selection */
def selectionSort(xs: List[Int]): List[Int] = {
  def minimum(xs: List[Int]): List[Int] = (List(xs.head) /: xs.tail) {
    (ys, x) =>
      if (x < ys.head) (x :: ys)
      else (ys.head :: x :: ys.tail)
  }

  if (xs.isEmpty) List()
  else {
    val ys = minimum(xs)
    if ys.tail.isEmpty then ys
    else ys.head :: selectionSort(ys.tail)
  }
}

/* insert */

/* quick */
def quickSort(list: List[Int]): List[Int] = list match {
  case Nil => Nil
  case List() => List()
  case head :: tail => val (left, right) = tail.partition(_ < head)
    quickSort(left) ::: head :: quickSort(right)
}

def quickSort0(list: List[Int]): List[Int] = if list.length < 2 then list else quickSort0(list.filter(_ < list.head)) ::: list.head :: quickSort0(list.filter(_ > list.head))

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