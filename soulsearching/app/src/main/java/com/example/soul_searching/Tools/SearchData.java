package com.example.soul_searching.Tools;

import java.util.HashMap;
import java.util.Map;

public class SearchData {
    private Map<String,SearchData> child;

    public Map<String, SearchData> getChild() {
        return child == null ? new HashMap<String,SearchData>() : child;
    }

    public void setChild(Map<String, SearchData> child) {
        this.child = child;
    }

    public void SetData(String s,int index){
        if(child == null){
            child = new HashMap<String,SearchData>();
        }

        for(int i = 0;i < index + 1;i++){

        }
        if(child.containsKey(s)){

        }
    }
}
