package pl.edu.pw.mini.namefactory;

public class EloUpdatedPair {
    private double winnerPoints;
    private double loserPoints;

    public EloUpdatedPair(double winnerPoints, double loserPoints) {
        this.winnerPoints = winnerPoints;
        this.loserPoints = loserPoints;
    }

    public double getWinnerPoints() {
        return winnerPoints;
    }

    public double getLoserPoints() {
        return loserPoints;
    }
}
