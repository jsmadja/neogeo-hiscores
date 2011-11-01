package com.anzymus.neogeo.hiscores.success;

public class AllClearerTitleStrategyTest extends
		AbstractAllClearerTitleStrategyTest {

	protected TitleUnlockingStrategy getAllClearStrategy() {
		return new AllClearerTitleStrategy();
	}

	protected long getNumAllClearsToCreate() {
		return 1;
	}

}
