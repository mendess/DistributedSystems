import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) throws IOException{
        Map<String, Item> items = new HashMap<>();
        ServerSocket serverSocket = new ServerSocket(12345);
        Socket s = serverSocket.accept();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
        String message;
        while((message = bufferedReader.readLine()) != null){
            System.out.println(message);
            if(message.contains("ls")) listItems(items, printWriter);
            else if(message.contains("quit")) break;
            else{
                addItems(items, message);
            }
        }
    }

    private static void addItems(Map<String,Item> items, String item){
        ScheduledFuture<?> remove = scheduler.schedule(() -> items.remove(item), 10, TimeUnit.SECONDS);
        items.put(item, new Item(item, remove));
    }

    private static void listItems(Map<String,Item> items, PrintWriter printWriter){
        StringBuilder s = new StringBuilder();
        for(Item item : items.values()){
            s.append(item.item).append("\t").append(item.timeLeft()).append("\n");
        }
        printWriter.println(s.toString());
        printWriter.flush();
    }

    private static class Item {
        private String item;
        private final ScheduledFuture<?> remove; // I just keep this to get the delay

        public Item(String item, ScheduledFuture<?> remove){
            this.item = item;
            this.remove = remove;
        }

        private long timeLeft(){
            return remove.getDelay(TimeUnit.SECONDS);
        }
    }

}
