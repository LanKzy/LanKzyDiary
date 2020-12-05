package com.example.soul_searching.ui.diary_edit;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.soul_searching.R;
import com.example.soul_searching.Tools.DiaryData;
import com.example.soul_searching.Tools.LanKzy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class EditDiary extends Fragment {

    private EditDiaryViewModel editDiaryViewModel;
    private EditDiary ins;

    Calendar calendar= Calendar.getInstance(Locale.CHINA);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editDiaryViewModel =
                new ViewModelProvider(this).get(EditDiaryViewModel.class);
        View root = inflater.inflate(R.layout.diary_edit, container, false);
        final TextView textView = root.findViewById(R.id.edit_content);
        editDiaryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ins = this;

        FloatingActionButton setTime = getView().findViewById(R.id.settime);
        FloatingActionButton saveDiary = getView().findViewById(R.id.save_diary);

        final int[] year = new int[1];
        year[0] = calendar.get(Calendar.YEAR);
        final int[] month = new int[1];
        month[0] = calendar.get(Calendar.MONTH);
        final int[] day = new int[1];
        day[0] = calendar.get(Calendar.DAY_OF_MONTH);
        final int[] hour = new int[1];
        hour[0] = calendar.get(Calendar.HOUR_OF_DAY);
        final int[] minute = new int[1];
        minute[0] = calendar.get(Calendar.MINUTE);
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                System.out.println(year[0] + "-" + month[0] + "-" + day[0] + " " + day[0] + ":" + hour[0] + ":" + minute[0]);

                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        year[0] = i;
                        month[0] = i1;
                        day[0] = i2;
                        System.out.println(year[0] + "-" + month[0] + "-" + day[0] + " " + hour[0] + ":" + minute[0]);
                    }
                }
                        // 设置初始日期
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH)).show();
                new TimePickerDialog( getActivity(),
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute1) {
                            hour[0] = hourOfDay;
                            minute[0] = minute1;
                            System.out.println(year[0] + "-" + month[0] + "-" + day[0] + " " + hour[0] + ":" + minute[0]);
                        }


                    }
                    // 设置初始时间
                    , calendar.get(Calendar.HOUR_OF_DAY)
                    , calendar.get(Calendar.MINUTE)
                    // true表示采用24小时制
                    ,true).show();
                //string date = "xxxx-xx-xx xx:xx:xx";
                //
            }
        });

        saveDiary.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                DiaryData data = new DiaryData();
                long Time = System.currentTimeMillis();

                String current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Time);
                System.err.println("CurrentTime:" + Time);

                String targetTime = year[0] + "-" + (month[0] + 1) + "-" + day[0] + " " + hour[0] + ":" + minute[0];
                System.err.println("TargetTime" + targetTime);

                EditText content = getView().findViewById(R.id.edit_content);
                //data.CurrentTime = current;
//                Calendar cal = Calendar.getInstance(Locale.CHINA);
//                cal.set(year[0],month[0],day[0],hour[0],minute[0]);
//                cal.setTimeZone(TimeZone.getTimeZone("GMT"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                try {
                    System.err.println((sdf).parse(targetTime).getTime());
                    //data.TargetTime = (sdf).parse(targetTime).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //data.Content = content.getText().toString();

                //data.dataList = LanKzy.getDataList() == null ? new LinkedList<DiaryData>() : LanKzy.getDataList();
                //data.dataList.add(data);

                //System.err.println("Content" + data.Content);
                //LanKzy.SaveData(data);

            }
        });
    }
}