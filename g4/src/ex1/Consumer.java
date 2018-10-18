package ex1;

import utils.Value;

public class Consumer implements Runnable{

    private final BoundedBuffer buff;
    private Value consumption;

    public Consumer(BoundedBuffer buff, Value consumption){
        this.buff = buff;
        this.consumption = consumption;
    }

    public int getValue(){
        return this.consumption.getValues();
    }

    @Override
    public void run(){
        while(this.consumption.hasValues()){
            try{
                Thread.sleep(500);
                this.buff.get();
                this.consumption.decrement();
            }catch(InterruptedException ignored){

            }
        }
    }
}
