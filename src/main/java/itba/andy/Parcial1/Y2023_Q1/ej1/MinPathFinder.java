package itba.andy.Parcial1.Y2023_Q1.ej1;

public class MinPathFinder {
    public int findMinPath(int[][] weightMatrix) {
        int rows = weightMatrix.length;
        int cols = weightMatrix[0].length;

        // Creo una matriz de tamaño rows x cols para almacenar los costos minimos de
        // cada celda
        int[][] dp = new int[rows][cols];

        // Inicializo el punto de inicio
        dp[0][0] = weightMatrix[0][0];

        // Relleno la primera fila (solo se puede mover a la derecha)
        for (int j = 1; j < cols; j++) {
            dp[0][j] = dp[0][j - 1] + weightMatrix[0][j];
        }

        // Relleno la primera columna (solo se puede mover hacia abajo)
        for (int i = 1; i < rows; i++) {
            dp[i][0] = dp[i - 1][0] + weightMatrix[i][0];
        }

        // Relleno el resto de la tabla
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                // Calculo el costo minimo para llegar a la celda actual
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + weightMatrix[i][j];
            }
        }

        // Devuelvo el costo minimo para llegar a la celda de destino
        return dp[rows - 1][cols - 1];
    }
}
