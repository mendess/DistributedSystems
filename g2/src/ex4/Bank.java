package ex4;

public class Bank {

    private class Account {

        private int balance;

        private Account(){
            this.balance = 0;
        }

        private synchronized void take(int amount){
            this.balance -= amount;
        }

        private synchronized void put(int amount){
            this.balance += amount;
        }

        private synchronized int getBalance(){
            return this.balance;
        }

    }
    private Account[] accounts;

    public Bank(int slots){
        this.accounts = new Account[slots];
        for(int i = 0; i < slots; i++) this.accounts[i] = new Account();
    }

    public int slots(){
        return this.accounts.length;
    }

    public int query(int i){
        return this.accounts[i].getBalance();
    }

    public void transfer(int f, int t, int i){
        synchronized(this.accounts[Math.min(f,t)]){
            synchronized(this.accounts[Math.max(f,t)]){
                this.accounts[f].take(i);
                this.accounts[t].put(i);
            }
        }
    }
}
