package ex5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiEchoServer {

    public static void main(String[] args) throws IOException{
        ServerSocket s = new ServerSocket(12345);
        for(;;){
            Socket socket = s.accept();
            new Thread(new Echo(socket)).start();
        }

    }


    private static class Echo implements Runnable{
        Socket s;
        private Echo(Socket s){
            this.s = s;
        }

        @Override
        public void run(){
            try{
                PrintWriter pr = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
                BufferedReader buff = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
                String message;
                while((message = buff.readLine()) != null){
                    if(message.equals("quit")) break;
                    pr.println(message);
                    pr.flush();
                }
                pr.close();
                buff.close();
                this.s.close();
            }catch(IOException ignored){
            }
        }
    }

}
