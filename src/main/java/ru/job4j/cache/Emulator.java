package ru.job4j.cache;

import java.io.IOException;
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
        this.path = ask("Enter path to dir: ");
    }

    /**
     * Метод загрузки файлов в кэш
     */
    public void loadFile(String fileName) {
        cache.load(fileName);
    }

    /**
     * получения данных их кэша и вывод пользователю
     */
    public void getFromCache() {
        System.out.println(cache.get(ask("Enter file name: ")));
    }

    /**
     * Получение данных от пользователя.
     * @param ask вопрос для пользователя.
     * @return ответ введеный в консоль.
     */
    private static String ask(String ask) {
        Scanner sc = new Scanner(System.in);
        System.out.println(ask);
        return sc.nextLine();
    }

    public static void main(String[] args) throws IOException {
        String line = "";
        Emulator em = new Emulator();
        while (!line.equals("q")) {
            em.getDir();
            em.cache = new DirFileCache(em.path);
            em.loadFile(ask("Enter file name for load: "));
            em.getFromCache();
            line = ask("q to exit, any key to continue.");
        }
    }
}