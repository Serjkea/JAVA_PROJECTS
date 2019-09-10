package ru.skprojects.cache;

public interface Cache<K, V> {

    public void putValue(K key, V value);
    public V getValue(K key);
    public K getKey();

}
