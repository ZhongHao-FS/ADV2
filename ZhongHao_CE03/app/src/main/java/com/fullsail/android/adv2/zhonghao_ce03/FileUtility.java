package com.fullsail.android.adv2.zhonghao_ce03;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileUtility {
    private static final String TAG = "FileUtility log:";

    public static File getFileObject(Context _context) {
        File folderPath;
        File storageLocation = _context.getFilesDir();
        folderPath = new File(storageLocation, "AD2/CE03");
        File file = new File(folderPath, "weatherLog");

        if (!file.exists()) {
            try {
                boolean mkd = folderPath.mkdirs();
                Log.i(TAG, "Directory is made: " + mkd);
                boolean create = file.createNewFile();
                Log.i(TAG, "File is created: " + create);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    public static void writeArrayList(Context _context, ArrayList<String> _weatherInfo) {
        File file = getFileObject(_context);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeChars(_weatherInfo.get(0) + "|");
            oos.writeChars(_weatherInfo.get(1) + "|");
            oos.writeChars(_weatherInfo.get(2) + "|");
            oos.writeChars(_weatherInfo.get(3));
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readArrayList(Context _context) {
        ArrayList<String> arrayList = new ArrayList<>();
        File file = getFileObject(_context);

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();
            if (obj != null) {
                arrayList = (ArrayList<String>) obj;
            }
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return arrayList;
    }
}
