package com.bk.bestfind.utils;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.bk.bestfind.application.BaseApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Dell on 24-Jun-17.
 */
public class Utils {

    public static String getPathFile(Uri fileUri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = BaseApplication.getContext().getContentResolver().query(fileUri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();

        return filePath;
    }

    public static String savePhotoLocal(Bitmap bitmap) {

        String desFilePath = "";

        try {
            String desFileName = "Cropped_" + System.currentTimeMillis() + ".png";
            File storageDir = BaseApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File desFile = new File(storageDir, desFileName);
//            File desFile = new File(desFilePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(desFile));
            desFilePath = desFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return desFilePath;
    }
}
