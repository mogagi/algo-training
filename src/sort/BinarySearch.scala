package sort

object BinarySearch extends App {

  assert(binarySearch(Array(1, 2, 3, 5, 7, 9), 7)() == 4)
  assert(binarySearch(Array(1, 2, 3, 5, 7, 9), 4)() < 0)

  def binarySearch(arr: Array[Int], target: Int)(low: Int = 0, high: Int = arr.length - 1): Int = {
    if (low > high) return -1
    val mid = low + (high - low) / 2
    if arr(mid) == target then mid
    else if arr(mid) > target then binarySearch(arr, target)(low, mid - 1)
    else binarySearch(arr, target)(mid + 1, high)
  }
}