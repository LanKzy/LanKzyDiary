package com.example.soul_searching.Tools;

import android.Manifest;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.os.Environment;

import androidx.core.app.ActivityCompat;

import com.example.soul_searching.MainActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class LanKzy {
    public static String file;

    private static String path;

    //缓存直接获取这个就行
    public static Map<String,GridParams> getDataList() {
        return dataList;
    }

    private static Map<String,GridParams> dataList;

    private static Map<String,CountDownTimer> sdts;

    public static String[] placeHolder = new String[]{
        "心情怎么样","what is the weather today？","今日花费","special thing？","done thing？","today's mistake？"
    };

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    public static void SaveData(Map<String,GridParams> data) {
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

    public static Map<String,GridParams> GetData() {
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

            Map<String,GridParams> data = (Map<String,GridParams>) ois.readObject();
            ois.close();
            fis.close();
            //每次读取缓存的时候就存到这个全局变量里
            dataList = data;
            System.err.println("Set dataList");
            if(dataList == null){
                System.err.println("new dataList");
                dataList = new HashMap<String,GridParams>();
            }
            return dataList;
        } catch (Exception e) {
            System.err.println("初始化失败");
            System.err.println(e);
        }
        dataList = new HashMap<String,GridParams>();
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

    public static String GetPassword(){
        //拿不到返回的是这个
        String password = "";
        //存的指定路径     cw.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + /password
        //拿的到就返回密码
        FileReader fr = null;
        verifyStoragePermissions(MainActivity.Ins);
        try {
            ContextWrapper cw = new ContextWrapper(MainActivity.Ins.getApplicationContext());
            File directory = cw.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            fr = new FileReader(directory + "/password");
            int tempChar = 0;
            while((tempChar = fr.read()) != -1 ){
                password += (char)tempChar;
            }

            fr.close();
        } catch (Exception e) {
            System.err.println("初始化密码失败");
            System.err.println(e);
        }
        //这里返回
        return password;
    }

    public static void SetPassword(String password){
        FileOutputStream fos = null;
        verifyStoragePermissions(MainActivity.Ins);
        //if(state.equals(Environment.MEDIA_MOUNTED)){
        System.err.println(file);
        System.err.println(path);
        try {
            ContextWrapper cw = new ContextWrapper(MainActivity.Ins.getApplicationContext());
            File directory = cw.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(directory, "password");
            fos = new FileOutputStream(file,false);
            fos.write(password.getBytes());
            fos.flush();
            fos.close();

        } catch (Exception e) {
            System.err.println("啦啦啦~~~" + e);
        }
    }

    public static void SaveImage(GridParams gp,String fileName){
        //图片路径
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName + ".jpg");
        int w = 512;
        int h = 1024;
        //整一个图片出来
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        画笔
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        画布
        Canvas c = new Canvas(bmp);
        //bmp.compress()
        paint.setColor(Color.WHITE);
        paint.setTextSize(16);
//设置一些参数
        int x = 10;
        int y = 10;
        //然后根据数据画在画布上
        for(GridParam g:gp.gridParamList){
            c.drawText(g.placeHolder, x, y, paint);
            //设置一个偏移  要不都画一起了
            y += h / 8 / 2;
            c.drawText(g.content, x, y, paint);
            y += h / 8;
        }
        try {
            OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
            System.err.println("save Image:" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + fileName + ".jpg");
            //画完了保存
            bmp.compress(Bitmap.CompressFormat.JPEG,100,os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}

