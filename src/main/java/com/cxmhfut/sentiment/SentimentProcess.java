package com.cxmhfut.sentiment;

import java.io.IOException;

public class SentimentProcess {
    /**
     * 输入文件名
     */
    private final static String INPUT_FILENAME = "src/data/input.txt";

    private final static String OUTPUT_FILENAME = "src/data/output.txt";

    public static void main(String[] args) {
        try {
            SentimentClassify.process(INPUT_FILENAME, OUTPUT_FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("文件处理异常");
        }

        System.out.println("情感分析处理完成");
    }
}
