package itba.andy.TP3.SortedLinkedList;

// lista simplemente encadenada, no acepta repetidos (false e ignora) ni nulls (exception)
public class SortedLinkedList<T extends Comparable<? super T>> implements
        SortedListService<T> {

    private Node root;

    // insert resuelto todo en la clase SortedLinkedList, iterativo
    public boolean insert(T data) {

        if (data == null)
            throw new IllegalArgumentException("data cannot be null");

        Node prev = null;
        Node current = root;

        while (current != null && current.data.compareTo(data) < 0) {
            // avanzo
            prev = current;
            current = current.next;
        }

        // repetido?
        if (current != null && current.data.compareTo(data) == 0) {
            System.err.println(String.format("Insertion failed. %s repeated", data));
            return false;
        }

        Node aux = new Node(data, current);
        // es el lugar para colocarlo
        if (current == root) {
            // el primero es un caso especial: cambia root
            root = aux;
        } else {
            // nodo interno
            prev.next = aux;
        }

        return true;
    }

    // insert resuelto todo en la clase SortedLinkedList, recursivo
    public boolean insert2(T data) {
        if (data == null)
            throw new IllegalArgumentException("data cannot be null");

        boolean[] rta = new boolean[1];
        root = insertRec(data, root, rta);
        return rta[0];
    }

    public Node insertRec(T data, Node current, boolean[] rta) {

        return null;

    }

    // insert resuelto delegando al nodo
    public boolean insert3(T data) {

        // COMPLETAR
        return true;
    }

    @Override
    public boolean find(T data) {
        return getPos(data) != -1;
    }

    // delete resuelto todo en la clase SortedLinkedList, iterativo
    @Override
    public boolean remove(T data) {
        // completar
        return true;
    }

    // delete resuelto todo en la clase SortedLinkedList, recursivo
    // @Override
    public boolean remove2(T data) {
        // completar
        return true;

    }

    public Node removeRec(T data, Node current, boolean[] rta) {

        // completar
        return null;
    }

    // delete resuelto delegando al nodo
    // @Override
    public boolean remove3(T data) {
        // completar
        return true;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        int rta = 0;

        Node current = root;

        while (current != null) {
            // avanzo
            rta++;
            current = current.next;
        }
        return rta;
    }

    @Override
    public void dump() {
        Node current = root;

        while (current != null) {
            // avanzo
            System.out.println(current.data);
            current = current.next;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof SortedLinkedList))
            return false;

        @SuppressWarnings("unchecked")
        SortedLinkedList<T> auxi = (SortedLinkedList<T>) other;

        Node current = root;
        Node currentOther = auxi.root;
        while (current != null && currentOther != null) {
            if (current.data.compareTo(currentOther.data) != 0)
                return false;

            // por ahora si, avanzo ambas
            current = current.next;
            currentOther = currentOther.next;
        }

        return current == null && currentOther == null;

    }

    // -1 si no lo encontro
    protected int getPos(T data) {
        Node current = root;
        int pos = 0;

        while (current != null) {
            if (current.data.compareTo(data) == 0)
                return pos;

            // avanzo
            current = current.next;
            pos++;
        }
        return -1;
    }

    @Override
    public T getMin() {
        if (root == null)
            return null;

        return root.data;
    }

    @Override
    public T getMax() {

        if (root == null)
            return null;

        Node current = root;

        while (current.next != null) {
            // avanzo
            current = current.next;
        }

        return current.data;
    }

    private final class Node {
        private T data;
        private Node next;

        private Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }

    }

    public static void main(String[] args) {
        SortedLinkedList<String> l = new SortedLinkedList<>();

        System.out.println("lista " + (l.isEmpty() ? "" : "NO") + " vacia");
        System.out.println(l.size());
        System.out.println(l.getMin());
        System.out.println(l.getMax());
        System.out.println();

        System.out.println(l.insert("hola"));
        l.dump();
        System.out.println();

        System.out.println("lista " + (l.isEmpty() ? "" : "NO") + " vacia");
        System.out.println();

        System.out.println(l.insert("tal"));
        l.dump();
        System.out.println();

        System.out.println(l.insert("ah"));
        l.dump();
        System.out.println();

        System.out.println(l.insert("veo"));
        l.dump();
        System.out.println();

        System.out.println(l.insert("bio"));
        l.dump();
        System.out.println();

        System.out.println(l.insert("tito"));
        l.dump();
        System.out.println();

        System.out.println(l.insert("hola"));
        l.dump();
        System.out.println();

        System.out.println(l.insert("aca"));
        l.dump();
        System.out.println();

        System.out.println(l.size());
        System.out.println(l.getMin());
        System.out.println(l.getMax());
    }

}
