package com.ljryh.client.mapper.shorturl;

import com.ljryh.client.entity.shorturl.UrlInfo;

import java.util.List;

/**
 * @Description：Url持久层
 * @Auther： 一枚方糖
 * @Date： /08/13/19:04/
 */
public interface UrlMapper {
    /**
     * 查询短网址
     * @param url
     * @return
     */
    String getLongUrl(String url);

    /**
     * 保存短网址
     * @param urlInfo
     * @return
     */
    int saveUrlInfo(UrlInfo urlInfo);

    /**
     * 更新网址访问次数
     * @param surl
     * @return
     */
    int updataUrlViews(String surl);

    /**
     * 查询所有Url信息
     * @return
     */
    List<UrlInfo> getAllUrlInfo();

}
