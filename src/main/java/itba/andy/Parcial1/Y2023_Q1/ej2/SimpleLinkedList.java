package itba.andy.Parcial1.Y2023_Q1.ej2;

public class SimpleLinkedList<T> {

    private Node root = null;
    private Node last = null;

    public void add(T data) {
        Node newNode = new Node(data, null);
        if (root == null) {
            root = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void dump() {
        Node current = root;

        while (current != null) {
            // avanzo
            System.out.println(current.data);
            current = current.next;
        }
    }

    private final class Node {
        private T data;
        private Node next;

        private Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
}
