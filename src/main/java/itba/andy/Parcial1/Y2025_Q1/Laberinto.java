package itba.andy.Parcial1.Y2025_Q1;

public class Laberinto {

    public static boolean existeCamino(int[][] laberinto, int filaInicio, int columnaInicio, int filaFin,
            int columnaFin) {
        // Verifico si estoy fuera de los límites del laberinto
        if (filaInicio < 0 || filaInicio >= laberinto.length ||
                columnaInicio < 0 || columnaInicio >= laberinto[0].length) {
            return false;
        }

        // Verifico si llegue al destino
        if (filaInicio == filaFin && columnaInicio == columnaFin) {
            return true;
        }

        // Verifico si la celda actual es una pared o ya fue visitada
        if (laberinto[filaInicio][columnaInicio] != 0) {
            return false;
        }

        // Marco la celda como visitada
        laberinto[filaInicio][columnaInicio] = -1;

        // Intento moverme en las 4 direcciones posibles
        boolean arriba = existeCamino(laberinto, filaInicio - 1, columnaInicio, filaFin, columnaFin);
        boolean abajo = existeCamino(laberinto, filaInicio + 1, columnaInicio, filaFin, columnaFin);
        boolean izquierda = existeCamino(laberinto, filaInicio, columnaInicio - 1, filaFin, columnaFin);
        boolean derecha = existeCamino(laberinto, filaInicio, columnaInicio + 1, filaFin, columnaFin);

        // Restauro el valor original de la celda
        // laberinto[filaInicio][columnaInicio] = 0;

        // Devuelvo true si se encontró un camino en alguna dirección
        return arriba || abajo || izquierda || derecha;
    }

    public static void main(String[] args) {
        int[][] laberinto = {
                { 0, 0, 1, 0 },
                { 1, 0, 1, 0 },
                { 0, 0, 0, 0 },
                { 0, 1, 1, 1 }
        };

        boolean existe = existeCamino(laberinto, 0, 0, 3, 0);
        if (existe) {
            System.out.println("Existe un camino en el laberinto.");
        } else {
            System.out.println("No existe un camino en el laberinto.");
        }
        System.out.println("Caminos recorridos:");
        imprimirLaberinto(laberinto);

        int[][] laberintoSinSalida = {
                { 0, 0, 1, 0 },
                { 1, 0, 1, 1 },
                { 0, 0, 0, 0 },
                { 0, 1, 1, 1 }
        };
        boolean existeSinSalida = existeCamino(laberintoSinSalida, 0, 0, 0, 3);
        if (existeSinSalida) {
            System.out.println("Existe un camino en el laberinto sin salida (¡error!).");
        } else {
            System.out.println("No existe un camino en el laberinto sin salida.");
        }
        System.out.println("Caminos recorridos:");
        imprimirLaberinto(laberintoSinSalida);
    }

    public static void imprimirLaberinto(int[][] laberinto) {
        for (int[] fila : laberinto) {
            for (int celda : fila) {
                System.out.print(celda + " ");
            }
            System.out.println();
        }
    }
}
