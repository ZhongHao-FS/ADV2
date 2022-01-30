package com.fullsail.android.adv2.zhonghao_ce04;


import android.content.Context;
import android.net.Uri;

import java.io.File;

public class FileUtility {
    private static final String IMAGE_FOLDER = "content://com.android.providers.media.documents/document/";

    protected static File[] getImageFiles(Context _context) {
        Uri uri = Uri.parse(IMAGE_FOLDER);
        File protectedStorage = new File(IMAGE_FOLDER);

        return protectedStorage.listFiles();
    }
}
