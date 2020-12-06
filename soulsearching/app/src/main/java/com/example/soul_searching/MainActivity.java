package com.example.soul_searching;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.soul_searching.Tools.GridParams;
import com.example.soul_searching.Tools.LanKzy;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Map<String, GridParams> data;

    public static MainActivity Ins;

    public NotificationManager getManager() {
        return manager;
    }

    private NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.err.println("MainActivity1");
        Ins = this;

        LanKzy.file = "temp";
        data = LanKzy.GetData();

        //Init();


//        JobInfo.Builder builder = new JobInfo.Builder(123, djs.registerComponentCallbacks())
//                .setMinimumLatency(2000) // 设置任务允许最少延迟时间
//                .setOverrideDeadline(50000) // 设置deadline,若到期还没有到达规定的条件也会执行
//                .setRequireNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) //设置网络条件，非蜂窝数据的
//                .setRequiresCharging(true) // 设置充电条件
//                .setRequiresDeviceIdle(false) // 设置手机是否idle状态
//                .build();
//        Intent intent = onNewIntent(this,);
//        startService();

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel("1","倒计时结束",3);

//        Notification notification = new NotificationCompat.Builder(this, "1")
//                .setAutoCancel(true)
//                .setContentTitle("啦啦啦")
//                .setContentText("啦啦啦~")
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
//                .build();
//        manager.notify(1,notification);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.save_diary);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.diary_edit, R.id.diary_item, R.id.diary_safety,R.id.set_question)
//                .setDrawerLayout(drawer)
//                .build();
        //侧边导航栏点击切换
//        NavController navController = Navigation.findNavController(this, R.id.diary_edit_fragement);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.diary_edit_fragement);
        System.err.println("MainActivity2");
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        DiaryData data = LanKzy.GetData();
//        if (data != null){
//            for (DiaryData d:data.dataList) {
//                //Fragment f = new DiaryGirdItem("啦啦啦~",d.Content,"这是一个通知",d.TargetTime);
//
//            }
//            //初始化
//        }
        System.err.println("MainActivity3");

        ConstraintLayout passwordContainer = findViewById(R.id.main_password_container);

//        if(LanKzy.GetPassword() != null){
//            System.err.println(LanKzy.GetPassword());
//            Button passwordCheck = findViewById(R.id.password_check);
//            EditText password = findViewById(R.id.main_password_input);
//            ColorStateList color = password.getTextColors();
//
//            password.bringToFront();
//            passwordCheck.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(password.getText().toString().equals(LanKzy.GetPassword().toString())){
//                        passwordContainer.setVisibility(View.INVISIBLE);
//                        password.setTextColor(color);
//                        password.setText("");
//                    }else{
//                        password.setTextColor(Color.parseColor("#FFFF0000"));
//                    }
//                }
//            });
//        }else{
            passwordContainer.setVisibility(View.INVISIBLE);
//        }
//        for (int i = 0;i < 3;i++){
//            System.err.println(i);
//            Fragment f = new DiaryGirdItem();
//
//            RecyclerView rec = findViewById(R.id.homepage_view);
//            GridLayoutManager layoutManager2 = new GridLayoutManager(this,2);
//            LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
//            StaggeredGridLayoutManager layoutManager1=new StaggeredGridLayoutManager(3,1);
//            rec.setLayoutManager(layoutManager);
//            DiaryAdapter adapter = new DiaryAdapter();
//            rec.setAdapter(adapter);
//
//        }


    }

    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        manager.createNotificationChannel(channel);
    }


}