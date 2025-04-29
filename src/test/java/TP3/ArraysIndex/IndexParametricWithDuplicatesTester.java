package TP3.ArraysIndex;

import itba.andy.TP3.ArraysIndex.IndexParametricService;
import itba.andy.TP3.ArraysIndex.IndexParametricWithDuplicates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IndexParametricWithDuplicatesTester {

    private IndexParametricService<Integer> intIndex;
    private IndexParametricService<String> strIndex;

    @BeforeEach
    public void setUp() {
        intIndex = new IndexParametricWithDuplicates<>(Integer.class);
        strIndex = new IndexParametricWithDuplicates<>(String.class);
    }

    @Test
    public void testIntegerEmptyRange() {
        Integer[] result = intIndex.range(10, 50, true, true);
        assertArrayEquals(new Integer[] {}, result, "Range on empty index should return empty array");
    }

    @Test
    public void testIntegerInitializeAndSearch() {
        intIndex.initialize(new Integer[] {100, 50, 30, 50, 80});

        assertTrue(intIndex.search(50), "Search for 50 should return true");
        assertFalse(intIndex.search(40), "Search for 40 should return false");
    }

    @Test
    public void testIntegerOccurrences() {
        intIndex.initialize(new Integer[] {100, 50, 30, 50, 80});
        assertEquals(2, intIndex.occurrences(50), "Occurrences of 50 should be 2");
    }

    @Test
    public void testIntegerRangeQueries() {
        intIndex.initialize(new Integer[] {100, 50, 30, 50, 80});

        Integer[] result1 = intIndex.range(10, 50, true, true);
        assertArrayEquals(new Integer[] {30, 50, 50}, result1);

        Integer[] result2 = intIndex.range(45, 100, true, false);
        assertArrayEquals(new Integer[] {50, 50, 80}, result2);
    }

    @Test
    public void testIntegerMinMax() {
        intIndex.initialize(new Integer[] {100, 50, 30, 50, 80});
        assertEquals(30, intIndex.getMin(), "Min should be 30");
        assertEquals(100, intIndex.getMax(), "Max should be 100");
    }

    @Test
    public void testIntegerDeletion() {
        intIndex.initialize(new Integer[] {100, 50, 30, 50, 80});
        intIndex.delete(50);
        assertEquals(1, intIndex.occurrences(50), "After deletion, occurrences of 50 should be 1");
    }

    @Test
    public void testStringEmptyRange() {
        String[] result = strIndex.range("hola", "tal", true, true);
        assertArrayEquals(new String[] {}, result);
    }

    @Test
    public void testStringInitializeAndRange() {
        strIndex.initialize(new String[] {"hola", "ha", "sii"});

        String[] result1 = strIndex.range("a", "b", true, true);
        assertArrayEquals(new String[] {}, result1);

        String[] result2 = strIndex.range("a", "quizas", true, true);
        assertArrayEquals(new String[] {"ha", "hola"}, result2);
    }

    @Test
    public void testStringInsertAndSearch() {
        strIndex.initialize(new String[] {"hola", "ha", "sii"});
        strIndex.insert("adios");

        assertTrue(strIndex.search("adios"), "Search for 'adios' should return true");
    }

    @Test
    public void testStringRangeAfterInsert() {
        strIndex.initialize(new String[] {"hola", "ha", "sii"});
        strIndex.insert("adios");

        String[] result = strIndex.range("a", "i", true, true);
        assertArrayEquals(new String[] {"adios", "ha", "hola"}, result);
    }

    @Test
    public void testStringMinMax() {
        strIndex.initialize(new String[] {"hola", "ha", "sii"});
        strIndex.insert("adios");

        assertEquals("adios", strIndex.getMin());
        assertEquals("sii", strIndex.getMax());
    }
}
