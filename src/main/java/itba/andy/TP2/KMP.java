package itba.andy.TP2;

import java.util.ArrayList;

public class KMP {
    public static int[] nextComputation(String pattern) {
        int patternLen = pattern.length();
        int[] next = new int[patternLen + 1]; // +1 en tamaño para guardar -1 en next[0]
        next[0] = -1;
        next[1] = 0;

        int len = 0;
        int i = 1;

        while (i < patternLen) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                i++;
                next[i] = len;
            }
            else if (len > 0) {
                len = next[len]; // No incremento i, para volver a chequear desde next[len]
            }
            else {
                i++;
                next[i] = 0; // len es 0, entonces no hay prefijo. Se guarda y se incrementa i
            }
        }
        return next;
    }

    public static int IndexOf(String query, String target) {
        if (query == null || query.isEmpty()) {
            throw new IllegalArgumentException("Query cannot be null or empty");
        }
        if (target == null || target.isEmpty()) {
            throw new IllegalArgumentException("Target cannot be null or empty");
        }

        int[] next = nextComputation(query);

        int rec = 0; // puntero para recorrer el target
        int pquery = 0; // puntero para recorrer el query

        while (rec < target.length()) {
            if (query.charAt(pquery) == target.charAt(rec)) {
                rec++;
                pquery++;
                if (pquery == query.length()) {
                    return rec - pquery; // Se encontró el patrón!!!
                }
            }
            else {
                pquery = next[pquery];
                if (pquery < 0) {
                    rec++;
                    pquery++;
                }
            }
        }
        return -1; // No se encontró el patrón
    }

    public static ArrayList<Integer> findAll(String query, String target) {
        ArrayList<Integer> result = new ArrayList<>();
        // Recorrer el target skippeando a donde se encuentra el patrón
        for (int i = 0; i < target.length(); i++) {
            int index = IndexOf(query, target.substring(i));
            if (index == -1) {
                break;
            }
            // Guardo i (acumulado, del string original) pues index se mide en los substrings
            i += index;
            result.add(i);
        }
        return result;
    }
}
