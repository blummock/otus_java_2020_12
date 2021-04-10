package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private Map<Long, Message> history = new HashMap<>();

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        history.put(oldMsg.getId(), oldMsg.clone());
        history.put(newMsg.getId(), newMsg.clone());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(history.get(id));
    }
}
