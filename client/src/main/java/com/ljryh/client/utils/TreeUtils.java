package com.ljryh.client.utils;

import com.ljryh.client.entity.SMenu;

import java.util.ArrayList;
import java.util.List;

public class TreeUtils {

    private List<SMenu> menuList = new ArrayList<SMenu>();

    public TreeUtils(List<SMenu> menuList) {
        this.menuList = menuList;
    }

    //建立树形结构
    public List<SMenu> builTree() {
        List<SMenu> treeMenus = new ArrayList<SMenu>();
        for (SMenu menuNode : getRootNode()) {
            menuNode = buildChilTree(menuNode);
            treeMenus.add(menuNode);
        }
        return treeMenus;
    }

    //递归，建立子树形结构
    private SMenu buildChilTree(SMenu pNode) {
        List<SMenu> chilMenus = new ArrayList<SMenu>();
        for (SMenu menuNode : menuList) {
            if (menuNode.getParentId().equals(pNode.getId())) {
                chilMenus.add(buildChilTree(menuNode));
            }
        }
        pNode.setChildren(chilMenus);
        return pNode;
    }

    //获取根节点
    private List<SMenu> getRootNode() {
        List<SMenu> rootMenuLists = new ArrayList<SMenu>();
        for (SMenu menuNode : menuList) {
            if (menuNode.getParentId() == 0) {
                rootMenuLists.add(menuNode);
            }
        }
        return rootMenuLists;
    }


}
