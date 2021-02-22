package ru.otus.tests;

import ru.otus.anotations.After;
import ru.otus.anotations.Before;
import ru.otus.anotations.Test;

import java.lang.annotation.Annotation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ThirdDemoTests {

    @Before
    public void init() {
      Annotation annotation = ThirdDemoTests.class.getDeclaredAnnotations()[3];
    }

    @After
    public void finish() {
        System.out.println("finishThird");
    }

    @Test
    public void nullTest() {
        assertThat("2").isNotNull();
    }
}
