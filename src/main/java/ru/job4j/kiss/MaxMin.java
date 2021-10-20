package ru.job4j.kiss;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * \* User: zhv
 * \* Date: 21.10.2021
 * \* Description: Класс поиска максимального и минимального элемента в списке.
 * \
 */

public class MaxMin {
    /**
     * Метод нахождения максимального значения в списке.
     * @param value список объектов.
     * @param comparator компоратор с натуральной сортировкой.
     * @param <T> Тип объектов.
     * @return максимальное значение объекта.
     */

    public <T> T max(List<T> value, Comparator<T> comparator) {
        return this.search(value, comparator::compare, compRes -> compRes > 0);
    }
    /**
     * Метод нахождения минимального значения в списке.
     * @param value список объектов.
     * @param comparator компоратор с натуральной сортировкой.
     * @param <T> Тип объектов.
     * @return минимальное значение объекта.
     */

    public <T> T min(List<T> value, Comparator<T> comparator) {
        return this.search(value, comparator::compare, compRes -> compRes < 0);
    }

    /**
     * Метод поиска по предикату и компопатору через функцию.
     * @param list список поиска объекта.
     * @param func функция поведения компоратора.
     * @param predicate предикат условия поведения.
     * @param <T> Тип объектов.
     * @return искомый объект исходя из входящих условий.
     */

    private <T> T search(List<T> list, BiFunction<T, T, Integer> func,
                                        Predicate<Integer> predicate) {
        T res = list.get(0);
        for (int i = 0; i < list.size() - 1; i++) {
            T second = list.get(i + 1);
            if (predicate.test(func.apply(res, second))) {
                res = second;
            }
        }
        return res;
    }
}