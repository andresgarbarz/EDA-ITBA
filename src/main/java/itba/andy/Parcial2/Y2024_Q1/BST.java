package itba.andy.Parcial2.Y2024_Q1;

import java.util.HashMap;

// acepta repetidos
public class BST<T extends Comparable<? super T>> implements BSTreeInterface<T> {

	private Node root;

	@Override
	public void insert(T myData) {
		if (myData == null)
			throw new RuntimeException("element cannot be null");

		root = insert(root, myData);
	}

	private Node insert(Node currentNode, T myData) {
		if (currentNode == null) {
			return new Node(myData);
		}

		if (myData.compareTo(currentNode.data) <= 0)
			currentNode.left = insert(currentNode.left, myData);
		else
			currentNode.right = insert(currentNode.right, myData);

		return currentNode;
	}

	@Override
	public HashMap<T, Integer> inRange(T lower, T upper) {
		if (lower == null || upper == null)
			throw new RuntimeException("lower and upper cannot be nulls");

		// Invertir si es necesario
		if (lower.compareTo(upper) > 0) {
			T temp = lower;
			lower = upper;
			upper = temp;
		}

		HashMap<T, Integer> result = new HashMap<>();
		inRangeHelper(root, lower, upper, result);
		return result;
	}

	private void inRangeHelper(Node currentNode, T lower, T upper, HashMap<T, Integer> result) {
		if (currentNode == null)
			return;

		int lowerComp = currentNode.data.compareTo(lower);
		int upperComp = currentNode.data.compareTo(upper);

		if (lowerComp >= 0 && upperComp <= 0) {
			result.put(currentNode.data, result.getOrDefault(currentNode.data, 0) + 1);
		}

		if (lowerComp >= 0) {
			inRangeHelper(currentNode.left, lower, upper, result);
		}

		if (upperComp < 0) {
			inRangeHelper(currentNode.right, lower, upper, result);
		}
	}

	class Node implements NodeTreeInterface<T> {

		private T data;
		private Node left;
		private Node right;

		public Node(T myData) {
			this.data = myData;
		}

		public T getData() {
			return data;
		}

		public NodeTreeInterface<T> getLeft() {
			return left;
		}

		public NodeTreeInterface<T> getRight() {
			return right;
		}

	}

	public static void main(String[] args) {
		BST<Integer> myTree = new BST<>();

		myTree.insert(60);
		myTree.insert(40);
		myTree.insert(20);
		myTree.insert(120);
		myTree.insert(300);
		myTree.insert(500);
		myTree.insert(400);
		myTree.insert(30);
		myTree.insert(450);
		myTree.insert(20);
		myTree.insert(20);
		myTree.insert(22);
		myTree.insert(4);
		myTree.insert(100);
		myTree.insert(110);

		HashMap<Integer, Integer> rta;

		rta = myTree.inRange(1, 10);
		System.out.println(rta); // {4: 1}

		rta = myTree.inRange(23, 91);
		System.out.println(rta); // {40=1, 60=1, 30=1}

		rta = myTree.inRange(23, 400);
		System.out.println(rta); // {400=1, 100=1, 40=1, 120=1, 60=1, 300=1, 30=1, 110=1}

		rta = myTree.inRange(20, 26);
		System.out.println(rta); // {20=3, 22=1}

		rta = myTree.inRange(200, 430);
		System.out.println(rta); // {400=1, 300=1}

		rta = myTree.inRange(30, 20);
		System.out.println(rta); // {20:3, 22:1, 30:1}

		rta = myTree.inRange(121, 200);
		System.out.println(rta); // {}

		rta = myTree.inRange(900, 800);
		System.out.println(rta); // {}
	}

}