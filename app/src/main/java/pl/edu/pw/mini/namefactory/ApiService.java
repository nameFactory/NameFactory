package pl.edu.pw.mini.namefactory;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("names_db")
    Call<ApiNamesDB> getNamesDB();

    @POST("user")
    Call<ApiNewUser> createNewUserAccount(@Body ApiNewUserRequest body);

    @POST("ranking")
    Call<ApiEmptyResponse> createNewRanking(@Body ApiNewRankingRequest body);

    @POST("match")
    Call<ApiEmptyResponse> createNewMatch(@Body ApiNewMatchRequest body);

    @POST("match_list")
    Call<ApiGetMatches> getMatchesList(@Body ApiGetMatchesRequest body);

    @GET("top50")
    Call<ApiTop> getTop50();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.namefactory.pl/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
