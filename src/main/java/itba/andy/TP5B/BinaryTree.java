package itba.andy.TP5B;

import itba.andy.TP5B.BinaryTreeService;

/* Código de @BautistaPessagno */

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Implementación de un árbol binario genérico.
 * Permite construir el árbol a partir de un archivo de texto y realizar
 * operaciones básicas.
 * 
 * @param <T> Tipo de dato que almacenará cada nodo del árbol
 */
public class BinaryTree<T> implements BinaryTreeService<T> {

	// Nodo raíz del árbol
	private Node<T> root;
	// Scanner para leer el archivo de entrada
	private Scanner inputScanner;
	// Contador de tokens procesados
	private int tokenCount;
	// Tamaño del árbol (cantidad de nodos)
	private int size;
	// Tipo de dato que se almacenará en los nodos
	private final Class<?> componentType;

	/**
	 * Constructor que inicializa el árbol a partir de un archivo.
	 * El archivo debe estar en el directorio TP5B de los recursos.
	 * 
	 * @param fileName      Nombre del archivo a leer
	 * @param componentType Clase del tipo de dato que se almacenará en los nodos
	 */
	public BinaryTree(String fileName, Class<?> componentType)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, FileNotFoundException {
		// Cargar el archivo desde los recursos
		InputStream is = getClass().getClassLoader().getResourceAsStream("TP5B/" + fileName);

		if (is == null)
			throw new FileNotFoundException(fileName);

		this.componentType = componentType;
		inputScanner = new Scanner(is);
		inputScanner.useDelimiter("\\s+"); // Usar espacios como delimitadores

		buildTree();

		inputScanner.close();
	}

	/**
	 * Construye el árbol a partir de los tokens leídos del archivo.
	 * Utiliza una cola para mantener el orden de los nodos pendientes.
	 */
	private void buildTree() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {

		Queue<NodeHelper<T>> pendingOps = new LinkedList<>();
		String token;

		// Inicializar el árbol con un nodo raíz
		root = new Node<>();
		pendingOps.add(new NodeHelper<T>(root, (Node<T> n) -> (n)));

		while (inputScanner.hasNext()) {
			token = inputScanner.next();

			NodeHelper<T> aPendingOp = pendingOps.remove();
			Node<T> currentNode = aPendingOp.getNode();

			if (token.equals("?")) {
				// Si el token es "?", agregamos dos nodos nulos a la cola
				pendingOps.add(new NodeHelper<T>(null, null));
				pendingOps.add(new NodeHelper<T>(null, null));
			} else {
				// Si es un valor, lo asignamos al nodo actual
				Function<Node<T>, Node<T>> anAction = aPendingOp.getAction();
				currentNode = anAction.apply(currentNode);

				// Crear una nueva instancia del tipo de dato con el token
				Constructor<?> cons = componentType.getConstructor(String.class);
				currentNode.data = (T) cons.newInstance(token);
				size++;

				// Agregar los hijos pendientes a la cola
				pendingOps.add(new NodeHelper<>(currentNode, (Node<T> n) -> (n.setLeftTree(new Node<>()))));
				pendingOps.add(new NodeHelper<>(currentNode, (Node<T> n) -> (n.setRightTree(new Node<>()))));
			}
			tokenCount++;
		}

		// Si el árbol está vacío, establecer la raíz como nula
		if (root.data == null)
			root = null;
	}

	/**
	 * Imprime el árbol en orden preorden (raíz, izquierda, derecha)
	 */
	@Override
	public void preorder() {
		if (root == null)
			throw new IllegalStateException();
		System.out.println(root.preorder(new StringBuilder()));
	}

	/**
	 * Imprime el árbol en orden postorden (izquierda, derecha, raíz)
	 */
	@Override
	public void postorder() {
		if (root == null)
			throw new IllegalStateException();
		System.out.println(root.postorder(new StringBuilder()));
	}

	/**
	 * Imprime el árbol en formato jerárquico
	 */
	public void printHierarchy() {
		printHierarchy("", root);
	}

	/**
	 * Método auxiliar para imprimir el árbol en formato jerárquico
	 * 
	 * @param initial Indentación inicial
	 * @param current Nodo actual
	 */
	private void printHierarchy(String initial, Node<T> current) {
		if (current == null) {
			System.out.println(initial + "└── " + "null");
			return;
		}
		// Imprimimos el dato
		System.out.println(initial + "└── " + current.data);

		// Si no es hoja, seguimos
		if (!current.isLeaf()) {
			printHierarchy(initial + "    ", current.left);
			printHierarchy(initial + "    ", current.right);
		}
	}

	/**
	 * Obtiene una representación en texto del árbol
	 * 
	 * @return String con la representación del árbol
	 */
	public String getTree() {
		Queue<Node<T>> queue = new LinkedList<>();
		queue.add(root);
		StringBuilder sb = new StringBuilder();

		int count = tokenCount;

		while (count != 0) {
			Node current = queue.remove();
			if (current == null) {
				sb.append("?\t");
				queue.add(null);
				queue.add(null);
			} else {
				sb.append(current.data).append("\t");
				queue.add(current.left);
				queue.add(current.right);
			}
			count--;
		}
		return sb.toString();
	}

	/**
	 * Guarda la representación del árbol en un archivo
	 * 
	 * @param name Nombre del archivo de salida
	 */
	public void toFile(String name) {
		try {
			File file = new File("src/main/resources/TP5B/" + name);
			PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);
			writer.print(getTree());
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException("Failed to write to file: " + e.getMessage(), e);
		}
	}

	/**
	 * Calcula la altura del árbol
	 * 
	 * @return Altura del árbol (-1 si está vacío)
	 */
	public int getHeight() {
		if (root == null)
			return -1;
		return getHeightRec(root, 0);
	}

	/**
	 * Método auxiliar recursivo para calcular la altura
	 * 
	 * @param node   Nodo actual
	 * @param height Altura actual
	 * @return Altura máxima desde este nodo
	 */
	private int getHeightRec(Node<T> node, int height) {
		if (node.isLeaf())
			return height;

		int heightLeft = 0;
		int heightRight = 0;

		if (node.left != null)
			heightLeft = getHeightRec(node.left, height + 1);
		if (node.right != null)
			heightRight = getHeightRec(node.right, height + 1);

		return Math.max(heightLeft, heightRight);
	}

	/**
	 * Compara este árbol con otro objeto
	 * 
	 * @param o Objeto a comparar
	 * @return true si los árboles son iguales
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof BinaryTree<?> that))
			return false;
		return getTree().equals(that.getTree());
	}

	/**
	 * Clase interna que representa un nodo del árbol
	 */
	static class Node<T> {
		private T data;
		private Node<T> left;
		private Node<T> right;

		public Node<T> setLeftTree(Node<T> aNode) {
			left = aNode;
			return left;
		}

		public Node<T> setRightTree(Node<T> aNode) {
			right = aNode;
			return right;
		}

		private boolean isLeaf() {
			return left == null && right == null;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (!(o instanceof Node<?> node))
				return false;
			return data.equals(node.data)
					&& (left == null && node.left == null || (left != null && left.equals(node.left)))
					&& (right == null && node.right == null || (right != null && right.equals(node.right)));
		}

		private String preorder(StringBuilder s) {
			s.append(data).append(" ");
			if (left != null)
				left.preorder(s);
			if (right != null)
				right.preorder(s);
			return s.toString();
		}

		private String postorder(StringBuilder s) {
			if (left != null)
				left.postorder(s);
			if (right != null)
				right.postorder(s);
			s.append(data).append(" ");
			return s.toString();
		}
	}

	/**
	 * Clase auxiliar para mantener el estado durante la construcción del árbol
	 */
	static class NodeHelper<T> {
		private Node<T> aNode;
		private Function<Node<T>, Node<T>> anAction;

		public NodeHelper(Node<T> aNode, Function<Node<T>, Node<T>> anAction) {
			this.aNode = aNode;
			this.anAction = anAction;
		}

		public Node<T> getNode() {
			return aNode;
		}

		public Function<Node<T>, Node<T>> getAction() {
			return anAction;
		}
	}

	/**
	 * Método principal con ejemplos de uso
	 */
	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		// Ejemplo de uso con data1
		BinaryTree rta = new BinaryTree("data1", String.class);
		rta.printHierarchy();
		rta.toFile("result.txt");
		System.out.println(rta.getHeight());

		// Ejemplo de comparación de árboles
		BinaryTree original = new BinaryTree("data0_3", String.class);
		original.toFile("result.txt");
		BinaryTree copia = new BinaryTree("mydata0_3", String.class);
		System.out.println(original.equals(copia)); // true
		System.out.println(copia.equals(original)); // true
		System.out.println(original.equals(original)); // true
		System.out.println(copia.equals(copia)); // true
		BinaryTree otro = new BinaryTree("data0_1", String.class);
		System.out.println(original.equals(otro)); // false
		System.out.println(otro.equals(original)); // false

		// Ejemplo de recorridos
		rta = new BinaryTree("data0_3", String.class);
		rta.preorder();
		rta.postorder();
		rta.printHierarchy();
		rta.toFile("result.txt");
		System.out.println(rta.equals(new BinaryTree("result.txt", String.class)));
	}
}