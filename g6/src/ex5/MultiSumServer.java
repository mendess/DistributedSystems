package ex5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiSumServer {

    public static void main(String[] args) throws IOException{
        ServerSocket s = new ServerSocket(12345);
        for(;;){
            Socket socket = s.accept();
            new Thread(new Sum(socket)).start();
        }
    }

    private static class Sum implements Runnable {
        Socket s;
        private Sum(Socket s){
            this.s = s;
        }

        @Override
        public void run(){
            try{
                BufferedReader buff = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
                String message;
                int sum = 0;
                while((message = buff.readLine()) != null){
                    try{
                        int n = Integer.parseInt(message);
                        sum += n;
                    }catch(NumberFormatException e){
                        break;
                    }
                }
                PrintWriter pr = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
                pr.println("Sum: " + sum);
                pr.close();
                buff.close();
                this.s.close();
            }catch(IOException ignored){
            }
        }
    }

}
