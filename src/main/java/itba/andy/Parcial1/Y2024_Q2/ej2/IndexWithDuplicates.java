package itba.andy.Parcial1.Y2024_Q2.ej2;

import java.util.Arrays;

/**
 * @author dpenaloza
 *
 */
public class IndexWithDuplicates {

	final static private int chunksize = 5;

	private int[] indexedData;
	private int cantElems;

	public IndexWithDuplicates() {
		indexedData = new int[chunksize];
		cantElems = 0;
	}

	public void initialize(int[] unsortedElements) {

		if (unsortedElements == null)
			throw new RuntimeException("Problem: null data collection");

		indexedData = unsortedElements;
		Arrays.sort(indexedData);
		cantElems = indexedData.length;
	}

	public int[] getIndexedData() {
		return indexedData;
	}

	public void print() {
		System.out.print("[");
		for (int i : indexedData)
			System.out.print(i + " ");
		System.out.println("]");

	}

	public void merge(IndexWithDuplicates other) {
		int[] mergedData = new int[this.cantElems + other.cantElems];
		int i = 0, j = 0, k = 0;

		// Recorro ambos arrays hasta que uno de los dos se acabe
		while (i < this.cantElems && j < other.cantElems) {
			if (this.indexedData[i] < other.indexedData[j]) {
				mergedData[k++] = this.indexedData[i++];
			} else if (this.indexedData[i] > other.indexedData[j]) {
				mergedData[k++] = other.indexedData[j++];
			} else {
				mergedData[k++] = this.indexedData[i++];
				j++;
			}
		}

		// Copio los elementos restantes del array this
		while (i < this.cantElems) {
			mergedData[k++] = this.indexedData[i++];
		}

		// Copio los elementos restantes del array other
		while (j < other.cantElems) {
			mergedData[k++] = other.indexedData[j++];
		}

		// Actualizo el array indexedData con el nuevo array mergedData
		this.indexedData = mergedData;
		this.cantElems = mergedData.length;

	}

}
