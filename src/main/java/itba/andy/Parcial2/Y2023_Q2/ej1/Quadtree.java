package itba.andy.Parcial2.Y2023_Q2.ej1;

public class Quadtree {

    private QTNode root;

    public Quadtree(Integer[][] matrix) {
        if (!checkDimIsSquareAndEven(matrix))
            throw new RuntimeException("Invalid Dim");

        root = buildQuadtree(matrix, 0, 0, matrix.length);

    }

    private QTNode buildQuadtree(Integer[][] matrix, int x, int y, int size) {
        int quadSize = size / 2;
        int value = matrix[y][x];
        boolean same = true;
        QTNode node = new QTNode();

        // Chequeo si todos los valores en el cuadrante actual son iguales
        for (int i = y; i < y + size && same; i++) {
            for (int j = x; j < x + size && same; j++) {
                if (matrix[i][j] != value) {
                    same = false;
                }
            }
        }

        if (same) {
            node.data = value;
        } else {
            node.data = null;
            node.upperLeft = buildQuadtree(matrix, x, y, quadSize);
            node.upperRight = buildQuadtree(matrix, x + quadSize, y, quadSize);
            node.lowerLeft = buildQuadtree(matrix, x, y + quadSize, quadSize);
            node.lowerRight = buildQuadtree(matrix, x + quadSize, y + quadSize, quadSize);
        }
        return node;
    }

    public Integer[][] toMatrix() {
        int size = getMatrixSize(root);
        Integer[][] matrix = new Integer[size][size];
        fillMatrix(root, matrix, 0, 0, size);
        return matrix;
    }

    private int getHeight(QTNode node) {
        if (node == null)
            return 0;
        if (node.data != null)
            return 1;
        int ul = getHeight(node.upperLeft);
        int ur = getHeight(node.upperRight);
        int ll = getHeight(node.lowerLeft);
        int lr = getHeight(node.lowerRight);
        return 1 + Math.max(Math.max(ul, ur), Math.max(ll, lr));
    }

    private int getMatrixSize(QTNode node) {
        int height = getHeight(node);
        return (int) Math.pow(2, height - 1);
    }

    private void fillMatrix(QTNode node, Integer[][] matrix, int x, int y, int size) {
        if (node == null)
            return;

        if (node.data != null) {
            for (int i = y; i < y + size; i++) {
                for (int j = x; j < x + size; j++) {
                    matrix[i][j] = node.data;
                }
            }
        } else {
            int halfSize = size / 2;
            fillMatrix(node.upperLeft, matrix, x, y, halfSize);
            fillMatrix(node.upperRight, matrix, x + halfSize, y, halfSize);
            fillMatrix(node.lowerLeft, matrix, x, y + halfSize, halfSize);
            fillMatrix(node.lowerRight, matrix, x + halfSize, y + halfSize, halfSize);
        }
    }

    private static boolean checkDimIsSquareAndEven(Integer[][] matrix) {
        if (matrix == null)
            return false;

        int dim = matrix.length;

        // es par?
        if (dim % 2 == 1)
            return false;

        // todas las filas tienen la misma dimension?
        for (int rec = 0; rec < dim; rec++) {
            if (matrix[rec].length != dim)
                return false;
        }
        return true;
    }

    public String toString() {
        Integer[][] m = toMatrix();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < m.length; ++i) {
            for (int j = 0; j < m.length; ++j) {
                sb.append(m[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private class QTNode {
        private Integer data;

        private int dimension;

        public int getDim() {
            return dimension;
        }

        private QTNode upperLeft;
        private QTNode upperRight;
        private QTNode lowerLeft;
        private QTNode lowerRight;
    }

    public static void main(String[] args) {

        // caso de uso A
        Integer[][] matrix1 = new Integer[][] {
                { 1, 2 },
                { 3, 1 }
        };

        Quadtree qt1 = new Quadtree(matrix1);
        System.out.println(qt1);

        // caso de uso B
        Integer[][] matrix2 = new Integer[][] {
                { 1, 1 },
                { 1, 1 }
        };

        Quadtree qt2 = new Quadtree(matrix2);
        System.out.println(qt2);

        // caso de uso C
        Integer[][] matrix3 = new Integer[][] {
                { 1, 1, 3, 3 },
                { 1, 2, 3, 3 },
                { 3, 1, 4, 4 },
                { 2, 1, 4, 4 }
        };

        Quadtree qt3 = new Quadtree(matrix3);
        System.out.println(qt3);

        // caso de uso D
        Integer[][] matrix4 = new Integer[][] {
                { 1, 1, 3, 3, 8, 8, 8, 8 },
                { 1, 1, 3, 3, 8, 8, 8, 8 },
                { 3, 1, 4, 4, 8, 8, 8, 8 },
                { 2, 1, 4, 4, 8, 8, 8, 8 },
                { 1, 1, 1, 1, 7, 7, 7, 7 },
                { 1, 1, 1, 1, 7, 7, 7, 7 },
                { 1, 1, 1, 1, 7, 7, 7, 7 },
                { 1, 1, 1, 1, 7, 7, 7, 7 },
        };

        Quadtree qt4 = new Quadtree(matrix4);
        System.out.println(qt4);

    }

}
