
object Task1 {
  def main(args: Array[String]) = {
    var array: Array[Int] = Array()
    for i <- 1 to 50 do array :+= i
    println(array.mkString(", "))

    println(sum(array))
    println(recursiveSum(array))

    println(fibonacci(42))
  }


  def sum(array: Array[Int]) = {
      var sum: Int = 0
      for i <- array do sum += i
      sum
  }


  def recursiveSum(array: Array[Int]): Int = {
      if array.length == 0 then
        0
      else 
        array(0) + recursiveSum(array.drop(1))
  }

  def fibonacci(n: Int): BigInt = {
    if n <= 1 then
      n
    else
      fibonacci(n-2) + fibonacci(n-1)
  }
}

