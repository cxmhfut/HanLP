package com.cxmhfut.nlpcc2017;

import com.alibaba.fastjson.JSONArray;
import com.cxmhfut.utils.FileUtil;

public class TrainDataParser {

    private final static String TRAINDATA_FILENAME = "src/data/nlpcc2017/train_data.json";

    public static void main(String[] args) {
        String jsonData = FileUtil.readFile(TRAINDATA_FILENAME);
        JSONArray array = JSONArray.parseArray(jsonData);
        int dataSize = array.size();
        System.out.println("DataSize:" + dataSize);
    }
}
