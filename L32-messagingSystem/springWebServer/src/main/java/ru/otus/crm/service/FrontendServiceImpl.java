package ru.otus.crm.service;

import com.google.gson.Gson;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.ClientsList;
import ru.otus.crm.service.messageSystem.GetClientsResponseHandler;
import ru.otus.crm.service.messageSystem.PutClientRequestHandler;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.client.*;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageType;

import java.util.Optional;

@Service
public class FrontendServiceImpl implements FrontendService {

    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    private final MsClient msClient;
    private final SimpMessagingTemplate template;

    public FrontendServiceImpl(DBServiceClient dbServiceClient, SimpMessagingTemplate template) {
        CallbackRegistry callbackRegistry = new CallbackRegistryImpl();
        HandlersStore requestHandlerDatabaseStore = new HandlersStoreImpl();
        requestHandlerDatabaseStore.addHandler(MessageType.GET_CLIENTS, msg -> Optional.of(MessageBuilder.buildReplyMessage(msg, new ClientsList(dbServiceClient.findAll()))));
        requestHandlerDatabaseStore.addHandler(MessageType.PUT_CLIENT, new PutClientRequestHandler(dbServiceClient));
        MessageSystem messageSystem = new MessageSystemImpl();
        MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem, requestHandlerDatabaseStore, callbackRegistry);
        messageSystem.addClient(databaseMsClient);
        HandlersStore requestHandlerFrontendStore = new HandlersStoreImpl();
        requestHandlerFrontendStore.addHandler(MessageType.GET_CLIENTS, new GetClientsResponseHandler(callbackRegistry));
        requestHandlerFrontendStore.addHandler(MessageType.PUT_CLIENT, new GetClientsResponseHandler(callbackRegistry));
        msClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem, requestHandlerFrontendStore, callbackRegistry);
        messageSystem.addClient(msClient);
        this.template = template;
    }

    private void sendToWS(ClientsList clientsList) {
        template.convertAndSend("/topic/clients", new Gson().toJson(clientsList.getClientList()));
    }

    @Override
    public void putClientAsync(Client client, MessageCallback<Client> callback) {
        Message outMsg = msClient.produceMessage(DATABASE_SERVICE_CLIENT_NAME, client, MessageType.PUT_CLIENT, callback);
        msClient.sendMessage(outMsg);
    }

    @Override
    public void getClientsAsync() {
        Message outMsg = msClient.produceMessage(DATABASE_SERVICE_CLIENT_NAME, null, MessageType.GET_CLIENTS, c -> sendToWS((ClientsList) c));
        msClient.sendMessage(outMsg);
    }
}
