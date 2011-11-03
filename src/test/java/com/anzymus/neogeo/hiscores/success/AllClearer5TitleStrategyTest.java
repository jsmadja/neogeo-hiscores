package com.anzymus.neogeo.hiscores.success;

public class AllClearer5TitleStrategyTest extends AbstractAllClearerTitleStrategyTest {

    @Override
    protected TitleUnlockingStrategy getAllClearStrategy() {
        return new AllClearer5TitleStrategy();
    }

    @Override
    protected int getNumAllClearsToCreate() {
        return 5;
    }

}
