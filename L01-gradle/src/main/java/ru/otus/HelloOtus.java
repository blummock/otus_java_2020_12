package ru.otus;

import com.google.common.collect.ImmutableSet;

public class HelloOtus {

    public static final ImmutableSet<String> COLOR_NAMES = ImmutableSet.of(
            "red",
            "green",
            "blue");

    public static void main(String[] args) {
        System.out.println(COLOR_NAMES);
    }
}
