package com.fullsail.android.adv2.zhonghao_ce01;

import static android.os.Environment.getExternalStoragePublicDirectory;

import android.os.Environment;
import android.provider.ContactsContract;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FileUtility {

    protected static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                timeStamp,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    protected static File[] getImageFiles() {
        File files = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        return files.listFiles();
    }
}
