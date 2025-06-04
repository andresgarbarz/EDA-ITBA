package Parcial2.Y2024_Q1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import itba.andy.Parcial2.Y2024_Q1.BST;

import org.junit.Before;
import org.junit.Test;

public class BST_Tester {
    private BST<Integer> myTree;
    private HashMap<Integer, Integer> rta;

    @Before
    public void setUp() {
        myTree = new BST<>();
        myTree.insert(20);
        myTree.insert(20);
        myTree.insert(20);
        myTree.insert(30);
        myTree.insert(22);
        myTree.insert(4);
        myTree.insert(40);
        myTree.insert(60);
        myTree.insert(120);
        myTree.insert(100);
        myTree.insert(110);
        myTree.insert(300);
        myTree.insert(500);
        myTree.insert(400);
        myTree.insert(450);
    }

    @Test
    public void testInRange1() {
        rta = myTree.inRange(1, 10);
        HashMap<Integer, Integer> expected = new HashMap<>();
        expected.put(4, 1);
        assertEquals(expected, rta);
    }

    @Test
    public void testInRange2() {
        rta = myTree.inRange(23, 91);
        HashMap<Integer, Integer> expected = new HashMap<>();
        expected.put(40, 1);
        expected.put(60, 1);
        expected.put(30, 1);
        assertEquals(expected, rta);
    }

    @Test
    public void testInRange3() {
        rta = myTree.inRange(23, 400);
        HashMap<Integer, Integer> expected = new HashMap<>();
        expected.put(400, 1);
        expected.put(100, 1);
        expected.put(40, 1);
        expected.put(120, 1);
        expected.put(60, 1);
        expected.put(300, 1);
        expected.put(30, 1);
        expected.put(110, 1);
        assertEquals(expected, rta);
    }

    @Test
    public void testInRange4() {
        rta = myTree.inRange(20, 26);
        HashMap<Integer, Integer> expected = new HashMap<>();
        expected.put(20, 3);
        expected.put(22, 1);
        assertEquals(expected, rta);
    }

    @Test
    public void testInRange5() {
        rta = myTree.inRange(200, 430);
        HashMap<Integer, Integer> expected = new HashMap<>();
        expected.put(400, 1);
        expected.put(300, 1);
        assertEquals(expected, rta);
    }

    @Test
    public void testInRange6() {
        rta = myTree.inRange(30, 20);
        HashMap<Integer, Integer> expected = new HashMap<>();
        expected.put(20, 3);
        expected.put(22, 1);
        expected.put(30, 1);
        assertEquals(expected, rta);
    }

    @Test
    public void testInRange7() {
        rta = myTree.inRange(121, 200);
        assertTrue(rta.isEmpty());
    }

    @Test
    public void testInRange8() {
        rta = myTree.inRange(900, 800);
        assertTrue(rta.isEmpty());
    }
}
