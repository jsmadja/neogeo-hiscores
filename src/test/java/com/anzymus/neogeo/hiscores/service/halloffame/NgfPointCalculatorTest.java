package com.anzymus.neogeo.hiscores.service.halloffame;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class NgfPointCalculatorTest {

    PointCalculator calculator = new NgfPointCalculator();

    @Test
    public void should_do_basic_calculation() {
        int maxPoints = 1000;
        assertEquals(10, calculator.getPointsByIndex(0, maxPoints));
        assertEquals(8, calculator.getPointsByIndex(1, maxPoints));
        assertEquals(6, calculator.getPointsByIndex(2, maxPoints));
        assertEquals(5, calculator.getPointsByIndex(3, maxPoints));
        assertEquals(4, calculator.getPointsByIndex(4, maxPoints));
        assertEquals(3, calculator.getPointsByIndex(5, maxPoints));
        assertEquals(2, calculator.getPointsByIndex(6, maxPoints));
        assertEquals(1, calculator.getPointsByIndex(7, maxPoints));
        assertEquals(0, calculator.getPointsByIndex(8, maxPoints));
        assertEquals(0, calculator.getPointsByIndex(9, maxPoints));
    }

}
