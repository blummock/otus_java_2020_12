import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DigitsPrint {

    private static final Logger logger = LoggerFactory.getLogger(DigitsPrint.class);
    private String last = "thread2";

    public static void main(String[] args) {
        DigitsPrint digitsPrint = new DigitsPrint();
        new Thread(() -> digitsPrint.action("thread1")).start();
        new Thread(() -> digitsPrint.action("thread2")).start();
    }

    private synchronized void action(String threadName) {
        try {
            for (int i = 0; i <= 10; i++) {
                while (last.equals(threadName)) {
                    wait();
                }
                printDigitAndSleep(i);
                last = threadName;
                notifyAll();
            }
            for (int i = 9; i >= 0; i--) {
                while (last.equals(threadName)) {
                    wait();
                }
                printDigitAndSleep(i);
                last = threadName;
                notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyAll();
    }

    private void printDigitAndSleep(int dig) throws InterruptedException {
        logger.info("Message, i:{}", dig);
        Thread.sleep(1000);
    }
}
