package ro.certsign.imhere;

import android.support.v7.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImhereControllerBase {

    protected ImhereAPI   mImhereAPI = null;
    protected String      mServerUrl = "http://10.0.2.2:3000/";
    protected Gson        mGson;
    protected Retrofit    mRetrofit;

    ImhereControllerBase() {
        this.mServerUrl =   PreferenceManager.getDefaultSharedPreferences(MainActivity.context)
                            .getString(SettingsActivity.KEY_PREF_SERVER, "http://10.0.2.2:3000/");

        this.mGson = new GsonBuilder().setLenient().create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(this.mServerUrl)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();

        mImhereAPI = mRetrofit.create(ImhereAPI.class);
    }
}
