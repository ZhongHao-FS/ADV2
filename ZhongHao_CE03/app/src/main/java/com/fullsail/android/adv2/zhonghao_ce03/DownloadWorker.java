package com.fullsail.android.adv2.zhonghao_ce03;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DownloadWorker extends Worker {
    private final Context mContext;

    public DownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        final String URL_BASE = "http://api.weatherapi.com/v1";

        return null;
    }
}
