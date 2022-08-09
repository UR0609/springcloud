package com.ljryh.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author ljryh
 * @version 1.0
 * @date 2022/6/16 15:34
 */
public class NewImagesDto {

    @JsonProperty("root")
    private RootDTO root;

    public RootDTO getRoot() {
        return root;
    }

    public void setRoot(RootDTO root) {
        this.root = root;
    }

    public static class RootDTO {
        @JsonProperty("RESPONSE_CODE")
        private int RESPONSECODE;
        @JsonProperty("RESPONSE_MSG")
        private String RESPONSEMSG;
        @JsonProperty("PAGES")
        private PAGESDTO PAGES;
        @JsonProperty("SYD")
        private SYDDTO SYD;

        public int getRESPONSECODE() {
            return RESPONSECODE;
        }

        public void setRESPONSECODE(int RESPONSECODE) {
            this.RESPONSECODE = RESPONSECODE;
        }

        public String getRESPONSEMSG() {
            return RESPONSEMSG;
        }

        public void setRESPONSEMSG(String RESPONSEMSG) {
            this.RESPONSEMSG = RESPONSEMSG;
        }

        public PAGESDTO getPAGES() {
            return PAGES;
        }

        public void setPAGES(PAGESDTO PAGES) {
            this.PAGES = PAGES;
        }

        public SYDDTO getSYD() {
            return SYD;
        }

        public void setSYD(SYDDTO SYD) {
            this.SYD = SYD;
        }

        public static class PAGESDTO {
            @JsonProperty("PAGE")
            private List<PAGEDTO> PAGE;

            public List<PAGEDTO> getPAGE() {
                return PAGE;
            }

            public void setPAGE(List<PAGEDTO> PAGE) {
                this.PAGE = PAGE;
            }

            public static class PAGEDTO {
                @JsonProperty("PAGEID")
                private String PAGEID;
                @JsonProperty("PAGE_URL")
                private String PAGEURL;
                @JsonProperty("THUM_URL")
                private String THUMURL;
                @JsonProperty("FILE_NO")
                private String FILENO;
                @JsonProperty("PAGE_VER")
                private int PAGEVER;

                public String getPAGEID() {
                    return PAGEID;
                }

                public void setPAGEID(String PAGEID) {
                    this.PAGEID = PAGEID;
                }

                public String getPAGEURL() {
                    return PAGEURL;
                }

                public void setPAGEURL(String PAGEURL) {
                    this.PAGEURL = PAGEURL;
                }

                public String getTHUMURL() {
                    return THUMURL;
                }

                public void setTHUMURL(String THUMURL) {
                    this.THUMURL = THUMURL;
                }

                public String getFILENO() {
                    return FILENO;
                }

                public void setFILENO(String FILENO) {
                    this.FILENO = FILENO;
                }

                public int getPAGEVER() {
                    return PAGEVER;
                }

                public void setPAGEVER(int PAGEVER) {
                    this.PAGEVER = PAGEVER;
                }
            }
        }

        public static class SYDDTO {
            @JsonProperty("doc")
            private DocDTO doc;

            public DocDTO getDoc() {
                return doc;
            }

            public void setDoc(DocDTO doc) {
                this.doc = doc;
            }

            public static class DocDTO {
                @JsonProperty("version")
                private double version;
                @JsonProperty("DocInfo")
                private DocInfoDTO DocInfo;
                @JsonProperty("PageInfo")
                private PageInfoDTO PageInfo;
                @JsonProperty("VTREE")
                private VTREEDTO VTREE;

                public double getVersion() {
                    return version;
                }

                public void setVersion(double version) {
                    this.version = version;
                }

                public DocInfoDTO getDocInfo() {
                    return DocInfo;
                }

                public void setDocInfo(DocInfoDTO DocInfo) {
                    this.DocInfo = DocInfo;
                }

                public PageInfoDTO getPageInfo() {
                    return PageInfo;
                }

                public void setPageInfo(PageInfoDTO PageInfo) {
                    this.PageInfo = PageInfo;
                }

                public VTREEDTO getVTREE() {
                    return VTREE;
                }

                public void setVTREE(VTREEDTO VTREE) {
                    this.VTREE = VTREE;
                }

                public static class DocInfoDTO {
                    @JsonProperty("BATCH_ID")
                    private String BATCHID;
                    @JsonProperty("BUSI_NUM")
                    private String BUSINUM;
                    @JsonProperty("BIZ_ORG")
                    private int BIZORG;
                    @JsonProperty("APP_CODE")
                    private String APPCODE;
                    @JsonProperty("APP_NAME")
                    private String APPNAME;
                    @JsonProperty("BATCH_VER")
                    private int BATCHVER;
                    @JsonProperty("INTER_VER")
                    private int INTERVER;
                    @JsonProperty("STATUS")
                    private String STATUS;
                    @JsonProperty("CREATE_USER")
                    private long CREATEUSER;
                    @JsonProperty("CREATE_DATE")
                    private String CREATEDATE;
                    @JsonProperty("MODIFY_USER")
                    private String MODIFYUSER;
                    @JsonProperty("MODIFY_DATE")
                    private String MODIFYDATE;
                    @JsonProperty("DOC_EXT")
                    private DOCEXTDTO DOCEXT;

                    public String getBATCHID() {
                        return BATCHID;
                    }

                    public void setBATCHID(String BATCHID) {
                        this.BATCHID = BATCHID;
                    }

                    public String getBUSINUM() {
                        return BUSINUM;
                    }

                    public void setBUSINUM(String BUSINUM) {
                        this.BUSINUM = BUSINUM;
                    }

                    public int getBIZORG() {
                        return BIZORG;
                    }

                    public void setBIZORG(int BIZORG) {
                        this.BIZORG = BIZORG;
                    }

                    public String getAPPCODE() {
                        return APPCODE;
                    }

                    public void setAPPCODE(String APPCODE) {
                        this.APPCODE = APPCODE;
                    }

                    public String getAPPNAME() {
                        return APPNAME;
                    }

                    public void setAPPNAME(String APPNAME) {
                        this.APPNAME = APPNAME;
                    }

                    public int getBATCHVER() {
                        return BATCHVER;
                    }

                    public void setBATCHVER(int BATCHVER) {
                        this.BATCHVER = BATCHVER;
                    }

                    public int getINTERVER() {
                        return INTERVER;
                    }

                    public void setINTERVER(int INTERVER) {
                        this.INTERVER = INTERVER;
                    }

                    public String getSTATUS() {
                        return STATUS;
                    }

                    public void setSTATUS(String STATUS) {
                        this.STATUS = STATUS;
                    }

                    public long getCREATEUSER() {
                        return CREATEUSER;
                    }

                    public void setCREATEUSER(long CREATEUSER) {
                        this.CREATEUSER = CREATEUSER;
                    }

                    public String getCREATEDATE() {
                        return CREATEDATE;
                    }

                    public void setCREATEDATE(String CREATEDATE) {
                        this.CREATEDATE = CREATEDATE;
                    }

                    public String getMODIFYUSER() {
                        return MODIFYUSER;
                    }

                    public void setMODIFYUSER(String MODIFYUSER) {
                        this.MODIFYUSER = MODIFYUSER;
                    }

                    public String getMODIFYDATE() {
                        return MODIFYDATE;
                    }

                    public void setMODIFYDATE(String MODIFYDATE) {
                        this.MODIFYDATE = MODIFYDATE;
                    }

                    public DOCEXTDTO getDOCEXT() {
                        return DOCEXT;
                    }

                    public void setDOCEXT(DOCEXTDTO DOCEXT) {
                        this.DOCEXT = DOCEXT;
                    }

                    public static class DOCEXTDTO {
                        @JsonProperty("EXT_ATTR")
                        private EXTATTRDTO EXTATTR;

                        public EXTATTRDTO getEXTATTR() {
                            return EXTATTR;
                        }

                        public void setEXTATTR(EXTATTRDTO EXTATTR) {
                            this.EXTATTR = EXTATTR;
                        }

                        public static class EXTATTRDTO {
                            @JsonProperty("ID")
                            private String ID;
                            @JsonProperty("NAME")
                            private String NAME;
                            @JsonProperty("IS_SHOW")
                            private int ISSHOW;
                            @JsonProperty("IS_KEY")
                            private int ISKEY;
                            @JsonProperty("IS_NULL")
                            private int ISNULL;
                            @JsonProperty("INPUT_TYPE")
                            private int INPUTTYPE;
                            @JsonProperty("content")
                            private String content;

                            public String getID() {
                                return ID;
                            }

                            public void setID(String ID) {
                                this.ID = ID;
                            }

                            public String getNAME() {
                                return NAME;
                            }

                            public void setNAME(String NAME) {
                                this.NAME = NAME;
                            }

                            public int getISSHOW() {
                                return ISSHOW;
                            }

                            public void setISSHOW(int ISSHOW) {
                                this.ISSHOW = ISSHOW;
                            }

                            public int getISKEY() {
                                return ISKEY;
                            }

                            public void setISKEY(int ISKEY) {
                                this.ISKEY = ISKEY;
                            }

                            public int getISNULL() {
                                return ISNULL;
                            }

                            public void setISNULL(int ISNULL) {
                                this.ISNULL = ISNULL;
                            }

                            public int getINPUTTYPE() {
                                return INPUTTYPE;
                            }

                            public void setINPUTTYPE(int INPUTTYPE) {
                                this.INPUTTYPE = INPUTTYPE;
                            }

                            public String getContent() {
                                return content;
                            }

                            public void setContent(String content) {
                                this.content = content;
                            }
                        }
                    }
                }

                public static class PageInfoDTO {
                    @JsonProperty("PAGE")
                    private List<PAGEDTO> PAGE;

                    public List<PAGEDTO> getPAGE() {
                        return PAGE;
                    }

                    public void setPAGE(List<PAGEDTO> PAGE) {
                        this.PAGE = PAGE;
                    }

                    public static class PAGEDTO {
                        @JsonProperty("PAGEID")
                        private String PAGEID;
                        @JsonProperty("CREATE_USER")
                        private long CREATEUSER;
                        @JsonProperty("CREATE_USERNAME")
                        private String CREATEUSERNAME;
                        @JsonProperty("CREATE_TIME")
                        private String CREATETIME;
                        @JsonProperty("MODIFY_USER")
                        private long MODIFYUSER;
                        @JsonProperty("MODIFY_TIME")
                        private String MODIFYTIME;
                        @JsonProperty("PAGE_URL")
                        private String PAGEURL;
                        @JsonProperty("THUM_URL")
                        private String THUMURL;
                        @JsonProperty("IS_LOCAL")
                        private int ISLOCAL;
                        @JsonProperty("PAGE_VER")
                        private int PAGEVER;
                        @JsonProperty("PAGE_DESC")
                        private String PAGEDESC;
                        @JsonProperty("UPLOAD_ORG")
                        private String UPLOADORG;
                        @JsonProperty("PAGE_CRC")
                        private String PAGECRC;
                        @JsonProperty("PAGE_SIZE")
                        private int PAGESIZE;
                        @JsonProperty("PAGE_FORMAT")
                        private String PAGEFORMAT;
                        @JsonProperty("PAGE_ENCRYPT")
                        private int PAGEENCRYPT;
                        @JsonProperty("ORIGINAL_NAME")
                        private String ORIGINALNAME;
                        @JsonProperty("PAGE_EXT")
                        private PAGEEXTDTO PAGEEXT;
                        @JsonProperty("PAGE_DESCS")
                        private String PAGEDESCS;

                        public String getPAGEID() {
                            return PAGEID;
                        }

                        public void setPAGEID(String PAGEID) {
                            this.PAGEID = PAGEID;
                        }

                        public long getCREATEUSER() {
                            return CREATEUSER;
                        }

                        public void setCREATEUSER(long CREATEUSER) {
                            this.CREATEUSER = CREATEUSER;
                        }

                        public String getCREATEUSERNAME() {
                            return CREATEUSERNAME;
                        }

                        public void setCREATEUSERNAME(String CREATEUSERNAME) {
                            this.CREATEUSERNAME = CREATEUSERNAME;
                        }

                        public String getCREATETIME() {
                            return CREATETIME;
                        }

                        public void setCREATETIME(String CREATETIME) {
                            this.CREATETIME = CREATETIME;
                        }

                        public long getMODIFYUSER() {
                            return MODIFYUSER;
                        }

                        public void setMODIFYUSER(long MODIFYUSER) {
                            this.MODIFYUSER = MODIFYUSER;
                        }

                        public String getMODIFYTIME() {
                            return MODIFYTIME;
                        }

                        public void setMODIFYTIME(String MODIFYTIME) {
                            this.MODIFYTIME = MODIFYTIME;
                        }

                        public String getPAGEURL() {
                            return PAGEURL;
                        }

                        public void setPAGEURL(String PAGEURL) {
                            this.PAGEURL = PAGEURL;
                        }

                        public String getTHUMURL() {
                            return THUMURL;
                        }

                        public void setTHUMURL(String THUMURL) {
                            this.THUMURL = THUMURL;
                        }

                        public int getISLOCAL() {
                            return ISLOCAL;
                        }

                        public void setISLOCAL(int ISLOCAL) {
                            this.ISLOCAL = ISLOCAL;
                        }

                        public int getPAGEVER() {
                            return PAGEVER;
                        }

                        public void setPAGEVER(int PAGEVER) {
                            this.PAGEVER = PAGEVER;
                        }

                        public String getPAGEDESC() {
                            return PAGEDESC;
                        }

                        public void setPAGEDESC(String PAGEDESC) {
                            this.PAGEDESC = PAGEDESC;
                        }

                        public String getUPLOADORG() {
                            return UPLOADORG;
                        }

                        public void setUPLOADORG(String UPLOADORG) {
                            this.UPLOADORG = UPLOADORG;
                        }

                        public String getPAGECRC() {
                            return PAGECRC;
                        }

                        public void setPAGECRC(String PAGECRC) {
                            this.PAGECRC = PAGECRC;
                        }

                        public int getPAGESIZE() {
                            return PAGESIZE;
                        }

                        public void setPAGESIZE(int PAGESIZE) {
                            this.PAGESIZE = PAGESIZE;
                        }

                        public String getPAGEFORMAT() {
                            return PAGEFORMAT;
                        }

                        public void setPAGEFORMAT(String PAGEFORMAT) {
                            this.PAGEFORMAT = PAGEFORMAT;
                        }

                        public int getPAGEENCRYPT() {
                            return PAGEENCRYPT;
                        }

                        public void setPAGEENCRYPT(int PAGEENCRYPT) {
                            this.PAGEENCRYPT = PAGEENCRYPT;
                        }

                        public String getORIGINALNAME() {
                            return ORIGINALNAME;
                        }

                        public void setORIGINALNAME(String ORIGINALNAME) {
                            this.ORIGINALNAME = ORIGINALNAME;
                        }

                        public PAGEEXTDTO getPAGEEXT() {
                            return PAGEEXT;
                        }

                        public void setPAGEEXT(PAGEEXTDTO PAGEEXT) {
                            this.PAGEEXT = PAGEEXT;
                        }

                        public String getPAGEDESCS() {
                            return PAGEDESCS;
                        }

                        public void setPAGEDESCS(String PAGEDESCS) {
                            this.PAGEDESCS = PAGEDESCS;
                        }

                        public static class PAGEEXTDTO {
                            @JsonProperty("EXT_ATTR")
                            private List<EXTATTRDTO> EXTATTR;

                            public List<EXTATTRDTO> getEXTATTR() {
                                return EXTATTR;
                            }

                            public void setEXTATTR(List<EXTATTRDTO> EXTATTR) {
                                this.EXTATTR = EXTATTR;
                            }

                            public static class EXTATTRDTO {
                                @JsonProperty("ID")
                                private String ID;
                                @JsonProperty("NAME")
                                private String NAME;
                                @JsonProperty("VALUE")
                                private String VALUE;

                                public String getID() {
                                    return ID;
                                }

                                public void setID(String ID) {
                                    this.ID = ID;
                                }

                                public String getNAME() {
                                    return NAME;
                                }

                                public void setNAME(String NAME) {
                                    this.NAME = NAME;
                                }

                                public String getVALUE() {
                                    return VALUE;
                                }

                                public void setVALUE(String VALUE) {
                                    this.VALUE = VALUE;
                                }
                            }
                        }
                    }
                }

                public static class VTREEDTO {
                    @JsonProperty("APP_CODE")
                    private String APPCODE;
                    @JsonProperty("APP_NAME")
                    private String APPNAME;
                    @JsonProperty("NODE")
                    private NODEDTO NODE;

                    public String getAPPCODE() {
                        return APPCODE;
                    }

                    public void setAPPCODE(String APPCODE) {
                        this.APPCODE = APPCODE;
                    }

                    public String getAPPNAME() {
                        return APPNAME;
                    }

                    public void setAPPNAME(String APPNAME) {
                        this.APPNAME = APPNAME;
                    }

                    public NODEDTO getNODE() {
                        return NODE;
                    }

                    public void setNODE(NODEDTO NODE) {
                        this.NODE = NODE;
                    }

                    public static class NODEDTO {
                        @JsonProperty("ID")
                        private String ID;
                        @JsonProperty("NAME")
                        private String NAME;
                        @JsonProperty("LEAF")
                        private List<String> LEAF;

                        public String getID() {
                            return ID;
                        }

                        public void setID(String ID) {
                            this.ID = ID;
                        }

                        public String getNAME() {
                            return NAME;
                        }

                        public void setNAME(String NAME) {
                            this.NAME = NAME;
                        }

                        public List<String> getLEAF() {
                            return LEAF;
                        }

                        public void setLEAF(List<String> LEAF) {
                            this.LEAF = LEAF;
                        }
                    }
                }
            }
        }
    }
}
