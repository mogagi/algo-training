package pattern

object Decorator {
  trait Operator {
    def add(a: Int, b: Int): Int
    def minus(a: Int, b: Int): Int
  }

  class OperatorImpl extends Operator {
    def add(a: Int, b: Int): Int = a + b
    def minus(a: Int, b: Int): Int = a - b
  }

  trait Log extends Operator {
    abstract override def add(a: Int, b: Int): Int = {
      val rst = super.add(a, b)
      print(s"result = $rst")
      rst
    }
  }

  def main(args: Array[String]): Unit = {
    val operator = new OperatorImpl with Log
    operator.add(1, 2)
    operator.minus(1, 1)
  }
}