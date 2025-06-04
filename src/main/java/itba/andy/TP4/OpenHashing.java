package itba.andy.TP4;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.Function;

public class OpenHashing<K, V> implements IndexParametricService<K, V> {
    private int initialLookupSize = 10;
    final private double threshold = 0.75;
    private int size = 0;

    @SuppressWarnings("unchecked")
    private LinkedList<Slot<K, V>>[] Lookup = (LinkedList<Slot<K, V>>[]) new LinkedList[initialLookupSize];

    private Function<? super K, Integer> prehash = (k) -> {
        if (k instanceof String) {
            String str = (String) k;
            int sum = 0;
            for (int i = 0; i < str.length(); i++) {
                sum += (int) str.charAt(i);
            }
            return sum;
        }
        return k.hashCode();
    };

    public OpenHashing(Function<? super K, Integer> mappingFn) {
        if (mappingFn == null)
            throw new RuntimeException("fn not provided");
        prehash = mappingFn;
    }

    private int hash(K key) {
        if (key == null)
            throw new IllegalArgumentException("key cannot be null");
        return Math.abs(prehash.apply(key)) % Lookup.length;
    }

    @Override
    public void insertOrUpdate(K key, V data) {
        if (key == null || data == null) {
            String msg = String.format("inserting or updating (%s,%s). ", key, data);
            if (key == null)
                msg += "Key cannot be null. ";
            if (data == null)
                msg += "Data cannot be null.";
            throw new IllegalArgumentException(msg);
        }

        int index = hash(key);

        // If the slot is empty, create a new LinkedList
        if (Lookup[index] == null) {
            Lookup[index] = new LinkedList<>();
        }

        // Search for existing key in the overflow zone
        for (Slot<K, V> slot : Lookup[index]) {
            if (slot.key.equals(key)) {
                slot.value = data; // Update existing
                return;
            }
        }

        // Key not found, insert new slot
        Lookup[index].add(new Slot<>(key, data));
        size++;

        // Check load factor and resize if necessary
        if ((double) size / Lookup.length > threshold) {
            System.out.println("Ajustando tamaño de la tabla");
            Lookup = Arrays.copyOf(Lookup, Lookup.length * 2);
        }
    }

    @Override
    public V find(K key) {
        if (key == null)
            return null;

        int index = hash(key);

        // If slot is null, element doesn't exist
        if (Lookup[index] == null)
            return null;

        // Search in the overflow zone
        for (Slot<K, V> slot : Lookup[index]) {
            if (slot.key.equals(key)) {
                return slot.value;
            }
        }

        return null;
    }

    @Override
    public boolean remove(K key) {
        if (key == null)
            return false;

        int index = hash(key);

        // If slot is null, element doesn't exist
        if (Lookup[index] == null)
            return false;

        // Search and remove from overflow zone
        for (Slot<K, V> slot : Lookup[index]) {
            if (slot.key.equals(key)) {
                Lookup[index].remove(slot);
                size--;

                // If overflow zone becomes empty, set to null
                if (Lookup[index].isEmpty()) {
                    Lookup[index] = null;
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public void dump() {
        for (int i = 0; i < Lookup.length; i++) {
            if (Lookup[i] == null) {
                System.out.println(String.format("slot %d is empty", i));
            } else {
                System.out.print(String.format("slot %d contains: ", i));
                for (Slot<K, V> slot : Lookup[i]) {
                    System.out.print(slot + " ");
                }
                System.out.println();
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    // Slot class definition (copied from ClosedHashing)
    static private final class Slot<K, V> {
        private final K key;
        private V value;

        private Slot(K theKey, V theValue) {
            key = theKey;
            value = theValue;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;

            @SuppressWarnings("unchecked")
            Slot<K, V> slot = (Slot<K, V>) obj;
            return key != null ? key.equals(slot.key) : slot.key == null;
        }

        @Override
        public int hashCode() {
            return key != null ? key.hashCode() : 0;
        }

        public String toString() {
            return String.format("(key=%s, value=%s)", key, value);
        }
    }
}