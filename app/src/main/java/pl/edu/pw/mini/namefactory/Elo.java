package pl.edu.pw.mini.namefactory;

public class Elo {
    private static double getExpectedScore(double a, double b) {
        return 1.0 / (1 + Math.pow(10, (b - a)/400));
    }

    public static EloUpdatedPair getUpdatedScore(double winnerPoints, double loserPoints) {
        double K = 64.0;
        double expectedWinnerScore = getExpectedScore(winnerPoints, loserPoints);
        double expectedLoserScore = getExpectedScore(loserPoints, winnerPoints);
        double updatedWinnerPoints = winnerPoints + K * (1.0 - expectedWinnerScore);
        double updatedLoserPoints = loserPoints - K * expectedLoserScore;
        return new EloUpdatedPair(updatedWinnerPoints, updatedLoserPoints);
    }
}
