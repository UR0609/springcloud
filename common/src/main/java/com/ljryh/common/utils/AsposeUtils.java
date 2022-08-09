package com.ljryh.common.utils;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author ljryh
 * @version 1.0
 * @date 2022/4/27 10:47
 */
public class AsposeUtils {

    public static void main(String[] args) {

        AsposeUtils.wordToPdf("/Users/yibaiyi/Documents/work/demo.doc",
                "/Users/yibaiyi/Documents/work/demo.pdf");

    }

    /**
     * 加载license 用于破解 不生成水印
     *
     * @author LCheng
     * @date 2020/12/25 13:51
     */
    @SneakyThrows
    private static void getLicense() {
        try (InputStream is = AsposeUtils.class.getClassLoader().getResourceAsStream("License.xml")) {
            License license = new License();
            license.setLicense(is);
        }
    }

    /**
     * word转pdf
     *
     * @param wordPath word文件保存的路径
     * @param pdfPath  转换后pdf文件保存的路径
     * @author LCheng
     * @date 2020/12/25 13:51
     */
    @SneakyThrows
    public static void wordToPdf(String wordPath, String pdfPath) {
        getLicense();
        File file = new File(pdfPath);
        try (FileOutputStream os = new FileOutputStream(file)) {
            Document doc = new Document(wordPath);
            doc.save(os, SaveFormat.PDF);
        }
    }

}
