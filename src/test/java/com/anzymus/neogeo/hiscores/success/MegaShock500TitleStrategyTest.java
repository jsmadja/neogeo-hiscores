package com.anzymus.neogeo.hiscores.success;

public class MegaShock500TitleStrategyTest extends AbstractMegaShockTitleStrategyTest {

    protected TitleUnlockingStrategy getMegaShockStrategy() {
        return new MegaShock500TitleStrategy();
    }

    protected long getNumScoresToCreate() {
        return 500;
    }

}
