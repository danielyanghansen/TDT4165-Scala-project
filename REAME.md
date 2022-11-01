# Delivery 1

## Task 1: Scala Introduction (53p)

To read about the basic Scala syntax, feel free to take a look at the Scala documentation at https://docs.scala-lang.org/tour/basics.htmli or read the ***Learn Concurrent Programming in Scala.pdf*** that was included with this project.
- (a) Generate an array containing the values 1 up to (and including 50 using a `for` loop. (10p)
- (b) Create a function that sums the elements in an array of integers using a `for` loop. (13p)
- (c) Create a function that sums the elements in an array of integers using recursion. (13p)
- (d) Create a function to compute the nth Fibonacci number using recursion without using memoization (or other optimizations). Use `BigInt` instead of `Int`. What is the difference between these two data types? (17p)


## Task 2: Concurrency in Scala (47p)

One of the most important goals in the Scala porject is to learn concurrency programming. Here, it is done
by using threads.
You can read more about how to program threads in Scala at https://twitter.github.io/scala_school/concurrency.html
- (a) Create a function that takes as argument a function and returns a Thread initialized with the input function. Make sure that the returned thread is not started. (10p)
- (b) Given the following code snippet: (10p)

    ```
    private var counter: Int = 0
    def increaseCounter(): Unit = {
        counter += 1
    }
    ```
    

    Create a function that prints the current `counter` variable. Start three threads, two that initialize `increaseCounter` and one that initialize the print function. Run your program a few times and notice the print output. What is this phenomenon called? Give one example of a situation where it can be problematic.
- (c) Change `increaseCounter` so that it is thread-safe. Hint: atomicity. (13p)
- (d) One problem you will often meet in concurrency programming is deadlock. What is deadlock, and what can be done to prevent it? Write in Scala an example of a deadlock using `lazy val`. (14p)