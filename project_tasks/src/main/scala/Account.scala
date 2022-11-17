import exceptions._

class Account(val bank: Bank, initialBalance: Double) {

  class Balance(var amount: Double) {}

  val balance = new Balance(initialBalance)

  // TODO
  // for project task 1.2: implement functions
  // for project task 1.3: change return type and update function bodies
  def withdraw(
      amount: Double
  ): Either[Unit, String] =
    synchronized {
      if (amount < 0) {
        Right("Invalid amount")
      } else if (amount > balance.amount) {
        Right("Insufficient funds")
      } else {
        balance.amount -= amount
        Left(Unit)
      }
    }

  def deposit(
    amount: Double
  ): Either[Unit, String] =
    if (amount < 0) {
      Right("Invalid amount")
    } else {
      synchronized {
        balance.amount += amount
      }  
      Left(Unit)
    }
    
  def getBalanceAmount: Double = 
    synchronized {
      balance.amount
    }

  def transferTo(account: Account, amount: Double) = {
    bank addTransactionToQueue (this, account, amount)
  }

}
