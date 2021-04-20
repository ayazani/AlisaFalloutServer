package AlisaFallout34v;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssociationsSearcherTest {
    AssociationsSearcher searcher;
    String testWord;
    String checkWord;

    @BeforeEach
    void setSearcher() {
        searcher = new AssociationsSearcher();
    }

    @Test
    void wordChecker_TRUE() {
        testWord = "Армия";
        Assertions.assertTrue(searcher.wordChecker(testWord));
    }

    @Test
    void wordChecker_FALSE() {
        testWord = "Орангутан";
        Assertions.assertFalse(searcher.wordChecker(testWord));
    }

    @Test
    void getRandomWord() {
        testWord = "Армия";
        checkWord = "солдат";
        searcher.fillingAssociationsWords(testWord);
        testWord = searcher.getRandomWord();
        Assertions.assertEquals(testWord, checkWord);


    }
}