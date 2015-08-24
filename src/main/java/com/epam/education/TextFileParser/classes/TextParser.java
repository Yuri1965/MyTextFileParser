package com.epam.education.TextFileParser.classes;

import com.epam.education.TextFileParser.interfaces.TextElement;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Парсер текстового файла
 */
public class TextParser {
    // выражение для получения предложений
    public static final String REGEX_SENTENCE = "(?sx-m)[^\\r\\n].*?(?:(?:\\.|\\?|!))";

    // выражение для получения слов и любых знаков включая знаки пунктуации
    //public static final String REGEX_WORD_OR_PUNCTUATION = "([A-Za-zА-Яа-я]+)|(.)";
    public static final String REGEX_WORD_OR_PUNCTUATION = "([A-я]+)|(.)";

    // выражение для получения слова(буквы латинские или русские)
    public static final String REGEX_WORD = "([A-я]+)";

    // выражение для получения символов
    public static final String REGEX_SYMBOL = ".{1}";

    // выражение для получения последовательности пробелов ( > 1 ) в тексте
    public static final String REGEX_SPACES = "(\\s+)";

    // полный путь к файлу
    private String fileName;

    // кодировка файла
    private Charset fileEncoding;

    // текст из файла
    private String fileContent;

    // список предложений
    private List<Sentence> sentencesList = new ArrayList<Sentence>();
    // список слов
    private List<WordOrPunctuation> wordsList = new ArrayList<WordOrPunctuation>();
    // список символов
    private List<Symbol> symbolsList = new ArrayList<Symbol>();

    // конструктор
    public TextParser(String fileName, Charset fileEncoding) {
        this.fileName = fileName;
        this.fileEncoding = fileEncoding;
    }

    // геттеры
    public List<Sentence> getSentencesList() {
        return sentencesList;
    }

    public List<WordOrPunctuation> getWordsList() {
        return wordsList;
    }

    public List<Symbol> getSymbolsList() {
        return symbolsList;
    }

    // читает текст из файла
    private void readFromFile() throws IOException
    {
        byte[] textEncoded = Files.readAllBytes(Paths.get(fileName));

        fileContent = new String(textEncoded, fileEncoding);
    }

    // убирает из текста лишние пробелы
    private void trimFileContent() {
        fileContent = fileContent.replaceAll(REGEX_SPACES, " ");
    }

    // метод разбора текста на элементы
    public void doParse() {
        try {
            // читаем текст из файла
            readFromFile();
            // убираем из текста лишние пробелы
            trimFileContent();

            // парсим текст на элементы
            parseToSentences();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод разбирает текст на массив предложений
    private void parseToSentences() {
        Pattern patternSentence = Pattern.compile(REGEX_SENTENCE);
        Matcher matcher = patternSentence.matcher(fileContent);

        while (matcher.find()) {
            String sentenceString = matcher.group();

            Sentence sentence = parseToWords(sentenceString);

            sentencesList.add(sentence);
        }
    }

    // метод разбирает предложения на массив слов и прочих символов
    private Sentence parseToWords(String sentenceString) {
        Pattern patternWord = Pattern.compile(REGEX_WORD_OR_PUNCTUATION);
        Matcher matcher = patternWord.matcher(sentenceString);

        ArrayList<WordOrPunctuation> arrWords = new ArrayList<WordOrPunctuation>();

        while (matcher.find()) {
            String wordString = matcher.group();

            WordOrPunctuation word = parseToSymbols(wordString);

            // установим признак слово или нет
            word.setIsWord(wordString.matches(REGEX_WORD));

            arrWords.add(word);
            wordsList.add(word);
        }

        return new Sentence(arrWords);
    }

    // метод разбирает слова и прочие символы на массив конечных символов
    private WordOrPunctuation parseToSymbols(String wordString) {
        Pattern patternSymbol = Pattern.compile(REGEX_SYMBOL);
        Matcher matcher = patternSymbol.matcher(wordString);

        ArrayList<Symbol> arrWordSymbols = new ArrayList<Symbol>();

        while (matcher.find()) {
            String stringSymbol = matcher.group();

            Symbol symbol = new Symbol(stringSymbol.charAt(0));

            arrWordSymbols.add(symbol);
            symbolsList.add(symbol);
        }

        return new WordOrPunctuation(arrWordSymbols);
    }

    // метод вывода текста из начального файла (в консоль, файл и т.п.)
    public void printFileContent(Writer writer) throws IOException{
        writer.append(fileContent);
    }

    // метод вывода элементов текста (в консоль, файл и т.п.)
    public void printListTextElements(Writer writer, List<? extends TextElement> listElements) throws IOException{
        for (TextElement element : listElements) {
            element.printToWriter(writer);
        }
    }

    // основной методя для реализации лабораторной работы
    // метод меняет местами первое и последнее слово во всех предложениях текста
    public void swapFirstToLastWords() {
        for (Sentence sentence : sentencesList) {
            List<WordOrPunctuation> lst = (List<WordOrPunctuation>) sentence.getElementList();

            if (lst.size() > 1) {
                int firstIndex = -1;
                int lastIndex = -1;

                // поиск первого и последнего слова
                for (int i = 0; i < lst.size(); i++) {
                    // первое слово в предложении
                    if (lst.get(i).isWord() && firstIndex < 0) {
                        firstIndex = i;
                    }

                    // последнее слово в предложении
                    if (lst.get(i).isWord() && lastIndex < i) {
                        lastIndex = i;
                    }
                }

                // переставим слова
                sentence.swapSentenceTextElementByIndex(firstIndex, lastIndex);
            }
        }
    }

}
