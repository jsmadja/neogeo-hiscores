package com.neogeohiscores.web.services;

import com.anzymus.neogeo.hiscores.success.TitleUnlockingStrategy;
import com.neogeohiscores.entities.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static java.text.MessageFormat.format;

public class TitleBoard {

    @Inject
    private Session session;

    @Inject
    private GameRoom gameRoom;

    @Inject
    private ScoreBoard scoreBoard;

    private static final Logger LOG = LoggerFactory.getLogger(TitleBoard.class);

    private Map<Title, TitleUnlockingStrategy> strategiesByTitle;

    public void TitleBoard() {
        this.strategiesByTitle = findAllStrategies();
    }

    public List<UnlockedTitle> findLastUnlockedTitlesOrderByDateDesc() {
        return session.createCriteria(UnlockedTitle.class).setCacheable(true).addOrder(Order.desc("unlockDate")).setMaxResults(10).list();
    }

    public List<RelockedTitle> findLastRelockedTitlesOrderByDateDesc() {
        return session.createCriteria(RelockedTitle.class).addOrder(Order.desc("relockDate")).setMaxResults(10).setMaxResults(10).list();
    }

    // TODO @Asynchronous
    public void relockTitles(Score relockerScore) {
        Player relockerPlayer = relockerScore.getPlayer();
        List<Player> players = gameRoom.findAllPlayers();
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
        gameRoom.merge(player);
    }

    private RelockedTitle createRelockedTitle(Score relockerScore, Player player, Title title) {
        RelockedTitle relockedTitle = new RelockedTitle();
        relockedTitle.setPlayer(player);
        relockedTitle.setRelockerScore(relockerScore);
        relockedTitle.setTitle(title);
        relockedTitle = (RelockedTitle) session.merge(relockedTitle);
        return relockedTitle;
    }

    public void searchUnlockedTitlesFor(Player player) {
        strategiesByTitle = findAllStrategies();
        for (Title title : strategiesByTitle.keySet()) {
            TitleUnlockingStrategy strategy = strategiesByTitle.get(title);
            if (isUnlockable(player, title, strategy)) {
                Set<UnlockedTitle> unlockedTitles = player.getUnlockedTitles();
                UnlockedTitle unlockedTitle = new UnlockedTitle(player, title);
                unlockedTitles.add(unlockedTitle);
            }
        }
        gameRoom.merge(player);
    }

    private boolean isUnlockable(Player player, Title title, TitleUnlockingStrategy strategy) {
        return player.hasNotUnlocked(title) && strategy.isUnlockable(player);
    }

    public boolean isRelockable(Title title, Player player) {
        return !strategiesByTitle.get(title).isUnlockable(player);
    }

    public void remove(UnlockedTitle unlockedTitle) {
        unlockedTitle = (UnlockedTitle) session.load(UnlockedTitle.class, unlockedTitle.getId());
        unlockedTitle.getPlayer().getUnlockedTitles().remove(unlockedTitle);
    }

    public Map<Title, TitleUnlockingStrategy> findAllStrategies() {
        Map<Title, TitleUnlockingStrategy> strategies = new HashMap<Title, TitleUnlockingStrategy>();
        for (Title title : new ArrayList<Title>(findAllTitles())) {
            try {
                String classname = title.getClassname();
                TitleUnlockingStrategy strategy = (TitleUnlockingStrategy) Class.forName(classname).newInstance();
                strategy.initialize(this, scoreBoard);
                strategy.setTitle(title);
                strategies.put(title, strategy);
            } catch (Exception e) {
                LOG.warn("Can't create strategy from title: " + title, e);
            }
        }
        return strategies;
    }

    private List findAllTitles() {
        return session.createCriteria(Title.class).setCacheable(true).list();
    }

    public List<Achievement> getAchievementsFor(Player player) {
        Map<Title, TitleUnlockingStrategy> StrategiesByTitle = findAllStrategies();
        Collection<TitleUnlockingStrategy> strategies = StrategiesByTitle.values();
        List<Achievement> achievements = new ArrayList<Achievement>();
        for (TitleUnlockingStrategy strategy : strategies) {
            achievements.add(strategy.getAchievementFor(player));
        }
        return achievements;
    }
}
