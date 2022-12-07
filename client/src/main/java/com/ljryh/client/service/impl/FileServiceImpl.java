package com.ljryh.client.service.impl;

import cn.hutool.core.date.DateUtil;
import com.ljryh.client.entity.FileInfo;
import com.ljryh.client.entity.shiro.User;
import com.ljryh.client.mapper.FileMapper;
import com.ljryh.client.service.FileService;
import com.ljryh.common.entity.MicroException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.*;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author ljryh
 * @version 1.0
 * @date 2022/8/8 15:51
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FileMapper fileMapper;

    @Value("${upload.file-path}")
    private String uploadFilePath;
    @Value("${upload.api-server-addr}")
    private String apiServerAddr;

    @Override
    public FileInfo saveFile(InputStream fileStream, String fileName, String contentType, String s, String creator) throws IOException {
        String fileBasePath = uploadFilePath;
        //文件存储的相对路径，以日期分隔，每天创建一个新的目录
        String filePath = DateUtil.format(new Date(), "yyyyMMdd");
        if (contentType != null && !contentType.equals("")) {
            filePath = contentType + File.separator + filePath;
        }
        //文件存储绝对路径
        String absPath = fileBasePath.concat("/").concat(filePath);
        File path = new File(absPath);
        if (!path.exists()) {
            //创建目录
            path.mkdirs();
        }
        //临时文件名 ,纳秒的md5值
        String newName = String.valueOf(System.nanoTime());
        String fileAbsName = absPath.concat("/").concat(newName);
        int fileSize;
        MessageDigest digest = DigestUtils.getMd5Digest();
        try (InputStream proxyStream = new InputStream() {
            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                int l = fileStream.read(b, off, len);
                digest.update(b, off, len);
                return l;
            }

            @Override
            public void close() throws IOException {
                fileStream.close();
                super.close();
            }

            @Override
            public int available() throws IOException {
                return fileStream.available();
            }

            @Override
            public int read() throws IOException {
                return fileStream.read();
            }
        }; FileOutputStream os = new FileOutputStream(fileAbsName)) {
            int remainBytes = fileSize = proxyStream.available();
            byte[] buff = new byte[Math.min(remainBytes, 1024 * 10)];
            int bytes;
            log.info("开始写出文件:{}到:{}, size: {} bytes", fileName, fileAbsName, fileSize);
            while (remainBytes > 0) {
                bytes = proxyStream.read(buff, 0, Math.min(remainBytes, buff.length));
                os.write(buff, 0, bytes);
                remainBytes -= bytes;
//                logger.info("写出文件:{}:{},剩余数据量: {} bytes", fileName, fileAbsName, remainBytes);
            }
            // StreamUtils.copy(in, os);
        }
        String fileId = "";
        //获取文件的md5值
        String md5 = Hex.encodeHexString(digest.digest());
        filePath = filePath.concat("/").concat(md5);
        File newFile = new File(fileAbsName);
        //判断文件是否已经存在
        FileInfo fileInfo = this.selectByMd5(md5);
        if (fileInfo != null) {
            log.info("文件:{}已上传过", fileAbsName);
            if (new File(uploadFilePath + "/" + fileInfo.getFilePath()).exists()) {
                //文件路径不变
                filePath = fileInfo.getFilePath();
                //文件已存在则删除临时文件不做处理
                newFile.delete();
            } else {
                newFile.renameTo(new File(absPath.concat("/").concat(md5)));
            }
            fileId = fileInfo.getId();
        } else {
            log.info("上传文件{}完成:{}->{}", fileName, fileAbsName, absPath.concat("/").concat(md5));
            newFile.renameTo(new File(absPath.concat("/").concat(md5)));
        }
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return saveOrUpdateFileInfo(fileId, fileName, contentType, 1L, filePath, fileSize, md5);
    }

    @Override
    public FileInfo selectByIdOrMd5(String idOrMd5) {
        if (null == idOrMd5) {
            return null;
        }
        return fileMapper.selectByIdOrMd5(idOrMd5);
    }

    @Override
    public void writeFile(String idOrMd5, ServletOutputStream outputStream, int skip)   throws IOException {
        try (InputStream inputStream = readFile(idOrMd5)) {
            if (skip > 0) {
                long len = inputStream.skip(skip);
            }
            StreamUtils.copy(inputStream, outputStream);
        }
    }

    public InputStream readFile(String fileIdOrMd5) {
        FileInfo fileInfo = fileMapper.selectByIdOrMd5(fileIdOrMd5);
        return readFile(fileInfo);
    }

    public InputStream readFile(FileInfo fileInfo) {
        if (fileInfo == null ) {
            throw new MicroException("file not found or disabled");
        }
        //配置中的文件上传根路径
        String filePath = uploadFilePath + fileInfo.getFilePath();
        File file = new File(filePath);
        if (!file.exists()) {
            throw new MicroException("file not found");
        }
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException ignore) {
            //  never happen
            throw new MicroException("file not found");
        }
    }

    private FileInfo saveOrUpdateFileInfo(String fileId, String fileName, String type, Long id, String filePath, int fileSize, String md5) {
        FileInfo infoEntity = new FileInfo();
        infoEntity.setFilePath(filePath);
        infoEntity.setMd5(md5);
        infoEntity.setFileStatus("1");
        infoEntity.setFileSize(fileSize);
        infoEntity.setFileName(fileName);
        infoEntity.setFileType(type);
        infoEntity.setCreater(id);
        infoEntity.setUpdateTime(LocalDateTime.now());
        if (ObjectUtils.isEmpty(fileId)) {
            infoEntity.setCreateTime(LocalDateTime.now());
            fileMapper.insert(infoEntity);
        } else {
            infoEntity.setId(fileId);
            fileMapper.updateById(infoEntity);
        }
        // TODO-SEA 返回前端文件访问路径
        infoEntity.setFileTotalPath( apiServerAddr + "/file/download/" + md5);
        return infoEntity;
    }

    public FileInfo selectByMd5(String md5) {
        if (null == md5) {
            return null;
        }
        return fileMapper.selectByMd5(md5);
    }

}
