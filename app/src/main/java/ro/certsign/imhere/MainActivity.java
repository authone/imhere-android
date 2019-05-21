package ro.certsign.imhere;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
//import android.support.constraint.Constraints;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.support.v4.content.LocalBroadcastManager;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    public static String PULSES_UPDATE_ACTION_AFTER8 = "ro.certsign.imhere.PULSES_UPDATE_ACTION_AFTER8";
    public static String PULSES_UPDATE_ACTION_LASTH = "ro.certsign.imhere.PULSES_UPDATE_ACTION_LASTH";
    public static Context context;

    private FloatingActionButton btnRefresh = null;
    private FloatingActionButton btnSettings = null;
    private Switch swEnable = null;
    private RecyclerView recicleViewPulses = null;
    private PulsesViewAdapter viewAdapterPulses = null;

//    private ImhereControllerGet imhereControllerGet = null;

    private BroadcastReceiver bcastReceiver = null;

    PeriodicWorkRequest pulseSendWorkerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();

        btnRefresh = findViewById(R.id.buttonRefresh);
        btnSettings = findViewById(R.id.buttonSettings);
        swEnable = findViewById(R.id.enablePulse);

        viewAdapterPulses = new PulsesViewAdapter();
        recicleViewPulses = findViewById(R.id.listView);
        recicleViewPulses.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recicleViewPulses.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recicleViewPulses.setAdapter(viewAdapterPulses);

//        imhereControllerGet = new ImhereControllerGet();

        bcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("imhere", "bcastReceiver: onReceive: " + intent.getAction());
                ArrayList<Pulse> pulses = (ArrayList<Pulse>) intent.getSerializableExtra("all_pulses");
                if(intent.getAction() == MainActivity.PULSES_UPDATE_ACTION_AFTER8) {
                    viewAdapterPulses.setPulsesAfter8(pulses);
                }
                else if(intent.getAction() == MainActivity.PULSES_UPDATE_ACTION_LASTH) {
                    viewAdapterPulses.setPulsesLasth(pulses);
                }
            }
        };

        LocalBroadcastManager.getInstance(context).registerReceiver(
            bcastReceiver,
            new IntentFilter(MainActivity.PULSES_UPDATE_ACTION_AFTER8)
        );
        LocalBroadcastManager.getInstance(context).registerReceiver(
            bcastReceiver,
            new IntentFilter(MainActivity.PULSES_UPDATE_ACTION_LASTH)
        );

        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(false).setRequiredNetworkType(NetworkType.UNMETERED)
                .build();

        pulseSendWorkerTask = new PeriodicWorkRequest.Builder(PulseSendWorker.class, 6, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImhereControllerGet imhereControllerGet = new ImhereControllerGet();
                imhereControllerGet.getPulses();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.context, SettingsActivity.class);
                startActivity(intent);
            }
        });

        swEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be true if the switch is in the On position
                if(isChecked) {
                    WorkManager.getInstance().enqueue(pulseSendWorkerTask);
                    Log.d("imhere", "WorkManager: pulse sending task was activated");
                }
                else {
                    WorkManager.getInstance().cancelWorkById(pulseSendWorkerTask.getId());
                    Log.d("imhere", "WorkManager: pulse sending task was canceled");
                }
            }
        });

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        /* * Step 4: Ensure to unregister the receiver when the activity is destroyed so that
         *   you don't face any memory leak issues in the app */
        Log.d("imhere", "appCompatActivity: onStop: activity unregistered");
//        if(bcastReceiver != null) { unregisterReceiver(bcastReceiver); }
    }
}
