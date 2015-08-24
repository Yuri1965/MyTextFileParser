package com.epam.education.TextFileParser.classes;

import java.util.List;

/**
 * Элемент текста - слово или любой другой символ
 */
public class WordOrPunctuation extends CompositeTextElement {
    // признак того является ли элемент текста словом (=true-слово или =false-символ(не буковка))
    private boolean isWord = false;

    // конструкторы
    public WordOrPunctuation(List<Symbol> elementList) {
        super(elementList);
    }

    public WordOrPunctuation(List<Symbol> elementList, boolean isWord) {
        super(elementList);
        this.isWord = isWord;
    }

    // сеттеры и геттеры
    public boolean isWord() {
        return isWord;
    }

    public void setIsWord(boolean isWord) {
        this.isWord = isWord;
    }
}
