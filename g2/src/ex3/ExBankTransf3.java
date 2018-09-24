package ex3;

@SuppressWarnings("Duplicates")
public class ExBankTransf3 {

    public static void main(String[] args){
        int N = 10; // Number of accounts
        Bank b = new Bank(N);
        Mover m = new Mover(b);
        Adder c = new Adder(b);

        new Thread(m).start();
        new Thread(c).start();
    }
}

