package com.anzymus.neogeo.hiscores.clients;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.anzymus.neogeo.hiscores.domain.Game;
import com.anzymus.neogeo.hiscores.domain.Player;
import com.anzymus.neogeo.hiscores.domain.Score;

public class MessagesTest {

    @Test
    public void should_format_score() {
        Player player = new Player();
        String value = "12345";
        Game game = new Game("game");
        String level = "MVS";
        String pictureUrl = "http://www.image.com/id/1";
        Score score = new Score(value, player, level, game, pictureUrl);
        score.setMessage("(stage 5)");

        String expected = "game - [URL=\"http://www.image.com/id/1\"][SIZE=\"3\"]12.345[/SIZE][/URL]\n" //
                + "[I](stage 5)[/I]\n" //
                + "[SIZE=\"1\"]posté depuis [url]www.neogeo-hiscores.com[/url][/SIZE]";
        assertEquals(expected, Messages.toMessage(score));
    }

    @Test
    public void should_format_score_with_all_clear_status() {
        Player player = new Player();
        String value = "12345";
        Game game = new Game("game");
        String level = "MVS";
        String pictureUrl = "http://www.image.com/id/1";
        Score score = new Score(value, player, level, game, pictureUrl);
        score.setMessage("(stage 5)");
        score.setAllClear(true);

        String expected = "game - [URL=\"http://www.image.com/id/1\"][SIZE=\"3\"]12.345[/SIZE][/URL]\n" //
                + "[I](stage 5) - All clear![/I]\n" //
                + "[SIZE=\"1\"]posté depuis [url]www.neogeo-hiscores.com[/url][/SIZE]";
        assertEquals(expected, Messages.toMessage(score));
    }

    @Test
    public void should_format_score_with_stage() {
        Player player = new Player();
        String value = "12345";
        Game game = new Game("game");
        String level = "MVS";
        String pictureUrl = "http://www.image.com/id/1";
        Score score = new Score(value, player, level, game, pictureUrl);
        score.setMessage("message");
        score.setAllClear(true);
        score.setStage("stage 5");

        String expected = "game - [URL=\"http://www.image.com/id/1\"][SIZE=\"3\"]12.345[/SIZE][/URL]\n" //
                + "[I]message - stage 5 - All clear![/I]\n" //
                + "[SIZE=\"1\"]posté depuis [url]www.neogeo-hiscores.com[/url][/SIZE]";
        assertEquals(expected, Messages.toMessage(score));
    }

}
