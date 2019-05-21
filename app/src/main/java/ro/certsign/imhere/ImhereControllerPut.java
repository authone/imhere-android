package ro.certsign.imhere;


import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import java.net.HttpURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImhereControllerPut extends ImhereControllerBase implements Callback<ResponseBody>

    {
        ImhereControllerPut() { super(); }

        public void postPulse() {
            Log.d("imhere", "ImhereControllerGet: postPulses: task enqueued");

            Pulse p = new Pulse();
            p.setId(    PreferenceManager.getDefaultSharedPreferences(MainActivity.context)
                        .getString(SettingsActivity.KEY_PREF_USERID, "unknown")
            );
            p.setName(  PreferenceManager.getDefaultSharedPreferences(MainActivity.context)
                        .getString(SettingsActivity.KEY_PREF_USERNAME, "")
            );
            p.setRoom(  PreferenceManager.getDefaultSharedPreferences(MainActivity.context)
                        .getString(SettingsActivity.KEY_PREF_ROOM, "")
            );
            p.setSmoker(PreferenceManager.getDefaultSharedPreferences(MainActivity.context)
                        .getBoolean(SettingsActivity.KEY_PREF_ISMOKE, false)?1:0
            );
            p.setMsg("imhere");

            Call<ResponseBody> call = mImhereAPI.postPulse(p);
            call.enqueue(this);
        }

        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if(response.code() == HttpURLConnection.HTTP_OK) {
                Log.d("imhere", "ImhereControllerGet: postPulses: success");
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            Log.d("imhere", "ImhereControllerGet: postPulses: failed");
        }

    }
