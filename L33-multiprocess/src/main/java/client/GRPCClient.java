package client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.generated.DigitMessage;
import ru.otus.generated.RemoteDigitGenerationServiceGrpc;
import ru.otus.generated.StartMessage;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class GRPCClient {
    Logger logger = LoggerFactory.getLogger(GRPCClient.class);

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;
    private static final int startVal = 0;
    private static final int endVal = 30;
    private static final int iteration = 50;

    public void start() throws InterruptedException {
        AtomicInteger newValue = new AtomicInteger();
        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();
        RemoteDigitGenerationServiceGrpc.RemoteDigitGenerationServiceStub stub = RemoteDigitGenerationServiceGrpc.newStub(channel);
        CountDownLatch latch = new CountDownLatch(1);
        stub.generate(StartMessage.newBuilder().setStart(startVal).setEnd(endVal).build(), new StreamObserver<DigitMessage>() {
            @Override
            public void onNext(DigitMessage value) {
                logger.info("New value {}", value.getVal());
                newValue.set(value.getVal());
                latch.countDown();
            }

            @Override
            public void onError(Throwable t) {
                logger.error("onError", t);
            }

            @Override
            public void onCompleted() {

            }
        });
        int currentValue = 0;
        for (int i = 0; i < iteration; i++) {
            latch.await();
            int nVal = newValue.getAndSet(0);
            currentValue = currentValue + nVal + 1;
            logger.info("Current value {}", currentValue);
            Thread.sleep(1000);
        }
        channel.shutdown();
    }
}
