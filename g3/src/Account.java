import java.util.concurrent.locks.ReentrantLock;

class Account {

    private double balance;
    public final ReentrantLock lock;

    public Account(double initialBalance){
        this.balance = initialBalance;
        this.lock = new ReentrantLock();
    }

    public void take(double amount) throws NotEnoughFunds{
        if(amount > this.balance) throw new NotEnoughFunds(amount);
        this.balance -= amount;
    }

    public void put(double amount){
        this.balance += amount;
    }

    public double getBalance(){
        return this.balance;
    }
}
