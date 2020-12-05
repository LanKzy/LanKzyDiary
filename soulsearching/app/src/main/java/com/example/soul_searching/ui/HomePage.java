package com.example.soul_searching.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soul_searching.MainActivity;
import com.example.soul_searching.R;
import com.example.soul_searching.Tools.GridParams;
import com.example.soul_searching.Tools.LanKzy;
import com.example.soul_searching.Tools.TimerService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomePage extends Fragment implements View.OnClickListener {
    protected RecyclerView mRecyclerView;
    private static final String TAG = "HomePage";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView week;
    private TextView sayHello;
    private TextView date;
    private View mainBottom;
    private Button blockButton;
    private String[] dateOfWeek;
    private TextView bottomDate;

    public static String currentEditDate;
    public static String currentEditDow;

    private String[] dow = {"日","一","二","三","四","五","六"};


    private List<Button> buttons;


    private View rootView;

    public HomePage(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
// BEGIN_INCLUDE(initializeRecyclerView)
        rootView = inflater.inflate(R.layout.homepage, container, false);
        rootView.setTag(TAG);

        week = rootView.findViewById(R.id.week);
        sayHello = rootView.findViewById(R.id.sayHello);
        date = rootView.findViewById(R.id.date);
        mainBottom = rootView.findViewById(R.id.main_bottom);
        blockButton = rootView.findViewById(R.id.button10);
        bottomDate = rootView.findViewById(R.id.bottom_current_date);
        dateOfWeek = new String[7];

        buttons = new ArrayList<Button>();

        buttons.add(rootView.findViewById(R.id.button2));
        buttons.add(rootView.findViewById(R.id.button3));
        buttons.add(rootView.findViewById(R.id.button4));
        buttons.add(rootView.findViewById(R.id.button5));
        buttons.add(rootView.findViewById(R.id.button6));
        buttons.add(rootView.findViewById(R.id.button7));
        buttons.add(rootView.findViewById(R.id.button8));

        rootView.findViewById(R.id.button_my_diary).setOnClickListener(this);




        //DiaryData data = LanKzy.GetData();
        //GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
//        if(data != null){
//            DiaryAdapter adapter = new DiaryAdapter(data.dataList);
//            System.err.println("Set Adapter");
//            mRecyclerView.setAdapter(adapter);
//            mRecyclerView.setLayoutManager(mLayoutManager);
//            mRecyclerView.scrollToPosition(0);
//
//            DiaryJobService.setDiaryDataList(data.dataList);
//
//            JobScheduler jobScheduler = (JobScheduler) MainActivity.Ins.getSystemService(MainActivity.Ins.JOB_SCHEDULER_SERVICE);
//            JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(MainActivity.Ins, DiaryJobService.class));  //指定哪个JobService执行操作
//            builder.setMinimumLatency(0); //执行的最小延迟时间
//            builder.setOverrideDeadline(0);  //执行的最长延时时间
//            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NOT_ROAMING);  //非漫游网络状态
//            builder.setBackoffCriteria(TimeUnit.MINUTES.toMillis(10), JobInfo.BACKOFF_POLICY_LINEAR);  //线性重试方案
//            builder.setRequiresCharging(false); // 未充电状态
//            jobScheduler.schedule(builder.build());
//
//        }
        long current = System.currentTimeMillis();
        System.err.println(LanKzy.getDataList().size());
        for(GridParams gp : LanKzy.getDataList().values()){
            long target = gp.targetTime;
            if(current > target){
                //无需计时
                System.err.println("无需计时:" + current + "========" + target);
                continue;
            }

            final NotificationCompat.Builder builder = GetBuilder();
            CountDownTimer cdt = new CountDownTimer(target - current,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //计时中
                    //System.err.println(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    //这里弹窗
                    MainActivity.Ins.getManager().notify(1,builder.build());
                    Intent i = new Intent(MainActivity.Ins, TimerService.class);
                    //c.stopService(i);
                }
            };
            cdt.start();
            //ts.startService(intent);
        }
        System.err.println("HomePage");

        Init();
        return rootView;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Button button;
//        button = getView().findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavController nc = Navigation.findNavController(view);
//                nc.navigate(R.id.action_homePageBottom_to_lanKzyDiaryEditor);
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    private NotificationCompat.Builder GetBuilder(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "1")
                .setAutoCancel(true)
                .setContentTitle("弹窗标题")
                .setContentText("弹窗内容")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        return builder;
    }


    private void Init(){
        Calendar c = Calendar.getInstance();
        //今年第几周
        String week_s = c.get(Calendar.WEEK_OF_YEAR) + "周";
        String sayHello_s = "";
        String date_s = "";

        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        if(hourOfDay >=4 && hourOfDay < 9){
            sayHello_s = "早上好。";
        }else if(hourOfDay >= 9 && hourOfDay <= 11 ){
            sayHello_s = "上午好。";
        }else if(hourOfDay > 11 && hourOfDay <= 14){
            sayHello_s = "中午好。";
        }else if(hourOfDay > 14 && hourOfDay < 18){
            sayHello_s = "下午好。";
        }else if((hourOfDay >= 18 && hourOfDay <= 24) || (hourOfDay < 4)){
            sayHello_s = "晚上好。";
        }
        date_s += c.get(Calendar.YEAR) + "年" +
                (c.get(Calendar.MONTH) + 1) + "月" +
                c.get(Calendar.DAY_OF_MONTH) + "日";
        String DOW = "星期";
        int todayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        currentEditDow = dow[todayOfWeek - 1];
        date_s += DOW + currentEditDow;

        mainBottom.setVisibility(View.INVISIBLE);
        blockButton.setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.button).setVisibility(View.INVISIBLE);
        blockButton.setOnClickListener(this);
        week.setText(week_s);
        sayHello.setText(sayHello_s);
        date.setText(date_s);

        while(c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
            c.add(Calendar.DATE,-1);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月 dd日");

        for(int i = 0 ;i < 7;i++){
            dateOfWeek[i] = dateFormat.format(c.getTime());
            c.add(Calendar.DATE,1);
        }


        int index = 0;
        for (Button b:buttons) {
            b.setOnClickListener(this);
            if(index == todayOfWeek - 1){
                //给今天的按钮设置样式
            }
            b.setText(dow[index]);
            index++;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button2:
                ShowBottom(dateOfWeek[0]);
                currentEditDate = dateOfWeek[0];
                currentEditDow = dow[0];
                break;
            case R.id.button3:
                ShowBottom(dateOfWeek[1]);
                currentEditDate = dateOfWeek[1];
                currentEditDow = dow[1];
                break;
            case R.id.button4:
                ShowBottom(dateOfWeek[2]);
                currentEditDate = dateOfWeek[2];
                currentEditDow = dow[2];
                break;
            case R.id.button5:
                ShowBottom(dateOfWeek[3]);
                currentEditDate = dateOfWeek[3];
                currentEditDow = dow[3];
                break;
            case R.id.button6:
                ShowBottom(dateOfWeek[4]);
                currentEditDate = dateOfWeek[4];
                currentEditDow = dow[4];
                break;
            case R.id.button7:
                ShowBottom(dateOfWeek[5]);
                currentEditDate = dateOfWeek[5];
                currentEditDow = dow[5];
                break;
            case R.id.button8:
                ShowBottom(dateOfWeek[6]);
                currentEditDate = dateOfWeek[6];
                currentEditDow = dow[6];
                break;
            case R.id.button10:
                mainBottom.setVisibility(View.INVISIBLE);
                blockButton.setVisibility(View.INVISIBLE);
                break;
            case R.id.button_my_diary:
                NavController nc = Navigation.findNavController(v);
                nc.navigate(R.id.action_blankFragment_to_lanKzyDiaryEditor);
                break;
        }
//        NavController nc = Navigation.findNavController(v);
//        nc.navigate(R.id.action_blankFragment_to_homePageBottom);
    }

    private void ShowBottom(String key){
        mainBottom.setVisibility(View.VISIBLE);
        blockButton.setVisibility(View.VISIBLE);

        bottomDate.setText(key);

    }
}