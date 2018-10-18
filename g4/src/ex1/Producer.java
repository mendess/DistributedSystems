package ex1;

import utils.Value;

import java.util.Random;

public class Producer implements Runnable {

    private final BoundedBuffer buff;
    private Value production;

    public Producer(BoundedBuffer buff, Value production){
        this.buff = buff;
        this.production = production;
    }

    public int getValue(){
        return this.production.getValues();
    }

    @Override
    public void run(){
        Random r = new Random();
        while(this.production.hasValues()){
            try{
                Thread.sleep(1000);
                this.buff.put(r.nextInt(10));
                this.production.decrement();
            }catch(InterruptedException ignored){

            }
        }
    }
}
