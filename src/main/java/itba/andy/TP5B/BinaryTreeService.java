package itba.andy.TP5B;

public interface BinaryTreeService<T> {

	void preorder();

	void postorder();

	void printHierarchy();

	void toFile(String filename);

}