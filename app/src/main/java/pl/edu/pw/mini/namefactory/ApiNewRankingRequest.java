package pl.edu.pw.mini.namefactory;

class ApiNewRankingRequest {
    String username;
    String password;
    int ranking_id;
    boolean is_male;
    int tag_ids[];

    public ApiNewRankingRequest(String username, String password, int ranking_id, boolean is_male, int[] tag_ids) {
        this.username = username;
        this.password = password;
        this.ranking_id = ranking_id;
        this.is_male = is_male;
        this.tag_ids = tag_ids;
    }
}
