package com.ljryh.client.utils.aliyun;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.ljryh.common.utils.DateUtils;
import com.ljryh.common.utils.GsonUtils;
import com.ljryh.common.utils.ShortUUIDUtils;
import com.ljryh.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ljryh
 * @version 1.0
 * @date 2022/9/2 09:34
 */
public class AliyunOSSClientUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    private long bytesWritten = 0;
    private long totalBytes = -1;
    private boolean succeed = false;

    private static String number;


    //阿里云API的内或外网域名
    private static String ENDPOINT;
    //阿里云API的密钥Access Key ID
    private static String ACCESS_KEY_ID;
    //阿里云API的密钥Access Key Secret
    private static String ACCESS_KEY_SECRET;
    //阿里云API的bucket名称
    private static String BACKET_NAME;
    //阿里云API的文件夹名称
    private static String FOLDER;

    //初始化
    /* private static  OSSClient ossClient=AliyunOSSClientUtil.getOSSClient();*/
    //初始化属性
    static {
        ENDPOINT = OSSClientConstants.ENDPOINT;
        ACCESS_KEY_ID = OSSClientConstants.ACCESS_KEY_ID;
        ACCESS_KEY_SECRET = OSSClientConstants.ACCESS_KEY_SECRET;
        BACKET_NAME = OSSClientConstants.BACKET_NAME;
        FOLDER = OSSClientConstants.FOLDER;
    }

    /**
     * 获取阿里云OSS客户端对象
     *
     * @return ossClient
     */
    public static OSSClient getOSSClient() {
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration(); // 连接空闲超时时间，超时则关闭
        conf.setIdleConnectionTime(1000); // 在这里可以做一些配置，比如超时时间、最大连
        return new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    /**
     * 创建存储空间
     *
     * @param ossClient  OSS连接
     * @param bucketName 存储空间
     * @return
     */
    public static String createBucketName(OSSClient ossClient, String bucketName) {
        //存储空间
        final String bucketNames = bucketName;
        if (!ossClient.doesBucketExist(bucketName)) {
            //创建存储空间
            Bucket bucket = ossClient.createBucket(bucketName);
            log.info("创建存储空间成功");
            return bucket.getName();
        }
        return bucketNames;
    }

    /**
     * 删除存储空间buckName
     *
     * @param ossClient  oss对象
     * @param bucketName 存储空间
     */
    public static void deleteBucket(OSSClient ossClient, String bucketName) {
        ossClient.deleteBucket(bucketName);
        log.info("删除" + bucketName + "Bucket成功");
    }

    /**
     * 创建模拟文件夹
     *
     * @param ossClient  oss连接
     * @param bucketName 存储空间
     * @param folder     模拟文件夹名如"qj_nanjing/"
     * @return 文件夹名
     */
    public static String createFolder(OSSClient ossClient, String bucketName, String folder) {
        //文件夹名
        final String keySuffixWithSlash = folder;
        //判断文件夹是否存在，不存在则创建
        if (!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)) {
            //创建文件夹
            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
            log.info("创建文件夹成功");
            //得到文件夹名
            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
            String fileDir = object.getKey();
            return fileDir;
        }
        return keySuffixWithSlash;
    }

    /**
     * 根据key删除OSS服务器上的文件
     *
     * @param ossClient  oss连接
     * @param bucketName 存储空间
     * @param folder     模拟文件夹名 如"qj_nanjing/"
     * @param key        Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
     */
    public static void deleteFile(OSSClient ossClient, String bucketName, String folder, String key) {
        ossClient.deleteObject(bucketName, folder + key);
        log.info("删除" + bucketName + "下的文件" + folder + key + "成功");
    }


    //测试
    public static void main(String[] args) throws ParseException {
        //初始化OSSClient
        OSSClient ossClient = AliyunOSSClientUtils.getOSSClient();
        //上传文件
        String imgPath = "//Users/yibaiyi/tool/5361661994041.jpg";
        String[] file = imgPath.split(",");
        for (String filename : file) {
            File filess = new File(filename);
            Map<String, Object> map = uploadObject2OSS(ossClient, filess, BACKET_NAME, FOLDER);
            log.info(GsonUtils.ModuleTojosn(map));
        }
    }

    /**
     * 上传图片至OSS
     *
     * @param ossClient  oss连接
     * @param file       上传文件（文件全路径如：D:\\image\\cake.jpg）
     * @param bucketName 存储空间
     * @param folder     模拟文件夹名 如"qj_nanjing/"
     * @return Map 返回的唯一MD5数字签名 以及 对应的上传到文件的key
     */
    public static Map<String, Object> uploadObject2OSS(OSSClient ossClient, File file, String bucketName, String folder) {
        String resultStr = null;
        Map map = new HashMap();
        try {
            //以输入流的形式上传文件
            InputStream is = new FileInputStream(file);
            //文件名
            String fileName = file.getName();
            fileName.replace("/", "").replace("\\", "");
            fileName = fileName.substring(0, fileName.lastIndexOf("."))
                    + ShortUUIDUtils.getUuid() + fileName.substring(fileName.lastIndexOf("."));
            // key的组成
            /*String newFileName= UUID.randomUUID().toString();*/
            //文件大小
            Long fileSize = file.length();
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //上传的文件的长度
            metadata.setContentLength(is.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            //上传文件   (上传文件流的形式)
            PutObjectResult putResult = ossClient.putObject(bucketName, fileName, is, metadata);
            //解析结果
            System.out.println(getUrl(fileName));
            resultStr = putResult.getETag();
            map.put("resultStr", resultStr);
            map.put("imageUrl", fileName);
            map.put("url", getUrl(fileName));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
        }
        return map;
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static String getContentType(String fileName) {
        //文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension) || ".png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if (".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        if (".flv".equalsIgnoreCase(fileExtension)) {
            return "video/x-flv";
        }
        if (".mp3".equalsIgnoreCase(fileExtension)) {
            return "audio/mpeg";
        }
        if (".mp4".equalsIgnoreCase(fileExtension)) {
            return "video/mp4";
        }

        //默认返回类型
        return "image/jpeg";
    }

    //通过key获取到上传文件的地址
    public static String getUrl(String key) {
        OSSClient ossClient = AliyunOSSClientUtils.getOSSClient();
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(BACKET_NAME, key, expiration);
        if (url != null) {
            return url.toString().split("\\?")[0];
        }
        return null;
    }


    /**
     * 上传图片至OSS
     *
     * @param ossClient                    oss连接
     * @param （文件全路径如：D:\\image\\cake.jpg）
     * @param bucketName                   存储空间
     * @param folder                       模拟文件夹名 如"qj_nanjing/"
     * @return Map 返回的唯一MD5数字签名 以及 对应的上传到文件的key
     */
    public Map<String, Object> uploadObject2OSSnew(OSSClient ossClient, MultipartFile f, String bucketName, String folder, String bisType, String sessionKey) {
        number = sessionKey;
        String resultStr = null;
        Map map = new HashMap();
        try {
            //以输入流的形式上传文件
            InputStream is = f.getInputStream();
            //文件名
            String fileName = f.getOriginalFilename();
            if (fileName.lastIndexOf(".") == -1) {
                fileName = DateUtils.dateTimeNow(DateUtils.ISO_DATE_FORMAT) + "/" + Calendar.getInstance().getTimeInMillis() + ".jpg";
            }
            fileName.replace("/", "").replace("\\", "");
            if ("".equals(fileName)) {
                fileName = DateUtils.dateTimeNow(DateUtils.ISO_DATE_FORMAT) + "/" + fileName.substring(0, fileName.lastIndexOf(".")) + Calendar.getInstance().getTimeInMillis() + ".jpg";
            } else {
                fileName = DateUtils.dateTimeNow(DateUtils.ISO_DATE_FORMAT) + "/" + fileName.substring(0, fileName.lastIndexOf(".")) + Calendar.getInstance().getTimeInMillis() + fileName.substring(fileName.lastIndexOf("."), fileName.length());
            }
            if (StringUtils.isNotEmpty(bisType)) {
                fileName = bisType + "/" + fileName;
            }
            if (StringUtils.isNotEmpty(folder)) {
                fileName = folder + "/" + fileName;
            }

            // key的组成
            /*String newFileName= UUID.randomUUID().toString();*/
            //文件大小
            Long fileSize = f.getSize();
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //上传的文件的长度
            metadata.setContentLength(is.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(f.getContentType());
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            //上传文件   (上传文件流的形式)
            PutObjectResult putResult = ossClient.putObject(bucketName, fileName, is, metadata);
            // 带进度条的上传。
            //解析结果
            System.out.println(getUrl(fileName));
            resultStr = putResult.getETag();
            map.put("resultStr", resultStr);
            map.put("guid", fileName);
            map.put("imageUrl", getUrl(fileName));
            System.out.println(map);
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
        }
        return map;
    }

    //上传返回url
    public String getFileUrl(File file) {
        //上传图片 file 文件 folder 文件夹名字
        OSSClient ossClient = AliyunOSSClientUtils.getOSSClient();
        Map map = uploadObject2OSS(ossClient, file, BACKET_NAME, FOLDER);
        return this.getUrl(map.get("imageUrl").toString());
    }

    public String getFileUrlNew(MultipartFile m, String bisType, String sessionKey) {
        OSSClient ossClient = AliyunOSSClientUtils.getOSSClient();
        Map map = this.uploadObject2OSSnew(ossClient, m, BACKET_NAME, FOLDER, bisType, sessionKey);
        return map.get("imageUrl").toString();
    }

    public Map<String, String> getFileUrlNewFlow(MultipartFile m, String bisType) {
        OSSClient ossClient = AliyunOSSClientUtils.getOSSClient();
        Map map = this.uploadObject2OSSnew(ossClient, m, BACKET_NAME, FOLDER, bisType, "");
        return map;
    }

    /**
     * 将MultipartFile转换为File
     *
     * @param multiFile
     * @return
     */
    public static File MultipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若须要防止生成的临时文件重复,能够在文件名后添加随机码

        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
