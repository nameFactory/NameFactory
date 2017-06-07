package pl.edu.pw.mini.namefactory;

public class ApiGetMatchesRequest {
    String username;
    String password;
    int ranking_id;

    public ApiGetMatchesRequest(String username, String password, int ranking_id) {
        this.username = username;
        this.password = password;
        this.ranking_id = ranking_id;
    }
}
