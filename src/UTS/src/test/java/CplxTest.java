import org.example.Cplx;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CplxTest {
    private static Cplx cplx = new Cplx(2, 3);

    @Test
    void compareCplxArr() {
        double[] expected = {2.0, 3.0};
        double[] actual = cplx.getCplxArr();
        assertArrayEquals(expected, actual);
    }

    @Test
    void testToString() {
        String expected = "2.0 + 3.0i";
        String actual = cplx.toString();
        assertEquals(expected, actual);
    }

    @Test
    void testAddAss() {
        Cplx result = cplx.add(new Cplx(2, 5));
        assertEquals(new Cplx(4, 8), result);
    }

    @Test
    void testSubAss() {
        Cplx result = cplx.sub(new Cplx(2, 5));
        assertEquals(new Cplx(0, -2), result);
    }

    @BeforeEach
    void setup() {
        cplx = new Cplx(2, 3);
    }
}
