package com.ljryh.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.ljryh.common.entity.ImagesDto;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class XmlUtils {

    private final static String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";

    public final static String data2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<root>\n" +
            "  <RESPONSE_CODE>200</RESPONSE_CODE>\n" +
            "  <RESPONSE_MSG>请求SYD成功</RESPONSE_MSG>\n" +
            "  <PAGES>\n" +
            "    <PAGE PAGEID=\"6cdf0fd73c7d47c2888c9cabf82872fd\" PAGE_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCH1mCSE_4UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmBHRmCSRmNSRmNSR_B5R8PMamN0u8Cer_sMgmsIBmPI4SNM4SsDe_hF4_s5zUN_xmdLr_P6OSPHrmQOrmP2wJNM4msHG8NMaU4OrmsF4SgmtmsM4_NZqXhziIgU-VhAxUPzBXifrDCOrIiMRpuAqKPaTphGAMsfkdCIBpsyAAcIBDTH1XcM12cFBmeFOSsfl\" THUM_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCH1mCSE_4UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmBHRmCSRmNSRmNSR_B5R8PMamN0u8Cer_sMgmsIBmPI4SNM4SsDe_hF4_s5zUN_xmdLr_P6OSPHrmQOrmP2wJNM4msHG8NMaU4OrmsF4SgmtmsM4_NZqXhziJg-rU1UgpPEfcuAqSvIzXT27mQUOVuGfVBtEMsM4mgIdm5X1SiM4XgaEXcFdXg_Hd5DkSu_kpCeOm0==\" FILE_NO=\"6cdf0fd73c7d47c2888c9cabf82872fd\" PAGE_VER=\"1\"/>\n" +
            "    <PAGE PAGEID=\"c6ed9a5816df40e3ac926b1fd8a97e39\" PAGE_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCH1mCSE_4UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmBHRmCSRmNSRmNSR_B5R8PMamN0u8Cer_sMgmsIBmPI4SNM4SsDe_hF4_s5zUN_xmd7a_NyB_g5sSdOuSsDeJN21mBSG8PIa_4t4UBMa_gIBUg2w_BmqXhziIgU-VhAxUPzBXifrDCOrIiMRpuAqKNyA_hnzSsIB_FA42N2z86aypsMBXgaBXcIOANIlmh_H\" THUM_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCH1mCSE_4UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmBHRmCSRmNSRmNSR_B5R8PMamN0u8Cer_sMgmsIBmPI4SNM4SsDe_hF4_s5zUN_xmd7a_NyB_g5sSdOuSsDeJN21mBSG8PIa_4t4UBMa_gIBUg2w_BmqXhziJg-rU1UgpPEfcuAqSvIzXT27mQUOVuGfVBtkmgn12AA4puIHXcFlpunODCIl6fH18cM4phakSeFESr==\" FILE_NO=\"c6ed9a5816df40e3ac926b1fd8a97e39\" PAGE_VER=\"1\"/>\n" +
            "    <PAGE PAGEID=\"f71e726847df4285b3d17620d30409cb\" PAGE_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCH1mCSE_4UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmBHRmCSRmNSRmNSR_B5R8PMamN0u8Cer_sMgmsIBmPI4SNM4SsDe_hF4_s5zUN_xmd7g_New_BF4mQOOUB0EJNMamN6G8PFgS4OzSsXtmBXO8PmzmB6qpiyfU1UgpPEfcuAqSvIzXT27mQUOVuGfVBtHMu_1d5DESgGkXfIBmgalXc518cHz_hIBMsMOXcIESZ==\" THUM_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCH1mCSE_4UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmBHRmCSRmNSRmNSR_B5R8PMamN0u8Cer_sMgmsIBmPI4SNM4SsDe_hF4_s5zUN_xmd7g_New_BF4mQOOUB0EJNMamN6G8PFgS4OzSsXtmBXO8PmzmB6qpiyfU1z9XhXgUgfnUA7fVg_1YcyOKN0gDh7lUPw7p55z8Nez8NHrpsyyAPGd6ea4_5DB26FOSvMdSsIO8N2=\" FILE_NO=\"f71e726847df4285b3d17620d30409cb\" PAGE_VER=\"1\"/>\n" +
            "    <PAGE PAGEID=\"ffbee8a25d6248d6a3eba9ae511bb4da\" PAGE_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCH1mCSE_4UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmBHRmCSRmNSRmNSR_B5R8PMamN0u8Cer_sMgmsIBmPI4SNM4SsDe_hF4_s5zUN_xmd748N2EUgHs_Qte_gm1JN2v8h5G8NXOS1Or_N5z_sSwSB0rUC5qpiyfU1UgpPEfcuAqSvIzXT27mQUOVuGfVBtTd5Z1mgHOdTITXgITStIB8NIT2AIkD5XOmCy4pT2OD0==\" THUM_URL=\"http://10.0.105.245:7003/SunTRM/servlet/GetImage?UhFOUNO1mCH1mCSE_4UgpPEfcuzaVP67JufGSPDfX17CP5_QJsHrmBHRmCSRmNSRmNSR_B5R8PMamN0u8Cer_sMgmsIBmPI4SNM4SsDe_hF4_s5zUN_xmd748N2EUgHs_Qte_gm1JN2v8h5G8NXOS1Or_N5z_sSwSB0rUC5qpiyfU1z9XhXgUgfnUA7fVg_1YcyOKN0gDh7lUPw7_CMA2cMdmfATDCMkXPIT6eaO_ha1XfA16g_TSBMAphn=\" FILE_NO=\"ffbee8a25d6248d6a3eba9ae511bb4da\" PAGE_VER=\"1\"/>\n" +
            "  </PAGES>\n" +
            "  <SYD>\n" +
            "    <doc version=\"1.2\">\n" +
            "      <DocInfo>\n" +
            "        <BATCH_ID>9da10689074f32c1bba4bc7d4ab719e3</BATCH_ID>\n" +
            "        <BUSI_NUM>23321400TDEG2022A00110</BUSI_NUM>\n" +
            "        <BIZ_ORG>23321400</BIZ_ORG>\n" +
            "        <APP_CODE>CXCB</APP_CODE>\n" +
            "        <APP_NAME>车险承保</APP_NAME>\n" +
            "        <BATCH_VER>1</BATCH_VER>\n" +
            "        <INTER_VER>1</INTER_VER>\n" +
            "        <STATUS/>\n" +
            "        <CREATE_USER>2301350012</CREATE_USER>\n" +
            "        <CREATE_DATE>2022-06-16 15:01:42.173</CREATE_DATE>\n" +
            "        <MODIFY_USER/>\n" +
            "        <MODIFY_DATE/>\n" +
            "        <DOC_EXT>\n" +
            "          <EXT_ATTR ID=\"BUSI_NO\" NAME=\"投保单号\" IS_SHOW=\"1\" IS_KEY=\"1\" IS_NULL=\"0\" INPUT_TYPE=\"1\">23321400TDEG2022A00110</EXT_ATTR>\n" +
            "        </DOC_EXT>\n" +
            "      </DocInfo>\n" +
            "      <PageInfo>\n" +
            "        <PAGE PAGEID=\"6cdf0fd73c7d47c2888c9cabf82872fd\">\n" +
            "          <CREATE_USER>2301350012</CREATE_USER>\n" +
            "          <CREATE_USERNAME>高杰</CREATE_USERNAME>\n" +
            "          <CREATE_TIME>2022-06-16 15:02:55</CREATE_TIME>\n" +
            "          <MODIFY_USER>2301350012</MODIFY_USER>\n" +
            "          <MODIFY_TIME>2022-06-16 15:02:55</MODIFY_TIME>\n" +
            "          <PAGE_URL>05e4ab00-01d8-4b32-94af-031bbc534b58.png</PAGE_URL>\n" +
            "          <THUM_URL>05e4ab00-01d8-4b32-94af-031bbc534b58.png.jpg</THUM_URL>\n" +
            "          <IS_LOCAL>1</IS_LOCAL>\n" +
            "          <PAGE_VER>1</PAGE_VER>\n" +
            "          <PAGE_DESC/>\n" +
            "          <UPLOAD_ORG>阳光农业相互保险公司哈尔滨农垦支公司</UPLOAD_ORG>\n" +
            "          <PAGE_CRC>ea21770e7476f9a830320b99dc141952</PAGE_CRC>\n" +
            "          <PAGE_SIZE>824665</PAGE_SIZE>\n" +
            "          <PAGE_FORMAT>image/png</PAGE_FORMAT>\n" +
            "          <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "          <ORIGINAL_NAME>2WechatIMG3474.png</ORIGINAL_NAME>\n" +
            "          <PAGE_EXT/>\n" +
            "          <PAGE_DESCS/>\n" +
            "        </PAGE>\n" +
            "        <PAGE PAGEID=\"c6ed9a5816df40e3ac926b1fd8a97e39\">\n" +
            "          <CREATE_USER>2301350012</CREATE_USER>\n" +
            "          <CREATE_USERNAME>高杰</CREATE_USERNAME>\n" +
            "          <CREATE_TIME>2022-06-16 15:02:55</CREATE_TIME>\n" +
            "          <MODIFY_USER>2301350012</MODIFY_USER>\n" +
            "          <MODIFY_TIME>2022-06-16 15:02:55</MODIFY_TIME>\n" +
            "          <PAGE_URL>a50c6a3a-6c7d-4226-9ba6-bf4a6bcfd863.png</PAGE_URL>\n" +
            "          <THUM_URL>a50c6a3a-6c7d-4226-9ba6-bf4a6bcfd863.png.jpg</THUM_URL>\n" +
            "          <IS_LOCAL>1</IS_LOCAL>\n" +
            "          <PAGE_VER>1</PAGE_VER>\n" +
            "          <PAGE_DESC/>\n" +
            "          <UPLOAD_ORG>阳光农业相互保险公司哈尔滨农垦支公司</UPLOAD_ORG>\n" +
            "          <PAGE_CRC>ea21770e7476f9a830320b99dc141952</PAGE_CRC>\n" +
            "          <PAGE_SIZE>824665</PAGE_SIZE>\n" +
            "          <PAGE_FORMAT>image/png</PAGE_FORMAT>\n" +
            "          <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "          <ORIGINAL_NAME>4WechatIMG3474.png</ORIGINAL_NAME>\n" +
            "          <PAGE_EXT/>\n" +
            "          <PAGE_DESCS/>\n" +
            "        </PAGE>\n" +
            "        <PAGE PAGEID=\"f71e726847df4285b3d17620d30409cb\">\n" +
            "          <CREATE_USER>2301350012</CREATE_USER>\n" +
            "          <CREATE_USERNAME>高杰</CREATE_USERNAME>\n" +
            "          <CREATE_TIME>2022-06-16 15:02:55</CREATE_TIME>\n" +
            "          <MODIFY_USER>2301350012</MODIFY_USER>\n" +
            "          <MODIFY_TIME>2022-06-16 15:02:55</MODIFY_TIME>\n" +
            "          <PAGE_URL>f59861b0-4f01-4a15-9afb-9c752749c925.jpeg</PAGE_URL>\n" +
            "          <THUM_URL>f59861b0-4f01-4a15-9afb-9c752749c925.jpeg.jpg</THUM_URL>\n" +
            "          <IS_LOCAL>1</IS_LOCAL>\n" +
            "          <PAGE_VER>1</PAGE_VER>\n" +
            "          <PAGE_DESC>Adobe Photoshop 22.4 (Macintosh)</PAGE_DESC>\n" +
            "          <UPLOAD_ORG>阳光农业相互保险公司哈尔滨农垦支公司</UPLOAD_ORG>\n" +
            "          <PAGE_CRC>02df03e4bbf13f77e36d99bcd5b20361</PAGE_CRC>\n" +
            "          <PAGE_SIZE>94777</PAGE_SIZE>\n" +
            "          <PAGE_FORMAT>image/jpeg</PAGE_FORMAT>\n" +
            "          <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "          <ORIGINAL_NAME>3WechatIMG3543.jpeg</ORIGINAL_NAME>\n" +
            "          <PAGE_EXT>\n" +
            "            <EXT_ATTR ID=\"Camera model\" NAME=\"相机型号\" VALUE=\"\"/>\n" +
            "            <EXT_ATTR ID=\"Create SoftWare\" NAME=\"创建软件\" VALUE=\"Adobe Photoshop 22.4 (Macintosh)\"/>\n" +
            "          </PAGE_EXT>\n" +
            "          <PAGE_DESCS/>\n" +
            "        </PAGE>\n" +
            "        <PAGE PAGEID=\"ffbee8a25d6248d6a3eba9ae511bb4da\">\n" +
            "          <CREATE_USER>2301350012</CREATE_USER>\n" +
            "          <CREATE_USERNAME>高杰</CREATE_USERNAME>\n" +
            "          <CREATE_TIME>2022-06-16 15:02:55</CREATE_TIME>\n" +
            "          <MODIFY_USER>2301350012</MODIFY_USER>\n" +
            "          <MODIFY_TIME>2022-06-16 15:02:55</MODIFY_TIME>\n" +
            "          <PAGE_URL>b941fb34-d6c2-478a-974c-0519768b00d1.jpeg</PAGE_URL>\n" +
            "          <THUM_URL>b941fb34-d6c2-478a-974c-0519768b00d1.jpeg.jpg</THUM_URL>\n" +
            "          <IS_LOCAL>1</IS_LOCAL>\n" +
            "          <PAGE_VER>1</PAGE_VER>\n" +
            "          <PAGE_DESC>Adobe Photoshop 22.4 (Macintosh)</PAGE_DESC>\n" +
            "          <UPLOAD_ORG>阳光农业相互保险公司哈尔滨农垦支公司</UPLOAD_ORG>\n" +
            "          <PAGE_CRC>02df03e4bbf13f77e36d99bcd5b20361</PAGE_CRC>\n" +
            "          <PAGE_SIZE>94777</PAGE_SIZE>\n" +
            "          <PAGE_FORMAT>image/jpeg</PAGE_FORMAT>\n" +
            "          <PAGE_ENCRYPT>0</PAGE_ENCRYPT>\n" +
            "          <ORIGINAL_NAME>1WechatIMG3543.jpeg</ORIGINAL_NAME>\n" +
            "          <PAGE_EXT>\n" +
            "            <EXT_ATTR ID=\"Camera model\" NAME=\"相机型号\" VALUE=\"\"/>\n" +
            "            <EXT_ATTR ID=\"Create SoftWare\" NAME=\"创建软件\" VALUE=\"Adobe Photoshop 22.4 (Macintosh)\"/>\n" +
            "          </PAGE_EXT>\n" +
            "          <PAGE_DESCS/>\n" +
            "        </PAGE>\n" +
            "      </PageInfo>\n" +
            "      <VTREE APP_CODE=\"CXCB\" APP_NAME=\"车险承保\">\n" +
            "        <NODE ID=\"CCXCB001\" NAME=\"投保单\">\n" +
            "          <LEAF>ffbee8a25d6248d6a3eba9ae511bb4da</LEAF>\n" +
            "          <LEAF>6cdf0fd73c7d47c2888c9cabf82872fd</LEAF>\n" +
            "          <LEAF>f71e726847df4285b3d17620d30409cb</LEAF>\n" +
            "          <LEAF>c6ed9a5816df40e3ac926b1fd8a97e39</LEAF>\n" +
            "        </NODE>\n" +
            "      </VTREE>\n" +
            "    </doc>\n" +
            "  </SYD>\n" +
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


        ImagesDto imagesDto = XmlUtils.fromXML(data2, ImagesDto.class);
//
//
        System.out.println(JSONObject.toJSONString(imagesDto));
        // 第二版 加入类型
        List<Map<String, Object>> mapList = new CopyOnWriteArrayList<>();

        imagesDto.getPAGES().getPAGE().forEach(
                item -> {
                    Map<String, Object> map = new HashMap<>();
                    String url = item.getPAGEURL();
                    map.put("url",url);
                    String pageId = item.getPAGEID();
                    int i = 1;
                    for(ImagesDto.SYDDTO.DocDTO.PageInfoDTO.PAGEDTO pagedto : imagesDto.getSYD().getDoc().getPageInfo().getPAGE()){
                        if(pagedto.getPAGEID().equals(pageId)){
                            String[] type = pagedto.getPAGEURL().split("\\.");
                            map.put("type",type[type.length-1]);
                            System.out.println(JSONObject.toJSONString(pagedto.getPAGEEXT()));
                            map.put("software","无");
                            if(!checkObjAllFieldsIsNull(pagedto.getPAGEEXT())){
                                for(ImagesDto.SYDDTO.DocDTO.PageInfoDTO.PAGEDTO.PAGEEXTDTO.EXTATTRDTO extattrdto : pagedto.getPAGEEXT().getEXTATTR()){
                                    if(extattrdto.getID().equals("Create SoftWare")){
                                        map.put("software",extattrdto.getVALUE());
                                    }
                                }
                            }
                        }
                    }
                    for(ImagesDto.SYDDTO.DocDTO.VTREEDTO.NODEDTO node : imagesDto.getSYD().getDoc().getVTREE().getNODE()){
                        for(String nodeNub : node.getLEAF()){
                            if(nodeNub.equals(pageId)){
                                map.put("number",i);
                            }
                            i++;
                        }
                    }
                    mapList.add(map);
                }
        );


        System.out.println(JSONObject.toJSONString(mapList));
        System.out.println(JSONObject.toJSONString(1));

    }


    /**
     * 判断对象中属性值是否全为空
     *
     * @param object
     * @return
     */
    public static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }

        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);

                System.out.print(f.getName() + ":");
                System.out.println(f.get(object));

                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
