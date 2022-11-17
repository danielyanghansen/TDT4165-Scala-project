object Main extends App {
  override def main(args: Array[String]) = {
    var bank = new Bank
    var transactionQueue = new TransactionQueue
    transactionQueue.push(
      new Transaction(
        transactionQueue,
        transactionQueue,
        new Account(bank, 1000),
        new Account(bank, 1000),
        100,
        1
      )
    )
    println(transactionQueue.peek)
    println(transactionQueue.isEmpty)
    println(transactionQueue.pop)
    println(transactionQueue.isEmpty)
  }

  def thread(body: => Unit): Thread = {
    val t = new Thread {
      override def run() = body
    }
    t.start
    t
  }

}
