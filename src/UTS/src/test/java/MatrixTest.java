import org.example.Matrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixTest {

    @Test
    void testIsSquare() {
        int[][] data = new int[10][4];
        Matrix matrix = new Matrix(data);
        boolean result = matrix.isSquare();
        assertFalse(result, "Matrix is not square");
    }

    @Test
    void testTranspose() {
        Matrix matrix = new Matrix(new int[][]{
                {1, 6, 3},
                {2, 3, 4}
        });
        Matrix transposed = matrix.transpose();
        int[][] expected = new int[][]{
                {1,2},
                {6,3},
                {3,4}
        };
        assertArrayEquals(expected, transposed.getData());
    }

    @Test
    void testAdditionDimensionMismatch() {
        Matrix matrix = new Matrix(new int[][] {
                {1, 6, 3},
                {2, 3, 4}
        });
        Matrix other = new Matrix(new int[][]{
                {2, 3},
                {5, 9}
        });
        assertThrows(IllegalArgumentException.class, () -> matrix.add(other));
    }

    @Test
    void testMultiplicationDimensionMismatch() {
        Matrix matrix = new Matrix(new int[][]{
                {1, 6, 3},
                {2, 3, 4}
        });
        Matrix other = new Matrix(new int[][]{
                {2, 3},
                {5, 9}
        });
        assertThrows(IllegalArgumentException.class, () -> matrix.multiply(other));
    }

    @Test
    void testAdd() {
        Matrix matrix = new Matrix(new int[][] {
                {1, 6, 3},
                {2, 3, 4}
        });
        Matrix addition = matrix.add(new Matrix(new int[][]{
                {2, 3, 6},
                {5, 9, 1}
        }));
        int[][] expected = new int[][]{
                {3, 9, 9},
                {7, 12, 5}
        };
        assertArrayEquals(expected, addition.getData());
    }

    @Test
    void testMultiply() {
        Matrix matrix = new Matrix(new int[][] {
                {1, 6, 3},
                {2, 3, 4}
        });
        Matrix multiplication = matrix.multiply(new Matrix(new int[][]{
                {2, 3},
                {5, 9},
                {2, 2}
        }));
        int[][] expected = new int[][]{
                {38, 63},
                {27, 41}
        };
        assertArrayEquals(expected, multiplication.getData());
    }
}
