package itba.andy.Parcial1.Y2022_Q2.ej1;

public class Corredores {

    // Busco la posición más cercana a la izquierda de la clave
    private int getClosestLeftPosition(int key, int[] array) {
        if (array == null || array.length == 0) {
            return -1; // Array is empty or null
        }

        int left = 0;
        int right = array.length - 1;
        int result = array.length; // Por defecto, si no encuentra, devuelve la posición más grande

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] >= key) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }

    public int[] tiemposEntre(int[] tiempos, Pedido[] p) {
        int[] res = new int[p.length];
        for (int i = 0; i < p.length; i++) {
            int desde = p[i].desde;
            int hasta = p[i].hasta;
            int posDesde = getClosestLeftPosition(desde, tiempos);
            // Busco el hasta +1 (después hago -1 en la respuesta para obtener el de la
            // derecha de hasta)
            int posHasta = getClosestLeftPosition(hasta + 1, tiempos);

            res[i] = posHasta - posDesde; // +1 -1 se cancelan
        }
        return res;
    }

    public static void main(String[] args) {
        Corredores c = new Corredores();

        Pedido[] pedidos = new Pedido[] {
                new Pedido(200, 240),
                new Pedido(180, 210),
                new Pedido(220, 280),
                new Pedido(0, 200),
                new Pedido(290, 10000)
        };

        int[] tiempos = new int[] {
                192,
                200,
                210,
                221,
                229,
                232,
                240,
                240,
                243,
                247,
                280,
                285
        };

        int[] respuestas = c.tiemposEntre(tiempos, pedidos);
        for (int i = 0; i < respuestas.length; i++) {
            System.out.println(respuestas[i]);
        }

    }
}

class Pedido {
    public int desde;
    public int hasta;

    public Pedido(int desde, int hasta) {
        this.desde = desde;
        this.hasta = hasta;
    }
}
