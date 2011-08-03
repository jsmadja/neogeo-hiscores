package com.anzymus.neogeo.hiscores.service.halloffame;

class RedemslugPointCalculator implements PointCalculator {
    @Override
    public int getPointsByIndex(int i, int maxPoints) {
        return maxPoints - i < 0 ? 0 : maxPoints - i;
    }
}
