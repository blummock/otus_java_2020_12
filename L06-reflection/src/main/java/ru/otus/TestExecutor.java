package ru.otus;


import ru.otus.anotations.After;
import ru.otus.anotations.Before;
import ru.otus.anotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.Result.*;

public class TestExecutor<T> {

    private final Class<T> clazz;
    private T instance;


    public TestExecutor(Class<T> clazz) {
        this.clazz = clazz;
    }

    private boolean initInstance() {
        try {
            instance = clazz.getDeclaredConstructor().newInstance();
            return true;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Result> executeTests() {
        System.out.printf("--------> Run test class \"%s\" \n", clazz.getSimpleName());
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> before = Arrays.stream(methods).filter(method -> method.isAnnotationPresent(Before.class)).collect(Collectors.toList());
        List<Method> tests = Arrays.stream(methods).filter(method -> method.isAnnotationPresent(Test.class)).collect(Collectors.toList());
        List<Method> after = Arrays.stream(methods).filter(method -> method.isAnnotationPresent(After.class)).collect(Collectors.toList());
        return tests.stream().map(test -> {
            if (!initInstance() || !before.stream().map(this::invokeMethod).allMatch(SUCCESS::equals)) {
                return SKIPPED;
            } else {
                Result result = invokeMethod(test);
                after.forEach(this::invokeMethod);
                return result;
            }
        }).collect(Collectors.toList());
    }

    private Result invokeMethod(Method method) {
        System.out.printf("-----------> %s\n", method.getName());
        try {
            method.invoke(instance);
        } catch (InvocationTargetException e) {
            try {
                throw e.getTargetException();
            } catch (AssertionError er) {
                System.out.println(er.getMessage());
                return FAIL;
            } catch (Throwable t) {
                System.out.println(t.getMessage());
                return BROKEN;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return BROKEN;
        }
        return SUCCESS;
    }
}
