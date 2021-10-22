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

    @SuppressWarnings("checkstyle:VisibilityModifier")
    private final Map<K, SoftReference<V>> cache = new HashMap<>();

    public void put(K key, V value) {
        SoftReference<V> soft = new SoftReference<>(value);
        cache.put(key, soft);
    }

    public V get(K key) {
        V res = cache.get(key).get();
        if (res == null) {
            res = load(key);
        }
        return res;
    }

    protected abstract V load(K key);
}
