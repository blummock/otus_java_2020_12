package ru.otus.gc.bench;

import java.util.ArrayList;
import java.util.List;

/*
-Xms8G -Xmx8G
-Xms512m -Xmx512m
*/

public class OutOfMemoryDemo {

    public static void main(String[] args) throws InterruptedException {
        long beginTime = System.currentTimeMillis();
        long count = 5 * 1000 * 1000;
        int wordSize = 100 * 1000;
        List<String> buffer = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            buffer.add(String.valueOf(i).repeat(wordSize));
            if (i%2 == 1) {
                buffer.remove(0) ;
            }
            System.out.printf("Process %.2f min \r",  ((double) (System.currentTimeMillis() - beginTime))/ 1000 / 60);
            Thread.sleep(10);
        }
    }
}
