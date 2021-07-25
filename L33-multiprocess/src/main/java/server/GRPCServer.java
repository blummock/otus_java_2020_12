package server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.service.DigitsGeneratorService;

import java.io.IOException;

public class GRPCServer {

    public static final int SERVER_PORT = 8190;
    private Server server;
    private final Logger logger = LoggerFactory.getLogger(GRPCServer.class);

    public void start() throws IOException, InterruptedException {
        DigitsGeneratorService service = new DigitsGeneratorService();
        server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(service).build();
        server.start();
        logger.info("server waiting for client connections...");
        server.awaitTermination();
    }

    public void shutdown() {
        server.shutdown();
    }
}
