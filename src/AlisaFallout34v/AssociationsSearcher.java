package AlisaFallout34v;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AssociationsSearcher {
    private final File pathToVocabulary;
    private final SortedSet<String> vocabulary;
    private final Map<String,Integer> associations;

    AssociationsSearcher() {
        pathToVocabulary = new File("C://Users/Ayazani/Desktop/ThatFalloutMeme/source/AssociationsVocabulary.txt");
        vocabulary = new TreeSet<>();
        associations = new Hashtable<>();
        fillingDictionary();
    }

    //Заполняем словарь словами которые написаны Капсом в словаре
    private void fillingDictionary() {
        try (FileReader fileReader = new FileReader(pathToVocabulary)) {
            int c;
            String word = "";
            while ((c = fileReader.read()) != -1) {
                if ((char) c == ' ' || (char) c == '\n') {
                    word = "";
                }

                if ((char) c == ':') {
                    if (word.equals(word.toUpperCase()))
                        vocabulary.add(Alisa.removePunctuation(word));
                }

                word += (char) c;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean wordChecker(String text) {
        text = text.toUpperCase();
        return vocabulary.contains(text);
    }

    //Ищем в нашем словаре слово написанное Капсом, и заполняем ассоциации из списка
    public void fillingAssociationsWords(String associationWord) {
        associations.clear();
        associationWord = associationWord.toUpperCase();
        try (FileReader fileReader = new FileReader(pathToVocabulary)) {
            boolean associationFindedFlag = false;
            int c;
            String word = "";
            ArrayList<String> wordsForAssociations = new ArrayList<>();
            while ((c = fileReader.read()) != -1) {
                if (associationFindedFlag) {
                    if (c == ';') {
                        for (String s: wordsForAssociations) {
                            associations.putIfAbsent(s, Integer.parseInt(Alisa.removePunctuation(word)));
                        }
                        wordsForAssociations.clear();

                        if (Integer.parseInt(word) == 1)
                            break;

                    } else if (c == ',' || c == ' ') {
                        if (word.length() > 1) {
                            wordsForAssociations.add(word);
                        }
                    }
                } else if ((char) c == ':') {
                    if (word.equals(word.toUpperCase()))
                        if (associationWord.equals(Alisa.removePunctuation(word)))
                            associationFindedFlag = true;
                }

                if ((char) c == ' ' || (char) c == '\n' || (char) c == ',' || (char) c == ';' || (char) c == ':') {
                    word = "";
                } else {
                    word += (char) c;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRandomWord() {
        return Alisa.getOptimalWord(associations);
    }
}