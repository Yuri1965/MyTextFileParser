package com.epam.education.TextFileParser;

import com.epam.education.TextFileParser.classes.Sentence;
import com.epam.education.TextFileParser.classes.TextParser;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Лабораторная работа по парсингу текста
 *
 */
public class ParserRun
{
    public static void main( String[] args )
    {
        // файлы для вывода информации
        String fromFileName = "..\\TextFileParser\\test.txt";
        String toFileName1 = "..\\TextFileParser\\test_out1.txt";
        String toFileName2 = "..\\TextFileParser\\test_out2.txt";
        String toFileName3 = "..\\TextFileParser\\test_out3.txt";

        // создаем парсер и делаем разбор текста
        TextParser textParser = new TextParser(fromFileName, Charset.forName("utf-8"));
        textParser.doParse();

        // возьмем массив предложений из текста
        ArrayList<Sentence> sl = (ArrayList<Sentence>) textParser.getSentencesList();

        // это вывод в консоль (раскоментировать или закомментировать текст ниже)
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            // изначальный текст, который прочитали из файла
            textParser.printFileContent(writer);
            writer.write(10);

            // текст, который получается из массива с предложениями
            textParser.printListTextElements(writer, sl);
            writer.write(10);

            // переставим местами первое и последнее слово во всех предложениях текста и выведем информацию снова
            textParser.swapFirstToLastWords();
            textParser.printListTextElements(writer, sl);
            writer.write(10);

        } catch (IOException e) {
            e.printStackTrace();
        }
        // конец блока вывода в консоль

        // это вывод в файлы (раскоментировать или закомментировать текст ниже)
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(toFileName1)))) {
            // изначальный текст, который прочитали из файла
            textParser.printFileContent(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(toFileName2)))) {
            // текст, который получается из массива с предложениями
            textParser.printListTextElements(writer, sl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(toFileName3)))) {
            // переставим местами первое и последнее слово во всех предложениях текста и выведем информацию снова
            textParser.swapFirstToLastWords();
            textParser.printListTextElements(writer, sl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // конец блока вывода в файлы
    }
}
