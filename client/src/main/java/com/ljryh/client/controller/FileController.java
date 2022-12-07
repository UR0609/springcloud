package com.ljryh.client.controller;

import com.google.common.collect.Lists;
import com.ljryh.client.entity.FileInfo;
import com.ljryh.client.service.FileService;
import com.ljryh.common.entity.CallResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author ljryh
 * @version 1.0
 * @date 2022/8/8 15:15
 */
@Slf4j
@RestController
@RequestMapping("/file")
@Api(tags = "文件上传/下载")
public class FileController {

    private static final Pattern fileNameKeyWordPattern = Pattern.compile("(\\\\)|(/)|(:)(|)|(\\?)|(>)|(<)|(\")");

    @Resource
    private FileService fileService;

    /**
     * 上传单个文件
     *
     * @param file 上传文件
     * @return 上传结果
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("上传单个文件")
    public CallResult upload(@RequestPart("file") MultipartFile file) {

        if (file.isEmpty()) {
            return CallResult.fail("文件不能为空");
        }
        String fileName = file.getOriginalFilename();
        FileInfo fileInfo = new FileInfo();
        try {
            //TODO-SEA  获取当前登录人id==》creator
            String creator = "test";
            fileInfo = fileService.saveFile(file.getInputStream(), fileName, file.getContentType(), "", creator);
        } catch (IOException e) {
//            throw new MicroAlertException(500, "save file error", e);
            log.info("方法 : FileController.upload() 异常 {}", e.getMessage());
            CallResult.fail("文件上传异常");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "操作成功");
        map.put("code", 0);
        map.put("file", fileInfo);
        return CallResult.success(map);
    }

    /**
     * 上传文件,支持多文件上传.获取到文件流后,调用{@link ( InputStream , String, String, String, String)}进行文件保存
     * 上传成功后,将返回资源信息如:[{"id":"fileId","name":"fileName","md5":"md5"}]
     *
     * @param files 上传的文件
     * @return 文件上传结果.
     */
    @PostMapping(value = "/upload-multi", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("上传多个文件")
    public CallResult uploadList(@RequestPart("files") MultipartFile[] files) {
        if (files == null && files.length < 0) {
            return CallResult.fail("文件不能为空");
        }
        //查所有文件
        List<FileInfo> fileInfos = Lists.newArrayList();
        //遍历文件
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String fileName = file.getOriginalFilename();
            FileInfo fileInfo = new FileInfo();
            try {
                //TODO-SEA  获取当前登录人id==》creator
                String creator = "test";
                fileInfo = fileService.saveFile(file.getInputStream(), fileName, file.getContentType(), "", creator);
                fileInfos.add(fileInfo);
            } catch (IOException e) {
                log.info("方法 : FileController.upload() 异常 {}", e.getMessage());
                CallResult.fail("文件上传异常");
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "操作成功");
        map.put("code", 0);
        map.put("file", fileInfos);
        return CallResult.success(map);
    }

    /**
     * 通过文件ID下载已经上传的文件,支持断点下载
     * 如: http://host:port/file/download/aSk2a/file.zip 将下载 ID为aSk2a的文件.并命名为file.zip
     *
     * @param idOrMd5  要下载资源文件的id或者md5值
     * @param name     自定义文件名，该文件名不能存在非法字符.如果此参数为空(null).将使用文件上传时的文件名
     * @param response {@link javax.servlet.http.HttpServletResponse}
     * @param request  {@link javax.servlet.http.HttpServletRequest}
     * @return 下载结果, 在下载失败时, 将返回错误信息
     * @throws IOException 读写文件错误
     * @throws Exception   文件不存在
     */
    @GetMapping(value = "/download/{id}")
    @ApiOperation("下载(或预览)文件")
    public void downLoad(@ApiParam("文件的id或者md5") @PathVariable("id") String idOrMd5,
                         @ApiParam(value = "文件名,如果未指定,默认为上传时的文件名") @RequestParam(value = "name", required = false) String name,
                         @ApiParam(hidden = true) HttpServletResponse response, @ApiParam(hidden = true) HttpServletRequest request)
            throws IOException {
        FileInfo fileInfo = fileService.selectByIdOrMd5(idOrMd5);
        if (ObjectUtils.isEmpty(fileInfo)) {
            System.out.println("文件不存在");
            return;
        }
        String fileName = fileInfo.getFileName();

        String suffix = fileName.contains(".") ?
                fileName.substring(fileName.lastIndexOf(".")) : "";
        //获取contentType
        String contentType = fileInfo.getFileType() == null ?
                MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(fileName) :
                fileInfo.getFileType();
        //未自定义文件名，则使用上传时的文件名
        if (StringUtils.isEmpty(name)) {
            name = fileInfo.getFileName();
        }
        //如果未指定文件拓展名，则追加默认的文件拓展名
        if (!name.contains(".")) {
            name = name.concat(".").concat(suffix);
        }
        //关键字剔除
        name = fileNameKeyWordPattern.matcher(name).replaceAll("");
        int skip = 0;
        long fSize = fileInfo.getFileSize();
        //尝试判断是否为断点下载
        try {
            //获取要继续下载的位置
            String Range = request.getHeader("Range").replace("bytes=", "").replace("-", "");
            skip = Integer.parseInt(Range);
        } catch (Exception ignore) {
        }
        //文件大小
        response.setContentLength((int) fSize);
        response.setContentType(contentType);
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(name, "utf-8"));
        //断点下载
        if (skip > 0) {
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            String contentRange = "bytes " + skip + "-" + (fSize - 1) + "/" + fSize;
            response.setHeader("Content-Range", contentRange);
        }
        fileService.writeFile(idOrMd5, response.getOutputStream(), skip);
    }

}
