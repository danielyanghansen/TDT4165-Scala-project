# Delivery 1

## Task 1: Scala Introduction (53p)

- **(a) Generate an array containing the values 1 up to (and including) 50 using a `for` loop. (10p)**

  ```scala
  def main(args: Array[String]) = {
    var array: Array[Int] = Array()
    for i <- 1 to 50 do array :+= i

    println(array.mkString(", "))
  }
  ```

- **(b) Create a function that sums the elements in an array of integers using a `for` loop. (13p)**

  ```scala
  def sum(array: Array[Int]) = {
      var sum: Int = 0
      for i <- array do sum += i
      sum
  }
  ```

- **(c) Create a function that sums the elements in an array of integers using recursion. (13p)**

  ```scala
  def recursiveSum(array: Array[Int]): Int = {
      if array.length == 0 then
        0
      else
        array(0) + recursiveSum(array.drop(1))
  }
  ```

- **(d) Create a function to compute the nth Fibonacci number using recursion without using memoization (or other optimizations). Use `BigInt` instead of `Int`. What is the difference between these two data types? (17p)**

  ```scala
   def fibonacci(n: Int): BigInt = {
    if n <= 1 then
      n
    else
      fibonacci(n-2) + fibonacci(n-1)
  }
  ```

  - A `BigInt` is an arbitrarily large integer, as it uses a list of normal integers internally. An `Int` is a 32-bit integer, which means it can only hold values up to 2^32 - 1.

## Task 2: Concurrency in Scala (47p)

- **(a) Create a function that takes as argument a function and returns a Thread initialized with the input function. Make sure that the returned thread is not started. (10p)**

  ```scala
  def createThread(function: () => Unit): Thread = {
        new Thread {
            override def run = {
                function()
            }
        };
    }
  ```

  This also means that any set of functions can be bound to a thread using lambda syntax, e.g:

  ```scala
  createThread(() => {
                exampleUnitToUnitFunc()
                otherFunc(param)
                //...
                anotherFunc()
  })
  ```

  ... as long as the last function returns a `Unit`

- **(b) Given the following code snippet: (10p)**

  ```
  private var counter: Int = 0
  def increaseCounter(): Unit = {
      counter += 1
  }
  ```

  Create a function that prints the current `counter` variable. Start three threads, two that initialize `increaseCounter` and one that initialize the print function. Run your program a few times and notice the print output. What is this phenomenon called? Give one example of a situation where it can be problematic.

  - Output:

  ```
  lock...
  increase from 0 to    1
  unlock...
  Thread 1
  Counter is at 1
  lock...
  increase from 1 to    2
  unlock...
  Thread 5
  lock...
  increase from 2 to    3
  unlock...
  Thread 4
  lock...
  increase from 3 to    4
  unlock...
  Thread 3
  lock...
  increase from 4 to    5
  unlock...
  Thread 2

  ```

  This phenomenon is called `race conditions`. It occurs when two or more threads try to change (or access) the same variable, with the assumption that each thread's action is dependent on another's. It can be problematic while performing mathematical operations in which certain steps are dependent each other.
  A race condition is not really problematic if all the operations within the set of "race-able" operations are memory read-only.

- **(c) Change `increaseCounter` so that it is thread-safe. Hint: atomicity. (13p)**

  ```scala
  def increaseCounter(): Unit = synchronized {
        println("lock...")
        println(f"increase from $counter%d to ${counter+1}%4d")
        counter += 1
        println("unlock...")
    }
  ```

- **(d) One problem you will often meet in concurrency programming is deadlock. What is deadlock, and what can be done to prevent it? Write in Scala an example of a deadlock using `lazy val`. (14p)**

  - Deadlock occurs when two or more threads are waiting for each other to finish, but none of them will finish until the other one does. There are a couple of ways to prevent this.
    - For lazy evaluation: avoid circular dependencies
    - For different threads: if the threads are dependent on each other, design this dependency to be "staggered" (often based on thread ID), and make sure that locking of resources is done in an order that prevents the threads from waiting for each other to unlock resources.
    - In general: Eliminiate the 4 necessary conditions for deadlocks:
      - `Mutual exclusion` - Two or more processes trying to access the same unshareable
      - `Hold-and-wait` - A process holding (and blocking) one resource while waiting for access to another resource which is blocked by another process
      - `No preemption` -
      - `Circular Wait` -

  ```scala
  lazy val deadlock1: Int = {
      deadlock2
  }

  lazy val deadlock2: Int = {
      deadlock1
  }

  def main(args: Array[String]) = {
      deadlock1
  }
  ```
