package com.ljryh.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljryh.client.entity.FileInfo;

/**
 * @author ljryh
 * @version 1.0
 * @date 2022/8/8 16:26
 */
public interface FileMapper extends BaseMapper<FileInfo> {
    FileInfo selectByMd5(String md5);

}
