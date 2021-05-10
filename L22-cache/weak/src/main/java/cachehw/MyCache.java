package cachehw;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {

    private WeakHashMap<K, V> store;
    private List<HwListener<K, V>> listeners;

    public MyCache() {
        store = new WeakHashMap<>();
        listeners = new ArrayList<>();
    }

    @Override
    public void put(K key, V value) {
        store.put(key, value);
        listeners.forEach(kvHwListener -> kvHwListener.notify(key, value, "Put element"));
    }

    @Override
    public void remove(K key) {
        var value = store.remove(key);
        listeners.forEach(kvHwListener -> kvHwListener.notify(key, value, "Remove element"));
    }

    @Override
    public V get(K key) {
        var value = store.get(key);
        listeners.forEach(kvHwListener -> kvHwListener.notify(key, value, "Get element"));
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
