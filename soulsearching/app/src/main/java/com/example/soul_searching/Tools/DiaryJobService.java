package com.example.soul_searching.Tools;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

import com.example.soul_searching.DiaryAdapter;
import com.example.soul_searching.R;

import java.util.List;

public class DiaryJobService extends JobService {
    public static List<DiaryData> getDiaryDataList() {
        return diaryDataList;
    }

    public static void setDiaryDataList(List<DiaryData> diaryDataList) {
        DiaryJobService.diaryDataList = diaryDataList;
    }

    private static List<DiaryData> diaryDataList;
    @Override
    public boolean onStartJob(JobParameters params) {
        final NotificationCompat.Builder builder = GetBuilder();
        long currentTime = System.currentTimeMillis();
        int index = 0;
        for(DiaryData d : diaryDataList){
            DiaryAdapter.ViewHolder viewHolder = DiaryAdapter.viewHolders.get(index);
//            DateFormat.
//            final long[] time = {d.TargetTime - currentTime};
//            CountDownTimer cdt = new CountDownTimer(d.TargetTime - currentTime,1000) {
//                @Override
//                public void onTick(long millisUntilFinished) {
//                    //计时中
//                    viewHolder.getTextView().setText("" + --time[0]);
//                    System.err.println(millisUntilFinished);
//                }
//
//                @Override
//                public void onFinish() {
//                    //这里弹窗
//                    MainActivity.Ins.getManager().notify(1,builder.build());
//                    Context c = getApplicationContext();
//                    Intent i = new Intent(getApplicationContext(), TimerService.class);
//                    c.stopService(i);
//                }
//            };
            System.err.println("StartCommand------------------");
            //cdt.start();
        }

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private NotificationCompat.Builder GetBuilder(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setAutoCancel(true)
                .setContentTitle("提醒")
                .setContentText("今日份格子填了嘛")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        return builder;
    }
}
