package ru.otus;


import ru.otus.anotations.After;
import ru.otus.anotations.Before;
import ru.otus.anotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.otus.Result.*;

public class TestExecutor<T> {

    private Class<T> clazz;
    private T instance;


    public TestExecutor(Class<T> clazz) {
        this.clazz = clazz;
        try {
            instance = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public List<Result> executeTests() {
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> before = Arrays.stream(methods).filter(method -> method.isAnnotationPresent(Before.class)).collect(Collectors.toList());
        List<Method> tests = Arrays.stream(methods).filter(method -> method.isAnnotationPresent(Test.class)).collect(Collectors.toList());
        List<Method> after = Arrays.stream(methods).filter(method -> method.isAnnotationPresent(After.class)).collect(Collectors.toList());
        List<Result> testResults;
        boolean isSuccessBefore = before.stream().map(this::invokeMethod).allMatch(SUCCESS::equals);
        if (isSuccessBefore) {
            testResults = tests.stream().map(this::invokeMethod).collect(Collectors.toList());
        } else {
            testResults = Stream.generate(() -> SKIPPED).limit(tests.size()).collect(Collectors.toList());
        }
        after.forEach(this::invokeMethod);
        return testResults;
    }

    private Result invokeMethod(Method method) {
        try {
            method.invoke(instance);
        } catch (InvocationTargetException e) {
            try {
                throw e.getTargetException();
            } catch (AssertionError er) {
                er.printStackTrace();
                return FAIL;
            } catch (Throwable t) {
                t.printStackTrace();
                return BROKEN;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BROKEN;
        }
        return SUCCESS;
    }
}
