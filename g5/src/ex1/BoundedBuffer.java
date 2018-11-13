package ex1;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {

    private final int[] buff;
    private int size;
    private ReentrantLock lock;
    private Condition gets;
    private Condition puts;

    public BoundedBuffer(int size){
        this.buff = new int[size];
        this.size = 0;
        this.lock = new ReentrantLock();
        this.gets = this.lock.newCondition();
        this.puts = this.lock.newCondition();
    }

    public void put(int v) throws InterruptedException{
        this.lock.lock();
        while(this.size == this.buff.length){
            this.puts.await();
        }
        this.buff[this.size++] = v;
        //signals();
        this.gets.signal();
        this.lock.unlock();
    }

    private void signals(){
        if(this.size == this.buff.length){
            this.gets.signal();
        } else if (this.size == 0){
            this.puts.signal();
        }else{
            this.puts.signal();
            this.gets.signal();
        }
    }

    public int get() throws InterruptedException{
        this.lock.lock();
        while(this.size == 0){
            this.gets.await();
        }
        int v = this.buff[--this.size];
        //signals();
        this.puts.signal();
        this.lock.unlock();
        return v;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        int i;
        for(i = 0; i < this.size && i < this.buff.length - 1; i++){
            sb.append(this.buff[i]).append(", ");
        }
        if(i < this.size) sb.append(buff[i]).append(" ]");
        return sb.toString();
    }
}
