package Parcial1.Y2023_1Q;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import itba.andy.Parcial1.Y2023_Q1.ej2.IndexWithDuplicates;
import itba.andy.Parcial1.Y2023_Q1.ej2.SimpleLinkedList;

public class IndexWithDuplicatesTester {
    @Test
    public void test1() {
        IndexWithDuplicates<Integer> idx = new IndexWithDuplicates<>();
        idx.initialize(new Integer[] { 100, 50, 30, 50, 80, 10, 100, 30, 20, 138 });

        SimpleLinkedList<Integer> repeatedLst = new SimpleLinkedList<>();
        SimpleLinkedList<Integer> singleLst = new SimpleLinkedList<>();
        SimpleLinkedList<Integer> notIndexedLst = new SimpleLinkedList<>();

        idx.repeatedValues(new Integer[] { 10, 80, 10, 35, 80, 80, 1111 },
                repeatedLst, singleLst, notIndexedLst);

        // Verifico que la lista de repetidos esté vacía
        assertTrue(repeatedLst.isEmpty());

        System.out.println("Repeated Values");
        repeatedLst.dump();
        System.out.println("Single Values");
        singleLst.dump();
        System.out.println("Non Indexed Values");
        notIndexedLst.dump();
    }

    @Test
    public void test2() {
        IndexWithDuplicates<Integer> idx = new IndexWithDuplicates<>();
        idx.initialize(new Integer[] { 100, 50, 30, 50, 80, 10, 100, 30, 20, 138 });
        SimpleLinkedList<Integer> repeatedLst = new SimpleLinkedList<>();
        SimpleLinkedList<Integer> singleLst = new SimpleLinkedList<>();
        SimpleLinkedList<Integer> notIndexedLst = new SimpleLinkedList<>();
        idx.repeatedValues(new Integer[] { 100, 70, 40, 120, 33, 80, 10, 50 }, repeatedLst,
                singleLst, notIndexedLst);
        System.out.println("Repeated Values");
        repeatedLst.dump();
        System.out.println("Single Values");
        singleLst.dump();
        System.out.println("Non Indexed Values");
        notIndexedLst.dump();
    }
}
