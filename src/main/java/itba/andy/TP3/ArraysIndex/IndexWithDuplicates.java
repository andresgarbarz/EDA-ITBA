package itba.andy.TP3.ArraysIndex;

import java.util.Arrays;

public class IndexWithDuplicates implements IndexService {
    private int[] elements;
    private int size;
    private int capacity;

    private final int chunksize = 10;

    public IndexWithDuplicates() {
        this.capacity = 10;
        this.size = 0;
        this.elements = new int[capacity];
    }

    @Override
    public void initialize(int[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException("elements cannot be null");
        }
        for (int element: elements) {
            insert(element);
        }
    }

    @Override
    public void insert(int key) {
        if (size == capacity) {
            capacity += chunksize;
            resize();
        }
        int position = getClosestPosition(key);
        // Shifteo los elementos a la derecha para hacer espacio para la nueva key
        System.arraycopy(elements, position, elements, position + 1, size - position);
        elements[position] = key;
        size++;
    }

    private int getClosestPosition(int key) {
        int left = 0;
        int right = size - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (elements[mid] == key) {
                return mid;
            } else if (elements[mid] < key) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    @Override
    public boolean search(int key) {
        int position = getClosestPosition(key);
        return position < size && elements[position] == key;
    }

    @Override
    public void delete(int key) {
        int position = getClosestPosition(key);
        if (position < size && elements[position] == key) {
            // Shift elements to the left to remove the key
            System.arraycopy(elements, position + 1, elements, position, size - position - 1);
            size--;
            if (size <= capacity - chunksize) {
                capacity -= chunksize;
                resize();
            }
        }
    }

    @Override
    public int occurrences(int key) {
        int position = getClosestPosition(key);
        if (position < size && elements[position] == key) {
            int count = 1;
            // Count occurrences to the left
            for (int i = position - 1; i >= 0 && elements[i] == key; i--) {
                count++;
            }
            // Count occurrences to the right
            for (int i = position + 1; i < size && elements[i] == key; i++) {
                count++;
            }
            return count;
        }
        return 0;
    }

    @Override
    public int[] range(int leftKey, int rightKey, boolean leftIncluded, boolean rightIncluded) {
        // Busco la posición más cercana a leftKey y rightKey
        int leftPosition = getClosestPosition(leftKey);
        int rightPosition = getClosestPosition(rightKey);

        while (leftPosition > 0 && elements[leftPosition - 1] > leftKey) {
            leftPosition--;
        }

        if (leftIncluded) {
            while (leftPosition > 0 && elements[leftPosition - 1] == leftKey) {
                leftPosition--;
            }
        }
        else {
            while (leftPosition < size && elements[leftPosition] <= leftKey) {
                leftPosition++;
            }
        }

        while (rightPosition < size && elements[rightPosition] < rightKey) {
            rightPosition++;
        }

        if (rightIncluded) {
            while (rightPosition < size && elements[rightPosition] == rightKey) {
                rightPosition++;
            }
        }
        else {
            while (rightPosition > 0 && elements[rightPosition - 1] >= rightKey) {
                rightPosition--;
            }
        }

        return Arrays.copyOfRange(elements, leftPosition, rightPosition);
    }

    @Override
    public void sortedPrint() {
        for (int element : elements) {
            System.out.print(element + " ");
        }
    }

    private void checkSize() {
        if (size == 0) {
            throw new RuntimeException("Index is empty");
        }
    }

    @Override
    public int getMax() {
        checkSize();
        return elements[size - 1];
    }

    @Override
    public int getMin() {
        checkSize();
        return elements[0];
    }

    private void resize() {
        this.elements = Arrays.copyOf(elements, capacity);
    }
}
