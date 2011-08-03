package com.anzymus.neogeo.hiscores.service.halloffame;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RedemslugPointCalculatorTest {

    PointCalculator calculator = new RedemslugPointCalculator();

    @Test
    public void should_do_basic_calculation() {
        int maxPoints = 3;
        assertEquals(3, calculator.getPointsByIndex(0, maxPoints));
        assertEquals(2, calculator.getPointsByIndex(1, maxPoints));
        assertEquals(1, calculator.getPointsByIndex(2, maxPoints));
    }

    @Test
    public void should_do_basic_calculation_with_11_players() {
        int maxPoints = 10;
        assertEquals(10, calculator.getPointsByIndex(0, maxPoints));
        assertEquals(9, calculator.getPointsByIndex(1, maxPoints));
        assertEquals(8, calculator.getPointsByIndex(2, maxPoints));
        assertEquals(7, calculator.getPointsByIndex(3, maxPoints));
        assertEquals(6, calculator.getPointsByIndex(4, maxPoints));
        assertEquals(5, calculator.getPointsByIndex(5, maxPoints));
        assertEquals(4, calculator.getPointsByIndex(6, maxPoints));
        assertEquals(3, calculator.getPointsByIndex(7, maxPoints));
        assertEquals(2, calculator.getPointsByIndex(8, maxPoints));
        assertEquals(1, calculator.getPointsByIndex(9, maxPoints));
        assertEquals(0, calculator.getPointsByIndex(10, maxPoints));
        assertEquals(0, calculator.getPointsByIndex(11, maxPoints));
        assertEquals(0, calculator.getPointsByIndex(12, maxPoints));
        assertEquals(0, calculator.getPointsByIndex(13, maxPoints));
    }

}
