package sort

object QuickSort {
  val B = 4

  def main(args: Array[String]): Unit = {
    val unsorted = Array(9, 8, 7, 5, 6, 1, 4, 3, 2)
    quickSort(unsorted, 0, unsorted.length - 1)
    print(unsorted.mkString("Array(", ", ", ")"))
  }

  def quickSort(arr: Array[Int]): Array[Int] = if arr.length < 2 then arr else quickSort(arr.filter(_ < arr.head)) :+ arr.head :++ quickSort(arr.filter(_ > arr.head))

  def quickSort(arr: Array[Int], l: Int, r: Int): Unit = if l < r then
    val pivot = choosePivot(arr, l, r)
    val cut = blockPartition(arr, l, r, pivot)
    quickSort(arr, l, cut - 1)
    quickSort(arr, cut, r)

  def choosePivot(arr: Array[Int], from: Int, to: Int): Int = arr.slice(from, to).head

  def partition(arr: Array[Int], from: Int, to: Int, pivot: Int): Int = {
    var (l, r) = (from, to)
    while l < r do
      while arr(l) < pivot do l += 1
      while arr(r) > pivot do r -= 1
      if l < r then
        swap(arr, l, r)
        l += 1
        r -= 1
    l
  }

  def blockPartition(arr: Array[Int], from: Int, to: Int, pivot: Int): Int = {
    var (l, r) = (from, to)
    val offsetsL, offsetsR = new Array[Int](B)
    var startL, startR, numL, numR = 0
    while r - l + 1 > 2 * B do
      if numL == 0 then
        startL = 0
        for i <- 0 until B do
          offsetsL(numL) = i
          numL += (if pivot >= arr(l + i) then 1 else 0)
      if numR == 0 then
        startR = 0
        for i <- 0 until B do
          offsetsR(numR) = i
          numR += (if pivot <= arr(l + i) then 1 else 0)
      val num = numL min numR
      for j <- 0 until num do swap(arr, l + offsetsL(startL + j), r - offsetsR(startR + j))
      numL -= num
      numR -= num
      if numL == 0 then l += B
      if numR == 0 then r -= B
    partition(arr, l, r, pivot)
  }

  def swap(arr: Array[Int], index0: Int, index1: Int): Unit = {
    val tmp = arr(index0)
    arr(index0) = arr(index1)
    arr(index1) = tmp
  }
}
