package com.ljryh.client.controller;

import com.ljryh.client.entity.Commodity;
import com.ljryh.client.utils.aliyun.AliyunOSSClientUtils;
import com.ljryh.common.entity.CallResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author ljryh
 * @version 1.0
 * @date 2022/9/2 09:52
 */
@Log4j2
@Controller
@RequestMapping("/aliyunFile")
public class AliyunFileController {

    /***
     * 阿里云上传图片
     */
    @RequestMapping("uploadImg")
    @ResponseBody
    public CallResult uploadImg(@RequestParam(value = "file") MultipartFile multipartFile, Commodity commodity) {
        try {
            if (!multipartFile.isEmpty()) {
                File file = AliyunOSSClientUtils.MultipartFileToFile(multipartFile);
                String avatar = new AliyunOSSClientUtils().getFileUrl(file);
                System.out.println("id:" + commodity.getId());
                return CallResult.success("上传成功", avatar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CallResult.fail("上传失败");
    }

}
