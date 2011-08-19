package com.anzymus.neogeo.hiscores.domain;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SoccerScoreTest {

    @Test
    public void should_compare_two_equal_score() {
        Score score = new Score("2-3-4");
        SoccerScore score1 = new SoccerScore(score);
        SoccerScore score2 = new SoccerScore(score);

        assertEquals(0, score1.compareTo(score2));
    }

}
