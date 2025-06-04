package itba.andy.TP4;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;
import java.io.File;
import java.io.FileNotFoundException;

public class ClosedHashing<K, V> implements IndexParametricService<K, V> {
	private int initialLookupSize = 10;
	final private double threshold = 0.75;
	private int size = 0;

	@SuppressWarnings({ "unchecked" })
	private Slot<K, V>[] Lookup = (Slot<K, V>[]) new Slot[initialLookupSize];

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

	public ClosedHashing(Function<? super K, Integer> mappingFn) {
		if (mappingFn == null)
			throw new RuntimeException("fn not provided");

		prehash = mappingFn;
	}

	// ajuste al tama�o de la tabla
	private int hash(K key) {
		if (key == null)
			throw new IllegalArgumentException("key cannot be null");

		System.out.println(Math.abs(prehash.apply(key)) % Lookup.length);
		return Math.abs(prehash.apply(key)) % Lookup.length;
	}

	public void insertOrUpdate(K key, V data) {
		if (key == null || data == null) {
			String msg = String.format("inserting or updating (%s,%s). ", key, data);
			if (key == null)
				msg += "Key cannot be null. ";

			if (data == null)
				msg += "Data cannot be null.";

			throw new IllegalArgumentException(msg);
		}

		int originalHash = hash(key);
		int currentPos = originalHash;

		Slot<K, V> entry = Lookup[originalHash];

		if (entry != null) {
			if (entry.key.equals(key)) {
				entry.value = data;
			} else {
				while (Lookup[currentPos] != null) {
					currentPos = (currentPos + 1) % Lookup.length;
					if (Lookup[currentPos].isDeleted()) {
						Lookup[currentPos] = new Slot<K, V>(key, data);
						size++;
						return;
					}
				}
				Lookup[currentPos] = new Slot<K, V>(key, data);
				size++;
			}
		} else {
			Lookup[originalHash] = new Slot<K, V>(key, data);
			size++;
		}

		if ((double) size / Lookup.length > threshold) {
			System.out.println("Ajustando tamaño de la tabla");
			Lookup = Arrays.copyOf(Lookup, Lookup.length * 2);
		}
	}

	// find or get
	public V find(K key) {
		if (key == null)
			return null;

		int originalHash = hash(key);
		int currentPos = originalHash;

		Slot<K, V> entry = Lookup[originalHash];
		if (entry == null)
			return null;

		if (entry.key.equals(key)) {
			return entry.value;
		} else {
			while (Lookup[currentPos] != null) {
				currentPos = (currentPos + 1) % Lookup.length;
				if (Lookup[currentPos].key.equals(key)) {
					return Lookup[currentPos].value;
				}
			}
		}

		return null;
	}

	public boolean remove(K key) {
		if (key == null)
			return false;

		int originalHash = hash(key);
		int currentPos = originalHash;

		Slot<K, V> entry = Lookup[originalHash];
		if (entry == null)
			return false;
		while (Lookup[currentPos] != null) {
			currentPos = (currentPos + 1) % Lookup.length;
			if (Lookup[currentPos].key.equals(key)) {
				if (Lookup[(currentPos + 1) % Lookup.length] == null) {
					Lookup[currentPos] = null;
					size--;
					return true;
				}
				Lookup[currentPos].setDeleted(true);
				size--;
				return true;
			}
		}
		return false;
	}

	public void dump() {
		for (int rec = 0; rec < Lookup.length; rec++) {
			if (Lookup[rec] == null)
				System.out.println(String.format("slot %d is empty", rec));
			else
				System.out.println(String.format("slot %d contains %s", rec, Lookup[rec]));
		}
	}

	public int size() {
		return size;
	}

	static private final class Slot<K, V> {
		private final K key;
		private V value;
		private boolean deleted = false;

		private Slot(K theKey, V theValue) {
			key = theKey;
			value = theValue;
		}

		public boolean isDeleted() {
			return deleted;
		}

		public void setDeleted(boolean deleted) {
			this.deleted = deleted;
		}

		public String toString() {
			return String.format("(key=%s, value=%s)", key, value);
		}
	}

	// public static void main(String[] args) {
	// ClosedHashing<Integer, String> myHash = new ClosedHashing<>(f -> f);
	// myHash.insertOrUpdate(55, "Ana");
	// myHash.insertOrUpdate(44, "Juan");
	// myHash.insertOrUpdate(18, "Paula");
	// myHash.insertOrUpdate(19, "Lucas");
	// myHash.insertOrUpdate(21, "Sol");
	// myHash.dump();
	//
	// }

	// public static void main(String[] args) {
	// ClosedHashing<Integer, String> myHash= new ClosedHashing<>(f->f);
	// myHash.insertOrUpdate(55, "Ana");
	// myHash.insertOrUpdate(29, "Victor");
	// myHash.insertOrUpdate(55, "Tomas");
	// myHash.insertOrUpdate(29, "Lucas");
	// myHash.insertOrUpdate(21, "Sol");
	// myHash.dump();
	// }

	public static void main(String[] args) {
		ClosedHashing<String, String> myHash = new ClosedHashing<>(String::hashCode);

		try (Scanner scanner = new Scanner(new File("src/main/resources/TP4/amazon-categories30.txt"))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				int firstHash = line.indexOf('#');
				if (firstHash != -1) {
					String title = line.substring(0, firstHash);
					String categoryAndRank = line.substring(firstHash + 1);
					myHash.insertOrUpdate(title, categoryAndRank);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("Size: " + myHash.size());
		myHash.dump();
	}
}