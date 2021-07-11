import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DigitsPrint {

    private static final Logger logger = LoggerFactory.getLogger(DigitsPrint.class);

    public static void main(String[] args) {
        DigitsPrint digitsPrint = new DigitsPrint();
        new Thread(() -> digitsPrint.action()).start();
        new Thread(() -> digitsPrint.action()).start();
    }

    private synchronized void action() {
        for (int i = 0; i <= 10; i++) {
            try {
                printDigitAndSleep(i);
                notifyAll();
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 9; i >= 0; i--) {
            try {
                printDigitAndSleep(i);
                notifyAll();
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll();
    }

    private void printDigitAndSleep(int dig) throws InterruptedException {
        logger.info("Message, i:{}", dig);
        Thread.sleep(1000);
    }
}
