package itba.andy.TP3.StackQueue;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.BiFunction;

public class EvaluatorPostfija {

	private Scanner scannerLine;

	@SuppressWarnings("resource")
	public EvaluatorPostfija() {
		Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
		System.out.print("Introduzca la expresiOn en notaciOn postfija: ");
		inputScanner.hasNextLine();

		String line = inputScanner.nextLine();

		scannerLine = new Scanner(line).useDelimiter("\\s+");

	}

	// Método privado para obtener la precedencia de los operadores
	private int getPrecedence(String operador) {
		switch (operador) {
			case "^":
				return 3; // Exponenciación tiene la mayor precedencia
			case "*":
			case "/":
				return 2; // Multiplicación y división tienen precedencia media
			case "+":
			case "-":
				return 1; // Suma y resta tienen la menor precedencia
			default:
				return 0; // Por si hay algún operador desconocido
		}
	}

	// Método para verificar si un token es un operador
	private boolean isOperator(String token) {
		return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^");
	}

	// Método para verificar si un token es un operando (número o variable)
	private boolean isOperand(String token) {
		try {
			// Intentamos convertir el token a un número. Si tiene éxito, es un operando.
			Double.parseDouble(token);
			return true;
		} catch (NumberFormatException e) {
			return false; // Si no se puede convertir, no es un operando
		}
	}

	@SuppressWarnings("resource")
	private String infijaToPostfija(String expresion) {
		// Pila para los operadores
		Stack<String> OperatorStack = new Stack<>();
		// StringBuilder para construir la expresión en notación postfija
		StringBuilder res = new StringBuilder();

		// Usamos Scanner para procesar la expresión infija ya delimitada por espacios
		Scanner scanner = new Scanner(expresion);
		while (scanner.hasNext()) {
			String token = scanner.next();

			// Si el token es un operando
			if (isOperand(token)) {
				res.append(token).append(" "); // Agregar el operando a la lista de resultados
			}
			// Si el token es un paréntesis izquierdo
			else if (token.equals("(")) {
				OperatorStack.push(token); // Agregar a la pila
			}
			// Si el token es un paréntesis derecho
			else if (token.equals(")")) {
				// Desapilar hasta encontrar el paréntesis izquierdo
				while (!OperatorStack.isEmpty() && !OperatorStack.peek().equals("(")) {
					res.append(OperatorStack.pop()).append(" ");
				}
				OperatorStack.pop(); // Desapilar el paréntesis izquierdo
			}
			// Si el token es un operador
			else if (isOperator(token)) {
				// Desapilar operadores con mayor o igual precedencia y agregarlos al res
				while (!OperatorStack.isEmpty() && getPrecedence(token) <= getPrecedence(OperatorStack.peek())) {
					res.append(OperatorStack.pop()).append(" ");
				}
				OperatorStack.push(token); // Agregar el operador a la pila
			}
		}

		// Desapilar cualquier operador restante en la pila
		while (!OperatorStack.isEmpty()) {
			res.append(OperatorStack.pop()).append(" ");
		}

		return res.toString().trim(); // Devolver la expresión en notación postfija
	}

	public Double evaluate() {
		Stack<Double> auxi = new Stack<>();

		/* Si se recibe infija, descomentar */
		StringBuilder sb = new StringBuilder();
		while (scannerLine.hasNext()) {
			sb.append(scannerLine.next()).append(" ");
		}
		String postfija = infijaToPostfija(sb.toString().trim());
		this.scannerLine = new Scanner(postfija);

		HashMap<String, BiFunction<Double, Double, Double>> operations = new HashMap<>();
		operations.put("+", Double::sum);
		operations.put("-", (a, b) -> a - b);
		operations.put("*", (a, b) -> a * b);
		operations.put("/", (a, b) -> a / b);
		operations.put("^", Math::pow);

		while (scannerLine.hasNext()) {
			String token = scannerLine.next();
			if (operations.containsKey(token)) {
				Double b = auxi.pop();
				Double a = auxi.pop();
				auxi.push(operations.get(token).apply(a, b));
			} else {
				auxi.push(Double.parseDouble(token));
			}
		}
		return auxi.pop();
	}

	public static void main(String[] args) {
		EvaluatorPostfija evaluator = new EvaluatorPostfija();
//		 String postfija = evaluator.infijaToPostfija("3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3");
		Double rta = evaluator.evaluate();
		System.out.println(rta);
	}

}
