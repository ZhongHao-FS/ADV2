package com.fullsail.android.adv2.zhonghao_ce03;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadWorker extends Worker {
    private final Context mContext;
    private final String URL_BASE = "http://api.weatherapi.com/v1";
    private boolean mForcast = false;

    public DownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        String webAddress = URL_BASE;

        try {
            URL url = new URL(webAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.success();
    }
}
