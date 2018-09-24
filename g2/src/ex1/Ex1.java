package ex1;

import java.util.Scanner;
import java.util.Stack;

@SuppressWarnings("Duplicates")
public class Ex1 {
    private static final Counter counter = new Counter();

    private static class Counter {
        private int i;

        private synchronized void increment(){
            i++;
        }
    }

    private static class MethodAccess implements Runnable{

        private final int to;

        private MethodAccess(int to){
            this.to = to;
        }

        @Override
        public void run(){
            for(int i = 0; i < this.to; i++){
                Ex1.counter.increment();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Scanner s = new Scanner(System.in);
        System.out.print("#threads? ");
        int n = s.nextInt();
        System.out.print("count to? ");
        int i = s.nextInt();
        System.out.println("Total increments expected: " + i*n);
        Stack<Thread> threadList = new Stack<>();
        // Counting with method access
        for(int j = 0; j < n; j++){
            threadList.push(new Thread(new MethodAccess(i)));
            threadList.peek().start();
        }
        while(!threadList.empty())
            threadList.pop().join();
        System.out.println("Method access: " + counter.i);
    }
}
