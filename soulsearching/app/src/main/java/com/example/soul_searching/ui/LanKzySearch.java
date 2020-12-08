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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LanKzySearch#newInstance} factory method to
 * create an instance of this fragment.
 */
//搜索界面  点击了之后跳转到这
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

        //输入框
        input = rootView.findViewById(R.id.search_con);
        //展示区
        emmm = rootView.findViewById(R.id.textView4);
        //按钮  刚才报错是因为你弄了个FloatActionButton  这里获取的是Button   类型不对  就报错了
        Button button = rootView.findViewById(R.id.button11);

        //首先创建一个节点
        sd = new SearchData(0);

        List<SearchData> sdList = new ArrayList<SearchData>();
//        初始化子节点   节点的层级是按照关键词的长度算的   比如  今天吃啥   今在第一层     sd.child = sdList;就 放在这里
//        天在第二层  就在子节点的子节点里  以此类推  下面的循环就是进行这个操作
        sd.child = sdList;

        Map<String, GridParams> dataList = LanKzy.getDataList();

        //初始化数据
        //加载页面的时候获取缓存数据  然后转换成多叉树格式    啊  讲起来也好费劲
        //拿到所有的数据  然后遍历
        for(Map.Entry<String, GridParams> map : dataList.entrySet()){
            String date = map.getKey();
            //日期是作为数据的key保存的  所以直接拿key  就是搜索内容对应的日期
            //然后遍历value
            List<GridParam> list = map.getValue().gridParamList;
            int tt = 0;
            for(GridParam gp : list){
                tt++;
                //拿到关键词
                char[] placeHolder = gp.placeHolder.toCharArray();
                SearchData.DiaryData diaryData = new SearchData.DiaryData();
                diaryData.date = new ArrayList<String>();
                diaryData.content = new ArrayList<String>();
                diaryData.date.add(date);
                diaryData.content.add(gp.content);
                List<SearchData> tempList = sd.child;
//                在这遍历关键字
                for(int i = 0;i < placeHolder.length;i++){
                    int index = -1;
                    int tempIndex = 0;
                    //根据长度创建SearchData  就是节点
                    //
                    for(SearchData sdChild : tempList){
                        if(sdChild.node.equals(placeHolder[i])){
                            //这里判断有没有这个节点
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
//                        如果没有这个节点  就创建一个新的  然后塞进去
                        tempData = new SearchData(i + 1);
                        tempData.node = placeHolder[i];
                        tempData.child = new ArrayList<SearchData>();
                        //塞进去
                        tempList.add(tempData);
                        //然后把遍历的目标换成子元素的 子节点List
                        tempList = tempData.child;
                    }
                    if(i == placeHolder.length - 1){
                        //这里是已经到最后一个字符   比如今天吃啥  这个时候已经到啥了  这个节点存的值 SearchData.node 存的是'啥'
                        //然后把所有今天吃啥这个关键字下的content  就是存储的内容  和存储的日期 date 放进这个节点下的list
                        if(tempData.diaryDataList == null){
                            tempData.diaryDataList = new ArrayList<SearchData.DiaryData>();
                        }
                        tempData.diaryDataList.add(diaryData);
                    }

                }
            }
        }

        //这个是搜索界面的按钮   点了就去刚才初始化的数据里面查找
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyWord = input.getText().toString();
                if(keyWord.length() > 0){
                    char[] c = keyWord.toCharArray();       //深度1    2    3    4  这里是'啥'对应的节点存了所有今天吃啥这个关键字的数据
                    //首先把字符串分割成数组  方便查找 "今天吃啥" → ["今","天","吃","啥"]
//                    然后遍历
                    for(int i = 0;i < c.length;i++){
//                        上面已经创建好的数据有一个公开的方法  getData
//                        两个参数 一个是需要查找的字符 一个是字符的深度
                        List<SearchData.DiaryData> data = sd.getData(c,0);
                        //有数据就展示
                        if(data != null){
                            String text = keyWord + "\n\n";
                            for(SearchData.DiaryData d : data){
                                if(d.content != null){
                                    Iterator<String> it1 = d.content.iterator();
                                    Iterator<String> it2 = d.date.iterator();
                                    //从返回的数据里面拿到content和date的list
//                                    同时遍历两个list  并拼接
                                    while (it1.hasNext() && it2.hasNext()) {
                                        String tempDate = it2.next();
                                        String tempContent = it1.next();
                                        text += tempDate + "\n" + tempContent + "\n\n";
//                                        System.err.println(it2.next() + "\n");
//                                        System.err.println(it1.next() + "\n");
                                    }
                                }
                            }
//                            拼接结束之后展示到文本框
                            emmm.setText(text);
                            break;
                        }else{
                            //没有就不展示
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