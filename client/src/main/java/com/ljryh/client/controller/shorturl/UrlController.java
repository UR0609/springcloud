package com.ljryh.client.controller.shorturl;

import com.ljryh.client.entity.shorturl.ToResult;
import com.ljryh.client.service.shorturl.UrlService;
import com.ljryh.client.utils.shorturl.HashUtils;
import com.ljryh.client.utils.shorturl.UrlCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URI;

/**
 * @Description：Url控制器
 * @Auther： 一枚方糖
 * @Date： /08/13/20:18/
 */
@Controller
public class UrlController {
    @Resource
    private UrlService urlService;
    private static String host;


    @Value("${server.host}")
    public void setHost(String host) {
        this.host = host;
    }

    @RequestMapping("/generate")
    @ResponseBody
    public ToResult generateShortUrl(@RequestParam("longURL") String longURL) {
        if (UrlCheck.checkURL(longURL)) {
            if (!longURL.startsWith("http")) {
                longURL = "http://" + longURL;
            }
            String ShortUrl = urlService.saveUrlMap(HashUtils.hashToBase62(longURL), longURL, longURL);
            return new ToResult(200, "生成成功", 1, host + ShortUrl);
        }
        return new ToResult(500, "生成失败", 1, "url格式错误");
    }

    @RequestMapping("/ck/{longURL}")
    @ResponseBody
    public ToResult generateShortUrl2(@PathVariable("longURL") String longURL) {
        if (UrlCheck.checkURL(longURL)) {
            if (!longURL.startsWith("http")) {
                longURL = "http://" + longURL;
            }
            String ShortUrl = urlService.saveUrlMap(HashUtils.hashToBase62(longURL), longURL, longURL);
            return new ToResult(200, "生成成功", 1, host + ShortUrl);
        }
        return new ToResult(500, "生成失败", 1, "url格式错误");
    }

//    @RequestMapping("/{shortURL}")
//    public String redirect(@PathVariable String shortURL) {
//        String longUrl = urlService.getLongUrl(shortURL);
//        if(longUrl != null) {
//            urlService.updateUrlViews(shortURL);
//            // 302跳转
//            return "redirect:" + longUrl;
//        }
//        // 短链接未生成
//        return "redirect:/";
//    }

    @GetMapping(value = "{shortUrl}")
    public ResponseEntity<Void> getAndRedirect(@PathVariable String shortUrl) {
        String url = urlService.getLongUrl(shortUrl);
        if (url != null) {
            urlService.updateUrlViews(shortUrl);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(url))
                    .build();
        }
        return null;
    }

    @RequestMapping("/getAllInfo")
    @ResponseBody
    public ToResult getAllInfo() {
        String allUrlInfo = urlService.getAllUrlInfo();
        return new ToResult(200, "查询所有Url", 0, allUrlInfo);
    }


}
