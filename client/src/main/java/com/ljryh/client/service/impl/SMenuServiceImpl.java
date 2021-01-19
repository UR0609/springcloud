package com.ljryh.client.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljryh.client.entity.Permission;
import com.ljryh.client.entity.SMenu;
import com.ljryh.client.mapper.PermissionMapper;
import com.ljryh.client.mapper.SMenuMapper;
import com.ljryh.client.service.ISMenuService;
import com.ljryh.client.utils.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ljryh
 * @since 2021-01-06
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SMenuServiceImpl extends ServiceImpl<SMenuMapper, SMenu> implements ISMenuService {

    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 列表 与 菜单
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(value = "cache:menuList", key = "#id")
    public List<SMenu> list(Long id) {

//        List<SMenu> list = this.baseMapper.selectList(null);
        List<SMenu> list = this.baseMapper.selectListByUserId(id);

        List<SMenu> type1 = list.stream().filter((SMenu menu) -> menu.getType() == 1).collect(Collectors.toList());
        List<SMenu> type2 = list.stream().filter((SMenu menu) -> menu.getType() == 2).collect(Collectors.toList());

        for (SMenu menu : type1) {
            for (SMenu button : type2) {
                if (menu.getId().equals(button.getParentId())) {
                    if (menu.getMeta() == null) {
                        Map<String, Object> map = new ConcurrentHashMap<>();
                        map.put(button.getComponent(), true);
                        menu.setMeta(map);
                    } else {
                        menu.getMeta().put(button.getComponent(), true);
                    }
                }
            }
        }

        // 封装树形
        TreeUtils menuTree = new TreeUtils(type1);
        List<SMenu> result = menuTree.builTree();

        return result;
    }

    /**
     * 下拉select
     *
     * @param id
     * @return
     */
    @Cacheable(value = "cache:menuTree", key = "#id")
    public List<SMenu> selectTree(Long id) {
        List<SMenu> list = this.baseMapper.selectList(null);
        return list;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "cache:menuList", allEntries = true),
            @CacheEvict(cacheNames = "cache:selAllMenu", allEntries = true),
            @CacheEvict(cacheNames = "cache:menuTree", allEntries = true),
    })
    public boolean save(SMenu menu) {
        menu.setType(1);
        int result = this.baseMapper.insert(menu);
        log.info("<--------------------------插入数据 id : {}------------------------------>", menu.getId());
        return result == 1;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "cache:menuList", allEntries = true),
            @CacheEvict(cacheNames = "cache:menuTree", allEntries = true),
            @CacheEvict(cacheNames = "cache:selAllMenu", allEntries = true),
            @CacheEvict(cacheNames = "cache:menu", allEntries = true)
    })
    public boolean updateById(SMenu menu) {
        int result = this.baseMapper.updateById(menu);
        log.info("<--------------------------更新数据 id : {}------------------------------>", menu.getId());
        return result == 1;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "cache:menuList", allEntries = true),
            @CacheEvict(cacheNames = "cache:menuTree", allEntries = true),
            @CacheEvict(cacheNames = "cache:selAllMenu", allEntries = true),
            @CacheEvict(cacheNames = "cache:menu", key = "'id-' + #id"),
    })
    public boolean removeById(Serializable id) {
        int result = this.baseMapper.deleteById(id);
        log.info("<--------------------------清除缓存 id : {}------------------------------>", id);
        return result == 1;
    }

    @Override
    @Cacheable(cacheNames = "cache:menu", key = "'id-' + #id")
    public SMenu getById(Serializable id) {
        SMenu result = this.baseMapper.selectById(id);
        log.info("<--------------------------没有走缓存 查询 id : {}------------------------------>", id);
        return result;
    }


    @Override
    public List<SMenu> getPermissionNameByMenuId(SMenu entity) {
        List<SMenu> list = this.baseMapper.getPermissionNameByMenuId(entity);
        return list;
    }

    @Override
    public boolean bind(SMenu menu) {

        this.baseMapper.deleteSMenuByParentId(menu);

        if (menu.getPermissionName().size() == 0) {
            return true;
        }
        SMenu data = this.getById(menu.getId());
        List<Permission> list = permissionMapper.getPermissionByName(menu.getPermissionName());

        List<SMenu> insertList = new ArrayList<>();

        for (Permission permission : list) {
            SMenu sMenu = new SMenu();
            sMenu.setComponent(permission.getPermission());
            sMenu.setParentId(menu.getId());
            sMenu.setPermissionId(permission.getId());
            sMenu.setPath(data.getPath() + "/" + permission.getPermission());
            sMenu.setName(permission.getPermissionName());
            sMenu.setType(2);
            insertList.add(sMenu);
        }
        return this.saveBatch(insertList);
    }

    @Override
    @Cacheable(value = "cache:selAllMenu")
    public List<SMenu> selAllMenu() {
        List<SMenu> list = this.baseMapper.selAllMenu();

        // 封装树形
        TreeUtils menuTree = new TreeUtils(list);
        list = menuTree.builTree();

        return list;
    }

}
