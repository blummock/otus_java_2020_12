package ru.otus.processor;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

class ProcessorExceptionInSecTest {

    @Test
    void testProcess() {
        var processor = new ProcessorExceptionInSec();
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> processor.process(mock(Message.class), 1000000));
    }

}