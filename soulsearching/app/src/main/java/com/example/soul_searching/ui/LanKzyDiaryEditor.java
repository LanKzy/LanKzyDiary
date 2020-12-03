package com.example.soul_searching.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;

import com.example.soul_searching.R;
import com.example.soul_searching.Tools.LanKzy;

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

    private String[] tempPlaceholder;

    private int currentRow,currentCol;

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
        //System.err.println("width:" + rootView.getLayoutParams().width + "height:" + rootView.getLayoutParams().height);
        //width = g.getLayoutParams().width;

//        Button b = new Button(getContext());
//        g.addView(b);
        //b.setLayoutParams(new ViewGroup.LayoutParams(100,200));
//        b.setTranslationY();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Init();
        //初始化删除格子的按钮
        InitButtonScrollView();
    }

    private void Init(){
        tempPlaceholder = LanKzy.placeHolder;
        //初始化日记格子
        editorDate.setText(HomePage.currentEditDate);
        moreActionLayout.setVisibility(View.INVISIBLE);
        buttonScrollView.setVisibility(View.INVISIBLE);
        for(int r = 0;r < 4;r++){
            for(int c = 0;c < col;c++){
                AddDiaryItem(r,c);
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
                AddDiaryItem(currentRow,currentCol);
                moreActionLayout.setVisibility(View.INVISIBLE);
            }
        });

        editGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreActionLayout.setVisibility(View.INVISIBLE);
                buttonScrollView.setVisibility(View.VISIBLE);
            }
        });

        importImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreActionLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void AddDiaryItem(int r,int c){
        String placeHolder = "=============";
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
        currentRow = r;
        currentCol = c;
        EditText act = new EditText(getContext());
        act.setHeight(500);
        GridLayout.Spec rowSpec = GridLayout.spec(r, 1.0f);
        GridLayout.Spec columnSpec = GridLayout.spec(c, 1.0f);;
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
        System.err.println(placeHolder + "========" + r + "," + c);
        act.setText(placeHolder);
        //Button but = new Button(getContext());
//                but.setHeight(5);
//                but.setWidth(5);
        //but.setVisibility(View.INVISIBLE);
        editorLayout.addView(act,params);
        //g.addView(but,params);
    }

    private void InitButtonScrollView(){
        buttonLayout.removeAllViews();
        for(int r = 0;r < row;r++){
            for(int c = 0;c < col;c++){
                AddDeleteButton(r,c);
            }
        }
    }

    private void AddDeleteButton(int r,int c){
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
                buttonLayout.removeViewAt(finalR + finalC);
                editorLayout.removeViewAt(finalR + finalC);
            }
        });

        cl.addView(but,params);
        buttonLayout.addView(cl,params1);
    }

    //删除一个格子之后其他的重新排列 并且修改currentRow currentCol
    private void OnDeleteGrid(){

    }
}