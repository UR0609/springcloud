package com.ljryh.client.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljryh.client.entity.SDatabaseContent;
import com.ljryh.client.service.ISDatabaseContentService;
import com.ljryh.common.entity.CallResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Map;

/**
 * <p>
 * 数据库生成具体内容 前端控制器
 * </p>
 *
 * @author ljryh
 * @since 2023-02-27
 */
@RestController
@RequestMapping("/sys/database/content")
public class SDatabaseContentController {

    @Resource
    private ISDatabaseContentService databaseContentService;

    @RequestMapping(value = "/getheadData", method = RequestMethod.POST)
    public Object getheadData(@RequestBody SDatabaseContent entity) {
        QueryWrapper<SDatabaseContent> wrapper = new QueryWrapper<>();
        wrapper.setEntity(entity);
        return CallResult.success(databaseContentService.list(wrapper));
    }

    @RequestMapping(value = "/getTableData", method = RequestMethod.POST)
    public Object getTableData(@RequestBody Map<String,Object> map) throws ParseException {
        Map<String,Object> result = databaseContentService.getTableData(map);
        return CallResult.success(result);
    }

}
