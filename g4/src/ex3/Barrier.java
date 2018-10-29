package ex3;

public class Barrier {

    private int maxStrength;
    private int remainingStrength;
    private int round;

    public Barrier(int strength){
        this.round = 0;
        this.maxStrength = this.remainingStrength = strength;
    }

    public synchronized void hit() throws InterruptedException{
        this.remainingStrength--;
        int myRound = this.round;
        if(this.remainingStrength <= 0){
            this.remainingStrength = this.maxStrength;
            this.round++;
            System.out.println(Thread.currentThread().getName() + " notifying");
            this.notifyAll();
        }
        while(this.round == myRound){
                System.out.println(Thread.currentThread().getName() + " waiting");
                this.wait();
        }
    }
}
