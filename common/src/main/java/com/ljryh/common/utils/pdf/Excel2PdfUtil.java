package com.ljryh.common.utils.pdf;

import com.aspose.cells.*;
import com.ruoyi.common.utils.aliyun.ShortUUIDUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author ljryh
 * @version 1.0
 * @date 2023/8/27 20:46
 */
public class Excel2PdfUtil {

    /**
     * 加载配置文件
     *
     * @return
     */
    private static boolean getLicense() {
        boolean result = false;
        try (InputStream in = Excel2PdfUtil.class.getClassLoader()
                .getResourceAsStream("license.xml")) {
            // License的包路径必须为com.aspose.cells.License
            License license = new License();
            license.setLicense(in);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * @param inputStream
     * @param fileName
     * @param response
     */
    public static void excel2Pdf(InputStream inputStream, String fileName, HttpServletResponse response) {
        System.out.println("pdf转换中...");
        long old = System.currentTimeMillis();
        try (ByteArrayOutputStream fos = new ByteArrayOutputStream()) {
            // 验证
            if (!getLicense()) {
                throw new RuntimeException("文件转换失败!");
            }
            // 设置字体包位置
            IndividualFontConfigs configs = new IndividualFontConfigs();
            configs.setFontFolder("/System/Library/Fonts", true);
            LoadOptions loadOptions = new LoadOptions();
            loadOptions.setFontConfigs(configs);

            // 也可以直接指定路径 workbook = new Workbook(docPath, loadOptions);
            Workbook workbook = new Workbook(inputStream, loadOptions);

            PdfSaveOptions opts = new PdfSaveOptions();
            // 设置excel不换行在pdf显示
            // opts.setAllColumnsInOnePagePerSheet(true);
            // 设置一个sheet在一页pdf
            opts.setOnePagePerSheet(true);
            workbook.save(fos, opts);

            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".pdf", "UTF-8"));
            byte[] buffer = fos.toByteArray();
            InputStream arrayInputStream = new ByteArrayInputStream(buffer);

            byte[] buf = new byte[4096];
            int len = -1;
            while ((len = arrayInputStream.read(buf)) != -1) {
                response.getOutputStream().write(buf, 0, len);
            }

            // arrayInputStream 输入到PDF文件中去
            String name = ShortUUIDUtils.getUuid() + ".pdf";
            String filePath = "/Users/yibaiyi/IdeaProjects/ruoyi-vue-activiti-master/uploadPath/file/" + name;
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedInputStream in = null;
            BufferedOutputStream out = null;
            in = new BufferedInputStream(arrayInputStream);
            out = new BufferedOutputStream(new FileOutputStream(filePath));
            int len2 = -1;
            byte[] b = new byte[1024];
            while ((len2 = in.read(b)) != -1) {
                out.write(b, 0, len2);
            }
            in.close();
            out.close();


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

}
