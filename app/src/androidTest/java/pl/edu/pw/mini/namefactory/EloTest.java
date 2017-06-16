package pl.edu.pw.mini.namefactory;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.edu.pw.mini.namefactory.DatabasePackage.Database;
import pl.edu.pw.mini.namefactory.DatabasePackage.DatabaseHandler;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.InstrumentationRegistry.getTargetContext;
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
