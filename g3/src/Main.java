import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException{
        Bank bank = new Bank();
        Client c1 = new Client(0, bank);
        Client c2 = new Client(1, bank);
        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);
        t1.start();
        t2.start();
        System.out.println("cenas");
        for(int i = 0; i < 100; i++){
            bank.totalBalance(new int[]{0, 1});
        }
        t1.join();
        t2.join();
        System.out.println(Arrays.toString(bank.totalBalance(new int[]{0, 1})));


    }

    public int suma(List<Integer> l){
        return l.stream().mapToInt(aL -> aL).sum();
    }
}
