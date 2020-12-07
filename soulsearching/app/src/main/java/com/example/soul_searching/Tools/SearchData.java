package com.example.soul_searching.Tools;

import java.util.ArrayList;
import java.util.List;

public class SearchData {

    public Character node;

    public List<SearchData> child;

    public List<DiaryData> diaryDataList;

    public int depth;

    public SearchData(int depth){
        child = new ArrayList<SearchData>();
        this.depth = depth;
    }

    public List<DiaryData> getData(char[] c,int cIndex){
        System.err.println("get data" + c + ",,,");

        if(child == null){
            return null;
        }
        for(SearchData sd : child){
            System.err.println(sd.node);
            if(c.length == sd.depth && sd.node == c[cIndex]){
                System.err.println("return data");
                System.err.println(sd.diaryDataList + "============");
                return sd.diaryDataList;
            }
            if(depth - 1 == cIndex){
                break;
            }
            if(sd.node == c[cIndex]){
                System.err.println(sd.node + "," + c);
                return sd.getData(c,cIndex + 1);
            }
        }
        return null;
    }

    public static class DiaryData{
        public List<String> content;
        public List<String> date;
    }
}

