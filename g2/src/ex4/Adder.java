package ex4;

@SuppressWarnings("Duplicates")
public class Adder implements Runnable {

    private final Bank b;

    public Adder(Bank b){
        this.b = b;
    }

    public void run(){
        int slots = b.slots();
        int sum, i, tries;
        for(tries = 0; tries < 1000000; tries++){
            sum = 0;
            for(i = 0; i < slots; i++) sum += b.query(i);
            if(sum != 0) System.out.println("Total " + sum);
        }
    }
}