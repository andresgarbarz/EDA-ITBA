package itba.andy.TP5D;

import itba.andy.TP5C.BST;
import itba.andy.TP5C.NodeTreeInterface;

import java.util.LinkedList;
import java.util.Queue;


// acepta repetidos
public class AVL<T extends Comparable<? super T>> extends BST<T>
{

	private Node root;
	
	public void printHierarchy() {
		printHierarchy("",  root);
	}

	public void printHierarchy(String initial,  Node current) {
		
		if (current == null) {
	        System.out.println(initial +  "\\___ " + "null");
			return;
		}
		
		System.out.println(initial + "\\___ " + current);
		
		if ( current.left != null || current.right != null) {
			printHierarchy(initial + "    " ,  current.left) ;
			printHierarchy(initial + "    " ,   current.right) ;
	
		}
	
	}






	private Node rightRotate(Node pivote) {

		Node newRoot = pivote.left;
		pivote.left= newRoot.right;
       	newRoot.right = pivote;
        
 
        // Update heights
        pivote.height = Math.max(pivote.left==null?-1:pivote.left.height, pivote.right==null?-1:pivote.right.height) + 1;
       	newRoot.height = Math.max(newRoot.left==null?-1:newRoot.left.height, newRoot.right==null?-1:newRoot.right.height) + 1;
       
        return newRoot;
    }
 
    private Node leftRotate(Node pivote) {

    	Node newRoot = pivote.right;
        pivote.right= newRoot.left;
        newRoot.left = pivote;
  
        //  Update heights
        pivote.height = Math.max(pivote.left==null?-1:pivote.left.height, pivote.right==null?-1:pivote.right.height) + 1;
        newRoot.height = Math.max(newRoot.left==null?-1:newRoot.left.height, newRoot.right==null?-1:newRoot.right.height) + 1;
 
         return newRoot;
    }
 
    private int getBalance(Node currentNode) {
        if (currentNode == null)
            return 0;
 
        return (currentNode.left==null?-1:currentNode.left.height)- 
      		(currentNode.right==null?-1:currentNode.right.height);
        		
    }


	@Override
	public void insert(T myData) {
		if (myData == null)
			throw new RuntimeException("element cannot be null");

		root= insert(root, myData);
		
	}



	private Node insert(Node currentNode, T myData) {
		if (currentNode == null) 
			return new Node(myData);
		
		if (myData.compareTo(currentNode.data) <= 0)
			currentNode.left= insert(currentNode.left, myData);
		else
			currentNode.right= insert(currentNode.right, myData);
		
		// agregado para AVL
		int i = currentNode.left==null?-1:currentNode.left.height;
		int d = currentNode.right==null?-1:currentNode.right.height;
		currentNode.height = 1 + Math.max(i, d);

		 int balance = getBalance(currentNode);
		 
		 // Op: Left left
		 if (balance > 1 && myData.compareTo(currentNode.left.data) <= 0) 
	            return rightRotate(currentNode);
		 
		// Op: Right Right
        if (balance < -1 && myData.compareTo(currentNode.right.data) > 0) 
            return leftRotate(currentNode);
        
        // Op: Left Right
        if (balance > 1 && myData.compareTo(currentNode.left.data) > 0) {
            currentNode.left = leftRotate(currentNode.left);
            return rightRotate(currentNode);
        }
 
        // Op: Right Left 
        if (balance < -1 && myData.compareTo(currentNode.right.data) <= 0) {
            currentNode.right = rightRotate(currentNode.right);
            return leftRotate(currentNode);
        }
        
		return currentNode;
	}

	

		


	@Override
	public NodeTreeInterface<T> getRoot() {
		return root;
	}
	
	@Override
	public int getHeight() {
		if (root == null)
			return -1;
		
		return root.height;
	}
	
	// es iterativa
	@Override
	public void printByLevels() {
        if (root == null) {
            return;
        }
 
        // create an empty queue and enqueue the root node
        Queue<NodeTreeInterface<T>> queue = new LinkedList<>();
        queue.add(root);
 
        NodeTreeInterface<T >currentNode;
 
        // hay elementos?
        while (!queue.isEmpty())
        {
            currentNode = queue.remove();
            System.out.print(currentNode + " ");
            
            if (currentNode.getLeft() != null) {
                queue.add(currentNode.getLeft());
            }
 
            if (currentNode.getRight() != null) {
                queue.add(currentNode.getRight());
            }
         }
 
	}
	
	
	@Override
	public void delete(T myData) {
		throw new RuntimeException("Not implemented in this AVL version");
	}






	class Node implements NodeTreeInterface<T> {

		private T data;
		private Node left;
		private Node right;
		
		// para AVL
		private int height;
		
		
		@Override
		public String toString() {
			return data + " h=" + height;
		}
		
		public Node(T myData) {
			this.data= myData;
			
			this.height= 0;
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
		
		AVL<Integer>  myTree= new AVL<>();
		myTree.insert(1);
		myTree.printHierarchy();
		System.out.println();
		System.out.println();
		
		myTree.insert(2);
		myTree.printHierarchy();
		System.out.println();
		System.out.println();
		
		myTree.insert(4);
		myTree.printHierarchy();
		System.out.println();
		System.out.println();
		
		myTree.insert(7);
		myTree.printHierarchy();
		System.out.println();
		
		myTree.insert(15);
		myTree.printHierarchy();
		System.out.println();
		System.out.println();
		
		myTree.insert(3);
		myTree.printHierarchy();
		System.out.println();
		System.out.println();
		
		myTree.insert(10);
		myTree.printHierarchy();
		System.out.println();
		System.out.println();
		
		myTree.insert(17);
		myTree.printHierarchy();
		System.out.println();
		System.out.println();
		
		myTree.insert(19);
		myTree.printHierarchy();
		System.out.println();
		System.out.println();
		
		myTree.insert(16);
		myTree.printHierarchy();
		System.out.println();
		System.out.println();
	}
	
	
}