package ru.job4j.srp;

/**
 * \* User: zhv
 * \* Date: 08.11.2021
 * \* Time: 18:11
 * \* Description: Классы нарушаеющие SRP.
 * \
 */

import java.util.List;

/**
 * Класс  User перегруженный функционалом.
 * К примеру реализация вывода имени объекта User.
 */
public class User {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void printName() {
        System.out.println(this.name);
    }
}

/**
 * Класс Auto реализующий singleton.
 */
class Auto {
    private static Auto car;
    private String model;

    private Auto(String model) {
        this.model = model;
    }

    public static Auto createAuto(String model) {
        return new Auto(model);
    }
}

/**
 * Создание класса который включает в себя слишком много обязаностей.
 */
class News<V> {
    private List<V> news;

    public void getFromSite() {

    }

    public void getFromDB() {

    }

    public void sendToSite() {

    }

    public void sendToEmail() {

    }
}
