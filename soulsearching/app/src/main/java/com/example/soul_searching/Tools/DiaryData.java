package com.example.soul_searching.Tools;

import java.io.Serializable;
import java.util.List;

public class DiaryData implements Serializable {
    public List<DiaryData> dataList;

    public String CurrentTime;
    public long TargetTime;
    public String Content;
}
