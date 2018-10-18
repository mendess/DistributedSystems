package ex3;

import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args){
        Barrier b = new Barrier(2);
        Runnable r = () -> {
            b.hit();
            System.out.println("lmao" + Thread.currentThread().getName());
        };
        IntStream.range(0, 4).forEach(i -> {
            new Thread(r).start();
            try{
                Thread.sleep(1000);
            }catch(InterruptedException ignored){
            }
        });
    }

}
