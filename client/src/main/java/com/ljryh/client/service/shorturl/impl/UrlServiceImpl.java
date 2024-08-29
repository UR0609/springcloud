package com.ljryh.client.service.shorturl.impl;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.bloomfilter.BloomFilterUtil;
import cn.hutool.json.JSONUtil;
import com.ljryh.client.entity.shorturl.UrlInfo;
import com.ljryh.client.mapper.shorturl.UrlMapper;
import com.ljryh.client.service.shorturl.UrlService;
import com.ljryh.client.utils.RedisUtils;
import com.ljryh.client.utils.shorturl.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description：Url服务层实现
 * @Auther： 一枚方糖
 * @Date： /08/13/19:29/
 */
@Service
public class UrlServiceImpl implements UrlService {
    // 接口注入
    @Resource
    private UrlMapper urlMapper;
    @Resource
    private RedisUtils redisUtils;

    // 自定义长链接 - 防止重复字符串
    private static final String duplicate = "*";
    // 最近使用短连接过期时间（10分钟）
    private static final long timeout = 10 * 60;
    // bool过滤器
    private static final BitMapBloomFilter filter = BloomFilterUtil.createBitMap(10);

    @Override
    public String getLongUrl(String shortURL) {
        // 查找Redis中是否有缓存
        String longUrl = redisUtils.get(shortURL).toString();
        if (longUrl != null) {
            // 有缓存,增加缓存时间
            redisUtils.expire(shortURL, timeout);
            return longUrl;
        }
        // Redis没有缓存,查数据库
        longUrl = urlMapper.getLongUrl(shortURL);
        if (longUrl != null) {
            // 将短链接添加缓存
            redisUtils.set(shortURL,longUrl, timeout);
        }
        return longUrl;
    }

    @Override
    public String saveUrlMap(String shortURL, String longURL, String originalURL) {
        // 保存长度为1的短链接
        if (shortURL.length() == 1) {
            longURL += duplicate;
            shortURL = saveUrlMap(HashUtils.hashToBase62(longURL), longURL, originalURL);
        }
        // bool过滤器查找是否存在
        else if (filter.contains(shortURL)) {
            // 存在,查Redis是否有缓存
            String redisLongUrl = redisUtils.get(shortURL).toString();
            if (redisLongUrl != null && originalURL.equals(redisLongUrl)) {
                // 更新过期时间
                redisUtils.expire(shortURL, timeout);
                return shortURL;
            }
            // 没有缓存,长链接后加上指定字符串,重新hash
            longURL += duplicate;
            shortURL = saveUrlMap(HashUtils.hashToBase62(longURL), longURL, originalURL);
        } else {
            // 不存在,存入数据库
            try {
                UrlInfo urlInfo = new UrlInfo();
                urlInfo.setSurl(shortURL);
                urlInfo.setLurl(originalURL);
                urlInfo.setViews(0);
                urlInfo.setCreateTime(new Date());
                urlMapper.saveUrlInfo(urlInfo);
                filter.add(shortURL);
                // 添加缓存
                redisUtils.set(shortURL,originalURL, timeout);
            } catch (Exception e) {
                if (e instanceof DuplicateKeyException) {
                    // 数据库已经存在此短链接，则可能是布隆过滤器误判，在长链接后加上指定字符串，重新hash
                    longURL += duplicate;
                    shortURL = saveUrlMap(HashUtils.hashToBase62(longURL), longURL, originalURL);
                } else {
                    throw e;
                }
            }
        }
        return shortURL;
    }

    @Override
    public String getAllUrlInfo() {
        List<UrlInfo> allUrlInfo = urlMapper.getAllUrlInfo();
        String allInfo = JSONUtil.toJsonStr(allUrlInfo);
        return allInfo;
    }

    @Override
    public void updateUrlViews(String shortURL) {
        urlMapper.updataUrlViews(shortURL);
    }
}
