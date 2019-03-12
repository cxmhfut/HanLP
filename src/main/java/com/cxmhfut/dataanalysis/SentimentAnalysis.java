package com.cxmhfut.dataanalysis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cxmhfut.sentiment.SentimentClassifier;
import com.cxmhfut.utils.FileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentimentAnalysis {
    private final static String BASE_DIR = "src/sample/";
    private final static String NLPW_SAMPLE_DIR = BASE_DIR + "NLPW";
    private final static String XHJ_SAMPLE_DIR = BASE_DIR + "XHJ";

    private static Map<String, Integer> analysis(SentimentClassifier classifier, String fileName) {
        String jsonStr = FileUtil.readFile(fileName);
        jsonStr = "[" + jsonStr + "]";
        JSONArray array = JSONArray.parseArray(jsonStr);

        int count = 0;
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String target = obj.getString("target");
            String sample = obj.getString("sample");
            String targetSentiment = classifier.classify(target);
            String sampleSentimrnt = classifier.classify(sample);
            if (targetSentiment.equals(sampleSentimrnt)) {
                count++;
            }
        }

        Map<String, Integer> res = new HashMap<>();
        res.put("right", count);
        res.put("total", array.size());

        return res;
    }

    public static void main(String[] args) {
        List<String> dirNames = FileUtil.listSubDirNames(NLPW_SAMPLE_DIR);

        SentimentClassifier classifier = new SentimentClassifier();

        if (dirNames != null) {
            for (String dirName : dirNames) {
                File file = new File(dirName);
                System.out.println(file.getName());
                List<String> fileNames = FileUtil.listSubFileNames(dirName);
                int right = 0;
                int total = 0;
                for (String fileName : fileNames) {
                    Map<String, Integer> res = analysis(classifier, fileName);
                    right += res.get("right");
                    total += res.get("total");
                }
                System.out.printf("Accuracy(%d/%d):%.3f\n", right, total, (double) right / total);
            }
        }
    }
}
