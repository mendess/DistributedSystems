package ex2;

public class Main {

    public static void main(String[] args) throws InterruptedException{
        Warehouse w = new Warehouse();
        w.supply("a", 1);
        w.supply("b", 2);
        w.supply("c", 3);
        System.out.println(w);
        Thread t1 = new Thread(() -> w.consume(new String[]{"a"}));
        Thread t2 = new Thread(() -> w.consume(new String[]{"b", "c"}));
        t1.start();
        t2.start();
        t1.join();
        t1.join();
        System.out.println(w);
    }
}
