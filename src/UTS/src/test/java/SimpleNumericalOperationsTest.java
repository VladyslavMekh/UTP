import org.example.SimpleNumericalOperations;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleNumericalOperationsTest {

    SimpleNumericalOperations simpleNumericalOperations =
            new SimpleNumericalOperations();

    @Test
    void testAddition() {
        int result = simpleNumericalOperations.add(3, 5);
        assertEquals(8, result);
    }

    @Test
    void testPosNeg() {
        assertAll(
                () -> assertTrue(simpleNumericalOperations.isPositive(5)),
                () -> assertFalse(simpleNumericalOperations.isPositive(-5)),
                () -> assertTrue(simpleNumericalOperations.isNegative(-5)),
                () -> assertFalse(simpleNumericalOperations.isNegative(5))
        );
    }
}
