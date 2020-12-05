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
import java.util.HashMap;
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


    public static void SaveData(Map<String,List<GridParams>> data) {
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
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.flush();
            oos.close();
            fos.close();
        } catch (Exception e) {
            System.err.println("啦啦啦~~~" + e);
        }
    }

    public static Map<String,List<GridParams>> GetData() {
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

            Map<String,List<GridParams>> data = (Map<String,List<GridParams>>) ois.readObject();
            ois.close();
            fis.close();
            dataList = data;
            System.err.println("Set dataList");
            if(dataList == null){
                System.err.println("new dataList");
                dataList = new HashMap<String,List<GridParams>>();
            }
            return dataList;
        } catch (Exception e) {
            System.err.println("初始化失败");
            System.err.println(e);
        }
        dataList = new HashMap<String,List<GridParams>>();
        System.err.println("return new");
        return dataList;
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
