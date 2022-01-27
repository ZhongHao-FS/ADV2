package com.fullsail.android.adv2.zhonghao_ce03;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DownloadWorker extends Worker {
    private final Context mContext;
    private final String URL_BASE = "http://api.weatherapi.com/v1/current.json?key=2fed76e6ac4f4c07acf35245221801&q=";

    public DownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        String webAddress = URL_BASE + PreferenceFragment.LOCATION;
        String jsonData = "";
        HttpURLConnection connection;

        try {
            URL url = new URL(webAddress);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // Convert the stream to a string (think about out utils lib)
            jsonData = IOUtils.toString(url, StandardCharsets.UTF_8);
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (jsonData.equals("")) {
            return Result.failure();
        }
        try {
            JSONObject obj = new JSONObject(jsonData);
            JSONObject location = obj.getJSONObject("location");
            String localTime = location.getString("localtime");
            JSONObject current = obj.getJSONObject("current");
            double c = current.getDouble("temp_c");
            double f = current.getDouble("temp_f");
            JSONObject condition = current.getJSONObject("condition");
            String text = condition.getString("text");
            String icon = condition.getString("icon");

            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(text);
            arrayList.add(f + " F (" + c + " C)");
            arrayList.add(localTime);
            arrayList.add(icon);
            FileUtility.writeArrayList(mContext, arrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (SimpleWidgetProvider.mAppWidgetManager != null && SimpleWidgetProvider.mAppWidgetIds != null) {
            WidgetUtil.updateWidget(mContext, SimpleWidgetProvider.mAppWidgetManager, SimpleWidgetProvider.mAppWidgetIds);
        }
        return Result.success();
    }
}
