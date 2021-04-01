package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorExceptionInSec {

    ProcessorExceptionInSec() {}

    public Message process(Message message, long time) {
        if (time % 2 == 0) {
            throw new RuntimeException("time " + time + " sec");
        } else {
            return message;
        }
    }
}
