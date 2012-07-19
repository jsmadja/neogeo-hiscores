/**
 *     Copyright (C) 2011 Julien SMADJA <julien dot smadja at gmail dot com>
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.neogeohiscores.web.services;

import static java.text.MessageFormat.format;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Query;

import com.neogeohiscores.entities.Player;
import com.neogeohiscores.entities.RelockedTitle;
import com.neogeohiscores.entities.Score;
import com.neogeohiscores.entities.Title;
import com.neogeohiscores.entities.UnlockedTitle;

public class TitleRelockingService extends GenericService<RelockedTitle> {

    private static final Log LOG = LogFactory.getLog(TitleRelockingService.class);

    @Inject
    private PlayerService playerService;

    @Inject
    private TitleUnlockingService titleUnlockingService;

    public TitleRelockingService() {
        super(RelockedTitle.class);
    }

    // TODO @Asynchronous
    public void relockTitles(Score relockerScore) {
        Player relockerPlayer = relockerScore.getPlayer();
        List<Player> players = playerService.findAll();
        for (Player player : players) {
            if (!player.equals(relockerPlayer)) {
                relockTitles(relockerPlayer, relockerScore, player);
            }
        }
    }

    private void relockTitles(Player relockerPlayer, Score relockerScore, Player player) {
        Set<UnlockedTitle> unlockedTitles = player.getUnlockedTitles();
        List<UnlockedTitle> titlesToRemove = new ArrayList<UnlockedTitle>();
        for (UnlockedTitle unlockedTitle : unlockedTitles) {
            Title title = unlockedTitle.getTitle();
            if (isRelockable(title, player)) {
                RelockedTitle relockedTitle = createRelockedTitle(relockerScore, player, title);
                player.getRelockedTitles().add(relockedTitle);
                titlesToRemove.add(unlockedTitle);
                LOG.info(format("The score on {0} posted by {1} relocked the title {2} of player {3}", relockerScore.getGame(), relockerPlayer, title.getLabel(), player));
            }
        }
        unlockedTitles.removeAll(titlesToRemove);
        playerService.merge(player);
    }

    private RelockedTitle createRelockedTitle(Score relockerScore, Player player, Title title) {
        RelockedTitle relockedTitle = new RelockedTitle();
        relockedTitle.setPlayer(player);
        relockedTitle.setRelockerScore(relockerScore);
        relockedTitle.setTitle(title);
        relockedTitle = store(relockedTitle);
        return relockedTitle;
    }

    private boolean isRelockable(Title title, Player player) {
        return titleUnlockingService.isRelockable(title, player);
    }

    public void deleteAll() {
        Query query = session.createQuery("DELETE FROM RelockedTitle");
        query.executeUpdate();
    }

}
