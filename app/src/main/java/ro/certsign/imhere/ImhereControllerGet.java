package ro.certsign.imhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImhereControllerGet extends ImhereControllerBase implements Callback<List<Pulse>> {

    Call<List<Pulse>> callLasth, callAfter8;
    private ArrayList<Pulse> pulsesAfterEight, pulsesLastHour;

    ImhereControllerGet() { super(); }

    public List<Pulse> getLastHour() {return this.pulsesLastHour;}
    public List<Pulse> getAfterEight() {return this.pulsesAfterEight;}

    public void getPulses() {
        Log.d("imhere", "ImhereControllerGet: getPulses: 2 tasks enqueued");
        callLasth = mImhereAPI.getLastHour();
        callAfter8 = mImhereAPI.getAfterEight();
        callLasth.enqueue(this);
        callAfter8.enqueue(this);
    }


    @Override
    public void onResponse(Call<List<Pulse>> call, Response<List<Pulse>> response) {
        if(response.isSuccessful()) {
            ArrayList<Pulse> pulses = (ArrayList<Pulse>) response.body();
            if (call == callAfter8) {
                Log.d("imhere", "retrofit.onResponse: got pulses: " + pulses.toString());
                pulsesAfterEight = pulses;
                Intent in = new Intent(MainActivity.PULSES_UPDATE_ACTION_AFTER8);
                Bundle extras = new Bundle();
                extras.putSerializable("all_pulses", pulses);
                in.putExtras(extras);
                LocalBroadcastManager.getInstance(MainActivity.context).sendBroadcast(in);

//                for(int i=0; i<pulsesAfterEight.size(); i++) {
//                    Pulse currentPulse = pulsesAfterEight.get(i);
//                    Call<Pulse> c = mImhereAPI.getPulse(currentPulse.getId() );
//                    c.enqueue(new Callback<Pulse>() {
//                        @Override
//                        public void onResponse(Call<Pulse> call_p, Response<Pulse> response_p) {
//                            Pulse pulse = response_p.body();
//                        }
//
//                        @Override
//                        public void onFailure(Call<Pulse> call_p, Throwable t_p) {
//
//                        }
//                    });
//                }
            }
            else if (call == callLasth) {
                Log.d("imhere", "retrofit.onResponse: got pulses: " + pulses.toString());
                pulsesLastHour = pulses;
                Intent in = new Intent(MainActivity.PULSES_UPDATE_ACTION_LASTH);
                Bundle extras = new Bundle();
                extras.putSerializable("all_pulses", pulses);
                in.putExtras(extras);
                LocalBroadcastManager.getInstance(MainActivity.context).sendBroadcast(in);
            }
        } else {
            Log.d("imhere", response.errorBody().toString());
        }
    }

    @Override
    public void onFailure(Call<List<Pulse>> call, Throwable t) {
        t.printStackTrace();
    }
}
