package ex4;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class SumClient {

    public static void main(String[] args) throws IOException{
        Socket s = new Socket("localhost", 12345);
        BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        for(int i = 0; i < 10; i++){
            int n = new Random().nextInt();
            System.out.println("Writing: " + n);
            buff.write("" + n + "\n");
            buff.flush();
        }
        buff.write("quit\n");
        buff.flush();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String result = bufferedReader.readLine();
        System.out.println(result);

        buff.close();
        bufferedReader.close();
        s.close();
    }
}
