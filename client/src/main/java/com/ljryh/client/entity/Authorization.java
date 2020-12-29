package com.ljryh.client.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
public class Authorization implements Serializable {

    private static final long serialVersionUID = -4192026031656926158L;


    /**
     * id : 2
     * path : /home
     * component : HomeiconCls
     * name : 人员管理
     * iconCls : fa fa-user-circle-o
     * children : [{"id":null,"path":"/emp/basic","component":"EmpBasic","name":"基本资料","iconCls":null,"children":[],"meta":{"keepAlive":false,"requireAuth":true}}]
     * meta : {"keepAlive":false,"requireAuth":true}
     */

    private Integer id;
    private String path;
    private String component;
    private String name;
    private String iconCls;
    private MetaBean meta;
    private List<ChildrenBean> children;

    @NoArgsConstructor
    @Data
    public static class MetaBean {
        /**
         * keepAlive : false
         * requireAuth : true
         */

        private Boolean keepAlive;
        private Boolean requireAuth;
    }

    @NoArgsConstructor
    @Data
    public static class ChildrenBean {
        /**
         * id : null
         * path : /emp/basic
         * component : EmpBasic
         * name : 基本资料
         * iconCls : null
         * children : []
         * meta : {"keepAlive":false,"requireAuth":true}
         */

        private Object id;
        private String path;
        private String component;
        private String name;
        private Object iconCls;
        private MetaBean meta;
        private List<?> children;

        @NoArgsConstructor
        @Data
        public static class MetaBean {
            /**
             * keepAlive : false
             * requireAuth : true
             */

            private Boolean keepAlive;
            private Boolean requireAuth;
        }
    }
}
