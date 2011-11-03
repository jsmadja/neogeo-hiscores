package com.anzymus.neogeo.hiscores.success;

public class AllClearer10TitleStrategyTest extends AbstractAllClearerTitleStrategyTest {

    @Override
    protected TitleUnlockingStrategy getAllClearStrategy() {
        return new AllClearer10TitleStrategy();
    }

    @Override
    protected int getNumAllClearsToCreate() {
        return 10;
    }

}
