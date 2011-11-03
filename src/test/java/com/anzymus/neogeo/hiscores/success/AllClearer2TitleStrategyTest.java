package com.anzymus.neogeo.hiscores.success;

public class AllClearer2TitleStrategyTest extends AbstractAllClearerTitleStrategyTest {

    @Override
    protected TitleUnlockingStrategy getAllClearStrategy() {
        return new AllClearer2TitleStrategy();
    }

    @Override
    protected int getNumAllClearsToCreate() {
        return 2;
    }

}
