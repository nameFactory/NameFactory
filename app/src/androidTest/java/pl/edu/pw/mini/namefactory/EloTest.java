package pl.edu.pw.mini.namefactory;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class EloTest {
    @Test
    public void testPointsIncreaseDecrease() {
        EloUpdatedPair eloUpdatedPair = Elo.getUpdatedScore(1200, 1200);

        assertTrue(eloUpdatedPair.getLoserPoints() < eloUpdatedPair.getWinnerPoints());
    }

    @Test
    public void testPointsAfterTwoMatchesSumUpAndAreCloseEnough() {
        EloUpdatedPair eloUpdatedPair = Elo.getUpdatedScore(1200, 1200);
        double winnerPoints = eloUpdatedPair.getWinnerPoints();
        double loserPoints = eloUpdatedPair.getLoserPoints();
        eloUpdatedPair = Elo.getUpdatedScore(winnerPoints, loserPoints);
        winnerPoints = eloUpdatedPair.getWinnerPoints();
        loserPoints = eloUpdatedPair.getLoserPoints();

        assertTrue(Math.abs(winnerPoints + loserPoints - 2 * 1200) < 0.1);
        assertTrue(Math.abs(winnerPoints - 1258.17) < 0.1);
        assertTrue(Math.abs(loserPoints - 1141.83) < 0.1);
    }
}
