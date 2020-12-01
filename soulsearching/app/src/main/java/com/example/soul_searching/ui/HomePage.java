package com.example.soul_searching.ui;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soul_searching.DiaryAdapter;
import com.example.soul_searching.MainActivity;
import com.example.soul_searching.R;
import com.example.soul_searching.Tools.DiaryData;
import com.example.soul_searching.Tools.DiaryJobService;
import com.example.soul_searching.Tools.LanKzy;

import java.util.concurrent.TimeUnit;

public class HomePage extends Fragment {
    protected RecyclerView mRecyclerView;
    private static final String TAG = "HomePage";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


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
        View rootView = inflater.inflate(R.layout.homepage, container, false);
        rootView.setTag(TAG);

        DiaryData data = LanKzy.GetData();
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        if(data != null){
            DiaryAdapter adapter = new DiaryAdapter(data.dataList);
            System.err.println("Set Adapter");
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.scrollToPosition(0);

            DiaryJobService.setDiaryDataList(data.dataList);

            JobScheduler jobScheduler = (JobScheduler) MainActivity.Ins.getSystemService(MainActivity.Ins.JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(MainActivity.Ins, DiaryJobService.class));  //指定哪个JobService执行操作
            builder.setMinimumLatency(0); //执行的最小延迟时间
            builder.setOverrideDeadline(0);  //执行的最长延时时间
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NOT_ROAMING);  //非漫游网络状态
            builder.setBackoffCriteria(TimeUnit.MINUTES.toMillis(10), JobInfo.BACKOFF_POLICY_LINEAR);  //线性重试方案
            builder.setRequiresCharging(false); // 未充电状态
            jobScheduler.schedule(builder.build());

        }


        System.err.println("HomePage");

        return rootView;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button button;
        button = getView().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController nc = Navigation.findNavController(view);
                nc.navigate(R.id.action_blankFragment_to_diary_edit2);
            }
        });
    }
}