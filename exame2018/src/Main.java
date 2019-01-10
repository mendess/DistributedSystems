import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5000);
        Jogo j = new Foo();
        for(;;) {
            Socket s = server.accept();
            new Thread(new Session(j, s)).start();
        }
    }

    private static class Session implements Runnable {
        private Socket s;
        private Jogo j;

        public Session(Jogo j, Socket s) {
            this.s = s;
            this.j = j;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
                String message;
                while((message = in.readLine()) != null) {
                    out.println(j.inscrever(message).toString());
                }
            } catch (IOException ignored) {
            }
        }
    }
}
