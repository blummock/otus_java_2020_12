package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerPrinter;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.FacadeProcessorExceptionInSec;
import ru.otus.processor.LoggerProcessor;
import ru.otus.processor.ProcessorReplaceFields;
import ru.otus.processor.ProcessorUpperField10;

import java.util.Arrays;
import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
            Секунда должна определяьться во время выполнения.
       4. Сделать Listener для ведения истории: старое сообщение - новое (подумайте, как сделать, чтобы сообщения не портились)
     */

    public static void main(String[] args) {
        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */
        var processors = List.of(new FacadeProcessorExceptionInSec(),
                new ProcessorReplaceFields());

        var complexProcessor = new ComplexProcessor(processors, Throwable::printStackTrace);
        var history = new HistoryListener();
        complexProcessor.addListener(history);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field12("field12")
                .field13(new ObjectForMessage(Arrays.asList("1", "2", "3")))
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        var message2 = new Message.Builder(2L)
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(new ObjectForMessage(Arrays.asList("one", "two")))
                .build();

        result = complexProcessor.handle(message2);
        System.out.println("result:" + result);

        complexProcessor.removeListener(history);

        System.out.println("History: " + history.findMessageById(1L).orElseThrow());
    }
}
