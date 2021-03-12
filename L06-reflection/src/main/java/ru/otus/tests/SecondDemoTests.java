package ru.otus.tests;

import ru.otus.anotations.After;
import ru.otus.anotations.Before;
import ru.otus.anotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SecondDemoTests {

    private String testedString;

    @Before
    public void init() {
        System.out.println("initSecond");
    }

    @After
    public void finish() {
        System.out.println("finishSecond");
    }

    @After
    public void finish2() {
        System.out.println("finishSecond2");
    }

    @Test
    public void nullTest() {
        assertThat(testedString).isNull();
    }

    @Test
    public void lengthTest() {
        assertThat(testedString.length()).isEqualTo(3);
    }

}
