package itba.andy.Parcial1.Y2023_Q1.ej2;

import java.util.Arrays;

public class IndexWithDuplicates<E extends Comparable<E>> {

    private final int chunk_size = 5;
    private E[] m_idx;
    private int m_size;

    @SuppressWarnings("unchecked")
    public IndexWithDuplicates() {
        m_idx = (E[]) new Comparable[chunk_size];
    }

    public void initialize(E[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException("elements cannot be null");
        }
        for (E e : elements)
            insert(e);
    }

    private void grow() {
        if (m_size < m_idx.length)
            return;
        m_idx = Arrays.copyOf(m_idx, m_idx.length + chunk_size);
    }

    public void insert(E key) {
        grow();

        int position = 0;
        for (position = 0; position < m_size && m_idx[position].compareTo(key) < 0; ++position)
            ;

        for (int i = m_size; i > position; --i)
            m_idx[i] = m_idx[i - 1];
        m_idx[position] = key;
        ++m_size;
    }

    // O(m log n)
    public void repeatedValues(E[] values, SimpleLinkedList<E> repeatedLst, SimpleLinkedList<E> singleLst,
            SimpleLinkedList<E> notIndexedLst) {
        if (values == null || repeatedLst == null || singleLst == null || notIndexedLst == null) {
            throw new IllegalArgumentException("Los parametros no pueden ser null");
        }

        // Recorrido m
        for (E value : values) {
            int count = 0;
            int left = 0;
            int right = m_size - 1;

            // Búsqueda binaria para encontrar la primera ocurrencia
            // Busqueda log n
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int cmp = value.compareTo(m_idx[mid]);

                if (cmp == 0) {
                    // Encontré una ocurrencia, cuento todas las ocurrencias
                    count++;
                    // Cuenta a la izquierda
                    int i = mid - 1;
                    while (i >= 0 && value.compareTo(m_idx[i]) == 0) {
                        count++;
                        i--;
                    }
                    // Cuenta a la derecha
                    i = mid + 1;
                    while (i < m_size && value.compareTo(m_idx[i]) == 0) {
                        count++;
                        i++;
                    }
                    break;
                } else if (cmp < 0) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            // Agrego a la lista correspondiente según la cuenta
            if (count > 1) {
                repeatedLst.add(value);
            } else if (count == 1) {
                singleLst.add(value);
            } else {
                notIndexedLst.add(value);
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void main(String[] args) {
        IndexWithDuplicates<Integer> idx = new IndexWithDuplicates<>();
        idx.initialize(new Integer[] { 100, 50, 30, 50, 80, 10, 100, 30, 20, 138 });

        SimpleLinkedList<Integer> repeatedLst = new SimpleLinkedList();
        SimpleLinkedList<Integer> singleLst = new SimpleLinkedList();
        SimpleLinkedList<Integer> notIndexedLst = new SimpleLinkedList();
        idx.repeatedValues(new Integer[] { 100, 70, 40, 120, 33, 80, 10, 50 }, repeatedLst, singleLst, notIndexedLst);

        System.out.println("Repeated Values");
        repeatedLst.dump();

        System.out.println("Single Values");
        singleLst.dump();

        System.out.println("Non Indexed Values");
        notIndexedLst.dump();
    }

}
