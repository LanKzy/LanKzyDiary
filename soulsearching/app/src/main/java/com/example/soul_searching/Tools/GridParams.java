package com.example.soul_searching.Tools;

import java.io.Serializable;
import java.util.List;

public class GridParams implements Serializable {

    //这个是定时时间  每一个可以打开编辑页面的日记都对应一个定时
    //对应这个字段
    public long targetTime;

    //这个是每个格子具体的参数
    public List<GridParam> gridParamList;

}

