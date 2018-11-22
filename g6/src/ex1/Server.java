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
        BufferedReader buff = new BufferedReader(new InputStreamReader(s.getInputStream()));
        while((message = buff.readLine()) != null){
            System.out.println("Reading....");
            if (message.equals("quit")){
                break;
            }
            System.out.println(message);
        }
        s.close();
        serverSocket.close();
    }
}
