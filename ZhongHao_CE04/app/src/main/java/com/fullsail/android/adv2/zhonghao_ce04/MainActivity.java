package com.fullsail.android.adv2.zhonghao_ce04;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final ArrayList<Uri> mImageUriPaths = new ArrayList<>();
    Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    String[] projection = { MediaStore.Images.Media.DATA };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            loadGalleryImages();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    loadGalleryImages();
                } else {
                    Log.i("Permission", "was denied!");
                }
            });

    private void loadGalleryImages() {
        Cursor cursor = getContentResolver()
                .query(uri, projection, null, null, null);
        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                // long id = cursor.getLong(idColumn);
                // Uri imageUri = ContentUris.withAppendedId(uri, id);
                String imageString = cursor.getString(idColumn);
                Uri imageUri = Uri.parse(imageString);
                mImageUriPaths.add(imageUri);
            }

            if (mImageUriPaths != null) {
                showGridView();
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Select Images"), 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                int count = clipData.getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    mImageUriPaths.add(imageUri);
                }
            } else {
                Uri singleUri = data.getData();
                mImageUriPaths.add(singleUri);
            }
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