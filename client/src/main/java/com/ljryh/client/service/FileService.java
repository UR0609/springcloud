package com.ljryh.client.service;

import com.ljryh.client.entity.FileInfo;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author ljryh
 * @version 1.0
 * @date 2022/8/8 15:51
 */
public interface FileService {
    FileInfo saveFile(InputStream inputStream, String fileName, String contentType, String s, String creator) throws IOException;
}
