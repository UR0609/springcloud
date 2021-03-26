package com.ljryh.client.controller;

import com.ljryh.client.entity.HeaderData;
import com.ljryh.common.entity.CallResult;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author ljryh
 * @date 2021/3/19 15:43
 */
@RestController
@RequestMapping("/emp/test")
public class EmpTestController {

    @RequestMapping(value = "/header", method = RequestMethod.POST)
    public Object getHeader(@RequestBody HeaderData entity/*, HttpServletRequest request*/) {

        List<HeaderData> list = new CopyOnWriteArrayList<>();

        HeaderData headerData1 = new HeaderData();
        headerData1.setValue("编码", "code");
        list.add(headerData1);
        HeaderData headerData2 = new HeaderData();
        headerData2.setValue("姓名", "name");
        list.add(headerData2);
        HeaderData headerData3 = new HeaderData();
        headerData3.setValue("权限描述", "description");
        list.add(headerData3);

        return CallResult.success(list);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object getList(@RequestBody HeaderData entity/*, HttpServletRequest request*/) {

        List<DataBean> list = new CopyOnWriteArrayList<>();

        DataBean dataBean1 = new DataBean();
        dataBean1.setValue(1L, "01","西药开立权限","医生对西药处方权限","0");
        list.add(dataBean1);
        DataBean dataBean2 = new DataBean();
        dataBean2.setValue(2L, "02","草药开立权限","医生对草药处方权限","0");
        list.add(dataBean2);
        DataBean dataBean3 = new DataBean();
        dataBean3.setValue(3L, "03","成药开立权限","医生对成药处方权限","0");
        list.add(dataBean3);
        DataBean dataBean4 = new DataBean();
        dataBean4.setValue(4L, "04","麻醉开立权限","医生对麻醉处方权限","0");
        list.add(dataBean4);
        DataBean dataBean5 = new DataBean();
        dataBean5.setValue(5L, "05","精一开立权限","医生对精一处方权限","0");
        list.add(dataBean5);

        return CallResult.success(list);
    }

    @NoArgsConstructor
    @Data
    public static class DataBean {
        /**
         * keepAlive : false
         * requireAuth : true
         */

        private Long id;
        private String code;
        private String name;
        private String description;
        private String ifUse;

        public void setValue(Long id, String code, String name, String description, String ifUse) {
            this.id = id;
            this.code = code;
            this.name = name;
            this.description = description;
            this.ifUse = ifUse;
        }

    }

}
