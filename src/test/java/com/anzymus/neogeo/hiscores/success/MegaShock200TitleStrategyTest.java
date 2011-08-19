package com.anzymus.neogeo.hiscores.success;

public class MegaShock200TitleStrategyTest extends AbstractMegaShockTitleStrategyTest {

    protected TitleUnlockingStrategy getMegaShockStrategy() {
        return new MegaShock200TitleStrategy();
    }

    protected long getNumScoresToCreate() {
        return 200;
    }

}
