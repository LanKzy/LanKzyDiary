package com.example.soul_searching.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.soul_searching.R;
import com.example.soul_searching.Tools.GridParam;
import com.example.soul_searching.Tools.GridParams;
import com.example.soul_searching.Tools.LanKzy;
import com.example.soul_searching.Tools.SearchData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LanKzySearch#newInstance} factory method to
 * create an instance of this fragment.
 */

public class LanKzySearch extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;

    private EditText input;
    private TextView emmm;

    private SearchData sd;

    public LanKzySearch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LanKzySearch.
     */
    // TODO: Rename and change types and number of parameters
    public static LanKzySearch newInstance(String param1, String param2) {
        LanKzySearch fragment = new LanKzySearch();
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
        rootView = inflater.inflate(R.layout.fragment_lankzy_search, container, false);

        input = rootView.findViewById(R.id.search_con);
        emmm = rootView.findViewById(R.id.textView4);
        FloatingActionButton button = rootView.findViewById(R.id.button11);

        sd = new SearchData(0);

        List<SearchData> sdList = new ArrayList<SearchData>();

        sd.child = sdList;

        Map<String, GridParams> dataList = LanKzy.getDataList();

        //初始化数据
        for(Map.Entry<String, GridParams> map : dataList.entrySet()){
            String date = map.getKey();

            List<GridParam> list = map.getValue().gridParamList;
            int tt = 0;
            for(GridParam gp : list){
                tt++;
                char[] placeHolder = gp.placeHolder.toCharArray();
                SearchData.DiaryData diaryData = new SearchData.DiaryData();
                diaryData.date = new ArrayList<String>();
                diaryData.content = new ArrayList<String>();
                diaryData.date.add(date);
                diaryData.content.add(gp.content);
                List<SearchData> tempList = sd.child;
                for(int i = 0;i < placeHolder.length;i++){
                    int index = -1;
                    int tempIndex = 0;
                    for(SearchData sdChild : tempList){
                        if(sdChild.node.equals(placeHolder[i])){
                            index = tempIndex;
                            break;
                        }
                        tempIndex++;
                    }
                    SearchData tempData = null;
                    if(index != -1){
                        tempData = tempList.get(index);
                        if(tempData.child ==null){
                            tempData.child = new ArrayList<SearchData>();
                        }
                        tempList.add(tempData);
                        tempList = tempData.child;
                    }else{
                        tempData = new SearchData(i + 1);
                        tempData.node = placeHolder[i];
                        tempData.child = new ArrayList<SearchData>();
                        tempList.add(tempData);
                        tempList = tempData.child;
                    }
                    if(i == placeHolder.length - 1){
                        if(tempData.diaryDataList == null){
                            tempData.diaryDataList = new ArrayList<SearchData.DiaryData>();
                        }
                        tempData.diaryDataList.add(diaryData);
                    }

                }
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyWord = input.getText().toString();
                if(keyWord.length() > 0){
                    char[] c = keyWord.toCharArray();
                    for(int i = 0;i < c.length;i++){
                        List<SearchData.DiaryData> data = sd.getData(c,0);
                        if(data != null){
                            String text = keyWord + "\n\n";
                            for(SearchData.DiaryData d : data){
                                if(d.content != null){
                                    Iterator<String> it1 = d.content.iterator();
                                    Iterator<String> it2 = d.date.iterator();

                                    while (it1.hasNext() && it2.hasNext()) {
                                        String tempDate = it2.next();
                                        String tempContent = it1.next();
                                        text += tempDate + "\n" + tempContent + "\n\n";
//                                        System.err.println(it2.next() + "\n");
//                                        System.err.println(it1.next() + "\n");
                                    }
                                }
                            }
                            emmm.setText(text);
                            break;
                        }else{
                            System.err.println("啥也没查着");
                        }

                    }
                }
            }
        });

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                String s = editable.toString();
//                char[] chars = s.toCharArray();
//                for(int i = 0;i < chars.length;i++){
//                    System.err.println(String.valueOf(chars[i]));
////                    sd.setData(String.valueOf(chars[i]),i);
//                }
            }
        });


        return rootView;
    }
}