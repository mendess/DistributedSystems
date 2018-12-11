package ex4;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class SumClient {

    public static void main(String[] args) throws IOException{
        Socket s = new Socket("localhost", 12345);
        BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        for(int i = 0; i < 10; i++){
            int n = new Random().nextInt();
            buff.write("" + n + "\n");
            buff.flush();
            System.out.println("$ " + n);
            System.out.println(bufferedReader.readLine());
        }
        buff.write("quit\n");
        buff.flush();

        System.out.println(bufferedReader.readLine());

        buff.close();
        bufferedReader.close();
        s.close();
    }
}
