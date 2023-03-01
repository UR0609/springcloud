package com.ljryh.client.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljryh.client.entity.SDatabaseContent;

import java.text.ParseException;
import java.util.Map;

/**
 * <p>
 * 数据库生成具体内容 服务类
 * </p>
 *
 * @author ljryh
 * @since 2023-02-27
 */
public interface ISDatabaseContentService extends IService<SDatabaseContent> {

    Map<String,Object> getTableData(Map<String, Object> map) throws ParseException;
}
