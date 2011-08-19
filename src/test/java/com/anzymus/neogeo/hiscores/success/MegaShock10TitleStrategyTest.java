package com.anzymus.neogeo.hiscores.success;

public class MegaShock10TitleStrategyTest extends AbstractMegaShockTitleStrategyTest {

    protected TitleUnlockingStrategy getMegaShockStrategy() {
        return new MegaShock10TitleStrategy();
    }

    protected long getNumScoresToCreate() {
        return 10;
    }

}
