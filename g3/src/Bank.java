import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {

    private Map<Integer, Account> accounts;
    private ReentrantLock lock;

    public Bank(){
        this.accounts = new HashMap<>();
        this.lock = new ReentrantLock();
    }

    public int createAccount(double initialBalance){
        this.lock.lock();
        int id = this.accounts.keySet().stream().max(Integer::compareTo).orElse(-1) + 1;
        this.accounts.put(id, new Account(initialBalance));
        this.lock.unlock();
        return id;
    }

    public double closeAccount(int id) throws InvalidAccount{
        this.lock.lock();
        if(accounts.containsKey(id)){
            accounts.get(id).lock.lock();
            Account a = accounts.remove(id);
            a.lock.unlock();
            this.lock.unlock();
            return a.getBalance();
        }else{
            this.lock.unlock();
            throw new InvalidAccount(id);
        }
    }

    public void transfer(int from, int to, double amount) throws InvalidAccount, NotEnoughFunds{
        int min = Math.min(from, to);
        int max = Math.max(from, to);
        this.lock.lock();
        Account a1 = this.accounts.get(min);
        Account a2 = this.accounts.get(max);
        this.lock.unlock();
        if(a1 == null) throw new InvalidAccount(min);
        if(a2 == null) throw new InvalidAccount(max);
        a1.lock.lock();
        a2.lock.lock();
        try{
            a1.take(amount);
            a2.put(amount);
        }finally{
            a2.lock.unlock();
            a1.lock.unlock();
        }
    }

    public double[] totalBalance(int[] accounts){
        double[] r = new double[accounts.length];
        List<Account> accs = new ArrayList<>();
        this.lock.lock();
        for(Integer key : accounts){
            Account a = this.accounts.get(key);
            if(a == null) continue;
            a.lock.lock();
            accs.add(a);
        }
        for(int i = 0; i < accs.size(); i++){
            Account a = accs.get(i);
            r[i] = a.getBalance();
            a.lock.unlock();
        }
        this.lock.unlock();
        return r;
    }

}
