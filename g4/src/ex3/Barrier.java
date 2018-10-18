package ex3;

public class Barrier {

    private int maxStrength;
    private int remainingStrength;
    private boolean down;

    public Barrier(int strength){
        this.down = false;
        this.maxStrength = this.remainingStrength = strength;
    }

    public synchronized void hit(){
        this.remainingStrength--;
        down = this.remainingStrength <= 0;
        while(!down){
            try{
                System.out.println("Waiting: " + Thread.currentThread().getName());
                this.wait();
            }catch(InterruptedException ignored){
            }
        }
        this.notifyAll();
        this.remainingStrength = this.maxStrength;
    }

}
