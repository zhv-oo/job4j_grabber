package ru.job4j.cache;

/**
 * \* User: zhv
 * \* Date: 13.10.2021
 * \* Description: Абстрактный класс реализации кэша.
 * \
 */

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> {

    private final Map<K, SoftReference<V>> cache = new HashMap<>();

    public void put(K key, V value) {
        SoftReference<V> soft = new SoftReference<>(value);
        cache.put(key, soft);
    }

    public V get(K key) {
        SoftReference<V> softRef = cache.get(key);
        return softRef.get();
    }

    protected abstract V load(K key);
}
