package ru.otus.processor;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

class ProcessorExceptionInSecTest {

    @Test
    void throwExceptionTest() {
        var processor = new ProcessorExceptionInSec(() -> 10000);
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> processor.process(mock(Message.class)));
    }

    @Test
    void processTest() {
        var processor = new ProcessorExceptionInSec(() -> 37000);
        assertThat(processor.process(mock(Message.class))).isNotNull();
    }

}