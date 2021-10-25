package ru.job4j.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * \* User: zhv
 * \* Date: 13.10.2021
 * \* Description: Абстрактный класс реализации кэша.
 * \
 */

public abstract class AbstractCache<K, V> {

    @SuppressWarnings("checkstyle:VisibilityModifier")
    private final Map<K, SoftReference<V>> cache = new HashMap<>();

    public void put(K key, V value) {
        SoftReference<V> soft = new SoftReference<>(value);
        cache.put(key, soft);
    }

    public V get(K key) {
        V res = cache.getOrDefault(key, new SoftReference<>(null)).get();
        if (res == null) {
            res = load(key);
            cache.put(key, new SoftReference<>(res));
        }
        return cache.get(key).get();
    }

    protected abstract V load(K key);
}
