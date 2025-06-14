package itba.andy.TP5C;


public interface NodeTreeInterface<T extends Comparable<? super T>> {

	T getData();

	NodeTreeInterface<T> getLeft();

	NodeTreeInterface<T> getRight();

}