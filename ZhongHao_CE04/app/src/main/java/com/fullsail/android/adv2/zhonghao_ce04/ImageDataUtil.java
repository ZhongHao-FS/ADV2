package com.fullsail.android.adv2.zhonghao_ce04;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ImageDataUtil {
    private static final Uri URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    private static final String[] PROJECTION = { MediaStore.Images.Media.DATA };

    public static ArrayList<Uri> loadGallery(Context _context) {
        ArrayList<Uri> imageUris = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(_context, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            Cursor cursor = _context.getContentResolver()
                    .query(URI, PROJECTION, null, null, null);
            int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String imageString = cursor.getString(dataColumn);
                    Uri imageUri = Uri.parse(imageString);
                    imageUris.add(imageUri);
                }
            } else {
                Log.i("Sorry", "No image was found");
            }
        }

        return imageUris;
    }

}
