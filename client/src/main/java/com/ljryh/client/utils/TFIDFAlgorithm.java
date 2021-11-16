package com.ljryh.client.utils;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ljryh
 * @date 2021/11/1 9:59
 */
public class TFIDFAlgorithm {
    /**
     * 根据文件路径，文件中存放的100个网址的 url，获取 url 路径列表
     *
     * @param path 本地文件路径
     * @return 路径列表
     */
    public List<String> readUrlFromText(String path) {
        if (StringUtils.isBlank(path)) {
            return null;
        }
        List<String> urls = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                urls.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urls;
    }

    /**
     * 利用 Jsoup 工具，根据网址获取网页文本
     *
     * @param url 网址
     * @return 网页文本
     */
    public String getTextFromUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }

        String text = "";
        try {
            Document document = Jsoup.connect(url).get();
            text = document.text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.replace(" ", "");
    }

    /**
     * 运用 ansj 给文本分词
     *
     * @param text 文本内容
     * @return 分词结果
     */
    public List<Term> parse(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        List<Term> terms = ToAnalysis.parse(text).getTerms();
        if (terms == null || terms.size() == 0) {
            return null;
        }
        return terms;
    }

    /**
     * 计算一篇文章分词后除去标点符号后词的总数
     *
     * @param terms 分词后的集合
     * @return 一篇文章分词后除去标点符号后词的总数
     */
    private Integer countWord(List<Term> terms) {
        if (terms == null || terms.size() == 0) {
            return null;
        }
        for (int i = 0; i < terms.size(); i++) {
            if ("null".equals(terms.get(i).getNatureStr()) || terms.get(i).getNatureStr().startsWith("w")) {
                terms.remove(i);
            }
        }
        return terms.size();
    }

    /**
     * 计算词频 IF
     *
     * @param word  词
     * @param terms 分词结果集合
     * @return IF
     */
    public double computeTF(String word, List<Term> terms) {
        if (StringUtils.isBlank(word)) {
            return 0.0;
        }
        int count = 0;
        for (Term term : terms) {
            if (term.getName().equals(word)) {
                count += 1;
            }
        }
        return (double) count / countWord(terms);
    }

    /**
     * 统计词语的逆文档频率 IDF
     *
     * @param path 存放 url 的文件路径
     * @param word IDF
     */
    public double computeIDF(String path, String word) {
        if (StringUtils.isBlank(path) || StringUtils.isBlank(word)) {
            return 0.0;
        }

        List<String> urls = readUrlFromText(path);
        int count = 1;
        for (String url : urls) {
            String text = getTextFromUrl(url);
            if (text.contains(word)) {
                count += 1;
            }
        }
        return Math.log10((double) urls.size() / count);
    }

    /**
     * 计算词频-逆文档频率 TF—IDF
     *
     * @param filePath 存放url的文件路径
     * @param terms    分词结果集合
     * @param word     词
     * @return TF—IDF
     */

    public Double computeTFIDF(String filePath, List<Term> terms, String word) {
        return computeTF(word, terms) * computeIDF(filePath, word);
    }
}
