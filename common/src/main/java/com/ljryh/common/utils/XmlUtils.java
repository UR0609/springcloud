package com.ljryh.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class XmlUtils {

    private final static String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";

    public final static String data2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<root>\n" +
            "    <RESPONSE_CODE>200</RESPONSE_CODE>\n" +
            "    <RESPONSE_MSG>请求SYD成功</RESPONSE_MSG>\n" +
            "    <PAGES>\n" +
            "        <PAGE PAGEID=\"22fb705e27cf47d2999fdc25511b950c\" PAGE_URL=\"http://172.28.23.4:7003/SunTransAgent/servlet/GetImage?UhFOUNO1mCH1mCm1m4UgpPEfcuzaVP67JufGSPDfX17EDPA1YdL1mCH1mCm1m47CP5_QJu2R_17e_u5wSsUB_s2vmBms_C5w8P51SsZs8P_f_N01UN0umQLRSPFBmNHt8CeG_CetSdOO8NesJPIf_u6GUBe1mCAg8h2r_N6tJg-rU1UgpPEfcuAqSvIzXT27mQUOVuGfVBtO2cITpO5rptAE2PakSgaOSiMdDF618PIddFIOMsyAA2==\" THUM_URL=\"http://172.28.23.4:7003/SunTransAgent/servlet/GetImage?UhFOUNO1mCH1mCm1m4UgpPEfcuzaVP67JufGSPDfX17EDPA1YdL1mCH1mCm1m47CP5_QJu2R_17e_u5wSsUB_s2vmBms_C5w8P51SsZs8P_f_N01UN0umQLRSPFBmNHt8CeG_CetSdOO8NesJPIf_u6GUBe1mCAg8h2r_N6tJg-rU1z9XhXgUgfnUA7fVg_1YcyOKN0gDh7lUPw7pvHOmeZ12N0z6fIOdF6zmhaymi2rStAAXc21MsIEmFH=\" FILE_NO=\"22fb705e27cf47d2999fdc25511b950c\" PAGE_VER=\"3\"/>\n" +
            "        <PAGE PAGEID=\"48bb6b72bbf64f9fb65884ec632d3189\" PAGE_URL=\"http://172.28.23.4:7003/SunTransAgent/servlet/GetImage?UhFOUNO1mCH1mCm1m4UgpPEfcuzaVP67JufGSPDfX17EDPA1YdL1mCH1mCm1m47CP5_QJu2R_17e_u5wSsUB_s2vmBms_C5w8P51SsZs8P_f_N01UN0umQLRSsXE_sFf_C5G_NU48dOOUC_eJNeEUhHG_PUgmg6rUg2zSu6sJg-rU1UgpPEfcuAqSvIzXT27mQUOVuGfVBtAmiFAXiFBSvFH2Ner2NMBXga1pCfAmhHzdhIdmBfH6Z==\" THUM_URL=\"http://172.28.23.4:7003/SunTransAgent/servlet/GetImage?UhFOUNO1mCH1mCm1m4UgpPEfcuzaVP67JufGSPDfX17EDPA1YdL1mCH1mCm1m47CP5_QJu2R_17e_u5wSsUB_s2vmBms_C5w8P51SsZs8P_f_N01UN0umQLRSsXE_sFf_C5G_NU48dOOUC_eJNeEUhHG_PUgmg6rUg2zSu6sJg-rU1z9XhXgUgfnUA7fVg_1YcyOKN0gDh7lUPw7mF6Om5DEmhIBXByEDCMy6fAO8AIAmBykAAHOpFI1DC2=\" FILE_NO=\"48bb6b72bbf64f9fb65884ec632d3189\" PAGE_VER=\"2\"/>\n" +
            "        <PAGE PAGEID=\"6bccf314-4887-439b-a4fb-a938b23c43a6\" PAGE_URL=\"http://172.28.23.4:7003/SunTransAgent/servlet/GetImage?UhFOUNO1mCH1mCm1m4UgpPEfcuzaVP67JufGSPDfX17EDPA1YdL1mCH1mCm1m47CP5_QJu2R_17e_u5wSsUB_s2vmBms_C5w8P51SsZs8P_f_N01UN0umQLR_gIBSuSsmN2G_CZw_1OOmsf4JP5OUgHGSNes8hH1mumOmu5uJg-rU1UgpPEfcuAqSvIzXT27mQUOVuGfVBtddTIy_hG1Xe5OSsMlX6ZrpF6r_5FyX6aOMtIyXgI4p0==\" THUM_URL=\"http://172.28.23.4:7003/SunTransAgent/servlet/GetImage?UhFOUNO1mCH1mCm1m4UgpPEfcuzaVP67JufGSPDfX17EDPA1YdL1mCH1mCm1m47CP5_QJu2R_17e_u5wSsUB_s2vmBms_C5w8P51SsZs8P_f_N01UN0umQLR_gIBSuSsmN2G_CZw_1OOmsf4JP5OUgHGSNes8hH1mumOmu5uJg-rU1z9XhXgUgfnUA7fVg_1YcyOKN0gDh7lUPw7mhIySgZrp5516eDBmhITmeZODhaTDh_O8NydmeDEAcH=\" FILE_NO=\"6bccf314-4887-439b-a4fb-a938b23c43a6\" PAGE_VER=\"1\"/>\n" +
            "        <PAGE PAGEID=\"9452e08c-5365-45bf-9dbd-03294d11fec1\" PAGE_URL=\"http://172.28.23.4:7003/SunTransAgent/servlet/GetImage?UhFOUNO1mCH1mCm1m4UgpPEfcuzaVP67JufGSPDfX17EDPA1YdL1mCH1mCm1m47CP5_QJu2R_17e_u5wSsUB_s2vmBms_C5w8P51SsZs8P_f_N01UN0umQLR8N2tmg6r8hmG_Nmu_dOO_PIgJNfeSg2GmCm18NMemNFgUPmEJg-rU1UgpPEfcuAqSvIzXT27mQUOVuGfVBtAps0rDhadm5aApTMBA6ZrANIEXNeOpsHOpCIkpu_422==\" THUM_URL=\"http://172.28.23.4:7003/SunTransAgent/servlet/GetImage?UhFOUNO1mCH1mCm1m4UgpPEfcuzaVP67JufGSPDfX17EDPA1YdL1mCH1mCm1m47CP5_QJu2R_17e_u5wSsUB_s2vmBms_C5w8P51SsZs8P_f_N01UN0umQLR8N2tmg6r8hmG_Nmu_dOO_PIgJNfeSg2GmCm18NMemNFgUPmEJg-rU1z9XhXgUgfnUA7fVg_1YcyOKN0gDh7lUPw7dh_ymfI1DCyHDFIAMu_TSOaBdTMdmgITmiMA8Pa4StH=\" FILE_NO=\"9452e08c-5365-45bf-9dbd-03294d11fec1\" PAGE_VER=\"1\"/>\n" +
            "        <PAGE PAGEID=\"b187be11-abe2-47e0-a8a5-9cd6d3ee9e4d\" PAGE_URL=\"http://172.28.23.4:7003/SunTransAgent/servlet/GetImage?UhFOUNO1mCH1mCm1m4UgpPEfcuzaVP67JufGSPDfX17EDPA1YdL1mCH1mCm1m47CP5_QJu2R_17e_u5wSsUB_s2vmBms_C5w8P51SsZs8P_f_N01UN0umQLRSB5w_uIfmN5GSPIfm4OO_u6rJP5wSN6G8P_e_g2sUP6zUNMeJg-rU1UgpPEfcuAqSvIzXT27mQUOVuGfVBOz2cFOSBe1SeaOmh_Bd5aBpsyA2c2r2cITmCMEmfAkSr==\" THUM_URL=\"http://172.28.23.4:7003/SunTransAgent/servlet/GetImage?UhFOUNO1mCH1mCm1m4UgpPEfcuzaVP67JufGSPDfX17EDPA1YdL1mCH1mCm1m47CP5_QJu2R_17e_u5wSsUB_s2vmBms_C5w8P51SsZs8P_f_N01UN0umQLRSB5w_uIfmN5GSPIfm4OO_u6rJP5wSN6G8P_e_g2sUP6zUNMeJg-rU1z9XhXgUgfnUA7fVg_1YcyOKN0gDh7lUPw7dFAOXByySiFTX6DkXi2r6eDlA6DySsyEmBIkpunr265=\" FILE_NO=\"b187be11-abe2-47e0-a8a5-9cd6d3ee9e4d\" PAGE_VER=\"1\"/>\n" +
            "        <PAGE PAGEID=\"f3b9a193-4489-40de-8aec-f73868c991f5\" PAGE_URL=\"http://172.28.23.4:7003/SunTransAgent/servlet/GetImage?UhFOUNO1mCH1mCm1m4UgpPEfcuzaVP67JufGSPDfX17EDPA1YdL1mCH1mCm1m47CP5_QJu2R_17e_u5wSsUB_s2vmBms_C5w8P51SsZs8P_f_N01UN0umQLRUB_48P5E8NmG_C2w8dOOmhMfJNaaUPmGUBXs8CSwSsezmPStJg-rU1UgpPEfcuAqSvIzXT27mQUOVuGfVBOO_hI4DCfOSOaE_hGApvFBpualSBy4XNIlp5akD5Dy22==\" THUM_URL=\"http://172.28.23.4:7003/SunTransAgent/servlet/GetImage?UhFOUNO1mCH1mCm1m4UgpPEfcuzaVP67JufGSPDfX17EDPA1YdL1mCH1mCm1m47CP5_QJu2R_17e_u5wSsUB_s2vmBms_C5w8P51SsZs8P_f_N01UN0umQLRUB_48P5E8NmG_C2w8dOOmhMfJNaaUPmGUBXs8CSwSsezmPStJg-rU1z9XhXgUgfnUA7fVg_1YcyOKN0gDh7lUPw7mBH1Dh_ydFAk_TMdXNMHpsIApuaBD5DT_CH1pC2z6BH=\" FILE_NO=\"f3b9a193-4489-40de-8aec-f73868c991f5\" PAGE_VER=\"1\"/>\n" +
            "        <PAGE PAGEID=\"fd092cd9-1ebf-47a4-8852-ae508b51c478\" PAGE_URL=\"http://172.28.23.4:7003/SunTransAgent/servlet/GetImage?UhFOUNO1mCH1mCm1m4UgpPEfcuzaVP67JufGSPDfX17EDPA1YdL1mCH1mCm1m47CP5_QJu2R_17e_u5wSsUB_s2vmBms_C5w8P51SsZs8P_f_N01UN0umQLRUg2r8NIBUCeGmPA4U4OO_u5OJNZw_NHGSP6tmCa4_NFB_CXwJg-rU1UgpPEfcuAqSvIzXT27mQUOVuGfVBtkdTM1Mv2rX6FH_CM1XNMEMODEpCHOAPnz86DEStIESr==\" THUM_URL=\"http://172.28.23.4:7003/SunTransAgent/servlet/GetImage?UhFOUNO1mCH1mCm1m4UgpPEfcuzaVP67JufGSPDfX17EDPA1YdL1mCH1mCm1m47CP5_QJu2R_17e_u5wSsUB_s2vmBms_C5w8P51SsZs8P_f_N01UN0umQLRUg2r8NIBUCeGmPA4U4OO_u5OJNZw_NHGSP6tmCa4_NFB_CXwJg-rU1z9XhXgUgfnUA7fVg_1YcyOKN0gDh7lUPw7Sg_EdTFkphIdSe5OMsMB8PZzDF6OpTMlSBHOXNMHdTH=\" FILE_NO=\"fd092cd9-1ebf-47a4-8852-ae508b51c478\" PAGE_VER=\"1\"/>\n" +
            "    </PAGES>\n" +
            "    <SYD>\n" +
            "        <doc version=\"1.2\">\n" +
            "            <DocInfo>\n" +
            "                <BATCH_ID>d7a8c6c7472334189a2c839ce502e060</BATCH_ID>\n" +
            "                <BUSI_NUM>23024100TDDG2022000014</BUSI_NUM>\n" +
            "                <BIZ_ORG>23010300</BIZ_ORG>\n" +
            "                <APP_CODE>CXCB</APP_CODE>\n" +
            "                <APP_NAME>车险承保</APP_NAME>\n" +
            "                <BATCH_VER>3</BATCH_VER>\n" +
            "                <INTER_VER>3</INTER_VER>\n" +
            "                <STATUS>1</STATUS>\n" +
            "                <CREATE_USER>2302410006</CREATE_USER>\n" +
            "                <CREATE_DATE>2022-03-22 09:39:04.433</CREATE_DATE>\n" +
            "                <MODIFY_USER>lms</MODIFY_USER>\n" +
            "                <MODIFY_DATE>2022-03-22 09:32:22</MODIFY_DATE>\n" +
            "                <DOC_EXT>\n" +
            "                    <EXT_ATTR ID=\"BUSI_NO\" NAME=\"投保单号\" IS_SHOW=\"1\" IS_KEY=\"1\" IS_NULL=\"0\" INPUT_TYPE=\"1\">23024100TDDG2022000014</EXT_ATTR>\n" +
            "                </DOC_EXT>\n" +
            "            </DocInfo>\n" +
            "            <PageInfo>\n" +
            "                <PAGE PAGEID=\"22fb705e27cf47d2999fdc25511b950c\">\n" +
            "                    <CREATE_USER>lms</CREATE_USER>\n" +
            "                    <CREATE_USERNAME>lms</CREATE_USERNAME>\n" +
            "                    <CREATE_TIME>2022-03-22 09:37:25</CREATE_TIME>\n" +
            "                    <MODIFY_USER>lms</MODIFY_USER>\n" +
            "                    <MODIFY_TIME>2022-03-22 09:37:25</MODIFY_TIME>\n" +
            "                    <PAGE_URL>aac12589-495a-4993-be7e-f9205f8d0555.jpg</PAGE_URL>\n" +
            "                    <THUM_URL>aac12589-495a-4993-be7e-f9205f8d0555.jpg.jpg</THUM_URL>\n" +
            "                    <IS_LOCAL>1</IS_LOCAL>\n" +
            "                    <PAGE_VER>3</PAGE_VER>\n" +
            "                    <PAGE_DESC></PAGE_DESC>\n" +
            "                    <UPLOAD_ORG>阳光农业相互保险公司哈尔滨分公司财险业务部</UPLOAD_ORG>\n" +
            "                    <PAGE_CRC>7ae3b75d64d710827e2a0f3705051fc8</PAGE_CRC>\n" +
            "                    <PAGE_SIZE>95629</PAGE_SIZE>\n" +
            "                    <PAGE_FORMAT>image/jpeg</PAGE_FORMAT>\n" +
            "                    <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "                    <ORIGINAL_NAME>CCXCB004_ddb_autograph_202203220943077480_2.jpg</ORIGINAL_NAME>\n" +
            "                    <PAGE_EXT/>\n" +
            "                    <PAGE_DESCS/>\n" +
            "                </PAGE>\n" +
            "                <PAGE PAGEID=\"48bb6b72bbf64f9fb65884ec632d3189\">\n" +
            "                    <CREATE_USER>lms</CREATE_USER>\n" +
            "                    <CREATE_USERNAME>lms</CREATE_USERNAME>\n" +
            "                    <CREATE_TIME>2022-03-22 09:37:22</CREATE_TIME>\n" +
            "                    <MODIFY_USER>lms</MODIFY_USER>\n" +
            "                    <MODIFY_TIME>2022-03-22 09:37:22</MODIFY_TIME>\n" +
            "                    <PAGE_URL>c7171e41-56b9-4d3d-91db-5ff2e0fd9ce3.jpg</PAGE_URL>\n" +
            "                    <THUM_URL>c7171e41-56b9-4d3d-91db-5ff2e0fd9ce3.jpg.jpg</THUM_URL>\n" +
            "                    <IS_LOCAL>1</IS_LOCAL>\n" +
            "                    <PAGE_VER>2</PAGE_VER>\n" +
            "                    <PAGE_DESC></PAGE_DESC>\n" +
            "                    <UPLOAD_ORG>阳光农业相互保险公司哈尔滨分公司财险业务部</UPLOAD_ORG>\n" +
            "                    <PAGE_CRC>0f5ffc1b0c34fdd4e023b3a5a7f04728</PAGE_CRC>\n" +
            "                    <PAGE_SIZE>1264470</PAGE_SIZE>\n" +
            "                    <PAGE_FORMAT>image/jpeg</PAGE_FORMAT>\n" +
            "                    <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "                    <ORIGINAL_NAME>CCXCB004_ddb_autograph_202203220943077470_1.jpg</ORIGINAL_NAME>\n" +
            "                    <PAGE_EXT/>\n" +
            "                    <PAGE_DESCS/>\n" +
            "                </PAGE>\n" +
            "                <PAGE PAGEID=\"6bccf314-4887-439b-a4fb-a938b23c43a6\">\n" +
            "                    <CREATE_USER>2302410006</CREATE_USER>\n" +
            "                    <CREATE_USERNAME>叶建楠</CREATE_USERNAME>\n" +
            "                    <CREATE_TIME>2022-03-22 09:39:04</CREATE_TIME>\n" +
            "                    <MODIFY_USER>2302410006</MODIFY_USER>\n" +
            "                    <MODIFY_TIME>2022-03-22 09:39:04</MODIFY_TIME>\n" +
            "                    <PAGE_URL>6bccf314-4887-439b-a4fb-a938b23c43a6.jpg</PAGE_URL>\n" +
            "                    <THUM_URL>6bccf314-4887-439b-a4fb-a938b23c43a6.jpg.jpg</THUM_URL>\n" +
            "                    <IS_LOCAL>1</IS_LOCAL>\n" +
            "                    <PAGE_VER>1</PAGE_VER>\n" +
            "                    <PAGE_DESC>QQ截图20220322092457</PAGE_DESC>\n" +
            "                    <UPLOAD_ORG>阳光农业相互保险公司齐齐哈尔中心支公司依安保险社</UPLOAD_ORG>\n" +
            "                    <PAGE_CRC>b084cca8b64e2db569fca1581e1588ca</PAGE_CRC>\n" +
            "                    <PAGE_SIZE>111323</PAGE_SIZE>\n" +
            "                    <PAGE_FORMAT>image/jpeg</PAGE_FORMAT>\n" +
            "                    <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "                    <ORIGINAL_NAME>QQ截图20220322092457.jpg</ORIGINAL_NAME>\n" +
            "                    <PAGE_EXT/>\n" +
            "                    <PAGE_DESCS/>\n" +
            "                </PAGE>\n" +
            "                <PAGE PAGEID=\"9452e08c-5365-45bf-9dbd-03294d11fec1\">\n" +
            "                    <CREATE_USER>2302410006</CREATE_USER>\n" +
            "                    <CREATE_USERNAME>叶建楠</CREATE_USERNAME>\n" +
            "                    <CREATE_TIME>2022-03-22 09:39:04</CREATE_TIME>\n" +
            "                    <MODIFY_USER>2302410006</MODIFY_USER>\n" +
            "                    <MODIFY_TIME>2022-03-22 09:39:04</MODIFY_TIME>\n" +
            "                    <PAGE_URL>9452e08c-5365-45bf-9dbd-03294d11fec1.jpg</PAGE_URL>\n" +
            "                    <THUM_URL>9452e08c-5365-45bf-9dbd-03294d11fec1.jpg.jpg</THUM_URL>\n" +
            "                    <IS_LOCAL>1</IS_LOCAL>\n" +
            "                    <PAGE_VER>1</PAGE_VER>\n" +
            "                    <PAGE_DESC>a38e01637429f8f71c1d671839007d2</PAGE_DESC>\n" +
            "                    <UPLOAD_ORG>阳光农业相互保险公司齐齐哈尔中心支公司依安保险社</UPLOAD_ORG>\n" +
            "                    <PAGE_CRC>e16217cbf24619b8651ede06216c4b53</PAGE_CRC>\n" +
            "                    <PAGE_SIZE>124947</PAGE_SIZE>\n" +
            "                    <PAGE_FORMAT>image/jpeg</PAGE_FORMAT>\n" +
            "                    <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "                    <ORIGINAL_NAME>a38e01637429f8f71c1d671839007d2.jpg</ORIGINAL_NAME>\n" +
            "                    <PAGE_EXT/>\n" +
            "                    <PAGE_DESCS/>\n" +
            "                </PAGE>\n" +
            "                <PAGE PAGEID=\"b187be11-abe2-47e0-a8a5-9cd6d3ee9e4d\">\n" +
            "                    <CREATE_USER>2302410006</CREATE_USER>\n" +
            "                    <CREATE_USERNAME>叶建楠</CREATE_USERNAME>\n" +
            "                    <CREATE_TIME>2022-03-22 09:39:04</CREATE_TIME>\n" +
            "                    <MODIFY_USER>2302410006</MODIFY_USER>\n" +
            "                    <MODIFY_TIME>2022-03-22 09:39:04</MODIFY_TIME>\n" +
            "                    <PAGE_URL>b187be11-abe2-47e0-a8a5-9cd6d3ee9e4d.jpg</PAGE_URL>\n" +
            "                    <THUM_URL>b187be11-abe2-47e0-a8a5-9cd6d3ee9e4d.jpg.jpg</THUM_URL>\n" +
            "                    <IS_LOCAL>1</IS_LOCAL>\n" +
            "                    <PAGE_VER>1</PAGE_VER>\n" +
            "                    <PAGE_DESC>f7ae470d545a4cda3b696258c4ff9eb</PAGE_DESC>\n" +
            "                    <UPLOAD_ORG>阳光农业相互保险公司齐齐哈尔中心支公司依安保险社</UPLOAD_ORG>\n" +
            "                    <PAGE_CRC>9f2dd76f9a8ca867769ef1b0635e4f28</PAGE_CRC>\n" +
            "                    <PAGE_SIZE>109499</PAGE_SIZE>\n" +
            "                    <PAGE_FORMAT>image/jpeg</PAGE_FORMAT>\n" +
            "                    <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "                    <ORIGINAL_NAME>f7ae470d545a4cda3b696258c4ff9eb.jpg</ORIGINAL_NAME>\n" +
            "                    <PAGE_EXT/>\n" +
            "                    <PAGE_DESCS/>\n" +
            "                </PAGE>\n" +
            "                <PAGE PAGEID=\"f3b9a193-4489-40de-8aec-f73868c991f5\">\n" +
            "                    <CREATE_USER>2302410006</CREATE_USER>\n" +
            "                    <CREATE_USERNAME>叶建楠</CREATE_USERNAME>\n" +
            "                    <CREATE_TIME>2022-03-22 09:39:04</CREATE_TIME>\n" +
            "                    <MODIFY_USER>2302410006</MODIFY_USER>\n" +
            "                    <MODIFY_TIME>2022-03-22 09:39:04</MODIFY_TIME>\n" +
            "                    <PAGE_URL>f3b9a193-4489-40de-8aec-f73868c991f5.jpg</PAGE_URL>\n" +
            "                    <THUM_URL>f3b9a193-4489-40de-8aec-f73868c991f5.jpg.jpg</THUM_URL>\n" +
            "                    <IS_LOCAL>1</IS_LOCAL>\n" +
            "                    <PAGE_VER>1</PAGE_VER>\n" +
            "                    <PAGE_DESC>b4c5973df7b67280b9f651c318a980c</PAGE_DESC>\n" +
            "                    <UPLOAD_ORG>阳光农业相互保险公司齐齐哈尔中心支公司依安保险社</UPLOAD_ORG>\n" +
            "                    <PAGE_CRC>77c05e5748f5dc08c7e5f1f55801f5b8</PAGE_CRC>\n" +
            "                    <PAGE_SIZE>327759</PAGE_SIZE>\n" +
            "                    <PAGE_FORMAT>image/jpeg</PAGE_FORMAT>\n" +
            "                    <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "                    <ORIGINAL_NAME>b4c5973df7b67280b9f651c318a980c.jpg</ORIGINAL_NAME>\n" +
            "                    <PAGE_EXT/>\n" +
            "                    <PAGE_DESCS/>\n" +
            "                </PAGE>\n" +
            "                <PAGE PAGEID=\"fd092cd9-1ebf-47a4-8852-ae508b51c478\">\n" +
            "                    <CREATE_USER>2302410006</CREATE_USER>\n" +
            "                    <CREATE_USERNAME>叶建楠</CREATE_USERNAME>\n" +
            "                    <CREATE_TIME>2022-03-22 09:39:04</CREATE_TIME>\n" +
            "                    <MODIFY_USER>2302410006</MODIFY_USER>\n" +
            "                    <MODIFY_TIME>2022-03-22 09:39:04</MODIFY_TIME>\n" +
            "                    <PAGE_URL>fd092cd9-1ebf-47a4-8852-ae508b51c478.jpg</PAGE_URL>\n" +
            "                    <THUM_URL>fd092cd9-1ebf-47a4-8852-ae508b51c478.jpg.jpg</THUM_URL>\n" +
            "                    <IS_LOCAL>1</IS_LOCAL>\n" +
            "                    <PAGE_VER>1</PAGE_VER>\n" +
            "                    <PAGE_DESC>63bc03218f9802931c4c3ead3c257d6</PAGE_DESC>\n" +
            "                    <UPLOAD_ORG>阳光农业相互保险公司齐齐哈尔中心支公司依安保险社</UPLOAD_ORG>\n" +
            "                    <PAGE_CRC>dbd3382813bf00cd48cf4d235de36664</PAGE_CRC>\n" +
            "                    <PAGE_SIZE>291899</PAGE_SIZE>\n" +
            "                    <PAGE_FORMAT>image/jpeg</PAGE_FORMAT>\n" +
            "                    <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "                    <ORIGINAL_NAME>63bc03218f9802931c4c3ead3c257d6.jpg</ORIGINAL_NAME>\n" +
            "                    <PAGE_EXT/>\n" +
            "                    <PAGE_DESCS/>\n" +
            "                </PAGE>\n" +
            "            </PageInfo>\n" +
            "            <VTREE APP_CODE=\"CXCB\" APP_NAME=\"车险承保\">\n" +
            "                <NODE ID=\"CCXCB006\" NAME=\"身份证\">\n" +
            "                    <LEAF>9452e08c-5365-45bf-9dbd-03294d11fec1</LEAF>\n" +
            "                    <LEAF>b187be11-abe2-47e0-a8a5-9cd6d3ee9e4d</LEAF>\n" +
            "                </NODE>\n" +
            "                <NODE ID=\"CCXCB018\" NAME=\"行驶证\">\n" +
            "                    <LEAF>fd092cd9-1ebf-47a4-8852-ae508b51c478</LEAF>\n" +
            "                    <LEAF>f3b9a193-4489-40de-8aec-f73868c991f5</LEAF>\n" +
            "                </NODE>\n" +
            "                <NODE ID=\"CCXCB004\" NAME=\"其他单证\">\n" +
            "                    <LEAF>6bccf314-4887-439b-a4fb-a938b23c43a6</LEAF>\n" +
            "                    <LEAF>48bb6b72bbf64f9fb65884ec632d3189</LEAF>\n" +
            "                    <LEAF>22fb705e27cf47d2999fdc25511b950c</LEAF>\n" +
            "                </NODE>\n" +
            "            </VTREE>\n" +
            "        </doc>\n" +
            "    </SYD>\n" +
            "</root>";
    public final static String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<root><RESPONSE_CODE>200</RESPONSE_CODE><RESPONSE_MSG>查询成功，记录不存在</RESPONSE_MSG></root>";
    public String dataddd;



    /**
     * 序列化XML
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toXML(Object obj) {
        XStream stream = getXStream();
        stream.processAnnotations(obj.getClass());
        return new StringBuffer(XML_DECLARATION).append(stream.toXML(obj)).toString();
    }

    /**
     * 反序列化XML
     *
     * @param xmlStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromXML(String xmlStr, Class<T> clazz) {
        XStream stream = getXStream();
        stream.processAnnotations(clazz);
        Object obj = stream.fromXML(xmlStr);
        try {
            return clazz.cast(obj);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public static String getNodeValue(String xpath, String dataStr) {
        try {
            // 将字符串转为xml
            Document document = DocumentHelper.parseText(dataStr);
            // 查找节点
            Element element = (Element) document.selectSingleNode(xpath);
            if (element != null) {
                return element.getStringValue();
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取Xstream实例
     *
     * @return
     */
    public static XStream getXStream() {
        return new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));
    }

    public static Date parse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(strDate);
    }

    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }

    public static Map<String, Object> getWxSignature(String url) {

        Map<String, Object> map = new HashMap<>();
        String timestamp = String.format("%010d", System.currentTimeMillis() / 1000);
        String nocestr = UUID.randomUUID().toString().replace("-", "");
        String ticket = "54_lykUQOsEkocW9kbPKwkIyfZXHEIr3Hpc1E7fkybhGdSLIPC7I4w8aPygZHOxlwpLksV7fuWX__-ErfPrvHQ262y0etL9XZ8zdIrRCPscdW9NRHdR1cXAaE7yOgPCF9GEm1JHqNmrRdmlBKOCJZPeABAPFV";
        String string1 = "jsapi ticket=" + ticket + "&noncestr=" + nocestr + "&timestamp=" + timestamp + "&url=" + url;
        String signature = SHA1(string1);
        if (signature != null) {
            map.put("appid", "wxfb8668b88c06d733");
            map.put("timestamp", timestamp);
            map.put("noncestr", nocestr);
            map.put("signature", signature);
        }
        return map;
    }

    public static String SHA1(String decript) {



//        try {
//            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
//            digest.update(decript.getBytes());
//            byte messageDigest[] = digest.digest();
//            StringBuilder hexString = new StringBuilder();
//            for (int i = 0; i < messageDigest.length; i++) {
//                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
//                if (shaHex.length() < 2) {
//                    hexString.append(0);
//                }
//                hexString.append(shaHex);
//            }
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return null;

        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }



    public static void main(String[] args) {

        String s = "{\"updatedBy\":\"00000000\",\"createdBy\":\"00000000\",\"undwrtRequestDto\":{\"certiType\":\"T\",\"iModelNo\":1,\"startNodeNo\":1,\"prpTinsuredDtoList\":[{\"insuredflag\":\"2\",\"prpinsuredinsuredname\":\"安柏禄\",\"phonenumber\":\"18346309777\",\"postcode\":\"\",\"identifynumber\":\"231084200404170012\",\"insuredlanguagecode\":\"C\",\"linkername\":\"\",\"prpinsuredinsuredcode\":\"9999999999999999\",\"insuredtypename\":\"个人\",\"insurednature\":\"3\",\"postaddress\":\"\",\"bank\":\"\",\"insuredaddress\":\"黑龙江农业经济职业学院\",\"insuredlanguage\":\"中文\",\"insure_flag\":\"\",\"account\":\"\",\"insuredtype\":\"1\"},{\"insuredflag\":\"1\",\"prpinsuredinsuredname\":\"安柏禄\",\"phonenumber\":\"18346309777\",\"postcode\":\"\",\"identifynumber\":\"231084200404170012\",\"insuredlanguagecode\":\"C\",\"linkername\":\"\",\"prpinsuredinsuredcode\":\"9999999999999999\",\"insuredtypename\":\"个人\",\"insurednature\":\"3\",\"postaddress\":\"\",\"bank\":\"\",\"insuredaddress\":\"黑龙江农业经济职业学院\",\"insuredlanguage\":\"中文\",\"insure_flag\":\"\",\"account\":\"\",\"insuredtype\":\"1\"},{\"insuredflag\":\"9\",\"prpinsuredinsuredname\":\"安柏禄\",\"phonenumber\":\"18346309777\",\"postcode\":\"\",\"identifynumber\":\"231084200404170012\",\"insuredlanguagecode\":\"C\",\"linkername\":\"\",\"prpinsuredinsuredcode\":\"9999999999999999\",\"insuredtypename\":\"个人\",\"insurednature\":\"3\",\"postaddress\":\"\",\"bank\":\"\",\"insuredaddress\":\"黑龙江农业经济职业学院\",\"insuredlanguage\":\"中文\",\"insure_flag\":\"\",\"account\":\"\",\"insuredtype\":\"1\"}],\"prpTitemKindDtoList\":[{\"deductiblerate\":\"0.00\",\"exchangeratemain\":\"1.0\",\"calculateflag\":\"Y\",\"flag\":\" 1\",\"modecode\":\"\",\"familyname\":\"安柏禄\",\"notaxpremium\":\"67.73\",\"discount\":\"0.0\",\"familyno\":\"2\",\"shortrateflagname\":\"月比例\",\"thirdpersonamount\":\"0.00\",\"premium\":\"71.79\",\"shortrateflag\":\"1\",\"addressno\":\"0\",\"rate\":\"1.00\",\"currency\":\"CNY\",\"model\":\"120102\",\"value\":\"0.00\",\"basepremium\":\"0.00\",\"itemcodename\":\"赔偿责任\",\"amount\":\"70000.00\",\"taxrate\":\"6.00\",\"quantity\":\"0\",\"currencyname\":\"人民币\",\"kindname\":\"学生幼儿意外伤害保险（A款）\",\"itemno\":\"0\",\"mainsub\":\"1\",\"shortrate\":\"100.0000\",\"kindcode\":\"001\",\"unit\":\"\",\"adjustrate\":\"1.03\",\"modename\":\"\",\"itemcode\":\"0001\",\"deductible\":\"0.00\",\"guestamount\":\"0.00\",\"itemkindno\":\"1\",\"rationtype\":\"\",\"taxfee\":\"4.06\",\"itemdetailname\":\"赔偿责任\",\"unitamount\":\"0.00\",\"driveramount\":\"0.00\"},{\"deductiblerate\":\"0.00\",\"exchangeratemain\":\"1.0\",\"calculateflag\":\"Y\",\"flag\":\" 1\",\"modecode\":\"\",\"familyname\":\"安柏禄\",\"notaxpremium\":\"25.75\",\"discount\":\"0.0\",\"familyno\":\"2\",\"shortrateflagname\":\"月比例\",\"thirdpersonamount\":\"0.00\",\"premium\":\"27.30\",\"shortrateflag\":\"1\",\"addressno\":\"0\",\"rate\":\"0.30\",\"currency\":\"CNY\",\"model\":\"120102\",\"value\":\"0.00\",\"basepremium\":\"13.00\",\"itemcodename\":\"赔偿责任\",\"amount\":\"40000.00\",\"taxrate\":\"6.00\",\"quantity\":\"1\",\"currencyname\":\"人民币\",\"kindname\":\"附加疾病住院医疗保险（A款）\",\"itemno\":\"0\",\"mainsub\":\"2\",\"shortrate\":\"100.0000\",\"kindcode\":\"002\",\"unit\":\"\",\"adjustrate\":\"1.09\",\"modename\":\"\",\"itemcode\":\"0001\",\"deductible\":\"100.00\",\"guestamount\":\"0.00\",\"itemkindno\":\"2\",\"rationtype\":\"\",\"taxfee\":\"1.55\",\"itemdetailname\":\"赔偿责任\",\"unitamount\":\"0.00\",\"driveramount\":\"0.00\"},{\"deductiblerate\":\"0.00\",\"exchangeratemain\":\"1.0\",\"calculateflag\":\"Y\",\"flag\":\" 1\",\"modecode\":\"\",\"familyname\":\"安柏禄\",\"notaxpremium\":\"24.73\",\"discount\":\"0.0\",\"familyno\":\"2\",\"shortrateflagname\":\"月比例\",\"thirdpersonamount\":\"0.00\",\"premium\":\"26.21\",\"shortrateflag\":\"1\",\"addressno\":\"0\",\"rate\":\"1.60\",\"currency\":\"CNY\",\"model\":\"120102\",\"value\":\"0.00\",\"basepremium\":\"0.00\",\"itemcodename\":\"赔偿责任\",\"amount\":\"15000.00\",\"taxrate\":\"6.00\",\"quantity\":\"0\",\"currencyname\":\"人民币\",\"kindname\":\"附加意外伤害医疗保险（A款）\",\"itemno\":\"0\",\"mainsub\":\"2\",\"shortrate\":\"100.0000\",\"kindcode\":\"003\",\"unit\":\"\",\"adjustrate\":\"1.09\",\"modename\":\"\",\"itemcode\":\"0001\",\"deductible\":\"100.00\",\"guestamount\":\"0.00\",\"itemkindno\":\"3\",\"rationtype\":\"\",\"taxfee\":\"1.48\",\"itemdetailname\":\"赔偿责任\",\"unitamount\":\"0.00\",\"driveramount\":\"0.00\"},{\"deductiblerate\":\"0.00\",\"exchangeratemain\":\"1.0\",\"calculateflag\":\"Y\",\"flag\":\" 1\",\"modecode\":\"\",\"familyname\":\"安柏禄\",\"notaxpremium\":\"23.11\",\"discount\":\"0.0\",\"familyno\":\"2\",\"shortrateflagname\":\"月比例\",\"thirdpersonamount\":\"0.00\",\"premium\":\"24.50\",\"shortrateflag\":\"1\",\"addressno\":\"0\",\"rate\":\"0.50\",\"currency\":\"CNY\",\"model\":\"120102\",\"value\":\"0.00\",\"basepremium\":\"0.00\",\"itemcodename\":\"赔偿责任\",\"amount\":\"70000.00\",\"taxrate\":\"6.00\",\"quantity\":\"0\",\"currencyname\":\"人民币\",\"kindname\":\"附加疾病身故保险\",\"itemno\":\"0\",\"mainsub\":\"2\",\"shortrate\":\"100.0000\",\"kindcode\":\"005\",\"unit\":\"\",\"adjustrate\":\"0.70\",\"modename\":\"\",\"itemcode\":\"0001\",\"deductible\":\"0.00\",\"guestamount\":\"0.00\",\"itemkindno\":\"5\",\"rationtype\":\"\",\"taxfee\":\"1.39\",\"itemdetailname\":\"赔偿责任\",\"unitamount\":\"0.00\",\"driveramount\":\"0.00\"}],\"prpTlimitDtoList\":[],\"prpTmainDto\":{\"businesstypename\":\"非农\",\"endhour\":\"24\",\"prpmaininsuredaddress\":\"黑龙江农业经济职业学院\",\"sumamount\":\"195000.00\",\"startdate\":\"2022-05-07\",\"insurancenumber\":\"1\",\"sumquantity\":\"0.0\",\"businesstype\":\"00\",\"claimtimes\":\"0\",\"printno\":\"\",\"classcode\":\"E\",\"coinsuranceflagname\":\"独家承保\",\"notaxfree\":\"0.00\",\"season\":\"\",\"statunitcode\":\"\",\"itemKindSubNum\":\"0\",\"agricultinsured\":\"\",\"endorsetimes\":\"0\",\"applicode\":\"9999999999999999\",\"sumtaxfee\":\"8.48\",\"comname\":\"阳光农业相互保险公司哈尔滨分公司财险业务部\",\"agricultclass\":\"\",\"inputDate\":\"2022-05-06\",\"proportionflag2\":\"\",\"enddate\":\"2023-05-06\",\"taxfree\":\"0.00\",\"operatorcodename\":\"孙媛媛\",\"policysortname\":\"普通\",\"proportionflag1\":\"\",\"riskcode\":\"ECK\",\"agricultitemkindname\":\"\",\"operatetype\":\"\",\"allinsflagname\":\"法定\",\"tcoinsOutList\":[],\"handlercode\":\"2301030004\",\"reinsflag\":\"0\",\"businesstype1name\":\"商业性\",\"comcode\":\"23010300\",\"operatedate\":\"2022-05-06\",\"paytimes\":\"1\",\"agreementno\":\"\",\"starthour\":\"0\",\"approvercodename\":\"孙媛媛\",\"sumnotaxpremium\":\"141.32\",\"contractno\":\"\",\"policytype\":\"04\",\"coinslinkflag\":\"0\",\"showtype\":\"SHOW\",\"handler1name\":\"孙媛媛\",\"agricultinsuredname\":\"\",\"arbitboardname\":\"哈尔滨仲裁委员会\",\"makecom\":\"23010300\",\"statquantity\":\"0.00\",\"prpmaininsuredcode\":\"9999999999999999\",\"prptinsuredList\":[{\"insuredflag\":\"2\",\"prpinsuredinsuredname\":\"安柏禄\",\"phonenumber\":\"18346309777\",\"postcode\":\"\",\"identifynumber\":\"231084200404170012\",\"insuredlanguagecode\":\"C\",\"linkername\":\"\",\"prpinsuredinsuredcode\":\"9999999999999999\",\"insuredtypename\":\"个人\",\"insurednature\":\"3\",\"postaddress\":\"\",\"bank\":\"\",\"insuredaddress\":\"黑龙江农业经济职业学院\",\"insuredlanguage\":\"中文\",\"insure_flag\":\"\",\"account\":\"\",\"insuredtype\":\"1\"},{\"insuredflag\":\"1\",\"prpinsuredinsuredname\":\"安柏禄\",\"phonenumber\":\"18346309777\",\"postcode\":\"\",\"identifynumber\":\"231084200404170012\",\"insuredlanguagecode\":\"C\",\"linkername\":\"\",\"prpinsuredinsuredcode\":\"9999999999999999\",\"insuredtypename\":\"个人\",\"insurednature\":\"3\",\"postaddress\":\"\",\"bank\":\"\",\"insuredaddress\":\"黑龙江农业经济职业学院\",\"insuredlanguage\":\"中文\",\"insure_flag\":\"\",\"account\":\"\",\"insuredtype\":\"1\"},{\"insuredflag\":\"9\",\"prpinsuredinsuredname\":\"安柏禄\",\"phonenumber\":\"18346309777\",\"postcode\":\"\",\"identifynumber\":\"231084200404170012\",\"insuredlanguagecode\":\"C\",\"linkername\":\"\",\"prpinsuredinsuredcode\":\"9999999999999999\",\"insuredtypename\":\"个人\",\"insurednature\":\"3\",\"postaddress\":\"\",\"bank\":\"\",\"insuredaddress\":\"黑龙江农业经济职业学院\",\"insuredlanguage\":\"中文\",\"insure_flag\":\"\",\"account\":\"\",\"insuredtype\":\"1\"}],\"agricultitemkind\":\"\",\"assistfeeflag\":\"\",\"insuranceflag\":\"\",\"statisticsym\":\"2022-05-06\",\"prptitemkindList\":[{\"deductiblerate\":\"0.00\",\"exchangeratemain\":\"1.0\",\"calculateflag\":\"Y\",\"flag\":\" 1\",\"modecode\":\"\",\"familyname\":\"安柏禄\",\"notaxpremium\":\"67.73\",\"discount\":\"0.0\",\"familyno\":\"2\",\"shortrateflagname\":\"月比例\",\"thirdpersonamount\":\"0.00\",\"premium\":\"71.79\",\"shortrateflag\":\"1\",\"addressno\":\"0\",\"rate\":\"1.00\",\"currency\":\"CNY\",\"model\":\"120102\",\"value\":\"0.00\",\"basepremium\":\"0.00\",\"itemcodename\":\"赔偿责任\",\"amount\":\"70000.00\",\"taxrate\":\"6.00\",\"quantity\":\"0\",\"currencyname\":\"人民币\",\"kindname\":\"学生幼儿意外伤害保险（A款）\",\"itemno\":\"0\",\"mainsub\":\"1\",\"shortrate\":\"100.0000\",\"kindcode\":\"001\",\"unit\":\"\",\"adjustrate\":\"1.03\",\"modename\":\"\",\"itemcode\":\"0001\",\"deductible\":\"0.00\",\"guestamount\":\"0.00\",\"itemkindno\":\"1\",\"rationtype\":\"\",\"taxfee\":\"4.06\",\"itemdetailname\":\"赔偿责任\",\"unitamount\":\"0.00\",\"driveramount\":\"0.00\"},{\"deductiblerate\":\"0.00\",\"exchangeratemain\":\"1.0\",\"calculateflag\":\"Y\",\"flag\":\" 1\",\"modecode\":\"\",\"familyname\":\"安柏禄\",\"notaxpremium\":\"25.75\",\"discount\":\"0.0\",\"familyno\":\"2\",\"shortrateflagname\":\"月比例\",\"thirdpersonamount\":\"0.00\",\"premium\":\"27.30\",\"shortrateflag\":\"1\",\"addressno\":\"0\",\"rate\":\"0.30\",\"currency\":\"CNY\",\"model\":\"120102\",\"value\":\"0.00\",\"basepremium\":\"13.00\",\"itemcodename\":\"赔偿责任\",\"amount\":\"40000.00\",\"taxrate\":\"6.00\",\"quantity\":\"1\",\"currencyname\":\"人民币\",\"kindname\":\"附加疾病住院医疗保险（A款）\",\"itemno\":\"0\",\"mainsub\":\"2\",\"shortrate\":\"100.0000\",\"kindcode\":\"002\",\"unit\":\"\",\"adjustrate\":\"1.09\",\"modename\":\"\",\"itemcode\":\"0001\",\"deductible\":\"100.00\",\"guestamount\":\"0.00\",\"itemkindno\":\"2\",\"rationtype\":\"\",\"taxfee\":\"1.55\",\"itemdetailname\":\"赔偿责任\",\"unitamount\":\"0.00\",\"driveramount\":\"0.00\"},{\"deductiblerate\":\"0.00\",\"exchangeratemain\":\"1.0\",\"calculateflag\":\"Y\",\"flag\":\" 1\",\"modecode\":\"\",\"familyname\":\"安柏禄\",\"notaxpremium\":\"24.73\",\"discount\":\"0.0\",\"familyno\":\"2\",\"shortrateflagname\":\"月比例\",\"thirdpersonamount\":\"0.00\",\"premium\":\"26.21\",\"shortrateflag\":\"1\",\"addressno\":\"0\",\"rate\":\"1.60\",\"currency\":\"CNY\",\"model\":\"120102\",\"value\":\"0.00\",\"basepremium\":\"0.00\",\"itemcodename\":\"赔偿责任\",\"amount\":\"15000.00\",\"taxrate\":\"6.00\",\"quantity\":\"0\",\"currencyname\":\"人民币\",\"kindname\":\"附加意外伤害医疗保险（A款）\",\"itemno\":\"0\",\"mainsub\":\"2\",\"shortrate\":\"100.0000\",\"kindcode\":\"003\",\"unit\":\"\",\"adjustrate\":\"1.09\",\"modename\":\"\",\"itemcode\":\"0001\",\"deductible\":\"100.00\",\"guestamount\":\"0.00\",\"itemkindno\":\"3\",\"rationtype\":\"\",\"taxfee\":\"1.48\",\"itemdetailname\":\"赔偿责任\",\"unitamount\":\"0.00\",\"driveramount\":\"0.00\"},{\"deductiblerate\":\"0.00\",\"exchangeratemain\":\"1.0\",\"calculateflag\":\"Y\",\"flag\":\" 1\",\"modecode\":\"\",\"familyname\":\"安柏禄\",\"notaxpremium\":\"23.11\",\"discount\":\"0.0\",\"familyno\":\"2\",\"shortrateflagname\":\"月比例\",\"thirdpersonamount\":\"0.00\",\"premium\":\"24.50\",\"shortrateflag\":\"1\",\"addressno\":\"0\",\"rate\":\"0.50\",\"currency\":\"CNY\",\"model\":\"120102\",\"value\":\"0.00\",\"basepremium\":\"0.00\",\"itemcodename\":\"赔偿责任\",\"amount\":\"70000.00\",\"taxrate\":\"6.00\",\"quantity\":\"0\",\"currencyname\":\"人民币\",\"kindname\":\"附加疾病身故保险\",\"itemno\":\"0\",\"mainsub\":\"2\",\"shortrate\":\"100.0000\",\"kindcode\":\"005\",\"unit\":\"\",\"adjustrate\":\"0.70\",\"modename\":\"\",\"itemcode\":\"0001\",\"deductible\":\"0.00\",\"guestamount\":\"0.00\",\"itemkindno\":\"5\",\"rationtype\":\"\",\"taxfee\":\"1.39\",\"itemdetailname\":\"赔偿责任\",\"unitamount\":\"0.00\",\"driveramount\":\"0.00\"}],\"sumpremium\":\"149.80\",\"appliaddress\":\"黑龙江农业经济职业学院\",\"language\":\"C\",\"seasoncname\":\"\",\"makecomname\":\"阳光农业相互保险公司哈尔滨分公司财险业务部\",\"coinsuranceflag\":\"0\",\"operatorcode\":\"2301030004\",\"appliname\":\"安柏禄\",\"arguesolution\":\"2\",\"taxrate\":\"0.0000\",\"autotransrenewflag\":\"1\",\"judicalscope\":\"中华人民共和国管辖(港澳台除外)\",\"prptitemship\":{},\"tcoinsDetailOutList\":[],\"businessnature\":\"0\",\"transfertaxexp\":\"0.00\",\"reinsurancepointsinformationoutList\":[],\"prpitemcarList\":[],\"agentname\":\"\",\"autotransrenewflagname\":\"现金\",\"approvercode\":\"2301030004\",\"exchangecu\":\"1.0\",\"region\":\"01\",\"addressmessageList\":[],\"statunitname\":\"\",\"languagename\":\"中文\",\"allinsflag\":\"2\",\"prptengageList\":[{\"serialnname\":\"T\",\"engageserialno\":\"1\",\"clauses\":\"非车险业务见费出单提示\",\"clausescontext\":\"投保人应当领取或签署投保单后在投保单记录的保险起保日期前向保险公司\\n全额缴付保费，否则投保无效\\n\",\"clausecode\":\"T0060\"},{\"serialnname\":\"T\",\"engageserialno\":\"2\",\"clauses\":\"哈尔滨仲裁委员会合同争议解决办法\",\"clausescontext\":\"凡本合同引起的或与本合同有关的任何争议，\\n任何一方均有权将争议提交哈尔滨仲裁委员会进行仲裁。\\n\",\"clausecode\":\"T0093\"}],\"coinsSuranceAgreement\":\"\",\"prpmaininsuredname\":\"安柏禄\",\"disrate\":\"0.0\",\"coinslinkflagname\":\"无联保\",\"handler1code\":\"2301030004\",\"businesstype1\":\"00\",\"agentcode\":\"\",\"proposalno\":\"23010300TECK2022000030\",\"riskname\":\"学生、幼儿意外伤害保险（A款）\",\"prptlimitlist\":[],\"itemKindMainNum\":\"0\",\"policysort\":\"2\",\"handlername\":\"孙媛媛\",\"agricultclassname\":\"\",\"businessnaturename\":\"柜台销售\",\"oldpolicyno\":\"\",\"policytypename\":\"协议有分户\",\"arguesolutionname\":\"仲裁\",\"suminsured\":\"0\",\"biztype\":\"PROPOSAL\",\"dutyratioexp\":\"0.0000\",\"disratesxf\":\"0.0000\",\"premiumdisrate\":\"0.00\",\"inusercode\":\"2301030004\",\"addressnum\":\"0\",\"prptplanList\":[{\"plandate\":\"2022-05-07\",\"payno\":\"1\",\"planfee\":\"149.80\",\"payreason\":\"R10\",\"prpplancurrency\":\"CNY\",\"currency\":\"人民币\",\"delinquentfee\":\"149.80\"}]},\"prpTmainLiabDtoList\":[]}}";

        JSONObject jsonObject = JSONObject.parseObject(s);
        String makeCom = jsonObject.getJSONObject("undwrtRequestDto").getJSONObject("prpTmainDto").get("makecom").toString();
        System.out.println(makeCom);
//
//        String orderNo = "D220120ECK000005";
//
//        System.out.println(orderNo.substring(7,10));

//        String url = "https://wechat.samic.com.cn/nocar/insure-writeinfo";
//
////curl "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxfb8668b88c06d733&secret=0915f9bb825332810dbabc2a9dc04c54"
//        //1、获取AccessToken
////        String accessToken = WxUtils.getAccessToken();
//        String accessToken = "54_nA0E2okJfKVvwIJfv5FiObKSVSxBUwiOXeo5h-aCe2x-bk5j6FY2MnNwVM6IvFLI3MQfZ3lBktirAyQq0fB7hIY05jMRk4OUdaUfZE9mHVhI0Dbv1nV3abONDdWp3vNC7Ttt6lqRPW0eX9SiUHLiAEADQU";
//
//        //2、获取Ticket
////        String jsapi_ticket = "sM4AOVdWfPE4DxkXGEs8VGiNW-0sTfb6MQJ1UtVTcuEQIGGGYkA57zVTjj7rvoPhXiJO-soED07pU0uhrFHQSw";
//        String jsapi_ticket = WxUtils.getTicket(accessToken);
//
//        //3、时间戳和随机字符串
//        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
//        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳
//
//        //5、将参数排序并拼接字符串
//        String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
//
//        //6、将字符串进行sha1加密
//        String signature =WxUtils.SHA1(str);
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            // 获取微信signature
//            map.put("appId", "wxfb8668b88c06d733");
//            map.put("jsapi_ticket", jsapi_ticket);
//            map.put("timestamp", timestamp);
//            map.put("nonceStr", noncestr);
//            map.put("signature", signature);
//            map.put("url", url);
//        } catch (Exception e) {
//            //this.logger.error(e.getMessage());
//        }
//
//        System.out.println(GsonUtil.ModuleTojosn(map));
//      https://wechat.samic.com.cn/nocar/homepage%3FproductCode%3DEAK01%26code%3D091k34Ga1A0tHC0kyDJa1PVWRMOk34GN%26state%3DSTATE
//        return map;
//        232623195108114523
//        try {
//            int  age = getAge(parse("1952-08-02"));           //由出生日期获得年龄***
//            System.out.println("age:"+age);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        int age = 71;
//        if(age > 80 || age < 71){
//            System.out.println("ddddd");
//        } else {
//            System.out.println("aaaaa");
//        }

//        if ("2330101196004060070".contains("230101")) {
//            System.out.println("包含");
//        } else{
//            System.out.println("不包含");
//        }

//        ImagesDto imagesDto = fromXML(data,ImagesDto.class);
//
//        List<String> list = new CopyOnWriteArrayList<>();
//
//        imagesDto.getPAGES().getPAGE().forEach(
//                item -> {
//                    String s = item.getPAGEURL().replace("http://10.0.105.245:7003/SunTRM", "https://wechat.samic.com.cn/SunTRM");
//                    list.add(s);
//                }
//        );
//
//        List<Map<String, Object>> mapList = new CopyOnWriteArrayList<>();
//
//        imagesDto.getPAGES().getPAGE().forEach(
//                item -> {
//                    Map<String, Object> map = new HashMap<>();
//                    String url = item.getPAGEURL().replace("http://10.0.105.245:7003/SunTRM", "https://wechat.samic.com.cn/SunTRM");
//                    map.put("url",url);
//                    String pageId = item.getPAGEID();
//                    for(ImagesDto.SYDDTO.DocDTO.PageInfoDTO.PAGEDTO pagedto : imagesDto.getSYD().getDoc().getPageInfo().getPAGE()){
//                        if(pagedto.getPAGEID().equals(pageId)){
//                            String[] type = pagedto.getPAGEURL().split("\\.");
//                            map.put("type",type[type.length-1]);
//                        }
//                    }
//                    mapList.add(map);
//                }
//        );
//
//        System.out.println(123);

    }

}
