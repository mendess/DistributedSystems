package ex3;

import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args){
        Barrier b = new Barrier(5);
        Runnable r = () -> {
            try{
                b.hit();
            }catch(InterruptedException ignored){
            }
            System.out.println(Thread.currentThread().getName() + " passed barrier");
        };
        IntStream.range(0, 10).forEach(i -> {
            new Thread(r).start();
            /*try{
                Thread.sleep(1000);
            }catch(InterruptedException ignored){
            }*/
        });
    }

}
