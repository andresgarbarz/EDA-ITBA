package itba.andy.TP5C;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BST<T extends Comparable<? super T>> implements BSTreeInterface<T> {

    private Traversal traversal = Traversal.BYLEVELS;
    private Node<T> root;

    @Override
    public void setTraversal(Traversal traversal) {
        this.traversal = traversal;
    }

    @Override
    public void insert(T myData) {
        if (root == null)
            root = new Node<>(myData);
        else
            root.insert(myData);
    }

    @Override
    public void preOrder() {
        if (root == null)
            throw new IllegalStateException();
        System.out.println(root.preorder(new StringBuilder()));
    }

    @Override
    public void postOrder() {
        if (root == null)
            throw new IllegalStateException();
        System.out.println(root.preorder(new StringBuilder()));
    }

    @Override
    public void inOrder() {
        if (root == null)
            throw new IllegalStateException();
        System.out.println(root.inorder(new StringBuilder()));
    }

    @Override
    public int getHeight() {
        return root.getHeight();
    }

    @Override
    public boolean contains(T myData) {
        if (myData == null)
            return false;
        return root.contains(myData);
    }

    @Override
    public T getMax() {
        NodeTreeInterface<T> current = root;
        while (current != null) {
            if (current.getRight() == null)
                return current.getData();
            current = current.getRight();
        }
        return null;
    }

    @Override
    public T getMin() {
        NodeTreeInterface<T> current = root;
        while (current != null) {
            if (current.getLeft() == null)
                return current.getData();
            current = current.getLeft();
        }
        return null;
    }

    public T getCommonNode(T data1, T data2) {
        NodeTreeInterface<T> current = root;
        while (current != null) {
            int c1 = current.getData().compareTo(data1);
            int c2 = current.getData().compareTo(data2);
            if (c1 > 0 && c2 > 0)
                current = current.getLeft();
            else if (c1 < 0 && c2 < 0)
                current = current.getRight();
            else
                return current.getData();
        }
        return null;
    }

    public void delete(T myData) {
        if (myData == null)
            throw new IllegalArgumentException("Data cannot be null");
        if (root != null)
            root = root.delete(myData);
    }

    public void printByLevels() {
        if (root == null)
            return;
        Queue<NodeTreeInterface<T>> queue = new LinkedList<>();
        queue.add(root);
        NodeTreeInterface<T> current;
        while (!queue.isEmpty()) {
            current = queue.remove();
            System.out.print(current.getData() + " ");

            if (current.getLeft() != null)
                queue.add(current.getLeft());
            if (current.getRight() != null)
                queue.add(current.getRight());
        }
        System.out.println();
    }

    @Override
    public NodeTreeInterface<T> getRoot() {
        return root;
    }

    @Override
    public Iterator<T> iterator() {
        return switch (traversal) {
            case BYLEVELS -> new LevelIterator();
            case INORDER -> new InOrderIterator();
        };
    }

    private class LevelIterator implements Iterator<T> {
        private Queue<NodeTreeInterface<T>> queue;

        private LevelIterator() {
            queue = new LinkedList<>();

            if (root != null)
                queue.add(root);
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public T next() {
            NodeTreeInterface<T> current = queue.remove();
            if (current.getLeft() != null)
                queue.add(current.getLeft());

            if (current.getRight() != null)
                queue.add(current.getRight());

            return current.getData();
        }
    }

    private class InOrderIterator implements Iterator<T> {
        private Stack<NodeTreeInterface<T>> stack;
        private NodeTreeInterface<T> current;

        private InOrderIterator() {
            stack = new Stack<>();
            current = root;
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty() || current != null;
        }

        @Override
        public T next() {
            while (current != null) {
                stack.push(current);
                current = current.getLeft();
            }
            NodeTreeInterface<T> node = stack.pop();
            current = node.getRight();
            return node.getData();
        }
    }

    private class Node<T extends Comparable<? super T>> implements NodeTreeInterface<T> {
        private T data;
        private Node<T> left;
        private Node<T> right;
        private int height;

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }

        public void insert(T myData) {
            if (myData == null)
                throw new IllegalArgumentException("Data cannot be null");
            if (data.compareTo(myData) == 0)
                return; // No duplicates allowed
            if (myData.compareTo(data) < 0) {
                if (left == null)
                    left = new Node<>(myData);
                else
                    left.insert(myData);
            } else {
                if (right == null)
                    right = new Node<>(myData);
                else
                    right.insert(myData);
            }
        }

        public String preorder(StringBuilder s) {
            s.append(data).append(" ");
            if (left != null)
                left.preorder(s);
            if (right != null)
                right.preorder(s);
            return s.toString();
        }

        public String postorder(StringBuilder s) {
            if (left != null)
                left.postorder(s);
            if (right != null)
                right.postorder(s);
            s.append(data).append(" ");
            return s.toString();
        }

        public String inorder(StringBuilder s) {
            if (left != null)
                left.inorder(s);
            s.append(data).append(" ");
            if (right != null)
                right.inorder(s);
            return s.toString();
        }

        public int getHeight() {
            if (left == null && right == null)
                return 0;
            else {
                int leftHeight = (left == null ? 0 : left.getHeight());
                int rightHeight = (right == null ? 0 : right.getHeight());
                return Math.max(leftHeight, rightHeight) + 1;
            }
        }

        public boolean contains(T myData) {
            if (myData == null)
                throw new IllegalArgumentException("Data cannot be null");
            int c = myData.compareTo(data);
            if (c == 0)
                return true;
            else if (c < 0)
                return (left != null && left.contains(myData));
            else
                return (right != null && right.contains(myData));
        }

        public Node<T> delete(T myData) {
            if (myData == null)
                throw new IllegalArgumentException("Data cannot be null");
            int c = myData.compareTo(data);
            if (c < 0)
                if (left != null)
                    left = left.delete(myData);
                else if (c > 0)
                    if (right != null)
                        right = right.delete(myData);
                    else {
                        if (left == null)
                            return right;
                        else if (right == null)
                            return left;
                        else {
                            this.data = findlowest(left);
                            left = left.delete(this.data);
                        }
                    }
            return this;
        }

        private T findlowest(Node<T> node) {
            if (node.left == null)
                return node.data;
            else
                return findlowest(node.left);
        }

        @Override
        public T getData() {
            return data;
        }

        @Override
        public NodeTreeInterface<T> getLeft() {
            return left;
        }

        @Override
        public NodeTreeInterface<T> getRight() {
            return right;
        }
    }

    public static void main(String[] args) {
        BST<Integer> myTree = new BST<>();
        myTree.insert(50);
        myTree.insert(60);
        myTree.insert(80);
        myTree.insert(20);
        myTree.insert(70);
        myTree.insert(40);
        myTree.insert(44);
        myTree.insert(10);
        myTree.insert(30);

        myTree.printByLevels();

        myTree.delete(80);
        myTree.delete(10);

        myTree.printByLevels();

        for (Integer data : myTree) {
            System.out.print(data + " ");
        }
        System.out.print('\n');
        myTree.forEach(t -> System.out.print(t + " "));
        System.out.println('\n');

        myTree.setTraversal(Traversal.INORDER);

        for (Integer data : myTree) {
            System.out.print(data + " ");
        }
        System.out.print('\n');
    }
}
