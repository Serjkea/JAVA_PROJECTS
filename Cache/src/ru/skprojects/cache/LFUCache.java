package ru.skprojects.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LFUCache<K, V> implements Cache<K, V> {

    private class CacheEntry {

        private V data;
        private int frequency;

        CacheEntry() {}

        public void setData(V data) {
            this.data = data;
        }

        public V getData() {
            return this.data;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public int getFrequency() {
            return this.frequency;
        }

    }

    private LinkedHashMap<K, CacheEntry> cacheMap = new LinkedHashMap<K, CacheEntry>();
    private int cacheCapacity = 5;

    LFUCache(int cacheCapacity) {
        this.cacheCapacity = cacheCapacity;
    }

    @Override
    public void putValue(K key, V value) {
        if (isFull()) {
            cacheMap.remove(getKey());
        }
        CacheEntry newEntry = new CacheEntry();
        newEntry.setData(value);
        newEntry.setFrequency(0);
        cacheMap.put(key, newEntry);
    }

    @Override
    public V getValue(K key) {
        if (cacheMap.containsKey(key)) {
            CacheEntry tmpEntry = cacheMap.get(key);
            tmpEntry.setFrequency(tmpEntry.getFrequency()+1);
            cacheMap.put(key, tmpEntry);
            return tmpEntry.getData();
        }
        return null;
    }

    @Override
    public K getKey() {
        K key = null;
        int minFrequency = Integer.MAX_VALUE;
        for(Map.Entry<K, CacheEntry> entry: cacheMap.entrySet()) {
            int currFrequency = entry.getValue().getFrequency();
            if (currFrequency < minFrequency) {
                minFrequency = currFrequency;
                key = entry.getKey();
            }
        }
        return key;
    }

    public boolean isFull() {
        return cacheMap.size() >= cacheCapacity;
    }
}
