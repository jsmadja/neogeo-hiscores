package com.anzymus.neogeo.hiscores.success;

public class AllClearerTitleStrategyTest extends AbstractAllClearerTitleStrategyTest {

    @Override
    protected TitleUnlockingStrategy getAllClearStrategy() {
        return new AllClearerTitleStrategy();
    }

    @Override
    protected int getNumAllClearsToCreate() {
        return 1;
    }

}
