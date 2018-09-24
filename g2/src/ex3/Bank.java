package ex3;

public class Bank {

    private int[] accounts;

    public Bank(int n){
        this.accounts = new int[n];
    }

    public int slots(){
        return this.accounts.length;
    }

    public synchronized void transfer(int from, int to, int value){
        this.accounts[from] -= value;
        this.accounts[to] += value;
    }

    public synchronized int query(int i){
        return this.accounts[i];
    }
}
