package com.ljryh.common.utils;

import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author ljryh
 * @version 1.0
 * @date 2024/7/10 10:11
 */
public class WordUtils {
    /**
     * 适用于word 2007
     *
     * @param param    需要替换的参数
     * @param template 模板
     */
    public static XWPFDocument generateWord(Map<String, Object> param, String template) {
        XWPFDocument doc = null;
        try {
            //通过路径获取word模板
            OPCPackage pack = POIXMLDocument.openPackage(template);
            doc = new CustomXWPFDocument(pack);
            //通过InputStream 获取模板，此方法适用于jar包部署
            //  doc = new CustomXWPFDocument(template);
            if (param != null && param.size() > 0) {
                //处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                processParagraphs(paragraphList, param, doc);
                //处理表格
                Iterator<XWPFTable> it = doc.getTablesIterator();
                while (it.hasNext()) {
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                            processParagraphs(paragraphListTable, param, doc);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * 处理段落
     *
     * @param paragraphList
     * @throws FileNotFoundException
     * @throws InvalidFormatException
     */
    public static void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, Object> param, XWPFDocument doc) throws InvalidFormatException, FileNotFoundException {
        if (paragraphList != null && paragraphList.size() > 0) {
            for (XWPFParagraph paragraph : paragraphList) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        boolean isSetText = false;
                        for (Map.Entry<String, Object> entry : param.entrySet()) {
                            String key = entry.getKey();
                            if (text.indexOf(key) != -1) {
                                isSetText = true;
                                Object value = entry.getValue();
                                if (value instanceof String) {//文本替换
                                    text = text.replace(key, value.toString());
                                } else if (value instanceof Map) {    //图片替换
                                    text = text.replace(key, "");
                                    Map pic = (Map) value;
                                    int width = Integer.parseInt(pic.get("width").toString());
                                    int height = Integer.parseInt(pic.get("height").toString());
                                    int picType = getPictureType(pic.get("type").toString());
                                    //获取图片流，因本人项目中适用流
                                    //InputStream is = (InputStream) pic.get("content");
                                    String byteArray = (String) pic.get("content");
                                    CTInline inline = run.getCTR().addNewDrawing().addNewInline();
                                    insertPicture(doc, byteArray, inline, width, height, picType);

                                }
                            }
                        }
                        if (isSetText) {
                            run.setText(text, 0);
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取图片对应类型代码
     *
     * @param picType
     * @return int
     */
    private static int getPictureType(String picType) {
        int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
        if (picType != null) {
            if (picType.equalsIgnoreCase("png")) {
                res = CustomXWPFDocument.PICTURE_TYPE_PNG;
            } else if (picType.equalsIgnoreCase("dib")) {
                res = CustomXWPFDocument.PICTURE_TYPE_DIB;
            } else if (picType.equalsIgnoreCase("emf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_EMF;
            } else if (picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")) {
                res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
            } else if (picType.equalsIgnoreCase("wmf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_WMF;
            }
        }
        return res;
    }

    /**
     * insert Picture
     *
     * @param document
     * @param filePath
     * @param inline
     * @param width
     * @param height
     * @throws InvalidFormatException
     * @throws FileNotFoundException
     */
    private static void insertPicture(XWPFDocument document, String filePath,
                                      CTInline inline, int width,
                                      int height, int imgType) throws InvalidFormatException,
            FileNotFoundException {
        //通过流获取图片，因本人项目中，是通过流获取
        //document.addPictureData(imgFile,imgType);
        document.addPictureData(new FileInputStream(filePath), imgType);
        int id = document.getAllPictures().size() - 1;
        final int EMU = 9525;
        width *= EMU;
        height *= EMU;
//        String blipId = doc.addPictureData(new FileInputStream(new File(imgurl)), getPictureType(type));
//        String blipId = UUID.randomUUID().toString();
        System.out.println(document.addPictureData(new FileInputStream(filePath), imgType));
        String blipId = document.addPictureData(new FileInputStream(filePath), imgType);
//                document.getAllPictures().get(id).getPackageRelationship().getId();
        String picXml = getPicXml(blipId, width, height);
        XmlToken xmlToken = null;
        try {
            xmlToken = XmlToken.Factory.parse(picXml);
        } catch (XmlException xe) {
            xe.printStackTrace();
        }
        inline.set(xmlToken);
        inline.setDistT(0);
        inline.setDistB(0);
        inline.setDistL(0);
        inline.setDistR(0);
        CTPositiveSize2D extent = inline.addNewExtent();
        extent.setCx(width);
        extent.setCy(height);
        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
        docPr.setId(id);
        docPr.setName("IMG_" + id);
        docPr.setDescr("IMG_" + id);
    }

    /**
     * get the xml of the picture
     *
     * @param blipId
     * @param width
     * @param height
     * @return
     */
    private static String getPicXml(String blipId, int width, int height) {
        String picXml =
                "" + "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                        "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                        "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                        "         <pic:nvPicPr>" + "            <pic:cNvPr id=\"" + 0 +
                        "\" name=\"Generated\"/>" + "            <pic:cNvPicPr/>" +
                        "         </pic:nvPicPr>" + "         <pic:blipFill>" +
                        "            <a:blip r:embed=\"" + blipId +
                        "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>" +
                        "            <a:stretch>" + "               <a:fillRect/>" +
                        "            </a:stretch>" + "         </pic:blipFill>" +
                        "         <pic:spPr>" + "            <a:xfrm>" +
                        "               <a:off x=\"0\" y=\"0\"/>" +
                        "               <a:ext cx=\"" + width + "\" cy=\"" + height +
                        "\"/>" + "            </a:xfrm>" +
                        "            <a:prstGeom prst=\"rect\">" +
                        "               <a:avLst/>" + "            </a:prstGeom>" +
                        "         </pic:spPr>" + "      </pic:pic>" +
                        "   </a:graphicData>" + "</a:graphic>";
        return picXml;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[6024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    public static String getImagesFile(String path,String imageName) throws Exception {

        URL url = new URL(path);
        //打开连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求方式为"GET"
        conn.setRequestMethod("GET");
        //超时响应时间为10秒
        conn.setConnectTimeout(10 * 1000);
        //通过输入流获取图片数据
        InputStream inStream = conn.getInputStream();
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
        byte[] data = readInputStream(inStream);
        //new一个文件对象用来保存图片，默认保存当前工程根目录
        File imageFile = new File(imageName);
        //创建输出流
        FileOutputStream outStream = new FileOutputStream(imageFile);
        //写入数据
        outStream.write(data);
        //关闭输出流
        outStream.close();
//        File file = new File("InputStreamInfo.jpg");
//        if (file.delete()) {
//            System.out.println(file.getName() + " 文件已被删除！");
//        } else {
//            System.out.println("文件删除失败！");
//        }
        return imageName;
    }

    public static void main(String[] args) throws Exception {

        System.out.println(getImagesFile("http://ljryh.oss-cn-hangzhou.aliyuncs.com/de4276f082025aaf2e090d1af6edab64024f1afc.jpg764710523211919703893f829a96619408abb4fd724f41bd6c8.jpg","aaa.jpg"));

//        PictureRenderData pictureRenderData = Pictures.ofStream(new FileInputStream("d:\\zhangsan.jpg"), PictureType.PNG)
//                .size(102, 126).create();
        //定义一个URL对象
//        URL url = new URL("http://ljryh.oss-cn-hangzhou.aliyuncs.com/de4276f082025aaf2e090d1af6edab64024f1afc.jpg764710523211919703893f829a96619408abb4fd724f41bd6c8.jpg");
//        //打开连接
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        //设置请求方式为"GET"
//        conn.setRequestMethod("GET");
//        //超时响应时间为10秒
//        conn.setConnectTimeout(10 * 1000);
//        //通过输入流获取图片数据
//        InputStream inStream = conn.getInputStream();
//        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
//        byte[] data = readInputStream(inStream);
//        //new一个文件对象用来保存图片，默认保存当前工程根目录
//        File imageFile = new File("InputStreamInfo.jpg");
//        //创建输出流
//        FileOutputStream outStream = new FileOutputStream(imageFile);
//        //写入数据
//        outStream.write(data);
//        //关闭输出流
//        outStream.close();
//        File file = new File("InputStreamInfo.jpg");
//        if(file.delete()){
//            System.out.println(file.getName() + " 文件已被删除！");
//        }else{
//            System.out.println("文件删除失败！");
//        }

        Map<String, Object> param = new HashMap<>();
        param.put("name", "易柏忆");
        param.put("sex", "男");
        param.put("birthday", "1995-01-01");
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("width", 30);
        header.put("height", 30);
        header.put("type", "png");
        header.put("content", "/Users/yibaiyi/IdeaProjects/ruoyi-vue-activiti-master/uploadPath/docker.png");//图片路径
        param.put("image", header);
        Map<String, Object> header2 = new HashMap<String, Object>();
        header2.put("width", 30);
        header2.put("height", 30);
        header2.put("content", "/Users/yibaiyi/IdeaProjects/ruoyi-vue-activiti-master/uploadPath/guanxing.jpg");//图片路径
        header2.put("type", "jpg");
        param.put("guanxing", header2);
        XWPFDocument doc = WordUtils.generateWord(param, "/Users/yibaiyi/IdeaProjects/ruoyi-vue-activiti-master/uploadPath/wordTemplate.docx");
        FileOutputStream fopts = new FileOutputStream("/Users/yibaiyi/IdeaProjects/ruoyi-vue-activiti-master/uploadPath/test.docx");
        doc.write(fopts);
        fopts.close();
    }
}
