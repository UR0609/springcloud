package com.ljryh.client.controller;

import com.ljryh.client.entity.FileInfo;
import com.ljryh.client.service.FileService;
import com.ljryh.common.entity.CallResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

}
