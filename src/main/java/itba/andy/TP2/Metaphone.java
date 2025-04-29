package itba.andy.TP2;

/* Este código no está verificado */

public class Metaphone {

    // Método principal para codificar una palabra usando el algoritmo Metaphone
    public static String encode(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        // Convertimos a mayúsculas y eliminamos todo lo que no sea una letra de A-Z
        String word = input.toUpperCase().replaceAll("[^A-Z]", "");
        if (word.isEmpty()) {
            return "";
        }

        // Eliminamos la primera letra si la palabra comienza con ciertos prefijos especiales
        if (startsWithAny(word, "KN", "GN", "PN", "AE", "WR")) {
            word = word.substring(1);
        }

        // Reemplazamos 'X' al inicio por 'S'
        if (word.startsWith("X")) {
            word = "S" + word.substring(1);
        }

        // Reemplazamos 'WH' al inicio por 'W'
        if (word.startsWith("WH")) {
            word = "W" + word.substring(2);
        }

        StringBuilder metaphone = new StringBuilder();

        // Recorremos cada letra de la palabra
        for (int i = 0; i < word.length(); i++) {
            char current = word.charAt(i);
            char next = (i + 1 < word.length()) ? word.charAt(i + 1) : 0;
            char next2 = (i + 2 < word.length()) ? word.charAt(i + 2) : 0;
            char prev = (i > 0) ? word.charAt(i - 1) : 0;

            switch (current) {
                case 'A': case 'E': case 'I': case 'O': case 'U':
                    // Solo agregamos la vocal si está al principio
                    if (i == 0) metaphone.append(current);
                    break;

                case 'B':
                    // No agregamos 'B' si está al final y después de una 'M'
                    if (!(i == word.length() - 1 && prev == 'M')) {
                        metaphone.append('B');
                    }
                    break;

                case 'C':
                    // Ignoramos 'C' en "SCI", "SCE", "SCY"
                    if (prev == 'S' && isOneOf(next, 'E', 'I', 'Y')) break;
                    if (isOneOf(next, 'E', 'I', 'Y')) {
                        metaphone.append('S');
                    } else if (next == 'H') {
                        metaphone.append((i == 0 && !isVowel(next2)) ? 'K' : 'X');
                        i++;
                    } else {
                        metaphone.append('K');
                    }
                    break;

                case 'D':
                    // 'DGE', 'DGI', 'DGY' → 'J'
                    if (next == 'G' && isOneOf(next2, 'E', 'I', 'Y')) {
                        metaphone.append('J');
                        i++;
                    } else {
                        metaphone.append('T');
                    }
                    break;

                case 'F': case 'J': case 'L': case 'M': case 'N': case 'R':
                    metaphone.append(current);
                    break;

                case 'G':
                    if (next == 'H' && !isVowel(next2)) {
                        i++; // Saltamos 'GH'
                        break;
                    }
                    if (next == 'N' && (i + 1 == word.length() - 1 || i + 1 == word.length())) break;
                    if (isOneOf(next, 'E', 'I', 'Y')) {
                        metaphone.append('J');
                    } else {
                        metaphone.append('K');
                    }
                    break;

                case 'H':
                    // Solo agregamos 'H' si está entre vocales
                    if ((i == 0 || isVowel(prev)) && isVowel(next)) {
                        metaphone.append('H');
                    }
                    break;

                case 'K':
                    // Ignoramos 'K' si va después de 'C'
                    if (prev != 'C') metaphone.append('K');
                    break;

                case 'P':
                    if (next == 'H') {
                        metaphone.append('F');
                        i++;
                    } else {
                        metaphone.append('P');
                    }
                    break;

                case 'Q':
                    metaphone.append('K');
                    break;

                case 'S':
                    if (next == 'H') {
                        metaphone.append('X');
                        i++;
                    } else if ((next == 'I' || next == 'E') && isOneOf(next2, 'O', 'A')) {
                        metaphone.append('X');
                    } else {
                        metaphone.append('S');
                    }
                    break;

                case 'T':
                    if (next == 'H') {
                        metaphone.append('0');
                        i++;
                    } else if (next == 'I' && isOneOf(next2, 'O', 'A')) {
                        metaphone.append('X');
                    } else {
                        metaphone.append('T');
                    }
                    break;

                case 'V':
                    metaphone.append('F');
                    break;

                case 'W': case 'Y':
                    if (isVowel(next)) metaphone.append(current);
                    break;

                case 'X':
                    metaphone.append("KS");
                    break;

                case 'Z':
                    metaphone.append('S');
                    break;
            }
        }

        return metaphone.toString();
    }

    // Función auxiliar: verifica si un carácter es vocal
    private static boolean isVowel(char c) {
        return "AEIOUY".indexOf(c) >= 0;
    }

    // Función auxiliar: verifica si un carácter está en un conjunto
    private static boolean isOneOf(char c, char... options) {
        for (char o : options) {
            if (c == o) return true;
        }
        return false;
    }

    // Función auxiliar: verifica si una palabra empieza con alguno de varios prefijos
    private static boolean startsWithAny(String word, String... prefixes) {
        for (String prefix : prefixes) {
            if (word.startsWith(prefix)) return true;
        }
        return false;
    }
}
