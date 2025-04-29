package itba.andy.TP2;

public class Levenshtein {
    public static int distance(String a, String b) {
        int n = a.length();
        int m = b.length();
        // Creo una matriz de distancias (+1 para incluir los strings vacios)
        int[][] dp = new int[n + 1][m + 1];

        // Itero sobre la matriz
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                if (i == 0) {
                    dp[i][j] = j; // Si a es vacio, la distancia es j
                } else if (j == 0) {
                    dp[i][j] = i; // Si b es vacio, la distancia es i
                }
                // Si los caracteres son iguales, la distancia es la misma que sin esos caracteres
                // -1 porque la matriz empieza en i=0 con el string vacio, entonces el 1er caracter (indice 0) esta en i/j=1
                else if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(
                            Math.min(dp[i - 1][j] + 1, // borrar
                                    dp[i][j - 1] + 1), // insertar
                                    dp[i - 1][j - 1] + 1); // sustituir
                }
            }
        }
        return dp[n][m];
    }

    public static double normalizedSimilarity(String a, String b) {
        int maxLength = Math.max(a.length(), b.length());
        if (maxLength == 0) {
            return 1; // Ambos strings son vacios
        }
        return 1 - (double) distance(a, b) / maxLength;
    }
}
