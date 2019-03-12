package com.cxmhfut.sentiment;

import com.cxmhfut.utils.TestUtility;
import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class SentimentClassifier {
    /**
     * 情感语料目录
     */
    private final static String CORPUS_FOLDER = "src/data/TrainData";

    private final static String MODEL_PATH = "src/data/models/sentiment-model.ser";

    private IClassifier classifier;

    public SentimentClassifier() {
        try {
            classifier = new NaiveBayesClassifier(TestUtility.trainOrLoadModel(CORPUS_FOLDER, MODEL_PATH));
        } catch (IOException e) {
            System.out.println("Classifier Init Failed.");
            e.printStackTrace();
        }
    }

    public void predict(String text) {
        System.out.printf("《%s》 属于分类 【%s】\n", text, classifier.classify(text));
    }

    public String classify(String text) {
        return classifier.classify(text);
    }

    public void process(String inFilename, String outFilename) throws IOException {
        File inFile = new File(inFilename);
        File outFile = new File(outFilename);

        if (!inFile.exists()) {
            System.err.println("输入文件不存在");
            return;
        }

        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(inFile), StandardCharsets.UTF_8));
        PrintWriter pr = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(outFile), StandardCharsets.UTF_8)));

        String line;
        while ((line = br.readLine()) != null) {
            String sentiment = classifier.classify(line);
            pr.println(sentiment + " " + line);
        }

        br.close();
        pr.close();
    }
}
