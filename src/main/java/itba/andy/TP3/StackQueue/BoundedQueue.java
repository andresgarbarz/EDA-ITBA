package itba.andy.TP3.StackQueue;

public class BoundedQueue<T> {
    private T[] queue;
    private int first;
    private int last;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public BoundedQueue(int capacity) {
        this.capacity = capacity;
        this.queue = (T[]) new Object[capacity];
        this.first = 0;
        this.last = -1;
        this.size = 0;
    }

    public void enqueue(T item) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }
        last = (last + 1) % capacity; // % capacity para hacerlo circular
        queue[last] = item;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        T item = queue[first];
        first = (first + 1) % capacity; // % capacity para hacerlo circular
        size--;
        return item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public void dump() {
        for (int i = 0; i < size; i++) {
            System.out.print(queue[(first + i) % capacity] + " ");
        }
        System.out.println();
    }

}
