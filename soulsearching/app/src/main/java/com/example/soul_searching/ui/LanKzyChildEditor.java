package com.example.soul_searching.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.soul_searching.R;
import com.example.soul_searching.Tools.GridParam;
import com.example.soul_searching.Tools.GridParams;
import com.example.soul_searching.Tools.LanKzy;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LanKzyChildEditor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LanKzyChildEditor extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static String placeHolder;

    private static String content;

    private static LanKzyDiaryEditor parent;

    private static int index;

    private View rootView;
    private EditText placeHolderV;
    private EditText contentV;

    private String date;

    public LanKzyChildEditor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LanKzyChildEditor.
     */
    // TODO: Rename and change types and number of parameters
    public static LanKzyChildEditor newInstance(String param1, String param2) {
        LanKzyChildEditor fragment = new LanKzyChildEditor();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static void Init(String placeHolder_, String content_, LanKzyDiaryEditor v,int index_) {
        placeHolder = placeHolder_;
        content = content_;
        parent = v;
        index = index_;
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
        rootView = inflater.inflate(R.layout.fragment_lankzy_child_editor, container, false);

        placeHolderV = rootView.findViewById(R.id.editor_child_placeHolder);
        contentV = rootView.findViewById(R.id.editor_child_content);

        Init();
        return rootView;
    }

    private void Init(){
        placeHolderV.setText(placeHolder);
        contentV.setText(content);
        date = HomePage.currentEditDate;
        System.err.println("==========" + placeHolder);
        System.err.println("==========" + content);

        SaveData();
        String originPlaceHolder = placeHolder;
        String originContent = content;
        placeHolderV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                placeHolder = s.toString();
                SaveData();
            }
        });

        contentV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                content = s.toString();
                System.err.println(index + " child index");
                SaveData();
            }
        });

    }

    private void SaveData(){
        GridParams gridParamsList = parent.getGridParams();
        for(GridParam gp:gridParamsList.gridParamList){
            System.err.println(gp.index + "==========" + index);
            if(gp.index == index){
                gp.placeHolder = placeHolder;
                gp.content = content;
                System.err.println("Save:" + placeHolder + "====" + content);
                Map<String,GridParams> dataList = LanKzy.getDataList();
                if(dataList.containsKey(HomePage.currentEditDate)){
                    System.err.println("Replace " + HomePage.currentEditDate);
                    dataList.replace(date,gridParamsList);
                }else{
                    System.err.println("Set1 " + HomePage.currentEditDate);
                    System.err.println("Set1 " + gridParamsList.targetTime);
                    dataList.put(date,gridParamsList);
                }
                LanKzy.SaveData(dataList);
            }
        }

    }
}