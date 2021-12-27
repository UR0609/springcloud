package com.ljryh.common.utils;

import com.ljryh.common.entity.ImagesDto;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class XmlUtils {

    private final static String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";

    public final static String data2 ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<root><RESPONSE_CODE>200</RESPONSE_CODE><RESPONSE_MSG>查询成功，记录不存在</RESPONSE_MSG></root>";
    public final static String data =
            "<root>\n" +
            "  <RESPONSE_CODE>200</RESPONSE_CODE>\n" +
            "  <RESPONSE_MSG>请求SYD成功</RESPONSE_MSG>\n" +
            "  <PAGES>\n" +
            "    <PAGE PAGEID=\"05074bd877b74bdc90e0b9fd1634c266\" PAGE_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmdLOUhSzSu6zSdta_s0zJN2umP6GSN6E8dtg_NF4UBUaUhFgSgSqXhziIgU-VhAxUPzBXifrDCOrIiMRpuAqKNI4SBMkAAHOAAIySuaTXN2zAP_y8PaTMuZOmBIBXeD1\" THUM_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmdLOUhSzSu6zSdta_s0zJN2umP6GSN6E8dtg_NF4UBUaUhFgSgSqXhziJg-rU1UgpPEfcuAqSvIzXT27mQUOVuGfVBtESvHzStHO6eZO6ByySsI12AIAdhGHSgGkX6Zz8PaBX2==\" FILE_NO=\"05074bd877b74bdc90e0b9fd1634c266\" PAGE_VER=\"3\"/>\n" +
            "    <PAGE PAGEID=\"17f893684b214e9bba4526c64decb7d2\" PAGE_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmdLvSNAa_NUfS4tgUgHOJNMgmsXGSBmwm1teSsaa8hmtmPFgUNSqpiyfU1UgpPEfcuAqSvIzXT27mQUOVuGfVBtOSeaOdT5126DHdhmrDTIOpvHzMvMk8AITXNfdmCfBD0==\" THUM_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmdLvSNAa_NUfS4tgUgHOJNMgmsXGSBmwm1teSsaa8hmtmPFgUNSqpiyfU1z9XhXgUgfnUA7fVg_1YcyOKN0gDh7lUPw786FBMsMkXNME6iIBdFAOMOFOXBfOMuIOpO51XNfBSs2=\" FILE_NO=\"17f893684b214e9bba4526c64decb7d2\" PAGE_VER=\"3\"/>\n" +
            "    <PAGE PAGEID=\"1b9da404e91840b49a3b52a9deed091c\" PAGE_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmdLr8Nm1UC0r_QOzSs5EJN2u_s5GSN2umdOtSgFf_u5E8CesUg5qXhziIgU-VhAxUPzBXifrDCOrIiMRpuAqK6DOXP_46g_dXiMEXBIT8cMTdCeOpOaHd55r_5DdXNI4\" THUM_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmdLr8Nm1UC0r_QOzSs5EJN2u_s5GSN2umdOtSgFf_u5E8CesUg5qXhziJg-rU1UgpPEfcuAqSvIzXT27mQUOVuGfVBO1d5XOmhGAXcFAmFAy_hZOMvF1Sg_TANyBpuGH_Cy1d0==\" FILE_NO=\"1b9da404e91840b49a3b52a9deed091c\" PAGE_VER=\"3\"/>\n" +
            "    <PAGE PAGEID=\"3ee763bbff01489689b706c529c8cb7e\" PAGE_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmdLrmBIgmP_gmdOzUNAgJN2u_hmGSB0rSdt4mPMe_sXtUCeOmsZqpiyfU1UgpPEfcuAqSvIzXT27mQUOVuGfVBOr2NyEdT2zSu_BmBfOmTMOANylA6aypvH18AIkAPG1Sr==\" THUM_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmdLrmBIgmP_gmdOzUNAgJN2u_hmGSB0rSdt4mPMe_sXtUCeOmsZqpiyfU1z9XhXgUgfnUA7fVg_1YcyOKN0gDh7lUPw7mTIdXBIOpCy1Si2186ak2PI1mCHzX6aE2PadSsMO8AH=\" FILE_NO=\"3ee763bbff01489689b706c529c8cb7e\" PAGE_VER=\"3\"/>\n" +
            "    <PAGE PAGEID=\"a88a04de4da2434da9996dd8c9861a77\" PAGE_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmdLs8CSsSBX1UQOu_sSEJNMBmhHGSP6ES4OE_sFg8PHu8N_4SsmqXhziIgU-VhAxUPzBXifrDCOrIiMRpuAqKPa1SgIl2c5OMsy4pv5OXeDA_hGl_C2OSsI486DTDFIl\" THUM_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmdLs8CSsSBX1UQOu_sSEJNMBmhHGSP6ES4OE_sFg8PHu8N_4SsmqXhziJg-rU1UgpPEfcuAqSvIzXT27mQUOVuGfVBtTdFIHXiFEMsMT26D4_CfBMuIkSvMEX6aT2NyOXPGA6Z==\" FILE_NO=\"a88a04de4da2434da9996dd8c9861a77\" PAGE_VER=\"3\"/>\n" +
            "    <PAGE PAGEID=\"0654f0b9682f4afe953cdecc5b998a72\" PAGE_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmd7gUN0O_PUe_dtemB_aJN2w8C2G8CStmdt4mBDfmC5smB6EmB6qpiyiIgU-VhAxUPzBXifrDCOrIiMRpuAqKPG48Pnr_5DTd5Z1SuaySg_ASeaymeaHmf6z8cFlSuZr\" THUM_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmd7gUN0O_PUe_dtemB_aJN2w8C2G8CStmdt4mBDfmC5smB6EmB6qpiyiJg-rU1UgpPEfcuAqSvIzXT27mQUOVuGfVBt1XNI1Suay8PGOSBfBmhH1MsMODTFASvIydha1d5adm0==\" FILE_NO=\"0654f0b9682f4afe953cdecc5b998a72\" PAGE_VER=\"2\"/>\n" +
            "    <PAGE PAGEID=\"36e6765261364582b91a33c71620e93a\" PAGE_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmdL18hmz8Cetm1tf_BevJNMf8CHG8NXsUdtf8NFg_N5tUC0t8C0qpiyiIgU-VhAxUPzBXifrDCOrIiMRpuAqKcFyXPnzdTHz_hGBpunr6fA46gmO2NermeXO_CfOmCyB\" THUM_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmdL18hmz8Cetm1tf_BevJNMf8CHG8NXsUdtf8NFg_N5tUC0t8C0qpiyiJg-rU1UgpPEfcuAqSvIzXT27mQUOVuGfVBtlSgHOMvFBMuGA2NIk8PIEphI4pCfOSOZrAcFlSsfHpr==\" FILE_NO=\"36e6765261364582b91a33c71620e93a\" PAGE_VER=\"2\"/>\n" +
            "    <PAGE PAGEID=\"94ce143c2fde4cabb7c677c2bb15ddb0\" PAGE_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmd7BSNSO_P21m4OO8NI4JN21_s6GSN0t_4tgUN0v_g6wmuIgmseqpiyiIgU-VhAxUPzBXifrDCOrIiMRpuAqKAAkmCezXgmzDFIAphGymBMTDFHzDCfyMsfHp5aEmFI1\" THUM_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmd7BSNSO_P21m4OO8NI4JN21_s6GSN0t_4tgUN0v_g6wmuIgmseqpiyiJg-rU1UgpPEfcuAqSvIzXT27mQUOVuGfVBO1XB01m5ZOm5alMOXz6iFBd5ZOpsIlmg_HXgGyXBMTSZ==\" FILE_NO=\"94ce143c2fde4cabb7c677c2bb15ddb0\" PAGE_VER=\"2\"/>\n" +
            "    <PAGE PAGEID=\"9e2b70493fd1449a80cf063aba1134dc\" PAGE_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmdLwSBagmPmt_1OvUNFeJNMf_PSG8hIgSdte8PmvUNHzSNfe_C5qpiyiIgU-VhAxUPzBXifrDCOrIiMRpuAqKcIkSea4_FHrmfAO_hGOpvHrANI16gGd_hHODFAT8Ner\" THUM_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmdLwSBagmPmt_1OvUNFeJNMf_PSG8hIgSdte8PmvUNHzSNfe_C5qpiyiJg-rU1UgpPEfcuAqSvIzXT27mQUOVuGfVBtdSeaHXBIdXBer_TMkMsHr8AAAmi5zDC2rpvHO8Nyl22==\" FILE_NO=\"9e2b70493fd1449a80cf063aba1134dc\" PAGE_VER=\"2\"/>\n" +
            "    <PAGE PAGEID=\"f19fd7aecaf84ab4b6ac6ce6c84a8b34\" PAGE_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmdLvmhSzmNFem1O1mu2wJNMamPmG8h5wm1O1_P_BUg5uUBF4_PHqXhMgIgU-VhAxUPzBXifrDCOrIiMRpuAqKPaT8AIlXBfTd5a4pTM4Xgn1psMyMt6O_5DymBIA_FAd\" THUM_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCHEmNHE_1UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmB5RmCZRmCHRmBHR_seR_BSs8PF48NmtmCDfmsUgSNff_CmEmu5wUBf48PHt8NAxmdLgUgfnUA7fVg_1YcyOKN0gDh7lUPw7miFHpvH1Ssyl6iMTDTFHpvFTXiFAD5F1pTMBSBe1Xe5=\" FILE_NO=\"f19fd7aecaf84ab4b6ac6ce6c84a8b34\" PAGE_VER=\"1\"/>\n" +
            "  </PAGES>\n" +
            "  <SYD>\n" +
            "    <doc version=\"1.2\">\n" +
            "      <DocInfo>\n" +
            "        <BATCH_ID>6639ab93507e36fa9e4313a8f9b9b595</BATCH_ID>\n" +
            "        <BUSI_NUM>23010500TDDA2021A00394</BUSI_NUM>\n" +
            "        <BIZ_ORG>23010500</BIZ_ORG>\n" +
            "        <APP_CODE>CXCB</APP_CODE>\n" +
            "        <APP_NAME>车险承保</APP_NAME>\n" +
            "        <BATCH_VER>3</BATCH_VER>\n" +
            "        <INTER_VER>3</INTER_VER>\n" +
            "        <STATUS/>\n" +
            "        <CREATE_USER>2301350012</CREATE_USER>\n" +
            "        <CREATE_DATE>2021-08-02 11:29:57.190</CREATE_DATE>\n" +
            "        <MODIFY_USER>2301350012</MODIFY_USER>\n" +
            "        <MODIFY_DATE>2021-12-16 17:02:44</MODIFY_DATE>\n" +
            "        <DOC_EXT>\n" +
            "          <EXT_ATTR ID=\"BUSI_NO\" NAME=\"投保单号\" IS_SHOW=\"1\" IS_KEY=\"1\" IS_NULL=\"0\" INPUT_TYPE=\"1\">23010500TDDA2021A00394</EXT_ATTR>\n" +
            "        </DOC_EXT>\n" +
            "      </DocInfo>\n" +
            "      <PageInfo>\n" +
            "        <PAGE PAGEID=\"05074bd877b74bdc90e0b9fd1634c266\">\n" +
            "          <CREATE_USER>2301350012</CREATE_USER>\n" +
            "          <CREATE_USERNAME>2301350012</CREATE_USERNAME>\n" +
            "          <CREATE_TIME>2021-12-16 17:09:32</CREATE_TIME>\n" +
            "          <MODIFY_USER>2301350012</MODIFY_USER>\n" +
            "          <MODIFY_TIME>2021-12-16 17:09:32</MODIFY_TIME>\n" +
            "          <PAGE_URL>4df9ce9a-a709-461e-a519-f51bf6adafbf.png</PAGE_URL>\n" +
            "          <THUM_URL>4df9ce9a-a709-461e-a519-f51bf6adafbf.png.jpg</THUM_URL>\n" +
            "          <IS_LOCAL>1</IS_LOCAL>\n" +
            "          <PAGE_VER>3</PAGE_VER>\n" +
            "          <PAGE_DESC/>\n" +
            "          <UPLOAD_ORG>阳光农业相互保险公司哈尔滨分公司</UPLOAD_ORG>\n" +
            "          <PAGE_CRC>41b7c5f84c8aedeec4493d12d63e6c08</PAGE_CRC>\n" +
            "          <PAGE_SIZE>236370</PAGE_SIZE>\n" +
            "          <PAGE_FORMAT>image/png</PAGE_FORMAT>\n" +
            "          <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "          <ORIGINAL_NAME>44.png</ORIGINAL_NAME>\n" +
            "          <PAGE_EXT/>\n" +
            "          <PAGE_DESCS/>\n" +
            "        </PAGE>\n" +
            "        <PAGE PAGEID=\"0654f0b9682f4afe953cdecc5b998a72\">\n" +
            "          <CREATE_USER>2301350012</CREATE_USER>\n" +
            "          <CREATE_USERNAME>2301350012</CREATE_USERNAME>\n" +
            "          <CREATE_TIME>2021-08-09 09:38:25</CREATE_TIME>\n" +
            "          <MODIFY_USER>2301350012</MODIFY_USER>\n" +
            "          <MODIFY_TIME>2021-08-09 09:38:25</MODIFY_TIME>\n" +
            "          <PAGE_URL>fe045fd5-d23a-4884-8651-b27e01325125.jpg</PAGE_URL>\n" +
            "          <THUM_URL>fe045fd5-d23a-4884-8651-b27e01325125.jpg.jpg</THUM_URL>\n" +
            "          <IS_LOCAL>1</IS_LOCAL>\n" +
            "          <PAGE_VER>2</PAGE_VER>\n" +
            "          <PAGE_DESC/>\n" +
            "          <UPLOAD_ORG>阳光农业相互保险公司哈尔滨分公司</UPLOAD_ORG>\n" +
            "          <PAGE_CRC>a3648c2e57828c7246d9f3d139e82460</PAGE_CRC>\n" +
            "          <PAGE_SIZE>68255</PAGE_SIZE>\n" +
            "          <PAGE_FORMAT>image/jpeg</PAGE_FORMAT>\n" +
            "          <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "          <ORIGINAL_NAME>2Screenshot_20210806_092803.jpg</ORIGINAL_NAME>\n" +
            "          <PAGE_EXT/>\n" +
            "          <PAGE_DESCS/>\n" +
            "        </PAGE>\n" +
            "        <PAGE PAGEID=\"17f893684b214e9bba4526c64decb7d2\">\n" +
            "          <CREATE_USER>2301350012</CREATE_USER>\n" +
            "          <CREATE_USERNAME>2301350012</CREATE_USERNAME>\n" +
            "          <CREATE_TIME>2021-12-16 17:09:32</CREATE_TIME>\n" +
            "          <MODIFY_USER>2301350012</MODIFY_USER>\n" +
            "          <MODIFY_TIME>2021-12-16 17:09:32</MODIFY_TIME>\n" +
            "          <PAGE_URL>7a5a56eb-ffb4-4f37-b383-dc8a8c51afe6.jpeg</PAGE_URL>\n" +
            "          <THUM_URL>7a5a56eb-ffb4-4f37-b383-dc8a8c51afe6.jpeg.jpg</THUM_URL>\n" +
            "          <IS_LOCAL>1</IS_LOCAL>\n" +
            "          <PAGE_VER>3</PAGE_VER>\n" +
            "          <PAGE_DESC/>\n" +
            "          <UPLOAD_ORG>阳光农业相互保险公司哈尔滨分公司</UPLOAD_ORG>\n" +
            "          <PAGE_CRC>c10600a3125edaefd5b1e1efee2613a7</PAGE_CRC>\n" +
            "          <PAGE_SIZE>70862</PAGE_SIZE>\n" +
            "          <PAGE_FORMAT>image/jpeg</PAGE_FORMAT>\n" +
            "          <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "          <ORIGINAL_NAME>33.jpeg</ORIGINAL_NAME>\n" +
            "          <PAGE_EXT/>\n" +
            "          <PAGE_DESCS/>\n" +
            "        </PAGE>\n" +
            "        <PAGE PAGEID=\"1b9da404e91840b49a3b52a9deed091c\">\n" +
            "          <CREATE_USER>2301350012</CREATE_USER>\n" +
            "          <CREATE_USERNAME>2301350012</CREATE_USERNAME>\n" +
            "          <CREATE_TIME>2021-12-16 17:09:32</CREATE_TIME>\n" +
            "          <MODIFY_USER>2301350012</MODIFY_USER>\n" +
            "          <MODIFY_TIME>2021-12-16 17:09:32</MODIFY_TIME>\n" +
            "          <PAGE_URL>0932d004-9c11-4671-a461-5bae7a1893fa.png</PAGE_URL>\n" +
            "          <THUM_URL>0932d004-9c11-4671-a461-5bae7a1893fa.png.jpg</THUM_URL>\n" +
            "          <IS_LOCAL>1</IS_LOCAL>\n" +
            "          <PAGE_VER>3</PAGE_VER>\n" +
            "          <PAGE_DESC/>\n" +
            "          <UPLOAD_ORG>阳光农业相互保险公司哈尔滨分公司</UPLOAD_ORG>\n" +
            "          <PAGE_CRC>e2f7ba3704737aa2f532f09b2ba973ef</PAGE_CRC>\n" +
            "          <PAGE_SIZE>24434</PAGE_SIZE>\n" +
            "          <PAGE_FORMAT>image/png</PAGE_FORMAT>\n" +
            "          <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "          <ORIGINAL_NAME>5sign.png</ORIGINAL_NAME>\n" +
            "          <PAGE_EXT/>\n" +
            "          <PAGE_DESCS/>\n" +
            "        </PAGE>\n" +
            "        <PAGE PAGEID=\"36e6765261364582b91a33c71620e93a\">\n" +
            "          <CREATE_USER>2301350012</CREATE_USER>\n" +
            "          <CREATE_USERNAME>2301350012</CREATE_USERNAME>\n" +
            "          <CREATE_TIME>2021-08-09 09:38:25</CREATE_TIME>\n" +
            "          <MODIFY_USER>2301350012</MODIFY_USER>\n" +
            "          <MODIFY_TIME>2021-08-09 09:38:25</MODIFY_TIME>\n" +
            "          <PAGE_URL>28c98953-e697-4e82-973e-e91f515d0580.jpg</PAGE_URL>\n" +
            "          <THUM_URL>28c98953-e697-4e82-973e-e91f515d0580.jpg.jpg</THUM_URL>\n" +
            "          <IS_LOCAL>1</IS_LOCAL>\n" +
            "          <PAGE_VER>2</PAGE_VER>\n" +
            "          <PAGE_DESC/>\n" +
            "          <UPLOAD_ORG>阳光农业相互保险公司哈尔滨分公司</UPLOAD_ORG>\n" +
            "          <PAGE_CRC>25b9e06885901653155cd0e4f99c2b26</PAGE_CRC>\n" +
            "          <PAGE_SIZE>95400</PAGE_SIZE>\n" +
            "          <PAGE_FORMAT>image/jpeg</PAGE_FORMAT>\n" +
            "          <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "          <ORIGINAL_NAME>4Screenshot_20210806_092703.jpg</ORIGINAL_NAME>\n" +
            "          <PAGE_EXT/>\n" +
            "          <PAGE_DESCS/>\n" +
            "        </PAGE>\n" +
            "        <PAGE PAGEID=\"3ee763bbff01489689b706c529c8cb7e\">\n" +
            "          <CREATE_USER>2301350012</CREATE_USER>\n" +
            "          <CREATE_USERNAME>2301350012</CREATE_USERNAME>\n" +
            "          <CREATE_TIME>2021-12-16 17:09:32</CREATE_TIME>\n" +
            "          <MODIFY_USER>2301350012</MODIFY_USER>\n" +
            "          <MODIFY_TIME>2021-12-16 17:09:32</MODIFY_TIME>\n" +
            "          <PAGE_URL>022f1cf1-9e5f-464c-b00a-b1dd775d9438.jpeg</PAGE_URL>\n" +
            "          <THUM_URL>022f1cf1-9e5f-464c-b00a-b1dd775d9438.jpeg.jpg</THUM_URL>\n" +
            "          <IS_LOCAL>1</IS_LOCAL>\n" +
            "          <PAGE_VER>3</PAGE_VER>\n" +
            "          <PAGE_DESC/>\n" +
            "          <UPLOAD_ORG>阳光农业相互保险公司哈尔滨分公司</UPLOAD_ORG>\n" +
            "          <PAGE_CRC>6b24159441d32a8e48be6f166779605b</PAGE_CRC>\n" +
            "          <PAGE_SIZE>35996</PAGE_SIZE>\n" +
            "          <PAGE_FORMAT>image/jpeg</PAGE_FORMAT>\n" +
            "          <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "          <ORIGINAL_NAME>22.jpeg</ORIGINAL_NAME>\n" +
            "          <PAGE_EXT/>\n" +
            "          <PAGE_DESCS/>\n" +
            "        </PAGE>\n" +
            "        <PAGE PAGEID=\"94ce143c2fde4cabb7c677c2bb15ddb0\">\n" +
            "          <CREATE_USER>2301350012</CREATE_USER>\n" +
            "          <CREATE_USERNAME>2301350012</CREATE_USERNAME>\n" +
            "          <CREATE_TIME>2021-08-09 09:38:25</CREATE_TIME>\n" +
            "          <MODIFY_USER>2301350012</MODIFY_USER>\n" +
            "          <MODIFY_TIME>2021-08-09 09:38:25</MODIFY_TIME>\n" +
            "          <PAGE_URL>ca645d22-492b-4275-a056-fe076e83bf39.jpg</PAGE_URL>\n" +
            "          <THUM_URL>ca645d22-492b-4275-a056-fe076e83bf39.jpg.jpg</THUM_URL>\n" +
            "          <IS_LOCAL>1</IS_LOCAL>\n" +
            "          <PAGE_VER>2</PAGE_VER>\n" +
            "          <PAGE_DESC/>\n" +
            "          <UPLOAD_ORG>阳光农业相互保险公司哈尔滨分公司</UPLOAD_ORG>\n" +
            "          <PAGE_CRC>25b9e06885901653155cd0e4f99c2b26</PAGE_CRC>\n" +
            "          <PAGE_SIZE>95400</PAGE_SIZE>\n" +
            "          <PAGE_FORMAT>image/jpeg</PAGE_FORMAT>\n" +
            "          <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "          <ORIGINAL_NAME>3Screenshot_20210806_092703.jpg</ORIGINAL_NAME>\n" +
            "          <PAGE_EXT/>\n" +
            "          <PAGE_DESCS/>\n" +
            "        </PAGE>\n" +
            "        <PAGE PAGEID=\"9e2b70493fd1449a80cf063aba1134dc\">\n" +
            "          <CREATE_USER>2301350012</CREATE_USER>\n" +
            "          <CREATE_USERNAME>2301350012</CREATE_USERNAME>\n" +
            "          <CREATE_TIME>2021-08-09 09:38:25</CREATE_TIME>\n" +
            "          <MODIFY_USER>2301350012</MODIFY_USER>\n" +
            "          <MODIFY_TIME>2021-08-09 09:38:25</MODIFY_TIME>\n" +
            "          <PAGE_URL>8b8f1c57-7e1d-4e5f-8bfa-d9c7e29a9d41.jpg</PAGE_URL>\n" +
            "          <THUM_URL>8b8f1c57-7e1d-4e5f-8bfa-d9c7e29a9d41.jpg.jpg</THUM_URL>\n" +
            "          <IS_LOCAL>1</IS_LOCAL>\n" +
            "          <PAGE_VER>2</PAGE_VER>\n" +
            "          <PAGE_DESC/>\n" +
            "          <UPLOAD_ORG>阳光农业相互保险公司哈尔滨分公司</UPLOAD_ORG>\n" +
            "          <PAGE_CRC>25b9e06885901653155cd0e4f99c2b26</PAGE_CRC>\n" +
            "          <PAGE_SIZE>95400</PAGE_SIZE>\n" +
            "          <PAGE_FORMAT>image/jpeg</PAGE_FORMAT>\n" +
            "          <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "          <ORIGINAL_NAME>1Screenshot_20210806_092703.jpg</ORIGINAL_NAME>\n" +
            "          <PAGE_EXT/>\n" +
            "          <PAGE_DESCS/>\n" +
            "        </PAGE>\n" +
            "        <PAGE PAGEID=\"a88a04de4da2434da9996dd8c9861a77\">\n" +
            "          <CREATE_USER>2301350012</CREATE_USER>\n" +
            "          <CREATE_USERNAME>2301350012</CREATE_USERNAME>\n" +
            "          <CREATE_TIME>2021-12-16 17:09:32</CREATE_TIME>\n" +
            "          <MODIFY_USER>2301350012</MODIFY_USER>\n" +
            "          <MODIFY_TIME>2021-12-16 17:09:32</MODIFY_TIME>\n" +
            "          <PAGE_URL>3863b72d-6761-4c0b-ae1b-171f9b693bc3.png</PAGE_URL>\n" +
            "          <THUM_URL>3863b72d-6761-4c0b-ae1b-171f9b693bc3.png.jpg</THUM_URL>\n" +
            "          <IS_LOCAL>1</IS_LOCAL>\n" +
            "          <PAGE_VER>3</PAGE_VER>\n" +
            "          <PAGE_DESC/>\n" +
            "          <UPLOAD_ORG>阳光农业相互保险公司哈尔滨分公司</UPLOAD_ORG>\n" +
            "          <PAGE_CRC>adc4ebf18e3ffa96860995bfcfb50fa6</PAGE_CRC>\n" +
            "          <PAGE_SIZE>876611</PAGE_SIZE>\n" +
            "          <PAGE_FORMAT>image/png</PAGE_FORMAT>\n" +
            "          <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "          <ORIGINAL_NAME>11.png</ORIGINAL_NAME>\n" +
            "          <PAGE_EXT/>\n" +
            "          <PAGE_DESCS/>\n" +
            "        </PAGE>\n" +
            "        <PAGE PAGEID=\"f19fd7aecaf84ab4b6ac6ce6c84a8b34\">\n" +
            "          <CREATE_USER>2301350012</CREATE_USER>\n" +
            "          <CREATE_USERNAME>高杰</CREATE_USERNAME>\n" +
            "          <CREATE_TIME>2021-08-02 11:32:49</CREATE_TIME>\n" +
            "          <MODIFY_USER>2301350012</MODIFY_USER>\n" +
            "          <MODIFY_TIME>2021-08-02 11:32:49</MODIFY_TIME>\n" +
            "          <PAGE_URL>70f911d3-23d8-4a1c-8a83-25ccfa6f1b5b.pdf</PAGE_URL>\n" +
            "          <THUM_URL/>\n" +
            "          <IS_LOCAL>1</IS_LOCAL>\n" +
            "          <PAGE_VER>1</PAGE_VER>\n" +
            "          <PAGE_DESC/>\n" +
            "          <UPLOAD_ORG>阳光农业相互保险公司哈尔滨分公司农垦业务部</UPLOAD_ORG>\n" +
            "          <PAGE_CRC>9702056b0794c1792dfc61c864c3eee7</PAGE_CRC>\n" +
            "          <PAGE_SIZE>437702</PAGE_SIZE>\n" +
            "          <PAGE_FORMAT>application/pdf</PAGE_FORMAT>\n" +
            "          <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "          <ORIGINAL_NAME>23010500TDDA2021A00394202108021132480001.pdf</ORIGINAL_NAME>\n" +
            "          <PAGE_EXT/>\n" +
            "          <PAGE_DESCS/>\n" +
            "        </PAGE>\n" +
            "      </PageInfo>\n" +
            "      <VTREE APP_CODE=\"CXCB\" APP_NAME=\"车险承保\">\n" +
            "        <NODE ID=\"CCXCB001\" NAME=\"投保单\">\n" +
            "          <LEAF>f19fd7aecaf84ab4b6ac6ce6c84a8b34</LEAF>\n" +
            "          <LEAF>9e2b70493fd1449a80cf063aba1134dc</LEAF>\n" +
            "          <LEAF>0654f0b9682f4afe953cdecc5b998a72</LEAF>\n" +
            "          <LEAF>94ce143c2fde4cabb7c677c2bb15ddb0</LEAF>\n" +
            "          <LEAF>36e6765261364582b91a33c71620e93a</LEAF>\n" +
            "          <LEAF>a88a04de4da2434da9996dd8c9861a77</LEAF>\n" +
            "          <LEAF>3ee763bbff01489689b706c529c8cb7e</LEAF>\n" +
            "          <LEAF>17f893684b214e9bba4526c64decb7d2</LEAF>\n" +
            "          <LEAF>05074bd877b74bdc90e0b9fd1634c266</LEAF>\n" +
            "          <LEAF>1b9da404e91840b49a3b52a9deed091c</LEAF>\n" +
            "        </NODE>\n" +
            "      </VTREE>\n" +
            "    </doc>\n" +
            "  </SYD>\n" +
            "</root>\n";

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
        return new XStream(new XppDriver(new
                XmlFriendlyReplacer("_-", "_")));
    }

    public static void main(String[] args) {

        ImagesDto imagesDto = fromXML(data2,ImagesDto.class);

        List<String> list = new CopyOnWriteArrayList<>();

        imagesDto.getPAGES().getPAGE().forEach(
                item -> {
                    String s = item.getPAGEURL().replace("http://10.0.105.245:7003/SunTRM", "https://wechat.samic.com.cn/SunTRM");
                    list.add(s);
                }
        );

        System.out.println(123);

    }

}
