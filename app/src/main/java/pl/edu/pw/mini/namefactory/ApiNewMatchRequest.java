package pl.edu.pw.mini.namefactory;

class ApiNewMatchRequest {
    String username;
    String password;
    int ranking_id;
    int winner_id;
    int loser_id;

    public ApiNewMatchRequest(String username, String password, int ranking_id, int winner_id, int loser_id) {
        this.username = username;
        this.password = password;
        this.ranking_id = ranking_id;
        this.winner_id = winner_id;
        this.loser_id = loser_id;
    }
}
