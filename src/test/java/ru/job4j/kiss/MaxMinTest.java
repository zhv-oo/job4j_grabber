package ru.job4j.kiss;

import org.junit.Test;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Тестирование класса MaxMin.
 */
public class MaxMinTest {
    private final List<User> userList = List.of(
            new User("Maxim", 27),
            new User("Vadim", 32),
            new User("Viktor", 33),
            new User("Alex", 34),
            new User("Leonid", 35)
    );
    private final List<String> stringList = List.of(
            "One",
            "Two",
            "Three",
            "Four"
    );
    private final MaxMin maxMin = new MaxMin();
    private final UserComparator userComparator = new UserComparator();
    private final UserNameComparator userNameComparator = new UserNameComparator();

    @Test
    public void whenUserOneYoungerThenOther() {
        assertThat(maxMin.min(userList, userComparator).getAge(), is(27));
    }

    @Test
    public void whenUserFourOlderThenOther() {
        assertEquals(maxMin.max(userList, userComparator).getName(), "Leonid");
    }

    @Test
    public void whenSortByNameMin() {
        assertEquals(maxMin.min(userList, userNameComparator).getName(), "Alex");
    }

    @Test
    public void whenSortByNameMax() {
        assertEquals(maxMin.max(userList, userNameComparator).getName(), "Viktor");
    }

    @Test
    public void whenSortStringListMax() {
        assertEquals(maxMin.max(stringList, String::compareTo), "Four");
    }

    @Test
    public void whenSortStringListMin() {
        assertEquals(maxMin.min(stringList, String::compareTo), "Two");
    }

    /**
     * Класс для тестирования метода сортировки списка объекта.
     */
    private static class User {
        private final String name;
        private final Integer age;

        public User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

    }

    /**
     * Реализация компоратора сортировки по возрасту.
     */
    private static class UserComparator implements Comparator<User> {
        @Override
        public int compare(User o1, User o2) {
            return o2.age.compareTo(o1.age);
        }
    }

    /**
     * Реализация компоратора сортировки по имени.
     */
    private static class UserNameComparator implements Comparator<User> {
        @Override
        public int compare(User o1, User o2) {
            return o2.name.compareTo(o1.name);
        }
    }
}