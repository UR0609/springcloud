package com.ljryh.client.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljryh.client.entity.Children;
import com.ljryh.client.service.impl.ChildrenServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ljryh
 * @since 2020-02-13
 */
@RestController
@RequestMapping("/children")
public class ChildrenController {

    @Resource
    private ChildrenServiceImpl childrenService;

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Object findAll(Children children/*, HttpServletRequest request*/){
//        //获取前台发送过来的数据
//        Integer pageNo = Integer.valueOf(request.getParameter("pageNo"));
//        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
        IPage<Children> page = new Page<>(children.getPageNo(), children.getPageSize());
        QueryWrapper<Children> wrapper = new QueryWrapper<>();
        wrapper.setEntity(children);
        return childrenService.page(page,wrapper);
    }

}
