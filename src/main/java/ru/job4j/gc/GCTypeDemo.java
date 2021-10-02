package ru.job4j.gc;
/**
 * класс для тестирования разных реализаций GC
 * @version 0.8
 * @author zhitenev
 * используемые параметры для запуска:
 * - Serial => -XX:+UseSerialGC
 * - Parallel => -XX:+UseParallelGC
 * - CMS => -XX:+UseConcMarkSweepGC (допуступен до JDK 14 будет игнорироваться и использовать G1)
 * - G1 => -XX:+UseG1GC
 * - ZGC => -XX:+UseZGC (в 14 версии является эксперементальным, требует
 *          -XX: UnlockExperimentalVMOptions)
 */
import java.util.Random;

public class GCTypeDemo {
    public static void main(String[] args) {
        Random random = new Random();
        int length = 100;
        String[] data = new String[1_000_000];
        for (int i = 0; ; i = (i + 1) % data.length) {
            data[i] = String.valueOf(
                    (char) random.nextInt(255)
            ).repeat(length);
        }
    }
}