package TP2;

import itba.andy.TP2.Levenshtein;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LevenshteinTester {
    @Test
    void LevenshteinDistance_BigData_Test0() {
        assertEquals(2, Levenshtein.distance("big data", "bigdaa"));
    }
    @Test
    void LevenshteinDistance_BigData_Test1() {
        assertEquals(0, Levenshtein.distance("big data", "big data"));
    }
    @Test
    void LevenshteinDistance_BigData_Test2() {
        assertEquals(1, Levenshtein.distance("big data", "big dta"));
    }
    @Test
    void LevenshteinDistance_BigData_Test3() {
        assertEquals(2, Levenshtein.distance("big data", "big dtaaa"));
    }
    @Test
    void LevenshteinSimilarity_BigData_Test0() {
        assertEquals(0.75, Levenshtein.normalizedSimilarity("big data", "bigdaa"));
    }

}
