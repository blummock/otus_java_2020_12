package server.service;

import io.grpc.stub.StreamObserver;
import ru.otus.generated.DigitMessage;
import ru.otus.generated.RemoteDigitGenerationServiceGrpc;
import ru.otus.generated.StartMessage;

import java.util.stream.IntStream;

public class DigitsGeneratorService extends RemoteDigitGenerationServiceGrpc.RemoteDigitGenerationServiceImplBase {

    @Override
    public void generate(StartMessage request, StreamObserver<DigitMessage> responseObserver) {
        IntStream.range(request.getStart(), request.getEnd()).forEach(value -> {
            try {
                Thread.sleep(2000);
                responseObserver.onNext(DigitMessage.newBuilder().setVal(value).build());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        responseObserver.onCompleted();
    }
}
