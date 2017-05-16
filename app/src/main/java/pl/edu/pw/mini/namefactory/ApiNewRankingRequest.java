package pl.edu.pw.mini.namefactory;

class ApiNewRankingRequest {
    String username;
    String password;
    int ranking_id;
    int tag_ids[];

    public ApiNewRankingRequest(String username, String password, int ranking_id, int[] tag_ids) {
        this.username = username;
        this.password = password;
        this.ranking_id = ranking_id;
        this.tag_ids = tag_ids;
    }
}
