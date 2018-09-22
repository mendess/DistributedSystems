import java.util.Scanner;
import java.util.Stack;

public class Ex1 implements Runnable{

    private final int to;

    private Ex1(int to){
        this.to = to;
    }

    @Override
    public void run(){
        for(int i = 0; i < this.to; i++)
            System.out.println(i);
    }

    public static void main(String[] args) throws InterruptedException{
        Scanner s = new Scanner(System.in);
        System.out.print("#threads? ");
        int n = s.nextInt();
        System.out.print("count to? ");
        int i = s.nextInt();
        Stack<Thread> threadList = new Stack<>();
        for(int j = 0; j < n; j++){
            threadList.push(new Thread(new Ex1(i)));
            threadList.peek().start();
        }
        while(!threadList.empty())
            threadList.pop().join();
    }
}
