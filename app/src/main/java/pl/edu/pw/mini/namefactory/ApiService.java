package pl.edu.pw.mini.namefactory;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiService {
    @GET("names_db")
    Call<NamesDB> getNamesDB();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.namefactory.pl/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
