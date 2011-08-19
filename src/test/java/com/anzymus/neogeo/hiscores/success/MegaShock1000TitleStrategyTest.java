package com.anzymus.neogeo.hiscores.success;

public class MegaShock1000TitleStrategyTest extends AbstractMegaShockTitleStrategyTest {

    protected TitleUnlockingStrategy getMegaShockStrategy() {
        return new MegaShock1000TitleStrategy();
    }

    protected long getNumScoresToCreate() {
        return 1000;
    }

}
