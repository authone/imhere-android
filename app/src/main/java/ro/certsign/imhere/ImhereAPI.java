package ro.certsign.imhere;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ImhereAPI {

    @GET("lasth/")
    Call<List<Pulse>> getLastHour();

    @GET("aftereight/")
    Call<List<Pulse>> getAfterEight();

    @GET("user/{id}")
    Call<Pulse> getPulse(@Path("id") String id);

    @POST("imhere/")
    Call<ResponseBody> postPulse(@Body Pulse pulse);
}

