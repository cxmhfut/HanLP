package com.cxmhfut.nlpcc2017;

import com.alibaba.fastjson.JSONArray;
import com.cxmhfut.utils.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class TrainDataBuilder {

    private final static String BASE_DIR = "src/data/TrainData/";
    private final static String DIR_OTHER = BASE_DIR + "Other/";
    private final static String DIR_LIKE = BASE_DIR + "Like/";
    private final static String DIR_SADNESS = BASE_DIR + "Sadness/";
    private final static String DIR_DISGUST = BASE_DIR + "Disgust/";
    private final static String DIR_ANGER = BASE_DIR + "Anger/";
    private final static String DIR_HAPPINESS = BASE_DIR + "Happiness/";

    private final static int OTHER = 0;
    private final static int LIKE = 1;
    private final static int SADNESS = 2;
    private final static int DISGUST = 3;
    private final static int ANGER = 4;
    private final static int HAPPINESS = 5;

    private static int otherCount = 1;
    private static int likeCount = 1;
    private static int sadnessCount = 1;
    private static int disgustCount = 1;
    private static int angerCount = 1;
    private static int happinessCount = 1;

    private final static String TRAINDATA_FILENAME = "src/data/nlpcc2017/train_data.json";

    public void build() {
        checkDirs();

        String jsonStr = FileUtil.readFile(TRAINDATA_FILENAME);
        JSONArray array = JSONArray.parseArray(jsonStr);
        double total = 100000;
        System.out.println("DataSize:" + total);
        System.out.println("Building...");
        for (int i = 0; i < 100000; i++) {
            if (i % 100 == 0) {
                double now = (double) i;
                System.err.printf("\rprogress:%.2f%%", (now * 100 / total));
            }
            JSONArray itemArray = array.getJSONArray(i);
            JSONArray sourceArray = itemArray.getJSONArray(0);
            JSONArray targetArray = itemArray.getJSONArray(1);

            String source = sourceArray.getString(0).replaceAll(" ", "").trim();
            int sourceTag = sourceArray.getInteger(1);

            String target = targetArray.getString(0).replaceAll(" ", "").trim();
            int targetTag = targetArray.getInteger(1);

            build(sourceTag, source);
            build(targetTag, target);
        }
        System.err.println();
        System.out.println("Build Train Data Complete!");
    }

    public void build(int sentimentTag, String text) {
        String filename;
        switch (sentimentTag) {
            case OTHER:
                filename = DIR_OTHER + "other_" + otherCount + ".txt";
                otherCount++;
                break;
            case LIKE:
                filename = DIR_LIKE + "like_" + likeCount + ".txt";
                likeCount++;
                break;
            case SADNESS:
                filename = DIR_SADNESS + "sadness_" + sadnessCount + ".txt";
                sadnessCount++;
                break;
            case DISGUST:
                filename = DIR_DISGUST + "disgust_" + disgustCount + ".txt";
                disgustCount++;
                break;
            case ANGER:
                filename = DIR_ANGER + "anger_" + angerCount + ".txt";
                angerCount++;
                break;
            case HAPPINESS:
                filename = DIR_HAPPINESS + "happiness_" + happinessCount + ".txt";
                happinessCount++;
                break;
            default:
                filename = "";
                break;
        }

        try {
            File file = new File(filename);
            PrintStream ps = new PrintStream(file);
            ps.println(text);
            ps.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void checkDirs() {
        checkDir(BASE_DIR);
        checkDir(DIR_OTHER);
        checkDir(DIR_LIKE);
        checkDir(DIR_SADNESS);
        checkDir(DIR_DISGUST);
        checkDir(DIR_ANGER);
        checkDir(DIR_HAPPINESS);
    }

    private void checkDir(String dirName) {
        File dir = new File(dirName);

        if (!dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }

    }

    public static void main(String[] args) {
        TrainDataBuilder builder = new TrainDataBuilder();
        builder.build();
    }
}
