package ru.otus.crm.service;

import ru.otus.crm.model.Client;
import ru.otus.messagesystem.client.MessageCallback;

public interface FrontendService {

    void putClientAsync(Client client, MessageCallback<Client> callback);

    void getClientsAsync();
}

