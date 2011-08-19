package com.anzymus.neogeo.hiscores.success;

public class MegaShock20TitleStrategyTest extends AbstractMegaShockTitleStrategyTest {

    protected TitleUnlockingStrategy getMegaShockStrategy() {
        return new MegaShock20TitleStrategy();
    }

    protected long getNumScoresToCreate() {
        return 20;
    }

}
