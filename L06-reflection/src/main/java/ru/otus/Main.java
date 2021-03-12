package ru.otus;

import ru.otus.tests.FirstDemoTests;
import ru.otus.tests.SecondDemoTests;
import ru.otus.tests.ThirdDemoTests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Main {


    public static void main(String[] args) {
        runTests(Arrays.asList(FirstDemoTests.class, SecondDemoTests.class, ThirdDemoTests.class));
    }

    private static void runTests(List<Class<?>> toRun) {
        toRun.stream().map(tests -> new TestExecutor<>(tests).executeTests()).reduce((results, results2) -> {
            results.addAll(results2);
            return results;
        }).orElseThrow().stream().collect(Collectors.groupingBy(Enum::name, HashMap::new, Collectors.counting()))
                .forEach((s, results) -> System.out.print(s + "=" + results + " "));
    }
}
