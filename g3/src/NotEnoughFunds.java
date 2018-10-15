public class NotEnoughFunds extends Exception {

    public NotEnoughFunds(double amount){
        super("Not Enough Funds:" + amount);
    }
}
