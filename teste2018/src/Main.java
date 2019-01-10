import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5000);
        Controlador c = new FU();

        new Thread(new ShuttleSession(c)).start();
        for(;;){
            Socket client = server.accept();
            new Thread(new CltSession(c, client)).start();
        }
    }
}

