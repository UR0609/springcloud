package com.ljryh.common.utils.pdf;



import com.aspose.words.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author ljryh
 * @version 1.0
 * @date 2023/8/26 18:15
 */
public class Doc2PdfUtil {

    /**
     * 加载授权配置文件
     *
     * @return
     */
    private static boolean getLicense() {
        boolean result = false;
        try (InputStream in = Doc2PdfUtil.class.getClassLoader().getResourceAsStream("license.xml")) {
            // License的包路径必须为com.aspose.words.License
            License license = new License();
            license.setLicense(in);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * doc转pdf
     *
     * @param inputStream
     * @param fileName
     * @param response
     * @return
     */
    public static void doc2Pdf(InputStream inputStream,String fileName, HttpServletResponse response) {
        System.out.println("pdf转换中...");
        long old = System.currentTimeMillis();

        try (ByteArrayOutputStream fos = new ByteArrayOutputStream()) {
            // 验证
            if (!getLicense()) {
                throw new RuntimeException("文件转换失败!");
            }
            // 加载字体
            FontSettings settings = FontSettings.getDefaultInstance();
            settings.setFontsFolder("/System/Library/Fonts", true);
            LoadOptions loadOptions = new LoadOptions();
            loadOptions.setFontSettings(settings);

            // 也可以直接指定路径 document = new Document(docPath);
            Document document = new Document(inputStream, loadOptions);

            //  DocumentBuilder builder = new DocumentBuilder(document);
            // 设置纸张大小
            //  builder.getPageSetup().setPaperSize(PaperSize.A3);
            // 设置横向
            // builder.getPageSetup().setOrientation(Orientation.LANDSCAPE);

            document.save(fos, SaveFormat.PDF);
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".pdf", "UTF-8"));
            byte[] buffer = fos.toByteArray();
            InputStream arrayInputStream = new ByteArrayInputStream(buffer);
            byte[] buf = new byte[4096];
            int len = -1;
            while ((len = arrayInputStream.read(buf)) != -1) {
                response.getOutputStream().write(buf, 0, len);
            }
            long now = System.currentTimeMillis();
            System.out.println("pdf转换成功，共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件转换失败!");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * doc转pdf
     *
     * @param docPath
     * @return
     */
    public static void doc2Pdf(String docPath) {
        System.out.println("pdf转换中...");
        long old = System.currentTimeMillis();

        try  {
            // 验证
            if (!getLicense()) {
                throw new RuntimeException("文件转换失败!");
            }
            // 加载字体
            FontSettings settings = FontSettings.getDefaultInstance();

            String os = System.getProperty("os.name");
            if(os.startsWith("Linux")){
                settings.setFontsFolder("/usr/share/fonts/Fonts", true);
            }else if(os.startsWith("Mac")){
                settings.setFontsFolder("/System/Library/Fonts", true);
            }else{

            }
            LoadOptions loadOptions = new LoadOptions();
            loadOptions.setFontSettings(settings);

//             也可以直接指定路径 document = new Document(docPath);
//            Document document = new Document(inputStream, loadOptions);
            Document document = new Document(docPath, loadOptions);

            //  DocumentBuilder builder = new DocumentBuilder(document);
            // 设置纸张大小
            //  builder.getPageSetup().setPaperSize(PaperSize.A3);
            // 设置横向
            // builder.getPageSetup().setOrientation(Orientation.LANDSCAPE);

            SaveOptions pdfSaveOptions = PdfSaveOptions.createSaveOptions(SaveFormat.PDF);

            document.save(docPath.replace(".docx",".pdf"),pdfSaveOptions);

            long now = System.currentTimeMillis();
            System.out.println("pdf转换成功，共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件转换失败!");
        } finally {

        }
    }

}
