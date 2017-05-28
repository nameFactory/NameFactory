package pl.edu.pw.mini.namefactory;

import java.io.IOException;

public class ApiWrapper {
    static private ApiService apiService = ApiService.retrofit.create(ApiService.class);
    private String username;
    private String password;

    static ApiNewUser createNewUser(String email) throws IOException {
        return apiService.createNewUserAccount(new ApiNewUserRequest(email)).execute().body();
    }

    static ApiNamesDB getNamesDB() throws IOException {
        return apiService.getNamesDB().execute().body();
    }

    public ApiWrapper(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void createNewRanking(int ranking_id, boolean is_male, int tag_ids[]) throws IOException {
        apiService.createNewRanking(new ApiNewRankingRequest(this.username, this.password, ranking_id, is_male, tag_ids)).execute().body();
    }

    public void createNewMatch(int ranking_id, int winner_id, int loser_id) throws IOException {
        apiService.createNewMatch(new ApiNewMatchRequest(this.username, this.password, ranking_id, winner_id, loser_id)).execute().body();
    }

    public ApiGetMatches getMatches(int ranking_id) throws IOException {
        return apiService.getMatchesList(new ApiGetMatchesRequest(this.username, this.password, ranking_id)).execute().body();
    }
}
