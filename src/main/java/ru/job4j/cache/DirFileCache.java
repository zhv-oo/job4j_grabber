package ru.job4j.cache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Objects;
import java.util.StringJoiner;

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
        for (File subfile : Objects.requireNonNull(file.listFiles())) {
            if (subfile.getName().equals(key)) {
                res = this.getString(subfile.getAbsolutePath());
                break;
            }
        }
        return res;
    }

    /**
     * Метод получения данных из файла.
     * @param path абсолютный путь к файлу.
     * @return содержимое в ввиде строки.
     */
    public String getString(String path) {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(path))) {
            read.lines().forEach(out::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toString();
    }

}
