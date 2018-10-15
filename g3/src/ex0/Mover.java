package ex0;

import java.util.Random;

@SuppressWarnings("Duplicates")
public class Mover implements Runnable {

    private final Bank b;

    public Mover(Bank b){
        this.b = b;
    }

    public void run(){
        Random rand = new Random();
        int slots = b.slots();
        int f, t, tries;
        for(tries = 0; tries < 1000000; tries++){
            f = rand.nextInt(slots); // get one
            do{
                t = rand.nextInt(slots);
            }while(t == f);
            b.transfer(f, t, 10);
        }
    }
}
