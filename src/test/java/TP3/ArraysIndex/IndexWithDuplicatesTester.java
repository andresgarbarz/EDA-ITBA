package TP3.ArraysIndex;

import itba.andy.TP3.ArraysIndex.IndexService;
import itba.andy.TP3.ArraysIndex.IndexWithDuplicates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IndexWithDuplicatesTester {

    private IndexService myIndex;

    @BeforeEach
    public void setUp() {
        myIndex = new IndexWithDuplicates();
    }

    @Test
    public void testEmptyIndex() {
        // Test occurrences on empty index
        assertEquals(0, myIndex.occurrences(10), "Occurrences should be 0 for empty index");

        // Test delete on non-existent element
        myIndex.delete(10); // Should ignore without errors

        // Test search on empty index
        assertFalse(myIndex.search(10), "Search should return false for empty index");
    }

    @Test
    public void testInsertAndSearch() {
        // Insert and verify the functionality
        myIndex.insert(80);
        assertTrue(myIndex.search(80), "Should find 80 after insertion");

        myIndex.insert(20);
        assertTrue(myIndex.search(20), "Should find 20 after insertion");
        assertTrue(myIndex.search(80), "Should still find 80 after inserting 20");

        myIndex.insert(80);
        assertEquals(2, myIndex.occurrences(80), "Should find 2 occurrences of 80");
    }

    @Test
    public void testInitialize() {
        // Initialize with array and test
        try {
            myIndex.initialize(new int[] {100, 50, 30, 50, 80, 100, 100, 30});

            // Elements that should be found
            assertTrue(myIndex.search(30), "Should find 30 after initialization");
            assertTrue(myIndex.search(50), "Should find 50 after initialization");
            assertTrue(myIndex.search(80), "Should find 80 after initialization");
            assertTrue(myIndex.search(100), "Should find 100 after initialization");

            // Elements that shouldn't be found
            assertFalse(myIndex.search(20), "Should not find 20 after initialization");

            // Test occurrences
            assertEquals(2, myIndex.occurrences(30), "Should find 2 occurrences of 30");
            assertEquals(2, myIndex.occurrences(50), "Should find 2 occurrences of 50");
            assertEquals(1, myIndex.occurrences(80), "Should find 1 occurrence of 80");
            assertEquals(3, myIndex.occurrences(100), "Should find 3 occurrences of 100");
        } catch (Exception e) {
            fail("Initialization should not throw an exception");
        }
    }

    @Test
    public void testDelete() {
        try {
            myIndex.initialize(new int[] {100, 50, 30, 50, 80, 100, 100, 30});

            // Test deleting elements
            assertEquals(2, myIndex.occurrences(50), "Should find 2 occurrences of 50 before deletion");
            myIndex.delete(50);
            assertEquals(1, myIndex.occurrences(50), "Should find 1 occurrence of 50 after one deletion");

            assertEquals(2, myIndex.occurrences(30), "Should find 2 occurrences of 30 before deletion");
            myIndex.delete(30);
            assertEquals(1, myIndex.occurrences(30), "Should find 1 occurrence of 30 after one deletion");
            myIndex.delete(30);
            assertEquals(0, myIndex.occurrences(30), "Should find 0 occurrences of 30 after two deletions");
            assertFalse(myIndex.search(30), "Should not find 30 after deleting all occurrences");

            // Deleting non-existent element
            myIndex.delete(999); // Should not cause errors
        } catch (Exception e) {
            fail("Operations should not throw an exception");
        }
    }

    @Test
    public void testComplexScenario() {
        // Testing a more complex scenario with multiple operations
        myIndex.insert(80);
        myIndex.insert(20);
        myIndex.insert(80);

        try {
            myIndex.initialize(new int[] {100, 50, 30, 50, 80, 100, 100, 30});

            assertFalse(myIndex.search(20), "Should not find 20 after initialization");
            assertTrue(myIndex.search(80), "Should find 80 after initialization");

            assertEquals(2, myIndex.occurrences(50), "Should find 2 occurrences of 50");
            myIndex.delete(50);
            assertEquals(1, myIndex.occurrences(50), "Should find 1 occurrence of 50 after deletion");

            myIndex.delete(50);
            assertEquals(0, myIndex.occurrences(50), "Should find 0 occurrences of 50 after deleting all");
        } catch (Exception e) {
            fail("Operations should not throw an exception");
        }
    }
    @Test
    public void testRange() {
        try {
            myIndex.initialize(new int[] {100, 50, 30, 50, 80, 100, 100, 30});

            // Test range(50, 100, false, false) - should return [80]
            int[] result = myIndex.range(50, 100, false, false);
            assertArrayEquals(new int[] {80}, result, "Range (50, 100, false, false) should return [80]");

            // Test range(30, 50, true, false) - should return [30, 30]
            result = myIndex.range(30, 50, true, false);
            assertArrayEquals(new int[] {30, 30}, result, "Range (30, 50, true, false) should return [30, 30]");

            // Test range(45, 100, false, false) - should return [50, 50, 80]
            result = myIndex.range(45, 100, false, false);
            assertArrayEquals(new int[] {50, 50, 80}, result, "Range (45, 100, false, false) should return [50, 50, 80]");

            // Test range(45, 100, true, false) - should return [50, 50, 80]
            result = myIndex.range(45, 100, true, false);
            assertArrayEquals(new int[] {50, 50, 80}, result, "Range (45, 100, true, false) should return [50, 50, 80]");

            // Test range(10, 50, true, false) - should return [30, 30]
            result = myIndex.range(10, 50, true, false);
            assertArrayEquals(new int[] {30, 30}, result, "Range (10, 50, true, false) should return [30, 30]");

            // Test range(10, 20, false, false) - should return empty array
            result = myIndex.range(10, 20, false, false);
            assertArrayEquals(new int[] {}, result, "Range (10, 20, false, false) should return empty array");
        } catch (Exception e) {
            fail("Range operations should not throw an exception");
        }
    }
}