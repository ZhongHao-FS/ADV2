package com.fullsail.android.adv2.zhonghao_ce04;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final ArrayList<String> mImagePaths = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor mCursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            int index = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            mImagePaths.add(mCursor.getString(index));
            mCursor.moveToNext();
        }
        mCursor.close();
        if (mImagePaths != null) {
            showGridView();
        }
    }

    private void showGridView() {
        GridView imageGrid = findViewById(R.id.gridView);
        GridAdapter adapter = new GridAdapter(this, mImagePaths);
        imageGrid.setAdapter(adapter);
        Log.i("show", "GridView");
    }
}