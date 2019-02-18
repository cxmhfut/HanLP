package com.cxmhfut.nlpcc2017;

import com.alibaba.fastjson.JSONArray;
import com.cxmhfut.utils.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class TrainDataParser {

    private final static String TRAINDATA_FILENAME = "src/data/nlpcc2017/train_data.json";
    private final static String OUTPUT_FILENAME = "src/data/nlpcc2017/train_data.txt";

    /**
     * 解析训练语料
     */
    void parse() {
        String jsonStr = FileUtil.readFile(TRAINDATA_FILENAME);
        JSONArray array = JSONArray.parseArray(jsonStr);
        int dataSize = array.size();
        System.out.println("DataSize:" + dataSize);

        double total = (double) dataSize;

        try {
            File file = new File(OUTPUT_FILENAME);
            PrintStream ps = new PrintStream(file);

            for (int i = 0; i < array.size(); i++) {
                if (i % 100 == 0) {
                    double now = (double) i;
                    System.err.printf("\rprogress:%.2f%%", (now * 100 / total));
                }
                ps.append(parseItem(array.getJSONArray(i)));
            }
            System.err.println();

            System.out.println("写入完成");

            ps.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String parseItem(JSONArray itemArray) {
        JSONArray sourceArray = itemArray.getJSONArray(0);
        JSONArray targetArray = itemArray.getJSONArray(1);

        String source = sourceArray.getString(0).trim();
        int sourceTag = sourceArray.getInteger(1);
        String target = targetArray.getString(0).trim();
        int targetTag = targetArray.getInteger(1);

        return sourceTag + " " + source + "\n" + targetTag + " " + target + "\n";
    }

    public static void main(String[] args) {
        TrainDataParser parser = new TrainDataParser();
        parser.parse();
    }
}
