package com.ljryh.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.List;

// @XStreamImplicit 解析list
// @XStreamAlias 解析实体
// @XStreamAsAttribute 定义在父节点上

@Data
@XStreamAlias("root")
public class ImagesDto {

    @JsonProperty("RESPONSE_CODE")
    @XStreamAlias("RESPONSE_CODE")
    private int RESPONSECODE;
    @JsonProperty("RESPONSE_MSG")
    @XStreamAlias("RESPONSE_MSG")
    private String RESPONSEMSG;
    @JsonProperty("PAGES")
    @XStreamAlias("PAGES")
    private PAGESDTO PAGES;
    @JsonProperty("SYD")
    @XStreamAlias("SYD")
    private SYDDTO SYD;

    @Data
    @XStreamAlias("PAGES")
    public static class PAGESDTO {
        @JsonProperty("PAGE")
        @XStreamImplicit(itemFieldName = "PAGE")
        private List<PAGEDTO> PAGE;

        @Data
        @XStreamAlias("PAGE")
        public static class PAGEDTO {

            @XStreamAsAttribute
            @XStreamAlias("PAGEID")
            private String PAGEID;
            @XStreamAlias("PAGE_URL")
            @XStreamAsAttribute
            private String PAGEURL;
            @XStreamAlias("THUM_URL")
            @XStreamAsAttribute
            private String THUMURL;
            @XStreamAlias("FILE_NO")
            @XStreamAsAttribute
            private String FILENO;
            @XStreamAlias("PAGE_VER")
            @XStreamAsAttribute
            private int PAGEVER;

        }
    }

    @Data
    @XStreamAlias("SYD")
    public static class SYDDTO {

        @JsonProperty("doc")
        @XStreamAlias("doc")
        private DocDTO doc;

        @Data
        @XStreamAlias("doc")
        public static class DocDTO {

            @JsonProperty("version")
            @XStreamAlias("version")
            private double version;
            @JsonProperty("DocInfo")
            @XStreamAlias("DocInfo")
            private DocInfoDTO DocInfo;
            @JsonProperty("PageInfo")
            @XStreamAlias("PageInfo")
            private PageInfoDTO PageInfo;
            @JsonProperty("VTREE")
            @XStreamAlias("VTREE")
            private VTREEDTO VTREE;

            @Data
            @XStreamAlias("DocInfo")
            public static class DocInfoDTO {
                @JsonProperty("BATCH_ID")
                @XStreamAlias("BATCH_ID")
                private String BATCHID;
                @JsonProperty("BUSI_NUM")
                @XStreamAlias("BUSI_NUM")
                private String BUSINUM;
                @JsonProperty("BIZ_ORG")
                @XStreamAlias("BIZ_ORG")
                private int BIZORG;
                @JsonProperty("APP_CODE")
                @XStreamAlias("APP_CODE")
                private String APPCODE;
                @JsonProperty("APP_NAME")
                @XStreamAlias("APP_NAME")
                private String APPNAME;
                @JsonProperty("BATCH_VER")
                @XStreamAlias("BATCH_VER")
                private int BATCHVER;
                @JsonProperty("INTER_VER")
                @XStreamAlias("INTER_VER")
                private int INTERVER;
                @JsonProperty("STATUS")
                @XStreamAlias("STATUS")
                private String STATUS;
                @JsonProperty("CREATE_USER")
                @XStreamAlias("CREATE_USER")
                private String CREATEUSER;
                @JsonProperty("CREATE_DATE")
                @XStreamAlias("CREATE_DATE")
                private String CREATEDATE;
                @JsonProperty("MODIFY_USER")
                @XStreamAlias("MODIFY_USER")
                private String MODIFYUSER;
                @JsonProperty("MODIFY_DATE")
                @XStreamAlias("MODIFY_DATE")
                private String MODIFYDATE;
                @JsonProperty("DOC_EXT")
                @XStreamAlias("DOC_EXT")
                private DOCEXTDTO DOCEXT;

                @Data
                @XStreamAlias("DOC_EXT")
                public static class DOCEXTDTO {
                    @JsonProperty("EXT_ATTR")
                    @XStreamAlias("EXT_ATTR")
                    private EXTATTRDTO EXTATTR;

                    @Data
                    @XStreamAlias("EXT_ATTR")
                    public static class EXTATTRDTO {
                        @JsonProperty("ID")
                        @XStreamAlias("ID")
                        private String ID;
                        @JsonProperty("NAME")
                        @XStreamAlias("NAME")
                        private String NAME;
                        @JsonProperty("IS_SHOW")
                        @XStreamAlias("IS_SHOW")
                        private int ISSHOW;
                        @JsonProperty("IS_KEY")
                        @XStreamAlias("IS_KEY")
                        private int ISKEY;
                        @JsonProperty("IS_NULL")
                        @XStreamAlias("IS_NULL")
                        private int ISNULL;
                        @JsonProperty("INPUT_TYPE")
                        @XStreamAlias("INPUT_TYPE")
                        private int INPUTTYPE;
                        @JsonProperty("content")
                        @XStreamAlias("content")
                        private String content;
                    }
                }
            }

            @Data
            @XStreamAlias("DocInfo")
            public static class PageInfoDTO {
                @JsonProperty("PAGE")
                @XStreamImplicit(itemFieldName = "PAGE")
                private List<PAGEDTO> PAGE;

                @Data
                @XStreamAlias("PAGE")
                public static class PAGEDTO {
                    @JsonProperty("PAGEID")
                    @XStreamAlias("PAGEID")
                    @XStreamAsAttribute
                    private String PAGEID;
                    @JsonProperty("CREATE_USER")
                    @XStreamAlias("CREATE_USER")
                    private String CREATEUSER;
                    @JsonProperty("CREATE_USERNAME")
                    @XStreamAlias("CREATE_USERNAME")
                    private String CREATEUSERNAME;
                    @JsonProperty("CREATE_TIME")
                    @XStreamAlias("CREATE_TIME")
                    private String CREATETIME;
                    @JsonProperty("MODIFY_USER")
                    @XStreamAlias("MODIFY_USER")
                    private String MODIFYUSER;
                    @JsonProperty("MODIFY_TIME")
                    @XStreamAlias("MODIFY_TIME")
                    private String MODIFYTIME;
                    @JsonProperty("PAGE_URL")
                    @XStreamAlias("PAGE_URL")
                    private String PAGEURL;
                    @JsonProperty("THUM_URL")
                    @XStreamAlias("THUM_URL")
                    private String THUMURL;
                    @JsonProperty("IS_LOCAL")
                    @XStreamAlias("IS_LOCAL")
                    private int ISLOCAL;
                    @JsonProperty("PAGE_VER")
                    @XStreamAlias("PAGE_VER")
                    private int PAGEVER;
                    @JsonProperty("PAGE_DESC")
                    @XStreamAlias("PAGE_DESC")
                    private String PAGEDESC;
                    @JsonProperty("UPLOAD_ORG")
                    @XStreamAlias("UPLOAD_ORG")
                    private String UPLOADORG;
                    @JsonProperty("PAGE_CRC")
                    @XStreamAlias("PAGE_CRC")
                    private String PAGECRC;
                    @JsonProperty("PAGE_SIZE")
                    @XStreamAlias("PAGE_SIZE")
                    private int PAGESIZE;
                    @JsonProperty("PAGE_FORMAT")
                    @XStreamAlias("PAGE_FORMAT")
                    private String PAGEFORMAT;
                    @JsonProperty("PAGE_ENCRYPT")
                    @XStreamAlias("PAGE_ENCRYPT")
                    private int PAGEENCRYPT;
                    @JsonProperty("ORIGINAL_NAME")
                    @XStreamAlias("ORIGINAL_NAME")
                    private String ORIGINALNAME;
                    @JsonProperty("PAGE_EXT")
                    @XStreamAlias("PAGE_EXT")
                    private PAGEEXTDTO PAGEEXT;
                    @JsonProperty("PAGE_DESCS")
                    @XStreamAlias("PAGE_DESCS")
                    private String PAGEDESCS;

                    @Data
                    @XStreamAlias("PAGE_EXT")
                    public static class PAGEEXTDTO {

                        @JsonProperty("EXT_ATTR")
                        @XStreamImplicit(itemFieldName = "EXT_ATTR")
                        private List<EXTATTRDTO> EXTATTR;

                        @Data
                        @XStreamAlias("EXT_ATTR")
                        public static class EXTATTRDTO {
                            @JsonProperty("ID")
                            @XStreamAlias("ID")
                            @XStreamAsAttribute
                            private String ID;
                            @JsonProperty("NAME")
                            @XStreamAlias("NAME")
                            @XStreamAsAttribute
                            private String NAME;
                            @JsonProperty("APP_CODE")
                            @XStreamAlias("APP_CODE")
                            @XStreamAsAttribute
                            private String VALUE;
                        }
                    }

                }
            }

            @Data
            @XStreamAlias("DocInfo")
            public static class VTREEDTO {
                @JsonProperty("APP_CODE")
                @XStreamAlias("APP_CODE")
                private String APPCODE;
                @JsonProperty("APP_NAME")
                @XStreamAlias("APP_NAME")
                private String APPNAME;
                @JsonProperty("NODE")
                @XStreamImplicit(itemFieldName = "NODE")
                private List<NODEDTO> NODE;

                @Data
                @XStreamAlias("NODE")
                public static class NODEDTO {

                    @JsonProperty("ID")
                    @XStreamAlias("ID")
                    private String ID;
                    @JsonProperty("NAME")
                    @XStreamAlias("NAME")
                    private String NAME;
                    @JsonProperty("LEAF")
                    @XStreamImplicit(itemFieldName = "LEAF")
                    private List<String> LEAF;

                }
            }
        }
    }

}
