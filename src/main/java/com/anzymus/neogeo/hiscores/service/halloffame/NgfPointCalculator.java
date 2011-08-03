package com.anzymus.neogeo.hiscores.service.halloffame;

class NgfPointCalculator implements PointCalculator {
    private static final int[] POINTS = new int[] { 10, 8, 6, 5, 4, 3, 2, 1 };

    @Override
    public int getPointsByIndex(int i, int maxPoints) {
        if (i >= POINTS.length || i < 0) {
            return 0;
        }
        return POINTS[i];
    }
}
