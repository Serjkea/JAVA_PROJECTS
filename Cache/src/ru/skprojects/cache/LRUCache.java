package ru.skprojects.cache;

import java.util.LinkedHashMap;

public class LRUCache<K, V> implements Cache<K, V> {

    private LinkedHashMap<K, V> cacheMap;
    private int capacity;

    LRUCache(int capacity) {
        this.capacity = capacity;
        this.cacheMap = new LinkedHashMap<K, V>(capacity);
    }

    @Override
    public void putValue(K key, V value) {
        if (cacheMap.containsKey(key)) {
            cacheMap.remove(key);
        } else if (isFull()) {
            cacheMap.remove(cacheMap.keySet().iterator().next());
        }
        cacheMap.put(key, value);
    }

    @Override
    public V getValue(K key) {
        if (cacheMap.containsKey(key)) {
            V tmp = cacheMap.get(key);
            cacheMap.remove(key);
            cacheMap.put(key,tmp);
            return tmp;
        }
        return null;
    }

    @Override
    public K getKey() {
        return null;
    }

    private boolean isFull() {
        return cacheMap.size() >= capacity;
    }
}
