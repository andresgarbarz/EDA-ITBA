package TP2;

import itba.andy.TP2.KMP;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class KMPTester {

    @Test
    public void nextComputation_Test() {
        int[] expected = { -1, 0, 0, 0, 1, 2 };
        assertArrayEquals(expected, KMP.nextComputation("ABCAB"));
    }

    @Test
    public void indexOf_WhenPatternExists_ReturnsCorrectIndex() {
        assertEquals(2, KMP.IndexOf("CAB", "ABCABC"));
    }

    @Test
    public void indexOf_WhenPatternDoesNotExist_ReturnsNegativeOne() {
        assertEquals(-1, KMP.IndexOf("XYZ", "ABCABC"));
    }

    @Test
    public void indexOf_WithOverlappingPatterns_FindsFirstOccurrence() {
        assertEquals(0, KMP.IndexOf("AAA", "AAAAA"));
    }

    @Test
    public void findAll_WhenPatternExists_ReturnsAllIndices() {
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(0, 2, 4));
        assertEquals(expected, KMP.findAll("AB", "ABABAB"));
    }

    @Test
    public void findAll_WhenPatternDoesNotExist_ReturnsEmptyList() {
        ArrayList<Integer> expected = new ArrayList<>();
        assertEquals(expected, KMP.findAll("XYZ", "ABCABC"));
    }

    @Test
    public void findAll_WithOverlappingPatterns_ReturnsAllIndices() {
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(0, 4, 5, 6));
        assertEquals(expected, KMP.findAll("aaa", "aaabaaaaab"));
    }

    @Test
    public void indexOf_WithEmptyInputs_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> KMP.IndexOf("", "ABCABC"));
        assertThrows(IllegalArgumentException.class, () -> KMP.IndexOf("ABC", ""));
        assertThrows(IllegalArgumentException.class, () -> KMP.IndexOf(null, "ABCABC"));
        assertThrows(IllegalArgumentException.class, () -> KMP.IndexOf("ABC", null));
    }
}