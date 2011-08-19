package com.anzymus.neogeo.hiscores.success;

public class MegaShock50TitleStrategyTest extends AbstractMegaShockTitleStrategyTest {

    protected TitleUnlockingStrategy getMegaShockStrategy() {
        return new MegaShock50TitleStrategy();
    }

    protected long getNumScoresToCreate() {
        return 50;
    }

}
