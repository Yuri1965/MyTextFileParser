package com.epam.education.TextFileParser.classes;

import com.epam.education.TextFileParser.interfaces.TextElement;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Элемент в тексте, который содержит другие элементы текста
 */
public abstract class CompositeTextElement implements TextElement {
    // элемент текста
    private List<? extends TextElement> elementList;

    // конструктор
    public CompositeTextElement(List<? extends TextElement> elementList) {
        this.elementList = elementList;
    }

    // получает элемент текста
    @Override
    public Iterable<? extends TextElement> getElement() {
        return elementList;
    }

    // вывод куда угодно, в зависимости от того, что передали (консоль, файл, строка и т.п.),
    // все что можно наследовать от абстрактного класса "Writer")
    @Override
    public void printToWriter(Writer writer) throws IOException{
        for (TextElement element : elementList) {
            element.printToWriter(writer);
        }
    }

    // возвращает список элементов текста
    protected List<? extends TextElement> getElementList() {
        return elementList;
    }
}
