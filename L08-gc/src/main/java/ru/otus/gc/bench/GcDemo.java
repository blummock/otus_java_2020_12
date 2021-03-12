package ru.otus.gc.bench;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/*
О формате логов
http://openjdk.java.net/jeps/158


-Xms512m
-Xmx512m
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/dump
-XX:+UseG1GC
*/

/*
1)
    default, time: 83 sec (82 without Label_1)
2)
    -XX:MaxGCPauseMillis=100000, time: 82 sec //Sets a target for the maximum GC pause time.
3)
    -XX:MaxGCPauseMillis=10, time: 91 sec

4)
-Xms2048m
-Xmx2048m
    time: 81 sec

5)
-Xms5120m
-Xmx5120m
    time: 80 sec

5)
-Xms20480m
-Xmx20480m
    time: 81 sec (72 without Label_1)

*/

public class GcDemo {

    public static void main(String... args) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        long counters[] = new long[2];
        switchOnMonitoring(counters);
        long beginTime = System.currentTimeMillis();

        int size = 5 * 1000 * 1000;
        int loopCounter = 2000;
//        int loopCounter = 100000;
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Benchmark");

        Benchmark mbean = new Benchmark(loopCounter);
        mbs.registerMBean(mbean, name);
        mbean.setSize(size);
        mbean.run();
        System.out.printf("Time: %.3f min \n", ((double) (System.currentTimeMillis() - beginTime)) / 1000 / 60);
        System.out.printf("Garbage cycles %s \n", counters[0]);
        System.out.printf("Average %.3f ms \n", (double) counters[1] / counters[0]);
        System.out.printf("GC work time  %.3f min \n", (double) counters[1] / 1000 / 60);
    }

    private static void switchOnMonitoring(long[] counters) {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            AtomicInteger gcTypeCounter = new AtomicInteger();
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();
                    long duration = info.getGcInfo().getDuration();
                    counters[0]++;
                    counters[1] += duration;
                    gcTypeCounter.getAndIncrement();
                    System.out.printf("Cycle:%s Name:%s #%s action:%s gcCause:%s (duration %sms) \n", counters[0], gcName, gcTypeCounter, gcAction, gcCause, duration);
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}
