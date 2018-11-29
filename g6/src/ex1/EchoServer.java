package ex1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(12345);
        String message;
        Socket s = serverSocket.accept();
        BufferedReader buff = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedWriter buffW = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        while((message = buff.readLine()) != null){
            if (message.equals("quit")){
                break;
            }
            buffW.write(message + "\n");
            buffW.flush();
        }
        s.close();
        serverSocket.close();
    }
}
