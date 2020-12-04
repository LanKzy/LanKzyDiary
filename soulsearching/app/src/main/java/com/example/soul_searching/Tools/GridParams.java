package com.example.soul_searching.Tools;

import android.view.View;
import android.widget.Button;

public class GridParams {


    public String placeHolder;

    public GridParams(String placeHolder, String content, int r, int c,int index) {
        this.placeHolder = placeHolder;
        this.content = content;
        this.r = r;
        this.c = c;
        this.index = index;
    }

    public int index;

    public String content;

    public int r,c;

    private View inss;

    public void SetIns(View view){
        this.inss = view;
    }

    public View GetIns(){
        return inss;
    }
}
