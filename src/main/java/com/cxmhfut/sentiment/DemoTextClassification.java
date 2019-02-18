package com.cxmhfut.sentiment;

import com.cxmhfut.utils.TestUtility;
import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;

import java.io.IOException;

/**
 * 第一个demo,演示文本分类最基本的调用方式
 *
 * @author hankcs
 */
public class DemoTextClassification {
    /**
     * 搜狗文本分类语料库5个类目，每个类目下1000篇文章，共计5000篇文章
     */
    private static final String CORPUS_FOLDER = TestUtility.ensureTestData("搜狗文本分类语料库迷你版", "http://hanlp.linrunsoft.com/release/corpus/sogou-text-classification-corpus-mini.zip");
    /**
     * 模型保存路径
     */
    private static final String MODEL_PATH = "src/data/models/classification-model.ser";


    public static void main(String[] args) throws IOException {
        IClassifier classifier = new NaiveBayesClassifier(TestUtility.trainOrLoadModel(CORPUS_FOLDER, MODEL_PATH));
        predict(classifier, "C罗压梅西内马尔蝉联金球奖 2017=C罗年");
        predict(classifier, "英国造航母耗时8年仍未服役 被中国速度远远甩在身后");
        predict(classifier, "研究生考录模式亟待进一步专业化");
        predict(classifier, "如果真想用食物解压,建议可以食用燕麦");
        predict(classifier, "通用及其部分竞争对手目前正在考虑解决库存问题");
    }

    private static void predict(IClassifier classifier, String text) {
        System.out.printf("《%s》 属于分类 【%s】\n", text, classifier.classify(text));
    }
}
