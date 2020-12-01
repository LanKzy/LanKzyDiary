package com.example.soul_searching.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.soul_searching.R;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiaryGirdItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiaryGirdItem extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String title;
    private String desc;
    private String tsDesc;
    private Date date;

    /**
     *
     * @param title  标题
     * @param desc   描述
     * @param tsDesc 弹窗描述
     * @param date   触发时间
     */
    public DiaryGirdItem(String title,String desc,String tsDesc,Date date) {
        this.title = title;
        this.desc = desc;
        this.tsDesc = tsDesc;
        this.date = date;
        System.err.println("Create-----Item");
        //TimerService ts = new TimerService();
        Init();
    }

    public void Init(){
        long current = System.currentTimeMillis();
        long target = date.getTime();
        if(current > target){
            //无需计时
            System.err.println("无需计时:" + current + "========" + target);
            return;
        }

        System.err.println("StartService:" + current + "========" + target);
//        TimerService ts = new TimerService(target - current,title,tsDesc);
//        Intent intent = new Intent(MainActivity.Ins,TimerService.class);
//        MainActivity.Ins.startService(intent);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiaryGirdItem.
     */
    // TODO: Rename and change types and number of parameters
    public static DiaryGirdItem newInstance(String param1, String param2) {
        DiaryGirdItem fragment = new DiaryGirdItem("啦啦啦~","123","这是一个通知",new Date());
        System.err.println("Create-----Item1");
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.err.println("diary_gird_item");
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.err.println("diary_gird_item");
        return inflater.inflate(R.layout.diary_gird_item, container, false);
    }
}