package itba.andy.TP3.ArraysIndex;

public class ArraysUtilities {

	public static void main(String[] args) {
		int[] unsorted = new int[] { 34, 10, 8, 60, 21, 17, 28, 30, 2, 70, 50, 15, 62, 40 };
		// quicksort(unsorted);
		mergesort(unsorted);

		for (int i : unsorted) {
			System.out.print(i + " ");
		}
	}

	/*
	 * ORDENACION POR QUICKSORT
	 */
	public static void quicksort(int[] unsorted) {
		quicksort(unsorted, unsorted.length - 1);
	}

	public static void quicksort(int[] unsorted, int cantElements) {
		quicksortHelper(unsorted, 0, cantElements);
	}

	private static void quicksortHelper(int[] unsorted, int leftPos, int rightPos) {
		if (rightPos <= leftPos)
			return;

		// tomamos como pivot el primero. Podria ser otro elemento
		int pivotValue = unsorted[leftPos];

		// excluimos el pivot del cjto.
		swap(unsorted, leftPos, rightPos);

		// particionar el cjto sin el pivot
		int pivotPosCalculated = partition(unsorted, leftPos, rightPos - 1, pivotValue);

		// el pivot en el lugar correcto
		swap(unsorted, pivotPosCalculated, rightPos);

		// salvo unsorted[middle] todo puede estar mal
		// pero cada particion es autonoma
		quicksortHelper(unsorted, leftPos, pivotPosCalculated - 1);
		quicksortHelper(unsorted, pivotPosCalculated + 1, rightPos);

	}

	// particiona el cjto. de unsorted[leftPos] a unsorted[rightPos]
	// ejemplo:
	// unsorted= {34, 10, 8, 60, 21, 17, 28, 30, 2, 70, 50, 15, 62, 40}
	// leftPos= 0
	// rightPos= 12
	// pivotValue= 34
	// el resultado es que los elementos menores a 34 quedan a la izquierda
	// y los mayores a la derecha
	// retorna la posicion del pivot
	static private int partition(int[] unsorted, int leftPos, int rightPos, int pivotValue) {

		while (leftPos <= rightPos) {
			while (unsorted[leftPos] < pivotValue) {
				leftPos++;
			}

			while (unsorted[rightPos] > pivotValue) {
				rightPos--;
			}

			if (leftPos <= rightPos) {
				swap(unsorted, leftPos, rightPos);
				leftPos++;
				rightPos--;
			}
		}

		return leftPos;
	}

	/*
	 * FIN ORDENACION QUICKSORT
	 * 
	 */

	static private void swap(int[] unsorted, int pos1, int pos2) {
		int auxi = unsorted[pos1];
		unsorted[pos1] = unsorted[pos2];
		unsorted[pos2] = auxi;
	}

	public static void mergesort(int[] array) {
		if (array == null || array.length <= 1) {
			return;
		}
		mergesortHelper(array, 0, array.length - 1);
	}

	private static void mergesortHelper(int[] array, int left, int right) {
		if (left < right) {
			int mid = left + (right - left) / 2;

			// Sort first and second halves
			mergesortHelper(array, left, mid);
			mergesortHelper(array, mid + 1, right);

			// Merge the sorted halves
			merge(array, left, mid, right);
		}
	}

	private static void merge(int[] array, int left, int mid, int right) {
		// Create temporary arrays
		int n1 = mid - left + 1;
		int n2 = right - mid;

		int[] leftArray = new int[n1];
		int[] rightArray = new int[n2];

		// Copy data to temporary arrays
		for (int i = 0; i < n1; i++) {
			leftArray[i] = array[left + i];
		}
		for (int j = 0; j < n2; j++) {
			rightArray[j] = array[mid + 1 + j];
		}

		// Merge the temporary arrays
		int i = 0, j = 0;
		int k = left;

		while (i < n1 && j < n2) {
			if (leftArray[i] <= rightArray[j]) {
				array[k] = leftArray[i];
				i++;
			} else {
				array[k] = rightArray[j];
				j++;
			}
			k++;
		}

		// Copy remaining elements of leftArray if any
		while (i < n1) {
			array[k] = leftArray[i];
			i++;
			k++;
		}

		// Copy remaining elements of rightArray if any
		while (j < n2) {
			array[k] = rightArray[j];
			j++;
			k++;
		}
	}

}
