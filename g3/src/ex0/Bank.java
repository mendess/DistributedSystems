package ex0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {

    private class Account {
        private int money = 0;
        private ReentrantLock lock = new ReentrantLock();

        private void take(int amount){
            this.lock.lock();
            this.money -= amount;
            this.lock.unlock();
        }

        private void put(int amount){
            this.lock.lock();
            this.money += amount;
            this.lock.unlock();
        }

    }

    private ReentrantLock lock = new ReentrantLock();

    private Account[] accounts;

    public Bank(int n){
        this.accounts = new Account[n];
        for(int i = 0; i < this.accounts.length; i++){
            this.accounts[i] = new Account();
        }
    }

    public int slots(){
        return this.accounts.length;
    }

    public void transfer(int from, int to, int value){
        this.accounts[Math.min(from, to)].lock.lock();
        this.accounts[Math.max(from, to)].lock.lock();
        this.accounts[from].take(value);
        this.accounts[to].put(value);
        this.accounts[Math.max(from, to)].lock.unlock();
        this.accounts[Math.min(from, to)].lock.unlock();
    }

    public int query(int i){
        this.lock.lock();
        int r = this.accounts[i].money;
        this.lock.unlock();
        return r;
    }

    public int[] getAccounts(){
        int[] r = new int[this.accounts.length];
        for(Account account : accounts){
            account.lock.lock();
        }
        for(int i = 0; i < this.accounts.length; i++){
            Account account = accounts[i];
            r[i] = account.money;
        }
        for(Account account : this.accounts){
            account.lock.unlock();
        }
        return r;
    }
}
