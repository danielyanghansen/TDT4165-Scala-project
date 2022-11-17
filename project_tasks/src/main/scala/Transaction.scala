import exceptions._
import scala.collection.immutable.Queue

object TransactionStatus extends Enumeration {
  val SUCCESS, PENDING, FAILED = Value
}

class TransactionQueue {

  // TODO
  // project task 1.1
  // Add datastructure to contain the transactions
  private var queue: Queue[Transaction] = Queue()

  private def mutateQueue[ReturnType](
      function: Queue[Transaction] => (
          ReturnType,
          Queue[Transaction]
      )
  ): ReturnType = {
    synchronized {
      val result = function(queue)
      this.queue = result._2
      result._1
    }
  }

  // Remove and return the first element from the queue
  def pop: Transaction =
    mutateQueue[Transaction]((queue: Queue[Transaction]) => queue.dequeue)

  // Return whether the queue is empty
  def isEmpty: Boolean = queue.isEmpty

  // Add new element to the back of the queue
  def push(t: Transaction): Unit =
    mutateQueue[Unit](queue => (Unit, queue.enqueue(t)))

  // Return the first element from the queue without removing it
  def peek: Transaction = queue.head

  // Return an iterator to allow you to iterate over the queue
  def iterator: Iterator[Transaction] = queue.iterator
}

class Transaction(
    val transactionsQueue: TransactionQueue,
    val processedTransactions: TransactionQueue,
    val from: Account,
    val to: Account,
    val amount: Double,
    val allowedAttemps: Int
) extends Runnable {

  var status: TransactionStatus.Value = TransactionStatus.PENDING
  var attempt = 0

  override def run: Unit = {

    def doTransaction() = {
      // TODO - project task 3
      // Extend this method to satisfy requirements.
      attempt += 1
      val withdrawResult = from withdraw(amount)
      withdrawResult match {
        case Left(_) => {
          val depositResult = to deposit(amount)
          depositResult match {
            case Left(_) => {
              status = TransactionStatus.SUCCESS
            }
            case Right(string) => {
              println(string)
              from deposit(amount)
              if (attempt < allowedAttemps) {
                status = TransactionStatus.PENDING
              } else {
                status = TransactionStatus.FAILED
              }
            }
          }
        }
        case Right(string) => {
          //println(string) //Commented out so the test output is cleaner
          if (attempt < allowedAttemps) {
            status = TransactionStatus.PENDING
          } else {
            status = TransactionStatus.FAILED
          }
          
        }
      }
    }

    // TODO - project task 3
    // make the code below thread safe
    synchronized {
    if (status == TransactionStatus.PENDING) {
      if (attempt < allowedAttemps) {
        doTransaction()
        Thread.sleep(50)

      // you might want this to make more room for
      // new transactions to be added to the queue
      } else {
        status = TransactionStatus.FAILED
        print("Too many attempts")
      }
    
    }}
  }
}

