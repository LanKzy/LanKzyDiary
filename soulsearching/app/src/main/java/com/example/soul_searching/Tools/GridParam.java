package com.example.soul_searching.Tools;

import java.io.Serializable;

public class GridParam implements Serializable {
    //这个是默认显示的一句话
    public String placeHolder;
    //这是编辑之后的内容
    public String content;

    public GridParam(String placeHolder, String content, int r, int c,int index) {
        this.placeHolder = placeHolder;
        this.content = content;
        this.r = r;
        this.c = c;
        this.index = index;
    }

    public int index;


    public int r,c;
}
