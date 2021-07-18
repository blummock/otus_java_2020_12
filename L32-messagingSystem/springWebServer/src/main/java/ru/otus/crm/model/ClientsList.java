package ru.otus.crm.model;

import ru.otus.messagesystem.client.ResultDataType;

import java.util.List;

public class ClientsList extends ResultDataType {

    private final List<Client> clientList;

    public ClientsList(List<Client> clientList) {
        this.clientList = clientList;
    }

    public List<Client> getClientList() {
        return clientList;
    }
}
