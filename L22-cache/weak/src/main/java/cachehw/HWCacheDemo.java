package cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HWCacheDemo {
    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

    public static void main(String[] args) throws InterruptedException {
        new HWCacheDemo().demo();
    }

    private void demo() throws InterruptedException {
        HwCache<String, Integer> cache = new MyCache<>();

        // пример, когда Idea предлагает упростить код, при этом может появиться "спец"-эффект
        HwListener<String, Integer> listener = new HwListener<String, Integer>() {
            @Override
            public void notify(String key, Integer value, String action) {
                logger.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };

        cache.addListener(listener);
        cache.put("1", 1);
        cache.put("2", 2);

        logger.info("getValue:{}", cache.get("1"));
        cache.remove("1");
        for (int i = 3; i < 5; i++) {
            cache.put(String.valueOf(i), i);
        }
        System.gc();
        Thread.sleep(100);
        cache.get("1");
        cache.get("2");
        cache.get("3");
        cache.removeListener(listener);
    }
}
