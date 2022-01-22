package com.fullsail.android.adv2.zhonghao_ce03;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DownloadWorker extends Worker {
    private final Context mContext;
    private final String URL_BASE = "http://api.weatherapi.com/v1/current.json?key=2fed76e6ac4f4c07acf35245221801&q=";
    private boolean mForcast = false;

    public DownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        String webAddress = URL_BASE;
        String jsonData = "";
        HttpURLConnection connection = null;
        // Input stream reference
        InputStream is = null;

        try {
            URL url = new URL(webAddress);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // Get the stream
            is = connection.getInputStream();
            // Convert the stream to a string (think about out utils lib)
            jsonData = IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            // If we have a stream, try to close it.
            try{
                assert is != null;
                is.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            // If we have a connection disconnect it
            connection.disconnect();
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return Result.success();
    }
}
