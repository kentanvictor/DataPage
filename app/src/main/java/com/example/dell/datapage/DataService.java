package com.example.dell.datapage;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by KenTan on 2017/8/10.
 */

public class DataService {
    public static List<String> getData(int offset, int maxResult) {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            data.add("ListView数据的分批加载" + i);
        }
        return data;
    }
}
