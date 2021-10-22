package ru.job4j.cache;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

/**
 * \* User: zhv
 * \* Date: 19.10.2021
 * \* Description: Класс взаимодействия с пользователем.
 * \
 */
public class Emulator {
    private String path;
    private AbstractCache<String, String> cache;

    /**
     * Метод получения директории для загрузки от пользователя.
     */
    public void getDir() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter path to dir: ");
        this.path = sc.nextLine();
    }

    /**
     * Метод загрузки файлов в кэш
     */
    public void loadFile() {
        File file = new File(path);
        if (!file.exists()) {
            throw new IllegalArgumentException(
                    String.format("Not exist %s", file.getAbsoluteFile()));
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(
                    String.format("Not directory %s", file.getAbsoluteFile()));
        }
        for (File subfile : Objects.requireNonNull(file.listFiles())) {
            cache.put(subfile.getName(), cache.load(subfile.getName()));
        }
    }

    /**
     * получения данных их кэша и вывод пользователю
     */
    public void getFromCache() {
        System.out.println(cache.load("adress.txt"));
    }

    public static void main(String[] args) throws FileNotFoundException {
        Emulator em = new Emulator();
        em.getDir();
        em.cache = new DirFileCache(em.path);
        em.loadFile();
        em.getFromCache();
    }
}