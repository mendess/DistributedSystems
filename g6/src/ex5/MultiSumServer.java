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
                PrintWriter pr = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
                String message;
                int sum = 0;
                int count = 0;
                while((message = buff.readLine()) != null){
                    try{
                        int n = Integer.parseInt(message);
                        sum += n;
                        count++;
                        pr.println("Sum: " + sum);
                        pr.flush();
                    }catch(NumberFormatException e){
                        break;
                    }
                }
                pr.println("Avg: " + ((double) sum) / count);
                pr.close();
                buff.close();
                this.s.close();
            }catch(IOException ignored){
            }
        }
    }

}
