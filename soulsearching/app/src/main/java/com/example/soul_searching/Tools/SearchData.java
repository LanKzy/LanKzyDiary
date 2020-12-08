 package com.example.soul_searching.Tools;

import java.util.ArrayList;
import java.util.List;

public class SearchData {

    public Character node;

    public List<SearchData> child;

    //这个DiaryData 存储了俩list  一个是内容一个是时间
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
        //这里会判断字符的深度和值是否匹配  如果是正确的值  返回存储的diaryDataList  就是那个最后一个节点存储的数据
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
                //如果没有就继续去子元素里面查直到查到为止 没有数据就返回null
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

