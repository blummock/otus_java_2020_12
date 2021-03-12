package ru.otus.tests;

import ru.otus.anotations.After;
import ru.otus.anotations.Before;
import ru.otus.anotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FirstDemoTests {

    private String testedString;

    @Before
    public void init() {
        testedString = "hello";
    }

    @After
    public void finish() {
        System.out.println("finish");
    }

    @Test
    public void nullTest() {
        assertThat(testedString).isNotNull();
    }

    @Test
    public void lengthTest() {
        assertThat(testedString.length()).isEqualTo(3);
    }

    @Test
    public void lengthTypeTest() {
        assertThat((testedString.getClass().cast(Integer.class))).isEqualTo(3);
    }

}
