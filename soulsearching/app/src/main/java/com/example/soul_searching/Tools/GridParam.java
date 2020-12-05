package com.example.soul_searching.Tools;

import java.io.Serializable;

public class GridParam implements Serializable {
    public String placeHolder;

    public GridParam(String placeHolder, String content, int r, int c,int index) {
        this.placeHolder = placeHolder;
        this.content = content;
        this.r = r;
        this.c = c;
        this.index = index;
    }

    public int index;

    public String content;

    public int r,c;
}
