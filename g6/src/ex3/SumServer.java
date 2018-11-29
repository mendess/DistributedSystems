package ex3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SumServer {

    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(12345);
        Socket s = serverSocket.accept();
        BufferedReader buffR = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedWriter buffW = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        String message;
        int sum = 0;
        while((message = buffR.readLine()) != null){
            if(message.equals("quit")) break;
            int num;
            try{
                num = Integer.parseInt(message);
            }catch(NumberFormatException e){
                buffW.write("Error " + e.getMessage() + "\n");
                buffW.flush();
                break;
            }
            sum += num;
        }
        buffW.write("Sum: " + sum + "\n");
        buffW.flush();
        buffW.close();
        buffR.close();
        s.close();
        serverSocket.close();
    }

}
