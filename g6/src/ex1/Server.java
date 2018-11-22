package ex1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(12345);
        String message = "";
        Socket s = serverSocket.accept();
        while(message == null || !message.equals("quit")){
            System.out.println("Reading....");
            BufferedReader buff = new BufferedReader(new InputStreamReader(s.getInputStream()));
            message = buff.readLine();
            System.out.println(message);
        }
    }
}
