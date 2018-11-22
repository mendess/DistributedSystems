package ex2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException{
        Socket s = new Socket("localhost", 12345);
        String[] lines = {"Ola\n", "cenas\n", "coisas\n"};
        BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        for(String l : lines){
           buff.write(l);
           buff.flush();
        }
        s.close();
    }

}
