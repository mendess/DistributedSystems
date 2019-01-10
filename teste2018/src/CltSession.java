import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class CltSession implements Runnable {
    private Controlador c;
    private Socket s;

    public CltSession(Controlador c, Socket s) {
        this.c = c;
        this.s = s;
    }

    public void run() {
        try{

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
            String message;
            while((message = in.readLine()) != null) {
                try{
                    String[] cmd = message.split(" ");
                    switch (cmd[0]) {
                        case "go": c.quero_viajar(Integer.parseInt(cmd[1]), 0);
                            out.println("Partiste");
                            break;
                        case "leave": c.quero_sair(Integer.parseInt(cmd[1]));
                            out.println("Chegaste");
                            break;
                        default: out.println("Comando invalido");
                    }
                } catch(Exception e) {
                    out.println("Fuck you");
                }
            }
        } catch (Exception ignored) {
        }
    }
}

