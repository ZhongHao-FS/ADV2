package com.fullsail.android.adv2.zhonghao_ce03;


import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ForecastActivity extends AppCompatActivity {
    private ImageView mImageView_1;
    private ImageView mImageView_2;
    private ImageView mImageView_3;
    private TextView mTextView_1;
    private TextView mTextView_2;
    private TextView mTextView_3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        mImageView_1 = findViewById(R.id.imageView);
        mImageView_2 = findViewById(R.id.imageView2);
        mImageView_3 = findViewById(R.id.imageView3);
        mTextView_1 = findViewById(R.id.textView2);
        mTextView_2 = findViewById(R.id.textView3);
        mTextView_3 = findViewById(R.id.textView4);

        if (NetworkUtility.isConnected(this)) {
            downloadForecast();
        }
    }

    private void downloadForecast() {
        String URL_BASE = "http://api.weatherapi.com/v1/forecast.json?key=2fed76e6ac4f4c07acf35245221801&days=3&q=";
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
            return;
        }
        try {
            JSONObject obj = new JSONObject(jsonData);
            JSONObject forecast = obj.getJSONObject("forecast");
            JSONArray forecastday = forecast.getJSONArray("forecastday");
            JSONObject castday1 = forecastday.getJSONObject(0);
            String date1 = castday1.getString("date");
            JSONObject day1 = castday1.getJSONObject("day");
            double c1 = day1.getDouble("avgtemp_c");
            double f1 = day1.getDouble("avgtemp_f");
            JSONObject condition1 = day1.getJSONObject("condition");
            String text1 = condition1.getString("text");
            String icon1 = condition1.getString("icon");
            Uri uri1 = Uri.parse(icon1);
            mTextView_1.setText(text1 + "\n" + f1 + " F (" + c1 + " C)\n" + date1);
            mImageView_1.setImageURI(uri1);

            JSONObject castday2 = forecastday.getJSONObject(1);
            String date2 = castday2.getString("date");
            JSONObject day2 = castday2.getJSONObject("day");
            double c2 = day2.getDouble("avgtemp_c");
            double f2 = day2.getDouble("avgtemp_f");
            JSONObject condition2 = day2.getJSONObject("condition");
            String text2 = condition2.getString("text");
            String icon2 = condition2.getString("icon");
            Uri uri2 = Uri.parse(icon2);
            mTextView_2.setText(text2 + "\n" + f2 + " F (" + c2 + " C)\n" + date2);
            mImageView_2.setImageURI(uri2);

            JSONObject castday3 = forecastday.getJSONObject(2);
            String date3 = castday3.getString("date");
            JSONObject day3 = castday3.getJSONObject("day");
            double c3 = day3.getDouble("avgtemp_c");
            double f3 = day3.getDouble("avgtemp_f");
            JSONObject condition3 = day3.getJSONObject("condition");
            String text3 = condition3.getString("text");
            String icon3 = condition3.getString("icon");
            Uri uri3 = Uri.parse(icon3);
            mTextView_3.setText(text3 + "\n" + f3 + " F (" + c3 + " C)\n" + date3);
            mImageView_3.setImageURI(uri3);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
