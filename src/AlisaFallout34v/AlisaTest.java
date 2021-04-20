package AlisaFallout34v;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.HashMap;

class AlisaTest {
    String testText;
    String checkText;
    AssociationsSearcher searcher;

    @BeforeEach
    void setSearcher() {
        searcher = new AssociationsSearcher();
    }

    @Test
    void returnOneWord_STRINGANDSEARCHER() {
        testText = "Армия";
        checkText = "Армия";
        testText = Alisa.returnOneWord(testText,searcher);
        Assertions.assertEquals(testText, checkText);
    }

    @Test
    void returnOneWord_STRING() {
        testText = "привет";
        checkText = "привет";
        testText = Alisa.returnOneWord(testText);

        Assertions.assertEquals(testText, checkText);
    }

    @Test
    void returnOneWord_ARRAYLIST() {
        ArrayList<String> testArray = new ArrayList<>();
        testArray.add("алиса");
        checkText = "алиса";

        Assertions.assertEquals(Alisa.returnOneWord(testArray), checkText);
    }

    @Test
    void getOptimalWord() {
        HashMap<String, Integer> associations = new HashMap<>();
        associations.put("алиса", 3);
        testText = Alisa.getOptimalWord(associations);
        checkText = "алиса";

        Assertions.assertEquals(testText, checkText);
    }

    @Test
    void removePunctuation() {
        testText = "привет, алиса, как дела??";
        checkText = "привет алиса как дела";
        testText = Alisa.removePunctuation(testText);

        Assertions.assertEquals(testText, checkText);
    }

    @Test
    void upperCaseFirstLetter() {
        testText = "слово";
        checkText = "Слово";
        testText = Alisa.upperCaseFirstLetter(testText);

        Assertions.assertEquals(testText, checkText);
    }
}