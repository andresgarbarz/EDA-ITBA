package Parcial1.Y2023_1Q;

import org.junit.jupiter.api.Test;

import itba.andy.Parcial1.Y2023_Q1.ej1.MinPathFinder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinPathFinderTester {
    @Test
    public void test1() {
        int[][] v = new int[][] {
                { 2, 8, 32, 30 },
                { 12, 6, 18, 19 },
                { 1, 2, 4, 8 },
                { 1, 31, 1, 16 }
        };
        MinPathFinder minPathFinder = new MinPathFinder();
        int expected = 38;
        int actual = minPathFinder.findMinPath(v);
        assertEquals(expected, actual);
    }

    @Test
    public void test2() {
        int[][] v = new int[][] {
                { 2, 8, 32, 30 },
                { 12, 6, 18, 19 },
                { 1, 2, 4, 8 } };
        MinPathFinder minPathFinder = new MinPathFinder();
        int expected = 29;
        int actual = minPathFinder.findMinPath(v);
        assertEquals(expected, actual);
    }

    @Test
    public void test3() {
        int[][] v = new int[][] {
                { 1, 3, 1 },
                { 1, 5, 1 },
                { 4, 2, 1 } };
        MinPathFinder minPathFinder = new MinPathFinder();
        int expected = 7;
        int actual = minPathFinder.findMinPath(v);
        assertEquals(expected, actual);
    }
}
