package com.example.soul_searching.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.soul_searching.R;
import com.example.soul_searching.Tools.GridParam;
import com.example.soul_searching.Tools.GridParams;
import com.example.soul_searching.Tools.LanKzy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LanKzyDiaryEditor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LanKzyDiaryEditor extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int row = 3;
    private int col = 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;
    private TextView editorDate;

    private ScrollView buttonScrollView;
    private ScrollView editorScrollView;
    private Button buttonMoreAction;

    private GridLayout editorLayout;
    private GridLayout buttonLayout;
    private LinearLayout moreActionLayout;

    private Button addGrid;
    private Button editGrid;
    private Button importImage;
    private Button setTime;

    private String[] tempPlaceholder;

    private int currentRow,currentCol;

    GridParams gridParams;

    public GridParams getGridParams(){
        return gridParams;
    }


    public LanKzyDiaryEditor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LanKzyDiaryEditor.
     */
    // TODO: Rename and change types and number of parameters
    public static LanKzyDiaryEditor newInstance(String param1, String param2) {
        LanKzyDiaryEditor fragment = new LanKzyDiaryEditor();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.lankzy_diary_editor, container, false);

        editorDate = rootView.findViewById(R.id.editor_date);
        buttonScrollView = rootView.findViewById(R.id.button_scrollview);
        editorScrollView = rootView.findViewById(R.id.editor_scrollview);
        editorLayout = rootView.findViewById(R.id.editor_layout);
        buttonLayout = rootView.findViewById(R.id.editor_buttons_layout);
        buttonMoreAction = rootView.findViewById(R.id.button_more_action);
        moreActionLayout = rootView.findViewById(R.id.linearLayout_more_action);

        addGrid = rootView.findViewById(R.id.add_grid);
        editGrid = rootView.findViewById(R.id.edit_grid);
        importImage = rootView.findViewById(R.id.import_image);
        setTime = rootView.findViewById(R.id.set_time);

        gridParams = new GridParams();
        gridParams.gridParamList = new ArrayList<GridParam>();

        Map<String, GridParams> aa = LanKzy.getDataList();

        if(aa != null){
            GridParams dataList = aa.get(HomePage.currentEditDate);
            System.err.println("1");
            if(dataList != null){
                System.err.println("2");
                gridParams = dataList;
            }
        }

        //System.err.println("width:" + rootView.getLayoutParams().width + "height:" + rootView.getLayoutParams().height);
        //width = g.getLayoutParams().width;

//        Button b = new Button(getContext());
//        g.addView(b);
        //b.setLayoutParams(new ViewGroup.LayoutParams(100,200));
//        b.setTranslationY();

        //有数据加载数据   无数据使用默认模板
        System.err.println("Init LanKzyDiaryEditor ===========");
        Init(gridParams,false);


        //初始化删除格子的按钮
        InitButtonScrollView(gridParams.gridParamList);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void Init(GridParams dataList,boolean isReset){
        tempPlaceholder = LanKzy.placeHolder;
        //初始化日记格子
        editorDate.setText(HomePage.currentEditDate);
        moreActionLayout.setVisibility(View.INVISIBLE);
        buttonScrollView.setVisibility(View.INVISIBLE);
        editorLayout.removeAllViews();
        int tempIndex = 0;
        if(dataList.gridParamList != null && dataList.gridParamList.size() > 0){
            if(isReset){
                System.err.println("reset");
                int tempR = 0,tempC = 0;
                for(GridParam gp : dataList.gridParamList){
                    System.err.println("reset:" + tempR + "," + tempR);
                    System.err.println(gp.content);
                    AddDiaryItem(tempR,tempC,tempIndex,gp.content,gp.placeHolder);
                    gp.r = tempR;
                    gp.c = tempC;
                    if(tempC < col - 1){
                        tempC += 1;
                    }else{
                        tempC = 0;
                        tempR += 1;
                    }
                    tempIndex += 1;
                }
            }else{
                for(GridParam gp : dataList.gridParamList){
                    System.err.println(gp.content + "============");
                    AddDiaryItem(gp.r,gp.c,tempIndex,gp.content,gp.placeHolder);
                    tempIndex += 1;
                }
            }
        }else{
            for(int r = 0;r < 4;r++){
                for(int c = 0;c < col;c++){
                    AddDiaryItem(r,c,tempIndex,null,null);
                    tempIndex += 1;
                }
            }
        }


        buttonMoreAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreActionLayout.setVisibility(View.VISIBLE);
            }
        });

        addGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreActionLayout.setVisibility(View.INVISIBLE);
                currentCol += 1;
                if(currentCol >= col){
                    currentCol = 0;
                    currentRow += 1;
                }
                AddDiaryItem(currentRow,currentCol,gridParams.gridParamList.size(),null,null);
                AddDeleteButton(currentRow,currentCol,gridParams.gridParamList.size(),null);
                moreActionLayout.setVisibility(View.INVISIBLE);
            }
        });

        editGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreActionLayout.setVisibility(View.INVISIBLE);
                buttonScrollView.setVisibility(View.VISIBLE);
                buttonScrollView.setScrollY(editorScrollView.getScrollY());
            }
        });

        importImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreActionLayout.setVisibility(View.INVISIBLE);
            }
        });
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
        Calendar calendar = Calendar.getInstance(timeZone);
        System.err.println(calendar.get(Calendar.HOUR_OF_DAY));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        int[] year = new int[1];
        int[] month = new int[1];
        int[] day = new int[1];
        int[] hour = new int[1];
        int[] minute = new int[1];
        final String[] targetTime = {"0000-00-00 00:00"};

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(year[0] + "-" + month[0] + "-" + day[0] + " " + day[0] + ":" + hour[0] + ":" + minute[0]);

                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        year[0] = i;
                        month[0] = i1 + 1;
                        day[0] = i2;

                        targetTime[0] = "" + year[0] + "-" + month[0] + "-" + day[0] + " " + hour[0] + ":" + minute[0];
                        long t = 0;
                        try{
                            t = sdf.parse(targetTime[0]).getTime();
                        }catch (Exception e){
                            System.err.println("时间出问题了");
                        }
                        dataList.targetTime = t;
                        gridParams.targetTime = t;
                        Map<String,GridParams> dataList = LanKzy.getDataList();
                        if(dataList.containsKey(HomePage.currentEditDate)){
                            System.err.println("Replace " + HomePage.currentEditDate);
                            System.err.println("Replace " + gridParams.targetTime);
                            dataList.replace(HomePage.currentEditDate,gridParams);
                        }else{
                            System.err.println("Set " + HomePage.currentEditDate);
                            System.err.println("Set " + gridParams.targetTime);
                            dataList.put(HomePage.currentEditDate,gridParams);
                        }
                        LanKzy.SaveData(dataList);
                        System.out.println("targetTime1 :" + year[0] + "-" + month[0] + "-" + day[0] + " " + hour[0] + ":" + minute[0]);
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

                                long t = 0;
                                try{
                                    t = sdf.parse(targetTime[0]).getTime();
                                }catch (Exception e){
                                    System.err.println("时间出问题了");
                                }
                                dataList.targetTime = t;
                                System.out.println("targetTime2 :" + year[0] + "-" + month[0] + "-" + day[0] + " " + hour[0] + ":" + minute[0]);
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
    }

    private void InitButtonScrollView(List<GridParam> dataList){
        buttonLayout.removeAllViews();
        buttonScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                System.err.println(i1);
                editorScrollView.setScrollY(i1);
            }
        });
//        scrollView.fullScroll(ScrollView.FOCUS_DOWN);滚动到底部
        if(dataList != null){
            for(GridParam gp : dataList){
                AddDeleteButton(gp.r,gp.c,gp.index,gp);
            }
        }else{
            for(int r = 0;r < row;r++){
                for(int c = 0;c < col;c++){
                    AddDeleteButton(r,c,gridParams.gridParamList.size(),null);
                }
            }
        }
    }

    private Button AddDiaryItem(int r,int c,int index,String content,String placeHolder_){
        String placeHolder = "=============";
        //EditText act = new EditText(getContext());
        Button act = new Button(getContext());
        if(placeHolder_ != null){
            placeHolder = placeHolder_;
        }else{
            if(tempPlaceholder.length > 0){
                int tempIndex = (int) (Math.random() * tempPlaceholder.length);
                placeHolder = tempPlaceholder[tempIndex];
                String[] newArray = new String[tempPlaceholder.length - 1];
                System.arraycopy(tempPlaceholder, 0, newArray, 0, tempIndex);
                if (tempIndex < tempPlaceholder.length - 1) {
                    System.arraycopy(tempPlaceholder, tempIndex + 1, newArray, tempIndex, newArray.length - tempIndex);
                }
                tempPlaceholder = newArray;
            }else{
                placeHolder = "俺也8知道写点啥了";
            }
            GridParam gp = new GridParam(placeHolder,"",r,c,index);
            //gp.SetIns(act);
            gridParams.gridParamList.add(gp);
        }

        currentRow = r;
        currentCol = c;
        act.setHeight(500);
        GridLayout.Spec rowSpec = GridLayout.spec(r, 1.0f);
        GridLayout.Spec columnSpec = GridLayout.spec(c, 1.0f);;
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
        System.err.println(placeHolder + "========" + r + "," + c);

        //Button but = new Button(getContext());
//                but.setHeight(5);
//                but.setWidth(5);
        //but.setVisibility(View.INVISIBLE);
        if(content == null){
            content = "";
        }
        String finalPlaceHolder = placeHolder;

        String finalContent = content;

        LanKzyDiaryEditor parent = this;
        act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //携带参数跳转到编辑页面
                //从编辑页面返回时判断值是否发生改变，如果改变，存到缓存 LanKzy.getDataList()
                NavController nc = Navigation.findNavController(v);
                System.err.println(finalPlaceHolder + "---------------");
                System.err.println(finalContent + "---------------");
                System.err.println(index);
                LanKzyChildEditor.Init(finalPlaceHolder, finalContent,parent,index);
                nc.navigate(R.id.action_lanKzyDiaryEditor_to_lanKzyChildEditor);
                System.err.println(finalPlaceHolder);
            }
        });
        placeHolder += "\n" + content;
        act.setText(placeHolder);
        editorLayout.addView(act,params);
        //g.addView(but,params);
        return act;
    }

    private void AddDeleteButton(int r,int c,int index,GridParam gp){
        Button but = new Button(getContext());
        ConstraintLayout cl = new ConstraintLayout(getContext());

        GridLayout.Spec rowSpec = GridLayout.spec(r, 1.0f);
        GridLayout.Spec columnSpec = GridLayout.spec(c, 1.0f);;
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
        params.height = 50;
        params.width = 50;
        GridLayout.LayoutParams params1 = new GridLayout.LayoutParams(rowSpec,columnSpec);
        params1.height = 500;
        params1.width = 500;
        int finalR = r;
        int finalC = c;
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                buttonLayout.removeViewAt(finalR + finalC);
//                editorLayout.removeViewAt(finalR + finalC);
                OnDeleteGrid(finalR,finalC,index,gp);
            }
        });

        cl.addView(but,params);
        buttonLayout.addView(cl,params1);
    }

    //删除一个格子之后其他的重新排列 并且修改currentRow currentCol
    private void OnDeleteGrid(int r, int c, int index, GridParam gp){
        if(buttonLayout.getChildCount() > 0 && editorLayout.getChildCount() > 0){
            View buttonTarget = buttonLayout.getChildAt(r + c);
            View editorTarget = editorLayout.getChildAt(r + c);
            if(gp != null){
                System.err.println("remove:" + gp.r + "," + gp.c);
                gridParams.gridParamList.remove(gp);
            }
            buttonLayout.removeView(buttonTarget);
            editorLayout.removeView(editorTarget);
            Init(gridParams,true);
            InitButtonScrollView(gridParams.gridParamList);
//            System.err.println("删除：" + r + "," + c);
//            System.err.println(currentRow + "," + currentCol);
//            int startR = r;
//            int startC = c + 1;
//            if (startC >= col && startR > 0){
//                startC = col - 1;
//                startR -= 1;
//            }
//            int tempR = startR,tempC = startC;
//            for(int i = startR;i < currentRow;i++){
//                System.err.println(i + ",,,");
//                for(int j = startC;j < currentCol;j++){
//                    buttonLayout.removeView(buttonLayout.getChildAt(i + j));
//                    //Button tempButton = (Button) buttonLayout.getChildAt(i + j);
//                    if (tempC < 0 && tempR > 0){
//                        tempC = col - 1;
//                        tempR = i - 1;
//                    }else{
//                        tempR = i;
//                        tempC = j - 1;
//                    }
//                    GridLayout.Spec rowSpec = GridLayout.spec(tempR, 1.0f);
//                    GridLayout.Spec columnSpec = GridLayout.spec(tempC, 1.0f);;
//                    GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
//                    System.err.println("reset " + i + "," + j + " to " + tempR + "," + tempC);
//                    int finalTempR = tempR;
//                    int finalTempC = tempC;
////                    tempButton.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            //重置点击事件
////                            buttonLayout.removeViewAt(finalTempR + finalTempC);
////                            editorLayout.removeViewAt(finalTempR + finalTempC);
////                            OnDeleteGrid(finalTempR, finalTempC);
////                        }
////                    });
////                    buttonLayout.removeView(tempButton);
////                    buttonLayout.addView(tempButton,params);
//
//                    //editor layout
//                    EditText tempEditText = (EditText) editorLayout.getChildAt(j + j);
//
//                    editorLayout.removeView(tempEditText);
//                    editorLayout.addView(tempEditText,params);
//                }
//            }


        }
        //然后重置按钮点击事件
    }
}