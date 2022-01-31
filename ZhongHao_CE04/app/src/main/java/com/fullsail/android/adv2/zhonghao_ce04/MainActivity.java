package com.fullsail.android.adv2.zhonghao_ce04;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
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
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(Intent.createChooser(intent, "Select Images"), 1);
                }
            });

    private void loadGalleryImages() {
        ArrayList<Uri> imageUriList = ImageDataUtil.loadGallery(this);
        showGridView(imageUriList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<Uri> imageUriList = new ArrayList<>();

        if (data != null) {
            if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                int count = clipData.getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    imageUriList.add(imageUri);
                }
            } else {
                Uri singleUri = data.getData();
                imageUriList.add(singleUri);
            }
        }

        showGridView(imageUriList);
    }

    private void showGridView(ArrayList<Uri> uriList) {
        GridView imageGrid = findViewById(R.id.gridView);
        GridAdapter adapter = new GridAdapter(this, uriList);
        imageGrid.setAdapter(adapter);
    }
}