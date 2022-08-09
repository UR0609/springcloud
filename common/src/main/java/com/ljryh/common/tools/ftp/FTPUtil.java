package com.ljryh.common.tools.ftp;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.util.StrUtil;
import com.ljryh.common.tools.log.LoggerHelper;
import com.ljryh.common.tools.redis.MyJedisPool;
import com.ljryh.common.utils.CharsetUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.SocketException;
import java.util.*;

/**
 * @author lizp
 * @version 1.0
 * @ClassName: FTPUtil
 * @Description: FTP文件通用类
 * @since 2018-04-12 10:50:00
 */

@SuppressWarnings("all")
public class FTPUtil {

    public static LoggerHelper log = new LoggerHelper(FTPUtil.class);

    private String strIp;
    private int intPort;
    private String user;
    private String password;

    private static FTPClient ftpClient = new FTPClient();

    public final static String GBK_ENCODING = "GBK";
    /**
     * 本地字符编码
     */
    private static String LOCAL_CHARSET = GBK_ENCODING;

    // FTP协议里面，规定文件名编码为iso-8859-1
    // private static String SERVER_CHARSET = "ISO-8859-1";
    public FTPUtil(String strIp, int intPort, String user, String password) {
        this.strIp = strIp;
        this.intPort = intPort;
        this.user = user;
        this.password = password;
    }

    public FTPUtil(String resourece) {
        try {
            Properties props = new Properties();
            InputStream in = MyJedisPool.class.getResourceAsStream("/"+resourece);
            props.load(in);
            this.strIp = String.valueOf(props.getProperty("ftp.ip"));
            this.intPort = Integer.valueOf(props.getProperty("ftp.port"));
            this.user = String.valueOf(props.getProperty("ftp.username"));
            this.password = String.valueOf(props.getProperty("ftp.password"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FTPUtil() {
        try {
            Properties props = new Properties();
            InputStream in = MyJedisPool.class.getResourceAsStream("/ftp.properties");
            props.load(in);
            this.strIp = String.valueOf(props.getProperty("ftp.ip"));
            this.intPort = Integer.valueOf(props.getProperty("ftp.port"));
            this.user = String.valueOf(props.getProperty("ftp.username"));
            this.password = String.valueOf(props.getProperty("ftp.password"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否连接中
     */
    public boolean isLogin() {
        return ftpClient.isConnected();
    }

    /**
     * 判断是否登入成功
     *
     * @return boolean
     */
    public boolean ftpLogin() {
        boolean isLogin = false;
        FTPClientConfig ftpClientConfig = new FTPClientConfig();
        ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());
        ftpClient.setControlEncoding(GBK_ENCODING);
        ftpClient.configure(ftpClientConfig);
        try {
            if (!ftpClient.isConnected()) {
                if (intPort > 0) {
                    ftpClient.connect(strIp, intPort);
                } else {
                    ftpClient.connect(strIp);
                }
            }
            // FTP服务器连接回答
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                log.error("登录FTP服务失败！");
                return isLogin;
            }
            ftpClient.login(user, password);
            log.info("恭喜" + user + "成功登陆FTP服务器");
            // 设置传输协议
            /*
             * if (FTPReply.isPositiveCompletion(ftpClient.sendCommand ("OPTS UTF8", "ON")))
             * {// 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）. LOCAL_CHARSET =
             * Constant.UTF_ENCONDING; }
             */
            ftpClient.setControlEncoding(LOCAL_CHARSET);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setDataTimeout(60000);       //设置传输超时时间为60秒
            ftpClient.setConnectTimeout(60000);       //连接超时为60秒

            isLogin = true;
        } catch (Exception e) {
            log.error(user + "登录FTP服务失败：", e);
        }
        ftpClient.setBufferSize(1024 * 2);
        ftpClient.setDataTimeout(30 * 1000);
        return isLogin;
    }

    /**
     * 退出关闭服务器连接
     *
     * @return
     */
    public void ftpLogOut() {
        if (null != ftpClient && ftpClient.isConnected()) {
            try {
                boolean reuslt = ftpClient.logout();// 退出FTP服务器
                if (reuslt) {
                    log.info("成功退出服务器");
                }
            } catch (Exception e) {
                log.error("退出FTP服务器异常：", e);
            } finally {
                try {
                    ftpClient.disconnect();// 关闭FTP服务器的连接
                    log.info("已断开连接");
                } catch (IOException e) {
                    log.error("关闭FTP服务器的连接异常！");
                }
            }
        }
    }

    public boolean uploadFile(File localFile, String remoteUpLoadPath) {
        return uploadFile(localFile, remoteUpLoadPath, null);
    }

    public String[] listNames(String pathName) {
        try {
            return ftpClient.listNames(pathName);
        } catch (Exception e) {
        }
        return null;
    }

    public boolean moveFile(String from, String to) {

        try {
            boolean rename = ftpClient.rename(from, to);
            if (rename) {
                log.info(from + " 成功重命名为：" + to);
                return rename;
            }
        } catch (Exception e) {

            log.info(from + " 重命名失败！");
        }
        return false;
    }

    /**
     * FTP 文件改名
     *
     * @param remoteFilePath
     * @param remoteFileOldName
     * @param remoteFileNewName
     * @return
     */
    public boolean rename(String remoteFilePath, String remoteFileOldName, String remoteFileNewName) {
        // if (remoteFileOldName == null || "".equals(remoteFileOldName.trim()) ||
        // remoteFileNewName == null || "".equals(remoteFileNewName.trim()))
        if (StringUtils.isNotEmpty(remoteFilePath) && StringUtils.isNotEmpty(remoteFileOldName)
                && StringUtils.isNotEmpty(remoteFileNewName)) {
            try {

                ftpClient.changeWorkingDirectory(remoteFilePath);
                ftpClient.enterLocalPassiveMode();
                if (ftpClient.rename(remoteFileOldName, remoteFileNewName)) {
                    log.info(remoteFileOldName + " 成功重命名为：" + remoteFileNewName);
                    return true;
                } else {
                    log.info(remoteFileOldName + " 重命名为：" + remoteFileNewName + " 失败");
                }
            } catch (Exception e) {
                log.info(remoteFileOldName + " 重命名失败！");
            }
        }
        return false;
    }

    /**
     * 检查文件名是否存在
     *
     * @param remoteFilePathName 全路径文件名
     * @return
     */
    public boolean checkFile(String remoteFilePathName) {
        InputStream inputStream = null;
        try {
            if (StringUtils.isNotEmpty(remoteFilePathName)) {
                inputStream = ftpClient.retrieveFileStream(remoteFilePathName);
                if (inputStream != null && ftpClient.getReplyCode() != 550) {
                    log.info(remoteFilePathName + " 文件存在");
                    return true;
                } else {
                    log.info(remoteFilePathName + " 文件不存在");
                    return false;
                }

            } else {
                return false;
            }
        } catch (Exception e) {
            log.info(remoteFilePathName + "检查文件是否存在失败！");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    ftpClient.completePendingCommand(); // 必须执行，否则在循环检查多个文件时会出错
                } catch (IOException e) {

                }
            }
        }
        return false;
    }

    /**
     * 上传Ftp文件
     *
     * @param localFile        当地文件
     * @param remoteUpLoadPath 上传服务器路径应该以/结束
     * @return boolean
     */
    public boolean uploadFile(File localFile, String remoteUpLoadPath, String remoteFileName) {
        BufferedInputStream inStream = null;
        InputStream is = null;
        boolean success = false;
        try {
            long oldLen = 0;
            long newLen = 0;

            while (true) {
                newLen = localFile.length();
                if ((newLen - oldLen) > 0) {
                    oldLen = newLen;
                    log.info(localFile.getName() + "传输中...");
                    Thread.sleep(3000);
                } else {

                    log.info(localFile.getName() + "传输完毕！");
                    break;
                }
            }
            ftpClient.changeWorkingDirectory(remoteUpLoadPath);// 改变工作路径
            // 检验文件是否存在
            if (remoteFileName == null || "".equals(remoteFileName.trim())) {
                remoteFileName = localFile.getName();
            }

            inStream = new BufferedInputStream(new FileInputStream(localFile));
            log.info(localFile.getName() + "开始上传.....");
            success = ftpClient.storeFile(new String(remoteFileName.getBytes(LOCAL_CHARSET)), inStream);
            if (success) {
                log.info(localFile.getName() + "上传成功");
                return true;
            } else {
                log.info(localFile.getName() + "上传失败");
                return false;
            }

        } catch (Exception e) {
            log.error("本地文件上传到" + remoteUpLoadPath + "报错：", e);
        } finally {
            try {
                /*
                 * if (is != null) { is.close(); ftpClient.completePendingCommand();
                 * //如果不调用会导致后面对FTPClient的操作都失败 }
                 */
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException e) {
                log.error("关闭上传的本地文件流异常：", e);
            }
        }
        return success;
    }

    /***
     * 下载文件
     *
     * @param remoteFileName     待下载文件名称
     * @param localDires         下载到当地那个路径下
     * @param remoteDownLoadPath remoteFileName所在的路径
     * @return boolean
     */
    public boolean downloadFile(String remoteFileName, String localDires, String remoteDownLoadPath) {

        String strFilePath = localDires + remoteFileName;
        BufferedOutputStream outStream = null;
        boolean success = false;
        try {
            // 下载之前判断文件是否是正在传输的文件
            long oldLen = 0;
            long newLen = 0;

            while (true) {
                FTPFile[] listFiles = ftpClient.listFiles(remoteDownLoadPath + remoteFileName);

                newLen = listFiles[0].getSize();
                if ((newLen - oldLen) > 0) {
                    oldLen = newLen;
                    log.info(remoteFileName + "传输中...");
                    Thread.sleep(3000);
                } else {

                    log.info(remoteFileName + "传输完毕！");
                    break;
                }
            }

            ftpClient.changeWorkingDirectory(remoteDownLoadPath);
            outStream = new BufferedOutputStream(new FileOutputStream(strFilePath));
            log.info(remoteFileName + "开始下载....");
            success = ftpClient.retrieveFile(remoteFileName, outStream);
            if (success == true) {
                log.info(remoteFileName + "成功下载到" + strFilePath);
                return success;
            } else {
                log.info(remoteFileName + "下载失败");
            }
        } catch (Exception e) {
            log.error(remoteFileName + "下载报错：", e);
        } finally {
            if (null != outStream) {
                try {
                    outStream.flush();
                    outStream.close();
                } catch (IOException e) {
                    log.error("关闭下载的文件流异常：", e);
                }
            }
        }
        return success;
    }

    /***
     * 新建文件夹
     *
     * @param remoteDirectoryPath Ftp 服务器路径 以目录"/"结束
     * @return boolean
     */
    public boolean makeDirectory(String ftpDirectory) {
        log.info("创建文件目录" + ftpDirectory);

        try {
            StringTokenizer s = new StringTokenizer(ftpDirectory, "/");
            s.countTokens();
            String pathName = "";
            while (s.hasMoreElements()) {
                pathName = pathName + "/" + (String) s.nextElement();
                try {
                    ftpClient.makeDirectory(pathName);
                } catch (Exception e) {
                    log.error("创建文件目录" + pathName + "报错", e);
                }
            }
            // ftpClient.makeDirectory(ftpDirectory);//此方法只能创建1级新目录！
            log.info("创建文件目录" + ftpDirectory + "成功");
        } catch (Exception e) {
            log.error("创建文件目录" + ftpDirectory + "报错：", e);
            return false;
        }
        return true;
    }

    /***
     * 上传文件夹
     *
     * @param localDirectory      当地文件夹
     * @param remoteDirectoryPath Ftp 服务器路径 以目录"/"结束
     * @return boolean
     */
    public boolean uploadDirectory(String localDirectory, String remoteDirectoryPath) {
        log.info("本地文件目录" + localDirectory + "开始往" + remoteDirectoryPath + "上传");

        try {
            File src = new File(localDirectory);
            remoteDirectoryPath = remoteDirectoryPath + src.getName() + "/";
            boolean makeDirFlag = ftpClient.makeDirectory(remoteDirectoryPath);

            File[] allFile = src.listFiles();
            for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
                if (!allFile[currentFile].isDirectory()) {
                    String srcName = allFile[currentFile].getPath().toString();
                    uploadFile(new File(srcName), remoteDirectoryPath);
                }
            }
            for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
                if (allFile[currentFile].isDirectory()) {
                    // 递归
                    uploadDirectory(allFile[currentFile].getPath().toString(), remoteDirectoryPath);
                }
            }
            log.info("本地文件目录" + localDirectory + "上传到" + remoteDirectoryPath + "目录成功");
        } catch (Exception e) {
            log.error("本地文件目录" + localDirectory + "上传到" + remoteDirectoryPath + "目录报错：", e);
            return false;
        }
        return true;
    }

    /***
     * 下载文件夹
     *
     * @param localDirectoryPath本地地址
     * @param remoteDirectory        远程文件夹
     * @return boolean
     */
    public boolean downLoadDirectory(String localDirectoryPath, String remoteDirectory) {
        log.info("开始从" + remoteDirectory + "目录往本地目录" + localDirectoryPath + "下载");
        try {
            FTPFile[] allFile = ftpClient.listFiles(remoteDirectory);
            for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
                if (!allFile[currentFile].isDirectory()) {
                    downloadFile(allFile[currentFile].getName(), localDirectoryPath, remoteDirectory);
                }
            }
            for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
                if (allFile[currentFile].isDirectory()) {
                    String strremoteDirectoryPath = remoteDirectory + File.separator + allFile[currentFile].getName();
                    downLoadDirectory(localDirectoryPath, strremoteDirectoryPath);
                }
            }
            log.info("远程路径为：" + remoteDirectory + ", 下载文件到" + localDirectoryPath + "成功");
        } catch (IOException e) {
            log.error("远程路径为：" + remoteDirectory + ", 下载到" + localDirectoryPath + "报错：", e);
            return false;
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param pathname FTP服务器保存目录
     * @param filename 要删除的文件名称
     * @return
     */
    public boolean deleteFile(String pathname, String fileName) {
        boolean flag = false;
        try {
            log.info("路径为：" + pathname + ", 开始删除文件：" + fileName);
            // 切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);

            boolean deleteFile = ftpClient.deleteFile(fileName);
            if (deleteFile) {
                flag = true;
                log.info("路径为：" + pathname + ", 删除" + fileName + "文件成功");
            } else {
                log.info("路径为：" + pathname + ", 删除" + fileName + "文件失败，可能是因为不存在该文件，不影响功能");
            }
        } catch (Exception e) {
            log.error("路径为：" + pathname + ", 删除" + fileName + "文件报错：", e);
        }
        return flag;
    }

    /**
     * 删除整个目录文件夹下的TXT文件
     *
     * @param remoteDirectory 远程文件夹
     * @return boolean
     */
    public boolean deleteDirectory(String remoteDirectory) {
        try {
            if (remoteDirectory.startsWith("/") && remoteDirectory.endsWith("/")) {
                ftpClient.changeWorkingDirectory(remoteDirectory); // 这里也要做这个才好
                FTPFile[] files = ftpClient.listFiles(remoteDirectory);
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {
                        String fileName = files[i].getName();
                        if (fileName.toUpperCase().indexOf("TXT") != -1) {
                            log.info("路径为：" + remoteDirectory + ", 开始删除文件");
                            boolean dele = ftpClient.deleteFile(remoteDirectory + fileName);
                            if (dele) {
                                log.info("路径为：" + remoteDirectory + "，删除" + fileName + "文件成功");
                            } else {
                                log.info("路径为：" + remoteDirectory + "，删除" + fileName + "文件失败");
                            }
                        }
                    } else if (files[i].isDirectory()) {
                        String currDirPath = remoteDirectory + files[i].getName() + "/";
                        ftpClient.changeWorkingDirectory(currDirPath);
                        deleteDirectory(currDirPath);
                    }
                }
                log.info("路径为：" + remoteDirectory + "，删除文件成功");
                return true;
            }
        } catch (Exception e) {
            log.error("路径为：" + remoteDirectory + "，删除文件报错：", e);
            return false;
        }
        return true;
    }

    // FtpClient的Set 和 Get 函数
    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        ftpClient = ftpClient;
    }

    /**
     * 连接FTP
     *
     * @param ip
     * @param username
     * @param password
     * @param port
     */
    public static FTPClient connctServer(String ip, String username, String password, int port, String clientEnCode)
            throws Exception {
        FTPClient ftp = new FTPClient();
        try {
            if (port == 0) {
                ftp.connect(ip);

            } else {
                ftp.connect(ip, port);
            }
        } catch (SocketException e) {
            // 当出现SocketException异常时，多为网络问题。如：连接超时或者网络不通
            return null;
        } catch (IOException e) {
            throw new Exception(e);
        }

        // 登录
        boolean isLogin = ftp.login(username, password);

        if (!isLogin) {
            return null;
        }

        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            return null;
        } else {
            ftp.setFileType(FTP.BINARY_FILE_TYPE);// 设置为二进制传输模式
            ftp.enterLocalPassiveMode();// 本地客户端需要主动连接活动的ftpServer
            ftp.setControlEncoding(clientEnCode);
            FTPClientConfig conf = new FTPClientConfig(getSystemKey(ftp.getSystemName()));
            conf.setServerLanguageCode("zh");
            ftp.configure(conf);
        }
        return ftp;
    }

    public static String getSystemKey(String systemName) {
        String[] values = systemName.split(" ");
        if (values != null && values.length > 0) {
            return values[0];
        } else {
            return null;
        }

    }

    /**
     * 获取随机字符串
     *
     * @return
     */
    public static String getRandomString() {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 建立FTPClient
     *
     * @return
     * @throws Exception
     * @author sean
     * @version 1.0
     * </pre>
     * Created on :2013-9-3 下午8:15:03 LastModified: History:
     * </pre>
     */
    private static FTPClient creatFTPClient() throws Exception {

        Properties props = new Properties();
        InputStream in = MyJedisPool.class.getResourceAsStream("/ftp.properties");
        props.load(in);
        String ip = String.valueOf(props.getProperty("ftp.ip"));
        int port = Integer.valueOf(props.getProperty("ftp.port"));
        String username = String.valueOf(props.getProperty("ftp.username"));
        String password = String.valueOf(props.getProperty("ftp.password"));

        // 获取FTPClient
        FTPClient ftpClient = connctServer(ip, username, password, port, "UTF-8");
        return ftpClient;
    }

    /**
     * 上传文件到FTP服务器并返回FTP文件名与文件路径
     *
     * @param file
     * @param path
     * @return
     * @throws Exception
     * @author sean
     * @version 1.0
     * </pre>
     * Created on :2013-9-3 下午8:17:22 LastModified: History:
     * </pre>
     */
    public static String uploadFile(MultipartFile file, String path, String fileName) {
        boolean success = true;
        try {
            // 设置访问目录
            ftpClient.changeWorkingDirectory(path);
//			ftpCreateDirectoryTree(ftpClient, path);
            // FTP上传文件
            byte[] bytes = file.getBytes();
            if (bytes.length != 0) {
                success = ftpClient.storeFile(CharsetUtils.changeCharset(fileName, CharsetUtils.GBK, CharsetUtils.ISO_8859_1),
                        file.getInputStream());
            }
            if (success) {
                log.info(fileName + "上传成功");
            } else {
                log.info(fileName + "上传失败");
            }
            ftpClient.disconnect();
        } catch (Exception e) {
            log.error("本地文件上传到" + path + "报错：", e);
        }
        String pathAndName = "";
        String pathLastStr = path.substring(path.length() - 1, path.length());
        if (pathLastStr.equals("/")) {
            pathAndName = path + fileName;
        } else {
            pathAndName = path + "/" + fileName;
        }
        return pathAndName;
    }

    /**
     * 获取ftp目录下文件名
     *
     * @param rootPath 路径
     * @return
     * @throws Exception
     */
    public static List<String> getFileList(String rootPath) throws Exception {
        List<String> fileList = new ArrayList<>();
        FTPFile[] listFiles = ftpClient.listFiles(rootPath);
        for (FTPFile s : listFiles) {
            fileList.add(s.getName());
        }
        return fileList;
    }

    public static void deleteFTPdir(String rootPath) throws Exception {

        ftpClient.dele(rootPath);
        ftpClient.removeDirectory(rootPath);
    }

    public static String uploadFile(InputStream is, String path, String fileName) throws Exception {
        FTPClient ftpClient = creatFTPClient();
        // 设置访问目录
        ftpCreateDirectoryTree(ftpClient, path);
        // FTP上传文件
        ftpClient.storeFile(fileName, is);
        ftpClient.disconnect();
        String pathAndName = "";
        String pathLastStr = path.substring(path.length() - 1, path.length());
        if (pathLastStr.equals("/")) {
            pathAndName = path.substring(0, path.length() - 1) + fileName;
        } else {
            pathAndName = path + "/" + fileName;
        }
        return pathAndName;
    }

    private static void ftpCreateDirectoryTree(FTPClient client, String dirTree) throws IOException {
        boolean dirExists = true;
        String[] directories = dirTree.split("/");
        for (String dir : directories) {
            if (!dir.isEmpty()) {
                if (dirExists) {
                    dirExists = client.changeWorkingDirectory(dir);
                }
                if (!dirExists) {
                    if (!client.makeDirectory(dir)) {
                        throw new IOException("Unable to create remote directory '" + dir + "'.  error='"
                                + client.getReplyString() + "'");
                    }
                    if (!client.changeWorkingDirectory(dir)) {
                        throw new IOException("Unable to change into newly created remote directory '" + dir
                                + "'.  error='" + client.getReplyString() + "'");
                    }
                }
            }
        }
    }

    /**
     * 上传Ftp文件
     *
     * @param base64Image      base64
     * @param remoteUpLoadPath 上传服务器路径应该以/结束
     * @return boolean
     */
    public String uploadFile(String base64Image, String remoteUpLoadPath, String imageName) {
        BufferedInputStream inStream = null;
        InputStream is = null;
        boolean success = false;
        if (StrUtil.isEmpty(imageName)) {
            imageName = System.currentTimeMillis() + ".png";
        }
        try {
            ftpClient.changeWorkingDirectory(remoteUpLoadPath);// 改变工作路径

            InputStream sbs = new ByteArrayInputStream(Base64ToImage(base64Image));
            inStream = new BufferedInputStream(sbs);
            log.info(imageName + "开始上传.....");
            success = ftpClient.storeFile(new String(imageName.getBytes(LOCAL_CHARSET)), inStream);
            if (success) {
                log.info(imageName + "上传成功");
                return remoteUpLoadPath + imageName;
            } else {
                log.info(imageName + "上传失败");
                return "";
            }

        } catch (Exception e) {
            log.error("本地文件上传到" + remoteUpLoadPath + "报错：", e);
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException e) {
                log.error("关闭上传的本地文件流异常：", e);
            }
        }
        return imageName;
    }

    /**
     * base64字符串转换成图片
     *
     * @param imgStr      base64字符串
     * @param imgFilePath 图片存放路径
     * @return
     * @author ZHANGJL
     * @dateTime 2018-02-23 14:42:17
     */
    public byte[] Base64ToImage(String imgStr) { // 对字节数组字符串进行Base64解码并生成图片

        Base64Decoder decoder = new Base64Decoder();
        // Base64解码
        @SuppressWarnings("static-access")
        byte[] b = decoder.decode(imgStr);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {// 调整异常数据
                b[i] += 256;
            }
        }
        return b;

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        FTPUtil ftp = null;
        try {
            ftp = new FTPUtil();
            ftp.ftpLogin();
            System.out.println(ftp.uploadFile(new File("C:\\audio\\155894735751811385343fbf2b211d90fa422cc8065380cd78ebc.jpg"), "/resources/"));
        } catch (Exception e) {
        } finally {
            ftp.ftpLogOut();
        }

    }
}
