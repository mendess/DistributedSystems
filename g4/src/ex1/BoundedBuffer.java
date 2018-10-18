package ex1;

import java.util.stream.IntStream;

public class BoundedBuffer {

    private final int[] buff;
    private final boolean print;
    private int size;
    private int last_print_length;

    public BoundedBuffer(int size, boolean print){
        this.buff = new int[size];
        this.size = 0;
        this.print = print;
    }

    public synchronized void put(int v) throws InterruptedException{
        while(this.size == this.buff.length){
            this.wait();
        }
        this.buff[this.size++] = v;
        printBuffer();
        this.notifyAll();
    }

    public synchronized int get() throws InterruptedException{
        while(this.size == 0){
            this.wait();
        }
        int v = this.buff[--this.size];
        printBuffer();
        this.notifyAll();
        return v;
    }

    private void printBuffer(){
        if(!this.print) return;
        System.out.print(toString());
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
//        IntStream.range(0, this.last_print_length).forEach(i -> sb.append("\b"));
        sb.append("[ ");
        int i;
        for(i = 0; i < this.size && i < this.buff.length - 1; i++){
            sb.append(this.buff[i]).append(", ");
        }
        if(i < this.size) sb.append(buff[i]).append(" ]");
//        this.last_print_length = sb.length() - this.last_print_length;
        return sb.toString();
    }
}
