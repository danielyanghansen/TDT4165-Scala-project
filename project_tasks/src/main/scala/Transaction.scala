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

  private def mutateQueue(
      function: Queue[Transaction] => Queue[Transaction]
  ): Unit =
    synchronized {
      this.queue = function(queue)
    }

  // Remove and return the first element from the queue
  def pop: Transaction = {
    val function: Queue[Transaction] => Queue[Transaction] =
      (queue: Queue[Transaction]) => queue.dequeue._2
    mutateQueue((queue: Queue[Transaction]) => {
      queue.dequeue match {
        case (head, tail) =>
          tail
      }
      Queue[Transaction]()
    })
  }
// def pq(q: Queue[List[Any]]): Unit = {
//   q.dequeue match {
//     case (head, tail) =>
//       println(head + "..."); // ... process head element ...
//       if (tail.nonEmpty) pq(tail)
//   }
// }

  // Return whether the queue is empty
  def isEmpty: Boolean = queue.isEmpty

  // Add new element to the back of the queue
  def push(t: Transaction): Unit = mutateQueue(queue => queue.enqueue(t))

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
      from withdraw amount
      to deposit amount
    }

    // TODO - project task 3
    // make the code below thread safe
    if (status == TransactionStatus.PENDING) {
      doTransaction
      Thread.sleep(50) // you might want this to make more room for
      // new transactions to be added to the queue
    }

  }
}
