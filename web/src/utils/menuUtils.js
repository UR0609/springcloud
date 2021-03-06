import axios from 'axios';

// 请求菜单数据并初始化
export const initMenu = (router, store) => {

    let token = localStorage.getItem("token");
    let refreshMenu = localStorage.getItem("refreshMenu");
    console.log(refreshMenu);
    // 首先判断 store 中数据是否存在，如果存在，则说明这次跳转是正常的跳转
    // 而不是用户按F5键或者直接在地址栏输入某个地址进入的，这时直接返回，不必执行菜单初始化
    if (store.state.routes.length > 0 && refreshMenu === true) {
        return;
    }
    axios({
        method: "GET",
        url: "/sys/menu/sysmenu",
        data: {},
        headers: {
            token: token
        }
    }).then(result => {

        if (result && result.status === 200) {
            if (result.data.status === 403) {
                router.push({path: "/"}).catch(err => {
                    err
                });
                return
            }
            // 将服务器返回的 JSON 格式的数据转成 router 需要的格式
            console.log(result.data);
            var fmtRoutes = formatRoutes(result.data);
            localStorage.setItem("refreshMenu", true);
            localStorage.setItem("routeData", JSON.stringify(fmtRoutes));
            console.log(fmtRoutes);
            // let home = router.find(r => r.path === '/Home');
            // console.log(home);
            // home.children = fmtRoutes
            // // 将准备好的数据动态添加到路由中
            // console.log(home);
            // router.addRoutes([home]);
            router.addRoutes(fmtRoutes);
            // 同时也将数据存到 store 中
            store.commit('initMenu', fmtRoutes);
        }
    });


}

// 将服务器返回的 JSON 转为 router 需要的格式
export const formatRoutes = (routes) => {
    let fmRoutes = [];
    routes.forEach(router => {
        let {
            path,
            component,
            name,
            meta,
            iconCls,
            children
        } = router;
        if (children && children instanceof Array) {
            // 如果有子节点则递归转换
            children = formatRoutes(children);
        }
        let fmRouter = {
            path: path,
            // 根据服务器返回的 component 动态加载需要的组件
            component(resolve) {
                if (component.startsWith("Home")) {
                    require(['../views/' + component + '.vue'], resolve)
                } else if (component.startsWith("Emp")) {
                    require(['../views/emp/' + component + '.vue'], resolve)
                } else if (component.startsWith("Sta")) {
                    require(['../views/statistics/' + component + '.vue'], resolve)
                } else if (component.startsWith("Sys")) {
                    require(['../views/system/' + component + '.vue'], resolve)
                }
            },
            name: name,
            iconCls: iconCls,
            meta: meta,
            children: children
        };
        fmRoutes.push(fmRouter);
    })
    return fmRoutes;
}
