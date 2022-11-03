import java.util.concurrent.atomic.AtomicReference

object Task2 {

    lazy val deadlock1: Int = {
        deadlock2
    }

    lazy val deadlock2: Int = {
        deadlock1
    }

    def main(args: Array[String]) = {
        val threads = Array(
            createThread(() => {
                increaseCounter()
                println("Thread 1")
                }),
            createThread(() => {
                increaseCounter()
                println("Thread 2")
                }),
            createThread(() => {
                increaseCounter()
                println("Thread 3")
                }),
            createThread(() => {
                increaseCounter()
                println("Thread 4")
                }),
            createThread(() => {
                increaseCounter()
                println("Thread 5")
                }),
            createThread(() => printCounter()),
        )

        for thread <- threads do
            thread.start()  

        //println(deadlock1)        
    }


    // Given code snippet
    private var counter: Int = 0
    def increaseCounter(): Unit = synchronized {
        println("lock...")
        println(f"increase from $counter%d to ${counter+1}%4d")
        counter += 1
        println("unlock...")
    }

    def printCounter(): Unit = synchronized {
        println(Console.RED + f"Counter is at $counter%d" + Console.WHITE)
    }


    /** (a) Create a function that takes as argument a function and returns a Thread
    *   initialized with the input function. Make sure that the returned thread is not started. (10p)
    */
    trait Runnable {
        def run(): Unit
    }
    trait Callable[V] {
        def call(): V
    }


    def createThread(function: () => Unit): Thread = {
        new Thread {
            override def run = {
                function()
            }
        };
    }
    
}

