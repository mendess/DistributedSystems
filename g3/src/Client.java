import java.util.Random;

public class Client implements Runnable{

    private int id;
    private Bank bank;

    private String color(){
        return new String[]{"\033[31m", "\033[32m"}[id % 2 == 0 ? 0 : 1];
    }

    private String clearColor(){
        return "\033[0m";
    }

    Client(int id, Bank bank){
        this.id = id;
        this.bank = bank;
    }

    @Override
    public void run(){
        bank.createAccount(10);
        for(int i = 0; i < 100; i++){
            Random r = new Random();
            double amount = 1;
            boolean give = r.nextBoolean();
            int from = give ? 0 : 1;
            int to = give ? 1 : 0;
            System.out.println(color() + "Moving " + amount + " from: " + from + " to: " + to + clearColor());
            try{
                bank.transfer(from, to, amount);
            }catch(InvalidAccount | NotEnoughFunds e){
                System.out.println(color() + e.getMessage() + clearColor());
            }
            System.out.println(color() + "Moved " + amount + " from: " + from + " to: " + to + clearColor());
        }
    }
}
