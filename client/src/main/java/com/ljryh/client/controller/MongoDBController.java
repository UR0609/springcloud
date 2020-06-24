package com.ljryh.client.controller;

import com.ljryh.client.config.mongodb.GridConfig;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/mongo")
public class MongoDBController {

    @Resource
    private GridFsTemplate gridFsTemplate;
    @Resource
    private GridConfig gridConfig;

    // 音频文件上传
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ObjectId uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        return gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
    }

    // 音频文件下载
    @RequestMapping(value = "/getFile", method = RequestMethod.GET)
    public void getFile(@RequestParam(value = "id") String id, HttpServletResponse response) throws IOException {
        System.out.println("id================" + id);
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(id)));
        GridFsResource fsResource = gridConfig.convertGridFSFile2Resource(gridFSFile);
        IOUtils.copy(fsResource.getInputStream(), response.getOutputStream());
    }

    @RequestMapping(value = "/deleteFile", method = RequestMethod.DELETE)
    public int deleteFile(@RequestParam(value = "fileName") String fileName) {
        gridFsTemplate.delete(new Query().addCriteria(Criteria.where("filename").is(fileName)));
        return 0;
    }

    @RequestMapping(value = "/deleteFileById", method = RequestMethod.DELETE)
    public int deleteFileById(@RequestParam(value = "id") String id) {
        gridFsTemplate.delete(new Query().addCriteria(Criteria.where("_id").is(id)));
        return 0;
    }

    @RequestMapping(value = "/deleteFileByFilesId", method = RequestMethod.DELETE)
    public int deleteFileByFilesId(@RequestParam(value = "filesId") String filesId) {
        gridFsTemplate.delete(new Query().addCriteria(Criteria.where("files_id").is(filesId)));
        return 0;
    }


}
