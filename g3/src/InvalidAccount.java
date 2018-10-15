public class InvalidAccount extends Exception {

    public InvalidAccount(int id){
        super("Invalid Account: "+id);
    }

}
