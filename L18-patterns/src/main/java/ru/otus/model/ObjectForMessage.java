package ru.otus.model;

import java.util.List;

public class ObjectForMessage {

    public ObjectForMessage() {}

    public ObjectForMessage(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    private List<String> data;

    @Override
    public String toString() {
        return "ObjectForMessage{" +
                "data=" + data +
                '}';
    }
}
