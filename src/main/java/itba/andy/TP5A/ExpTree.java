package itba.andy.TP5A;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class ExpTree implements ExpressionService {

	private Node root;
	private Map<String, Double> variables;

	public ExpTree() {
		System.out.print("Introduzca la expresión en notación infija con todos los paréntesis y blancos: ");

		// token analyzer
		Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
		String line = inputScanner.nextLine();
		inputScanner.close();

		variables = new HashMap<>();
		buildTree(line);
	}

	// Constructor que acepta string directamente para testing
	public ExpTree(String expression) {
		variables = new HashMap<>();
		buildTree(expression);
	}

	// Método para establecer valor de variable
	public void setVariable(String name, double value) {
		variables.put(name, value);
	}

	private void buildTree(String line) {
		// space separator among tokens
		Scanner lineScanner = new Scanner(line).useDelimiter("\\s+");
		root = new Node(lineScanner);
		lineScanner.close();
	}

	static final class Node {
		private String data;
		private Node left, right;

		private Scanner lineScanner;

		public Node(Scanner theLineScanner) {
			lineScanner = theLineScanner;

			Node auxi = buildExpression();
			data = auxi.data;
			left = auxi.left;
			right = auxi.right;

			if (lineScanner.hasNext())
				throw new RuntimeException("Bad expression");
		}

		private Node() {
		}

		private Node buildExpression() {
			Node node = new Node();

			if (lineScanner.hasNext("\\(")) {
				// Expresión parentizada: ( expresión operador expresión )
				lineScanner.next(); // consumir "("

				node.left = buildExpression();

				// Leer operador
				if (lineScanner.hasNext()) {
					String operator = lineScanner.next();
					if (!operator.matches("[+\\-*/]")) {
						throw new RuntimeException("Invalid operator: " + operator);
					}
					node.data = operator;
				} else {
					throw new RuntimeException("Expected operator");
				}

				node.right = buildExpression();

				// Consumir ")"
				if (lineScanner.hasNext("\\)")) {
					lineScanner.next();
				} else {
					throw new RuntimeException("Expected ')'");
				}
			} else {
				// Debe ser un número o variable
				if (lineScanner.hasNext()) {
					String token = lineScanner.next();

					// Verificar si es un número
					try {
						Double.parseDouble(token);
						node.data = token;
						node.left = null;
						node.right = null;
					} catch (NumberFormatException e) {
						// No es número, verificar si es variable válida
						if (token.matches("[a-zA-Z]+")) {
							node.data = token;
							node.left = null;
							node.right = null;
						} else {
							throw new RuntimeException("Invalid token: " + token);
						}
					}
				} else {
					throw new RuntimeException("Expected number, variable or '('");
				}
			}

			return node;
		}

	} // end Node class

	// Métodos requeridos por ExpressionService
	public double eval() {
		return root != null ? eval(root) : 0;
	}

	private double eval(Node node) {
		if (node.left == null && node.right == null) {
			// Es un número o variable
			try {
				// Intentar parsear como número
				return Double.parseDouble(node.data);
			} catch (NumberFormatException e) {
				// Es una variable
				if (variables.containsKey(node.data)) {
					return variables.get(node.data);
				} else {
					throw new RuntimeException("Variable '" + node.data + "' not defined");
				}
			}
		}

		// Es un operador
		double leftVal = eval(node.left);
		double rightVal = eval(node.right);

		switch (node.data) {
			case "+":
				return leftVal + rightVal;
			case "-":
				return leftVal - rightVal;
			case "*":
				return leftVal * rightVal;
			case "/":
				return leftVal / rightVal;
			default:
				throw new RuntimeException("Unknown operator: " + node.data);
		}
	}

	@Override
	public String toString() {
		return root != null ? toString(root) : "";
	}

	private String toString(Node node) {
		if (node.left == null && node.right == null) {
			return node.data;
		}
		return "(" + toString(node.left) + " " + node.data + " " + toString(node.right) + ")";
	}

	// Recorridos del árbol
	public void preorder() {
		System.out.println("Preorder: " + preorder(root));
	}

	private String preorder(Node node) {
		if (node == null)
			return "";
		if (node.left == null && node.right == null) {
			return node.data;
		}
		return node.data + " " + preorder(node.left) + " " + preorder(node.right);
	}

	public void postorder() {
		System.out.println("Postorder: " + postorder(root));
	}

	private String postorder(Node node) {
		if (node == null)
			return "";
		if (node.left == null && node.right == null) {
			return node.data;
		}
		return postorder(node.left) + " " + postorder(node.right) + " " + node.data;
	}

	public void inorder() {
		System.out.println("Inorder: " + inorder(root));
	}

	private String inorder(Node node) {
		if (node == null)
			return "";
		if (node.left == null && node.right == null) {
			return node.data;
		}
		return inorder(node.left) + " " + node.data + " " + inorder(node.right);
	}

	// hasta que armen los testeos
	public static void main(String[] args) {
		// Test con expresión con variables
		String testExpression = "( ( x * ( y - 10.2 ) ) - z )";

		try {
			ExpTree myExp = new ExpTree(testExpression);

			// Establecer valores de variables
			myExp.setVariable("x", 3.0);
			myExp.setVariable("y", 5.0);
			myExp.setVariable("z", 2.0);

			System.out.println("Expresión: " + testExpression);
			System.out.println("Variables: x=3.0, y=5.0, z=2.0");
			System.out.println("Árbol: " + myExp.toString());
			System.out.println("Resultado: " + myExp.eval());

			// Recorridos
			myExp.preorder(); // Notación prefija
			myExp.postorder(); // Notación postfija
			myExp.inorder(); // Notación infija
		} catch (RuntimeException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

} // end ExpTree class
