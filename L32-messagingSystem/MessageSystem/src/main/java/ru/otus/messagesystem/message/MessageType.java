package ru.otus.messagesystem.message;

public enum MessageType {
    GET_CLIENTS("GetClients"),
    PUT_CLIENT("PutClient");

    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
