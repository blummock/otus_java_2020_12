package ru.otus.crm.service.messageSystem;

import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;

import java.util.Optional;

public class PutClientRequestHandler implements RequestHandler<Client> {

    private final DBServiceClient dbServiceClient;

    public PutClientRequestHandler(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        Client client = MessageHelper.getPayload(msg);
        return Optional.of(MessageBuilder.buildReplyMessage(msg, dbServiceClient.save(client)));
    }
}
