package ex0;

import java.util.Arrays;

@SuppressWarnings("Duplicates")
public class Adder implements Runnable {

    private final Bank b;

    public Adder(Bank b){
        this.b = b;
    }

    public void run(){
        int slots = b.slots();
        int sum, i, tries;
        for(tries = 0; tries < 1000000; tries++){
            int[] accounts = b.getAccounts();
            sum = Arrays.stream(accounts).sum();
            if(sum != 0) System.out.println("Total " + sum);
        }
    }
}