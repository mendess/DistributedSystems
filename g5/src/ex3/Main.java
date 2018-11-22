package ex3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args){
        RWLock rwLock = new RWLock();
        Value v = new Value();
        Runnable reader = () -> {
            try{
                Thread.sleep(10);
            }catch(InterruptedException ignored){
            }
            rwLock.readLock();
            try{
                Thread.sleep(2000);
            }catch(InterruptedException ignored){
            }
            System.out.println("\033[32mReading: \033[0m" + v.getV());
            rwLock.readUnLock();
        };
        Runnable writing = () -> {
            rwLock.writeLock();
            try{
                Thread.sleep(100);
            }catch(InterruptedException ignored){
            }
            System.out.println("\033[31mWriting: \033[0m" + v.incrV());
            rwLock.writeUnlock();
        };
        List<Thread> thread = new ArrayList<>();
        IntStream.range(0, 30).forEach(i -> {
                    thread.add(new Thread(reader));
                    thread.add(new Thread(writing));
                }
        );
        thread.forEach(Thread::start);
        thread.forEach(t -> {
            try{
                t.join();
            }catch(InterruptedException ignored){
            }
        });
    }

    private static class Value {

        private int v;

        private int getV(){
            return v;
        }

        private int incrV(){
            return ++ v;
        }
    }
}
