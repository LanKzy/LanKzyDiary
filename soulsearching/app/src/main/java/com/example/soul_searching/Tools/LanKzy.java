package com.example.soul_searching.Tools;

import android.Manifest;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.os.Environment;

import androidx.core.app.ActivityCompat;

import com.example.soul_searching.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LanKzy {
    public static String file;

    private static String path;

    public static Map<String,List<GridParams>> getDataList() {
        return dataList;
    }

    private static Map<String,List<GridParams>> dataList;

    public static String[] placeHolder = new String[]{
        "今天天气","今天吃啥","玩啥游戏","看啥电影","诶嘿嘿~","耶耶耶"
    };

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    public static void SaveData(DiaryData data) {
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        verifyStoragePermissions(MainActivity.Ins);
        //if(state.equals(Environment.MEDIA_MOUNTED)){
        System.err.println(file);
        System.err.println(path);
        try {
            ContextWrapper cw = new ContextWrapper(MainActivity.Ins.getApplicationContext());
            File directory = cw.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(directory, "temp");
            System.err.println("Save:" + data.dataList);
            fos = new FileOutputStream(file);
            System.err.println("Save:" + data.dataList);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.flush();
            oos.close();
            fos.close();
            System.err.println("Save:" + data.dataList);
        } catch (Exception e) {
            System.err.println("啦啦啦~~~" + e);
        }
    }

    public static DiaryData GetData() {
        path = Environment.getExternalStorageDirectory().getAbsolutePath();
        ObjectInputStream ois = null;
        FileInputStream fis = null;
        verifyStoragePermissions(MainActivity.Ins);
        try {
            ContextWrapper cw = new ContextWrapper(MainActivity.Ins.getApplicationContext());
            File directory = cw.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(directory, "temp");
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);

            DiaryData data = (DiaryData) ois.readObject();
            ois.close();
            fis.close();
            dataList = data.dataList;
            return data;
        } catch (Exception e) {
            System.err.println(e);
        }

        return null;
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        System.err.println("Verify:" + permission);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
