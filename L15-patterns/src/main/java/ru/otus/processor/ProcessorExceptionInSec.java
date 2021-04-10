package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorExceptionInSec implements Processor {

    private final TimeProvider timeProvider;

    public ProcessorExceptionInSec(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }


    @Override
    public Message process(Message message) {
        long time = timeProvider.getTime() / 1000;
        if (time % 2 == 0) {
            throw new RuntimeException("time " + time + " sec");
        } else {
            return message;
        }
    }
}
