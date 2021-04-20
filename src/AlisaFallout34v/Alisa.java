package AlisaFallout34v;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Alisa {
    private static Socket clientSocket;//Сокет для общения
    private static ServerSocket server;//Сокет для сервера
    private static BufferedReader in;//Поток чтения из сокета
    private static BufferedWriter out;//Поток записи в сокет
    //private static HashMap<String,Double> associationDictionary;//Билбиотека весовых к

    public static void main(String[] args) {
        AssociationsSearcher searcher = new AssociationsSearcher();
        //setDictionary();
        try {
            try {
                server = new ServerSocket(4004);//Серверсокет прослушивает порт 4004
                System.out.println("Server is working!");
                clientSocket = server.accept();//будет ждать пока к нему не подключится клиент
                try {
                    //Когда связь установлена и получен сокет для общения с клиентом создаются потоки ввода и вывода
                    //для получения и отправки сообщений
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    while (true) {
                        String word = in.readLine();//Ждём сообщения от клиента
                        word = word.toLowerCase();
                        word = removePunctuation(word);
                        System.out.println(word);
                        word = returnOneWord(word, searcher);
                        if (searcher.wordChecker(word)) {
                            searcher.fillingAssociationsWords(word);
                            out.write(upperCaseFirstLetter(searcher.getRandomWord()) + "?\n");
                        } else {
                            out.write(upperCaseFirstLetter(returnOneWord(word)) + "?\n");
                        }
                        out.flush();//Выталкиваем все из буфера

                        if (word.equals("close")) break;
                    }
                }
                finally {
                    //Сокет закроется в любом случае как и все потоки
                    clientSocket.close();
                    in.close();
                    out.close();
                }
            } finally {
                System.out.println("Server was closed!");
                server.close();
            }
        } catch (IOException e){
            System.err.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public static String returnOneWord(String text, AssociationsSearcher searcher) {
        String delimiter = " ";
        String[] subStr = text.split(delimiter);
        ArrayList<String> words = new ArrayList<>();

        for (String s: subStr) {
            if (searcher.wordChecker(s.toUpperCase()))
                words.add(s);
        }

        if (words.size() > 0) {
            return returnOneWord(words);
        } else {
            return returnOneWord(text);
        }
    }

    public static String returnOneWord(String text) {
        String delimiter = " ";
        String[] subStr = text.split(delimiter);
        int index = (int) (Math.random() * subStr.length);

        return subStr[index];
    }

    public static String returnOneWord(ArrayList<String> text) {
        int index = (int) (Math.random() * text.size());

        return text.get(index);
    }

    public static String getOptimalWord(Map<String, Integer> associations) {
        ArrayList<String> randomWords = new ArrayList<>();
        double maxWeight = 0.0;

        //Если пришло только 1 слово из Словаря ассоциаций, то сразу его возвращаем
        if (associations.size() == 1) {
            for (String key : associations.keySet())
                return key;
        }

        //Находим максимальное значение весового коэффициента
        for (String key : associations.keySet()) {
            if (associations.get(key) > maxWeight) maxWeight = associations.get(key);
        }

        //Добавляем в ArrayList все слова, у которых максимальный найденный весовой коэффициент
        for (String key : associations.keySet()) {
            if (associations.get(key) == maxWeight) {
                randomWords.add(key);
            }
        }

        return returnOneWord(randomWords);
    }

    public static String removePunctuation(String str) {
        StringBuilder result = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isAlphabetic(c) || Character.isDigit(c) || Character.isSpaceChar(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String upperCaseFirstLetter(String word) {
        StringBuilder builder = new StringBuilder(word);
        builder.setCharAt(0, Character.toUpperCase(word.charAt(0)));

        return builder.toString();
    }
}