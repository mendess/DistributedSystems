package ex3;

import java.util.Random;

@SuppressWarnings("Duplicates")
public class Mover implements Runnable {

    Bank b;

    public Mover(Bank b){
        this.b = b;
    }

    public void run(){
        Random rand = new Random();
        int slots = b.slots();
        int f, t, tries;
        for(tries = 0; tries < 1000000; tries++){
            f = rand.nextInt(slots); // get one
            while((t = rand.nextInt(slots)) == f) ; // get a distinct other. Carefull!
            b.transfer(f, t, 10);
        }
    }
}
