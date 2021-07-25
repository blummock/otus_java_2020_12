import client.GRPCClient;
import server.GRPCServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        GRPCServer server = new GRPCServer();
        new Thread(() -> {
            try {
                server.start();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        GRPCClient client = new GRPCClient();
        client.start();
        server.shutdown();
    }
}
