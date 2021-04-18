package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorReplaceFields implements Processor{

    @Override
    public Message process(Message message) {
        return message.clone().toBuilder().field11(message.getField12()).field12(message.getField11()).build();
    }
}
