package ex1;

import utils.Value;

public class Main {

    public static void main(String[] args) throws InterruptedException{
        BoundedBuffer buff = new BoundedBuffer(10, false);
        Thread t1 = new Thread(new Producer(buff, new Value(20)));
        Thread t2 = new Thread(new Consumer(buff, new Value(20)));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

}
