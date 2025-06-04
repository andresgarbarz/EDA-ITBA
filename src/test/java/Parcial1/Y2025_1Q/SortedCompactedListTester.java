package Parcial1.Y2025_1Q;

import itba.andy.Parcial1.Y2025_Q1.SortedCompactedList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

@SuppressWarnings("unused")
public class SortedCompactedListTester {
    @Test
    public void testInsert() {
        SortedCompactedList<String> list = new SortedCompactedList<>();
        list.insert("hola");
        list.insert("tal");
        list.insert("ah");
        list.insert("veo");
        list.insert("ah");
        list.insert("bio");
        list.insert("ah");
        list.insert("veo");
        list.insert("ah");
        list.insert("tal");

        assertEquals(5, list.size());
    }

    @Test
    public void testRemove() {
        SortedCompactedList<String> list = new SortedCompactedList<>();
        list.insert("hola");
        list.insert("tal");
        list.insert("ah");
        list.insert("veo");
        list.insert("ah");
        list.insert("bio");
        list.insert("ah");
        list.insert("veo");
        list.insert("ah");
        list.insert("tal");

        list.dumpNodes();

        assertTrue(list.remove("hola"));
        assertTrue(list.remove("tal"));
        assertTrue(list.remove("ah"));
        assertTrue(list.remove("veo"));
        list.dumpNodes();
        assertEquals(4, list.size());
    }

    @Test
    public void testIterator() {
        SortedCompactedList<String> list = new SortedCompactedList<>();
        list.insert("hola");
        list.insert("tal");
        list.insert("ah");
        list.insert("veo");
        list.insert("ah");
        list.insert("bio");
        list.insert("ah");
        list.insert("veo");
        list.insert("ah");
        list.insert("tal");

        int count = 0;
        for (String s : list) {
            count++;
        }
        assertEquals(10, count);
    }

    @Test
    public void testRemoveNonExistent() {
        SortedCompactedList<String> list = new SortedCompactedList<>();
        list.insert("hola");
        list.insert("tal");

        assertFalse(list.remove("nonexistent"));
        assertEquals(2, list.size());
    }

    @Test
    public void testEmptyList() {
        SortedCompactedList<String> list = new SortedCompactedList<>();
        assertEquals(0, list.size());
        assertFalse(list.remove("anything"));
    }

    @Test
    public void testIteratorRemoveEven() {
        SortedCompactedList<String> list = new SortedCompactedList<>();
        list.insert("hola");
        list.insert("tal");
        list.insert("ah");
        list.insert("veo");
        list.insert("ah");
        list.insert("bio");
        list.insert("ah");
        list.insert("veo");
        list.insert("ah");
        list.insert("tal");

        // Remove even elements
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            it.next();
            if (it.hasNext()) {
                it.next(); // Skip even element
                it.remove(); // Remove even element
            }
        }

        assertEquals(4, list.size());
        int count = 0;
        for (String s : list) {
            count++;
        }
        assertEquals(5, count);
    }

    @Test
    public void testIteratorRemoveOdd() {
        SortedCompactedList<String> list = new SortedCompactedList<>();
        list.insert("hola");
        list.insert("tal");
        list.insert("ah");
        list.insert("veo");
        list.insert("ah");
        list.insert("bio");
        list.insert("ah");
        list.insert("veo");
        list.insert("ah");
        list.insert("tal");

        // Remove odd elements
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            it.next();
            it.remove(); // Remove odd element
            if (it.hasNext()) {
                it.next(); // Skip even element
            }
        }

        assertEquals(4, list.size());
        int count = 0;
        for (String s : list) {
            count++;
        }
        assertEquals(5, count);
    }

    @Test
    public void testIteratorRemoveAll() {
        SortedCompactedList<String> list = new SortedCompactedList<>();
        list.insert("hola");
        list.insert("tal");
        list.insert("ah");
        list.insert("veo");

        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }

        assertEquals(0, list.size());
        assertFalse(it.hasNext());
    }
}
