package ru.otus.processor;

import ru.otus.model.Message;

public class FacadeProcessorExceptionInSec extends ProcessorExceptionInSec implements Processor {

    private ProcessorExceptionInSec processor;

    public FacadeProcessorExceptionInSec() {
        this.processor = new ProcessorExceptionInSec();
    }


    @Override
    public Message process(Message message) {
        return processor.process(message, System.currentTimeMillis() / 60);
    }
}
