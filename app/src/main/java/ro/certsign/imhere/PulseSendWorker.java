package ro.certsign.imhere;

//import androidx.work.Worker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class PulseSendWorker extends Worker {
    ImhereControllerGet controller;
    public PulseSendWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("imhere", "PulseSendWorker: sending pulse");
        ImhereControllerPut imhereControllerPut = new ImhereControllerPut();
        imhereControllerPut.postPulse();
        return Result.success();
    }
}
