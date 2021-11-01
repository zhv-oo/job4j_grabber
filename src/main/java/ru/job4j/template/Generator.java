package ru.job4j.template;


/**
 * \* User: zhv
 * \* Date: 01.11.2021
 * \* Description: Создание шаблонизатора через подход TDD.
 * \
 */

import java.util.Map;

public interface Generator {
    String produce(String template, Map<String, String> args);
}