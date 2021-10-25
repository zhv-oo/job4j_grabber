package ru.job4j.cache;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
        File file = new File(cachingDir);
        String res = null;
        if (!file.exists()) {
            throw new IllegalArgumentException(
                    String.format("Not exist %s", file.getAbsoluteFile()));
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(
                    String.format("Not directory %s", file.getAbsoluteFile()));
        }
        try {
            res = Files.readString(Path.of(cachingDir, key));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
