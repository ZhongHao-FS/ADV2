package com.fullsail.android.adv2.zhonghao_ce04;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TestMain extends AppCompatActivity {
    private final ArrayList<Uri> mImageUriPaths = new ArrayList<>();
    Uri uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
    String[] projection = { MediaStore.Images.Media._ID };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor cursor = this.getContentResolver()
                .query(uri, projection, null, null, null);
        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        Log.i("cursor", String.valueOf(cursor.getCount()));

        while (cursor.moveToNext()) {
            long id = cursor.getLong(idColumn);
            Uri imageUri = ContentUris.withAppendedId(uri, id);
            mImageUriPaths.add(imageUri);
            Log.i("Image", "added");
        }

        if (mImageUriPaths != null) {
            showGridView();
        }
    }

    private void showGridView() {
        GridView imageGrid = findViewById(R.id.gridView);
        GridAdapter adapter = new GridAdapter(this, mImageUriPaths);
        imageGrid.setAdapter(adapter);
    }
}
