package com.epam.education.TextFileParser.interfaces;

import java.io.IOException;
import java.io.Writer;

/**
 * общий интерфейс для элементов текста
 */
public interface TextElement {

    // метод должен возвращать список элементов текста (предложения, слова и прочее)
    Iterable<? extends TextElement> getElement();

    // вывод текста куда угодно(консоль, файл, строка и т.п.),
    // все что можно наследовать от абстрактного класса "Writer")
    void printToWriter(Writer writer) throws IOException;
}
