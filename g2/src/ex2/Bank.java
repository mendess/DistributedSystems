package ex2;

public class Bank {

    private int[] accounts;

    public Bank(int n){
        this.accounts = new int[n];
    }

    public int slots(){
        return this.accounts.length;
    }

    public void take(int account, int amount){
        this.accounts[account] -= amount;
    }

    public void put(int account, int amount ){
        this.accounts[account] += amount;
    }

    public int query(int i){
        return this.accounts[i];
    }
}
