package com.cxmhfut;

import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import com.hankcs.hanlp.classification.models.NaiveBayesModel;
import com.hankcs.hanlp.corpus.io.IOUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SentimentClassify {
    /**
     * 情感语料目录
     */
    private final static String CORPUS_FOLDER = "src/data/TrainData";

    private final static String MODEL_PATH = "src/data/models/sentiment-model.ser";

    public static void main(String[] args) throws IOException {
        IClassifier classifier = new NaiveBayesClassifier(trainOrLoadModel());
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            String line = sc.nextLine();
            predict(classifier, line);
        }

        sc.close();
    }

    private static void predict(IClassifier classifier, String text) {
        System.out.printf("《%s》 属于分类 【%s】\n", text, classifier.classify(text));
    }

    private static NaiveBayesModel trainOrLoadModel() throws IOException {
        NaiveBayesModel model = (NaiveBayesModel) IOUtil.readObjectFrom(MODEL_PATH);

        if (model != null) {
            System.out.println("Load model from " + MODEL_PATH);
            return model;
        }

        File corpusFolder = new File(CORPUS_FOLDER);
        if (!corpusFolder.exists() || !corpusFolder.isDirectory()) {
            System.err.println("没有文本分类语料，请阅读IClassifier.train(java.lang.String)中定义的语料格式与语料下载：" +
                    "https://github.com/hankcs/HanLP/wiki/%E6%96%87%E6%9C%AC%E5%88%86%E7%B1%BB%E4%B8%8E%E6%83%85%E6%84%9F%E5%88%86%E6%9E%90");
            System.exit(1);
        }

        IClassifier classifier = new NaiveBayesClassifier();
        classifier.train(CORPUS_FOLDER);
        model = (NaiveBayesModel) classifier.getModel();
        IOUtil.saveObjectTo(model, MODEL_PATH);

        return model;
    }

    public static void process(String inFilename, String outFilename) throws IOException {
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

        IClassifier classifier = new NaiveBayesClassifier(trainOrLoadModel());

        String line;
        while ((line = br.readLine()) != null) {
            String sentiment = classifier.classify(line);
            pr.println(sentiment + " " + line);
        }

        br.close();
        pr.close();
    }
}
