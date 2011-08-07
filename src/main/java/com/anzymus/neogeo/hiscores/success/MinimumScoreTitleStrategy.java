package com.anzymus.neogeo.hiscores.success;

import com.anzymus.neogeo.hiscores.domain.Player;

public abstract class MinimumScoreTitleStrategy extends AbstractTitleStrategy {
    
    @Override
    public boolean isUnlocked(Player player) {
        return titleService.getNumScoresByPlayer(player) >= getNumScoresToUnlock();
    }

    protected abstract long getNumScoresToUnlock();
    
}
