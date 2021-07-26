package com.ljryh.client.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljryh.client.entity.TablesEntity;
import com.ljryh.client.service.ICodeGenerationService;
import com.ljryh.common.entity.CallResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 代码生成
 *
 * @author ljryh
 * @date 2021/5/24 13:59
 */
@RestController
@RequestMapping("/sys/generate")
public class CodeGenerationController {

    @Resource
    private ICodeGenerationService codeGenerationService;

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Object findAll(@RequestBody TablesEntity entity/*, HttpServletRequest request*/){
        IPage<TablesEntity> page = new Page<>(entity.getPageNo(), entity.getPageSize());
        QueryWrapper<TablesEntity> wrapper = new QueryWrapper<>();
        wrapper.setEntity(entity);
        return CallResult.success(codeGenerationService.page(page,wrapper));
    }


}
