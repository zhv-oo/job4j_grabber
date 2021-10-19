package ru.job4j.cache;

/**
 * \* User: zhv
 * \* Date: 13.10.2021
 * \* Description: Реализация кеширования из каталога.
 * \
 */
public class DirFileCache extends AbstractCache<String, String> {
    private final String cachingDir;

    /**
     * Метод задания директории для кэширования.
     * @param cachingDir путь.
     */
    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    @Override
    protected String load(String key) {
       return super.get(key);
    }

}
