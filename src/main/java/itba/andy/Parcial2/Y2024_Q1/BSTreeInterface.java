package itba.andy.Parcial2.Y2024_Q1;

import java.util.HashMap;

public interface BSTreeInterface<T extends Comparable<? super T>> {

	void insert(T myData);

	// implementar
	HashMap<T, Integer> inRange(T lower, T upper);
}