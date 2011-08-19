package com.anzymus.neogeo.hiscores.success;

public class MegaShock100TitleStrategyTest extends AbstractMegaShockTitleStrategyTest {

    protected TitleUnlockingStrategy getMegaShockStrategy() {
        return new MegaShock100TitleStrategy();
    }

    protected long getNumScoresToCreate() {
        return 100;
    }

}
