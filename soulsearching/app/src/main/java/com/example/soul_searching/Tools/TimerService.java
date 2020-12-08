package com.example.soul_searching.Tools;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.soul_searching.MainActivity;
import com.example.soul_searching.R;

public class TimerService extends Service {
    private long timeDelay;
    private String title;
    private String content;

    public TimerService(long l, String s, String content) {
        this.timeDelay = l;
        this.title = s;
        this.content = content;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public TimerService(){
        System.err.println("===============================");
//        this.timeDelay = timeDelay;
//        this.title = title;
//        this.content = content;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final NotificationCompat.Builder builder = GetBuilder();
        System.err.println("Start Service" + startId);
        CountDownTimer cdt = new CountDownTimer(startId,1) {
            @Override
            public void onTick(long millisUntilFinished) {
                //计时中
                System.err.println(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                //弹窗
                MainActivity.Ins.getManager().notify(1,builder.build());
                Context c = getApplicationContext();
                Intent i = new Intent(getApplicationContext(), TimerService.class);
                c.stopService(i);
            }

        };
        System.err.println("StartCommand------------------");
        cdt.start();
        return super.onStartCommand(intent, flags, startId);
    }

    private NotificationCompat.Builder GetBuilder(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        return builder;
    }
}
