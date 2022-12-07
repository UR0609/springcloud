package com.ljryh.common.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author ljryh
 * @version 1.0
 * @date 2022/8/16 16:17
 */
public class JsonTest {

    private static String s = "{\n" +
            "  \"addressmessageList\": [],\n" +
            "  \"addressnum\": \"0\",\n" +
            "  \"agentcode\": \"440700820002\",\n" +
            "  \"agentname\": \"陈瑾瑜\",\n" +
            "  \"agreementno\": \"44070082003002\",\n" +
            "  \"agricultclass\": \"\",\n" +
            "  \"agricultclassname\": \"\",\n" +
            "  \"agricultinsured\": \"\",\n" +
            "  \"agricultinsuredname\": \"\",\n" +
            "  \"agricultitemkind\": \"\",\n" +
            "  \"agricultitemkindname\": \"\",\n" +
            "  \"allinsflag\": \"2\",\n" +
            "  \"allinsflagname\": \"法定\",\n" +
            "  \"appliaddress\": \"恩平市圣堂镇龙步塘村工业功能区A11万嘉公司厂房D区\",\n" +
            "  \"applicode\": \"4407510000000378\",\n" +
            "  \"appliname\": \"恩平市富嘉裕五金制品有限公司\",\n" +
            "  \"approvercode\": \"4402020006\",\n" +
            "  \"approvercodename\": \"林树俊\",\n" +
            "  \"arbitboardname\": \"\",\n" +
            "  \"arguesolution\": \"1\",\n" +
            "  \"arguesolutionname\": \"诉讼\",\n" +
            "  \"assistfeeflag\": \"1\",\n" +
            "  \"autotransrenewflag\": \"\",\n" +
            "  \"biztype\": \"PROPOSAL\",\n" +
            "  \"businessnature\": \"1\",\n" +
            "  \"businessnaturename\": \"个人代理\",\n" +
            "  \"businesstype\": \"00\",\n" +
            "  \"businesstype1\": \"00\",\n" +
            "  \"businesstype1name\": \"商业性\",\n" +
            "  \"businesstypename\": \"非农\",\n" +
            "  \"claimtimes\": \"0\",\n" +
            "  \"classcode\": \"Z\",\n" +
            "  \"coinsSuranceAgreement\": \"\",\n" +
            "  \"coinslinkflag\": \"0\",\n" +
            "  \"coinslinkflagname\": \"无联保\",\n" +
            "  \"coinsuranceflag\": \"0\",\n" +
            "  \"coinsuranceflagname\": \"独家承保\",\n" +
            "  \"comcode\": \"44075100\",\n" +
            "  \"comname\": \"阳光农业相互保险公司蓬江支公司\",\n" +
            "  \"contractno\": \"\",\n" +
            "  \"disrate\": \"0.0\",\n" +
            "  \"disratesxf\": \"35.0000\",\n" +
            "  \"dutyratioexp\": \"0.0000\",\n" +
            "  \"enddate\": \"2023-08-16\",\n" +
            "  \"endhour\": \"24\",\n" +
            "  \"endorsetimes\": \"0\",\n" +
            "  \"exchangecu\": \"1.0\",\n" +
            "  \"handler1code\": \"4402030047\",\n" +
            "  \"handler1name\": \"钟雅婷\",\n" +
            "  \"handlercode\": \"4402030047\",\n" +
            "  \"handlername\": \"钟雅婷\",\n" +
            "  \"inputDate\": \"2022-08-16\",\n" +
            "  \"insuranceflag\": \"\",\n" +
            "  \"insurancenumber\": \"1\",\n" +
            "  \"inusercode\": \"4402030047\",\n" +
            "  \"itemKindMainNum\": \"0\",\n" +
            "  \"itemKindSubNum\": \"0\",\n" +
            "  \"judicalscope\": \"中华人民共和国管辖(港澳台除外)\",\n" +
            "  \"language\": \"C\",\n" +
            "  \"languagename\": \"中文\",\n" +
            "  \"makecom\": \"44075100\",\n" +
            "  \"makecomname\": \"阳光农业相互保险公司蓬江支公司\",\n" +
            "  \"notaxfree\": \"16027.32\",\n" +
            "  \"oldpolicyno\": \"\",\n" +
            "  \"operatedate\": \"2022-08-16\",\n" +
            "  \"operatetype\": \"\",\n" +
            "  \"operatorcode\": \"4402030047\",\n" +
            "  \"operatorcodename\": \"钟雅婷\",\n" +
            "  \"paytimes\": \"1\",\n" +
            "  \"policysort\": \"2\",\n" +
            "  \"policysortname\": \"普通\",\n" +
            "  \"policytype\": \"99\",\n" +
            "  \"policytypename\": \"其他\",\n" +
            "  \"premiumdisrate\": \"16508.14\",\n" +
            "  \"printno\": \"\",\n" +
            "  \"proportionflag1\": \"\",\n" +
            "  \"proportionflag2\": \"\",\n" +
            "  \"proposalno\": \"44075100TZFG2022000014\",\n" +
            "  \"prpitemcarList\": [],\n" +
            "  \"prpmaininsuredaddress\": \"恩平市圣堂镇龙步塘村工业功能区A11万嘉公司厂房D区\",\n" +
            "  \"prpmaininsuredcode\": \"4407510000000378\",\n" +
            "  \"prpmaininsuredname\": \"恩平市富嘉裕五金制品有限公司\",\n" +
            "  \"prptengageList\": [\n" +
            "    {\n" +
            "      \"clausecode\": \"T0060\",\n" +
            "      \"clauses\": \"非车险业务见费出单提示\",\n" +
            "      \"clausescontext\": \"投保人应当领取或签署投保单后在投保单记录的保险起保日期前向保险公司\\n全额缴付保费，否则投保无效\\n\",\n" +
            "      \"engageserialno\": \"1\",\n" +
            "      \"serialnname\": \"T\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"clausecode\": \"T0001\",\n" +
            "      \"clauses\": \"特别约定\",\n" +
            "      \"clausescontext\": \"1、医疗费每人每次事故免赔额为RMB 100元后按100%赔付；\\n2、伤残赔偿比例：\\n项目\\\\t伤残程度\\\\t百分比\\n（一）\\\\t一级伤残\\\\t100%\\n（二）\\\\t二级伤残\\\\t80%\\n（三）\\\\t三级伤残\\\\t65%\\n（四）\\\\t四级伤残\\\\t55%\\n（五）\\\\t五级伤残\\\\t45%\\n（六）\\\\t六级伤残\\\\t25%\\n（七）\\\\t七级伤残\\\\t15%\\n（八）\\\\t八级伤残\\\\t10%\\n（九）\\\\t九级伤残\\\\t4%\\n（十）\\\\t十级伤残\\\\t1%\\n3、发生本保险单责任范围内的事故，被保险人承担的诊疗项目、药品、住院服务及辅助器\\n具配置费用，保险人均按照国家工伤保险待遇规定的标准赔偿；每人每天按RMB 100元赔偿\\n误工补助，免赔3天，累计赔付不超180天。\\n4、发生保险事故后必需在24小时内报案，否则由于被保险人延迟报案导致保险人无法确认\\n事故原因及损伤程度的，保险人有权拒绝赔付；\\n5、除紧急抢救外，雇员均应在二级以上（含）或保险人认可（需及时告知保险人）的医疗\\n机构就诊；\\n6、附加职业病责任保险条款：在保险期间内，被保险人的雇员首次被诊断、鉴定为职业\\n病，依法应由被保险人承担的经济赔偿责任，保险人按照本附加险合同约定负责赔偿。但\\n雇员因接触、使用石棉、石棉制品或含有石棉成分的物质造成的损失、费用和责任，保险\\n人不负责赔偿。本保险单所载其他条件均不变。\\n7、自动承保新员工条款：兹经保险合同双方同意，本保险单扩展承保被保险人在保险期间\\n内雇佣的新员工。被保险人应在新员工入职后的15天内，及时向保险公司书面申报新员工\\n的投保信息并补缴相应的保费，未能在上述期限内及时批改的员工，以及未与被保险人签\\n订劳动合同的员工，保险公司自批改之日起承担保险责任，保险责任不得追溯到新员工入\\n职之日。\\n\",\n" +
            "      \"engageserialno\": \"2\",\n" +
            "      \"serialnname\": \"T\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"prptinsuredList\": [\n" +
            "    {\n" +
            "      \"account\": \"\",\n" +
            "      \"bank\": \"\",\n" +
            "      \"expirationDate\": \"长期\",\n" +
            "      \"identifynumber\": \"91440785MABN26R15G\",\n" +
            "      \"identifytype\": \"15\",\n" +
            "      \"insure_flag\": \"\",\n" +
            "      \"insuredaddress\": \"恩平市圣堂镇龙步塘村工业功能区A11万嘉公司厂房D区\",\n" +
            "      \"insuredflag\": \"2\",\n" +
            "      \"insuredidentity\": \"\",\n" +
            "      \"insuredlanguage\": \"中文\",\n" +
            "      \"insuredlanguagecode\": \"C\",\n" +
            "      \"insurednature\": \"4\",\n" +
            "      \"insuredtype\": \"2\",\n" +
            "      \"insuredtypename\": \"单位\",\n" +
            "      \"linkername\": \"陈达鹏\",\n" +
            "      \"phonenumber\": \"13071473418\",\n" +
            "      \"postaddress\": \"\",\n" +
            "      \"postcode\": \"\",\n" +
            "      \"prpinsuredinsuredcode\": \"4407510000000378\",\n" +
            "      \"prpinsuredinsuredname\": \"恩平市富嘉裕五金制品有限公司\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"account\": \"\",\n" +
            "      \"bank\": \"\",\n" +
            "      \"expirationDate\": \"长期\",\n" +
            "      \"identifynumber\": \"91440785MABN26R15G\",\n" +
            "      \"identifytype\": \"15\",\n" +
            "      \"insure_flag\": \"\",\n" +
            "      \"insuredaddress\": \"恩平市圣堂镇龙步塘村工业功能区A11万嘉公司厂房D区\",\n" +
            "      \"insuredflag\": \"1\",\n" +
            "      \"insuredidentity\": \"\",\n" +
            "      \"insuredlanguage\": \"中文\",\n" +
            "      \"insuredlanguagecode\": \"C\",\n" +
            "      \"insurednature\": \"4\",\n" +
            "      \"insuredtype\": \"2\",\n" +
            "      \"insuredtypename\": \"单位\",\n" +
            "      \"linkername\": \"陈达鹏\",\n" +
            "      \"phonenumber\": \"13071473418\",\n" +
            "      \"postaddress\": \"\",\n" +
            "      \"postcode\": \"\",\n" +
            "      \"prpinsuredinsuredcode\": \"4407510000000378\",\n" +
            "      \"prpinsuredinsuredname\": \"恩平市富嘉裕五金制品有限公司\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"prptitemkindDSFlist\": [\n" +
            "    {\n" +
            "      \"factor1\": \"0.9\",\n" +
            "      \"factor2\": \"1.1\",\n" +
            "      \"factor3\": \"1\",\n" +
            "      \"factor4\": \"\",\n" +
            "      \"factor5\": \"\",\n" +
            "      \"factor6\": \"\",\n" +
            "      \"factor7\": \"\",\n" +
            "      \"factor8\": \"\",\n" +
            "      \"iTEMKINDNO\": \"1\",\n" +
            "      \"pROPOSALNO\": \"44075100TZFG2022000014\",\n" +
            "      \"rISKCODE\": \"ZFG\",\n" +
            "      \"remark\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"factor1\": \"1.2\",\n" +
            "      \"factor2\": \"\",\n" +
            "      \"factor3\": \"\",\n" +
            "      \"factor4\": \"\",\n" +
            "      \"factor5\": \"\",\n" +
            "      \"factor6\": \"\",\n" +
            "      \"factor7\": \"\",\n" +
            "      \"factor8\": \"\",\n" +
            "      \"iTEMKINDNO\": \"2\",\n" +
            "      \"pROPOSALNO\": \"44075100TZFG2022000014\",\n" +
            "      \"rISKCODE\": \"ZFG\",\n" +
            "      \"remark\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"factor1\": \"0.8501\",\n" +
            "      \"factor2\": \"1\",\n" +
            "      \"factor3\": \"1\",\n" +
            "      \"factor4\": \"1\",\n" +
            "      \"factor5\": \"0.8588\",\n" +
            "      \"factor6\": \"\",\n" +
            "      \"factor7\": \"\",\n" +
            "      \"factor8\": \"\",\n" +
            "      \"iTEMKINDNO\": \"3\",\n" +
            "      \"pROPOSALNO\": \"44075100TZFG2022000014\",\n" +
            "      \"rISKCODE\": \"ZFG\",\n" +
            "      \"remark\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"factor1\": \"1\",\n" +
            "      \"factor2\": \"1\",\n" +
            "      \"factor3\": \"1\",\n" +
            "      \"factor4\": \"\",\n" +
            "      \"factor5\": \"\",\n" +
            "      \"factor6\": \"\",\n" +
            "      \"factor7\": \"\",\n" +
            "      \"factor8\": \"\",\n" +
            "      \"iTEMKINDNO\": \"4\",\n" +
            "      \"pROPOSALNO\": \"44075100TZFG2022000014\",\n" +
            "      \"rISKCODE\": \"ZFG\",\n" +
            "      \"remark\": \"\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"prptitemkindList\": [\n" +
            "    {\n" +
            "      \"addressno\": \"0\",\n" +
            "      \"adjustrate\": \"0.9900\",\n" +
            "      \"amount\": \"50000000.00\",\n" +
            "      \"basepremium\": \"0.00\",\n" +
            "      \"calculateflag\": \"Y\",\n" +
            "      \"currency\": \"CNY\",\n" +
            "      \"currencyname\": \"人民币\",\n" +
            "      \"deductible\": \"0.00\",\n" +
            "      \"deductiblerate\": \"0.00\",\n" +
            "      \"discount\": \"0.0\",\n" +
            "      \"drinkrate\": \"1\",\n" +
            "      \"driveramount\": \"0.00\",\n" +
            "      \"drunkrate\": \"1.20\",\n" +
            "      \"exchangeratemain\": \"1.0\",\n" +
            "      \"familyname\": \"\",\n" +
            "      \"familyno\": \"0\",\n" +
            "      \"flag\": \" 1\",\n" +
            "      \"guestamount\": \"0.00\",\n" +
            "      \"inc_lj\": \"50000000.00\",\n" +
            "      \"inc_mp\": \"500000.00\",\n" +
            "      \"itemcode\": \"0001\",\n" +
            "      \"itemcodename\": \"每人伤亡\",\n" +
            "      \"itemdetailname\": \"每人伤亡\",\n" +
            "      \"itemkindno\": \"1\",\n" +
            "      \"itemno\": \"0\",\n" +
            "      \"kindcode\": \"001\",\n" +
            "      \"kindname\": \"雇主责任险\",\n" +
            "      \"mainsub\": \"1\",\n" +
            "      \"measureratio\": \"\",\n" +
            "      \"modecode\": \"\",\n" +
            "      \"model\": \"\",\n" +
            "      \"modename\": \"\",\n" +
            "      \"notaxpremium\": \"33059.58\",\n" +
            "      \"premium\": \"35043.16\",\n" +
            "      \"quantity\": \"100\",\n" +
            "      \"rate\": \"0.8\",\n" +
            "      \"rationtype\": \"\",\n" +
            "      \"shortrate\": \"100.0000\",\n" +
            "      \"shortrateflag\": \"1\",\n" +
            "      \"shortrateflagname\": \"月比例\",\n" +
            "      \"taxfee\": \"1983.58\",\n" +
            "      \"taxrate\": \"6.00\",\n" +
            "      \"thirdpersonamount\": \"0.00\",\n" +
            "      \"unit\": \"\",\n" +
            "      \"unitamount\": \"0.00\",\n" +
            "      \"value\": \"0.00\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"addressno\": \"0\",\n" +
            "      \"adjustrate\": \"0.9900\",\n" +
            "      \"amount\": \"3000000.00\",\n" +
            "      \"basepremium\": \"0.00\",\n" +
            "      \"calculateflag\": \"Y\",\n" +
            "      \"currency\": \"CNY\",\n" +
            "      \"currencyname\": \"人民币\",\n" +
            "      \"deductible\": \"100.00\",\n" +
            "      \"deductiblerate\": \"100.00\",\n" +
            "      \"discount\": \"0.0\",\n" +
            "      \"drinkrate\": \"1\",\n" +
            "      \"driveramount\": \"0.00\",\n" +
            "      \"drunkrate\": \"1.20\",\n" +
            "      \"exchangeratemain\": \"1.0\",\n" +
            "      \"familyname\": \"\",\n" +
            "      \"familyno\": \"0\",\n" +
            "      \"flag\": \" 1\",\n" +
            "      \"guestamount\": \"0.00\",\n" +
            "      \"inc_lj\": \"3000000.00\",\n" +
            "      \"inc_los_lim\": \"100.00\",\n" +
            "      \"inc_mp\": \"30000.00\",\n" +
            "      \"itemcode\": \"0002\",\n" +
            "      \"itemcodename\": \"每人医疗费\",\n" +
            "      \"itemdetailname\": \"每人医疗费\",\n" +
            "      \"itemkindno\": \"2\",\n" +
            "      \"itemno\": \"0\",\n" +
            "      \"kindcode\": \"001\",\n" +
            "      \"kindname\": \"雇主责任险\",\n" +
            "      \"mainsub\": \"1\",\n" +
            "      \"measureratio\": \"\",\n" +
            "      \"modecode\": \"\",\n" +
            "      \"model\": \"\",\n" +
            "      \"modename\": \"\",\n" +
            "      \"notaxpremium\": \"9818.70\",\n" +
            "      \"premium\": \"10407.82\",\n" +
            "      \"quantity\": \"100\",\n" +
            "      \"rate\": \"4.0\",\n" +
            "      \"rationtype\": \"\",\n" +
            "      \"shortrate\": \"100.0000\",\n" +
            "      \"shortrateflag\": \"1\",\n" +
            "      \"shortrateflagname\": \"月比例\",\n" +
            "      \"taxfee\": \"589.12\",\n" +
            "      \"taxrate\": \"6.00\",\n" +
            "      \"thirdpersonamount\": \"0.00\",\n" +
            "      \"unit\": \"\",\n" +
            "      \"unitamount\": \"0.00\",\n" +
            "      \"value\": \"0.00\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"addressno\": \"0\",\n" +
            "      \"adjustrate\": \"0.9900\",\n" +
            "      \"amount\": \"0.00\",\n" +
            "      \"basepremium\": \"45450.98\",\n" +
            "      \"calculateflag\": \"N\",\n" +
            "      \"currency\": \"CNY\",\n" +
            "      \"currencyname\": \"人民币\",\n" +
            "      \"deductible\": \"0.00\",\n" +
            "      \"deductiblerate\": \"0.00\",\n" +
            "      \"discount\": \"0.0\",\n" +
            "      \"drinkrate\": \"1\",\n" +
            "      \"driveramount\": \"0.00\",\n" +
            "      \"drunkrate\": \"1.20\",\n" +
            "      \"exchangeratemain\": \"1.0\",\n" +
            "      \"familyname\": \"\",\n" +
            "      \"familyno\": \"0\",\n" +
            "      \"flag\": \" 1\",\n" +
            "      \"guestamount\": \"0.00\",\n" +
            "      \"inc_lim\": \"53000000.00\",\n" +
            "      \"itemcode\": \"9998\",\n" +
            "      \"itemcodename\": \"\",\n" +
            "      \"itemdetailname\": \"虚拟标的\",\n" +
            "      \"itemkindno\": \"3\",\n" +
            "      \"itemno\": \"0\",\n" +
            "      \"kindcode\": \"013\",\n" +
            "      \"kindname\": \"附加职业病责任保险条款\",\n" +
            "      \"mainsub\": \"2\",\n" +
            "      \"measureratio\": \"\",\n" +
            "      \"modecode\": \"\",\n" +
            "      \"model\": \"\",\n" +
            "      \"modename\": \"\",\n" +
            "      \"notaxpremium\": \"4287.83\",\n" +
            "      \"premium\": \"4545.10\",\n" +
            "      \"quantity\": \"0\",\n" +
            "      \"rate\": \"10.0\",\n" +
            "      \"rationtype\": \"\",\n" +
            "      \"shortrate\": \"100.0000\",\n" +
            "      \"shortrateflag\": \"1\",\n" +
            "      \"shortrateflagname\": \"月比例\",\n" +
            "      \"taxfee\": \"257.27\",\n" +
            "      \"taxrate\": \"6.00\",\n" +
            "      \"thirdpersonamount\": \"0.00\",\n" +
            "      \"unit\": \"\",\n" +
            "      \"unitamount\": \"0.00\",\n" +
            "      \"value\": \"0.00\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"addressno\": \"0\",\n" +
            "      \"adjustrate\": \"0.9900\",\n" +
            "      \"amount\": \"0.00\",\n" +
            "      \"basepremium\": \"45450.98\",\n" +
            "      \"calculateflag\": \"N\",\n" +
            "      \"currency\": \"CNY\",\n" +
            "      \"currencyname\": \"人民币\",\n" +
            "      \"deductible\": \"0.00\",\n" +
            "      \"deductiblerate\": \"0.00\",\n" +
            "      \"discount\": \"0.0\",\n" +
            "      \"drinkrate\": \"1\",\n" +
            "      \"driveramount\": \"0.00\",\n" +
            "      \"drunkrate\": \"1.20\",\n" +
            "      \"exchangeratemain\": \"1.0\",\n" +
            "      \"familyname\": \"\",\n" +
            "      \"familyno\": \"0\",\n" +
            "      \"flag\": \" 1\",\n" +
            "      \"guestamount\": \"0.00\",\n" +
            "      \"inc_lim\": \"53000000.00\",\n" +
            "      \"itemcode\": \"9998\",\n" +
            "      \"itemcodename\": \"\",\n" +
            "      \"itemdetailname\": \"虚拟标的\",\n" +
            "      \"itemkindno\": \"4\",\n" +
            "      \"itemno\": \"0\",\n" +
            "      \"kindcode\": \"014\",\n" +
            "      \"kindname\": \"附加自动承保新员工保险条款\",\n" +
            "      \"mainsub\": \"2\",\n" +
            "      \"measureratio\": \"\",\n" +
            "      \"modecode\": \"\",\n" +
            "      \"model\": \"\",\n" +
            "      \"modename\": \"\",\n" +
            "      \"notaxpremium\": \"0.00\",\n" +
            "      \"premium\": \"0.00\",\n" +
            "      \"quantity\": \"0\",\n" +
            "      \"rate\": \"0.0\",\n" +
            "      \"rationtype\": \"\",\n" +
            "      \"shortrate\": \"100.0000\",\n" +
            "      \"shortrateflag\": \"1\",\n" +
            "      \"shortrateflagname\": \"月比例\",\n" +
            "      \"taxfee\": \"0.00\",\n" +
            "      \"taxrate\": \"6.00\",\n" +
            "      \"thirdpersonamount\": \"0.00\",\n" +
            "      \"unit\": \"\",\n" +
            "      \"unitamount\": \"0.00\",\n" +
            "      \"value\": \"0.00\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"prptitemship\": {},\n" +
            "  \"prptmainliab\": {\n" +
            "    \"bkwardenddate\": \"\",\n" +
            "    \"bkwardstartdate\": \"\",\n" +
            "    \"businessdetail\": \"独资\",\n" +
            "    \"businesssite\": \"\",\n" +
            "    \"certificateadate\": \"\",\n" +
            "    \"certificateano\": \"034\",\n" +
            "    \"certificatebdate\": \"\",\n" +
            "    \"certificatebno\": \"\",\n" +
            "    \"certificatedate\": \"\",\n" +
            "    \"certificatedepart\": \"\",\n" +
            "    \"certificateno\": \"\",\n" +
            "    \"electricpower\": \"0.0\",\n" +
            "    \"insurearea\": \"\",\n" +
            "    \"nowturnover\": \"0.0\",\n" +
            "    \"officegrade\": \"\",\n" +
            "    \"officetype\": \"\",\n" +
            "    \"officetypename\": \"\",\n" +
            "    \"practicedate\": \"\",\n" +
            "    \"preturnover\": \"53000000\",\n" +
            "    \"proposalhis\": \"\",\n" +
            "    \"remark\": \"\",\n" +
            "    \"riskkind\": \"\",\n" +
            "    \"riskkindname\": \"\",\n" +
            "    \"salearea\": \"\",\n" +
            "    \"staffcount\": \"100\"\n" +
            "  },\n" +
            "  \"prptplanList\": [\n" +
            "    {\n" +
            "      \"currency\": \"人民币\",\n" +
            "      \"delinquentfee\": \"49996.08\",\n" +
            "      \"payno\": \"1\",\n" +
            "      \"payreason\": \"R10\",\n" +
            "      \"plandate\": \"2022-08-17\",\n" +
            "      \"planfee\": \"49996.08\",\n" +
            "      \"prpplancurrency\": \"CNY\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"region\": \"15\",\n" +
            "  \"reinsflag\": \"0\",\n" +
            "  \"reinsurancepointsinformationoutList\": [],\n" +
            "  \"riskcode\": \"ZFG\",\n" +
            "  \"riskname\": \"雇主责任保险\",\n" +
            "  \"season\": \"\",\n" +
            "  \"seasoncname\": \"\",\n" +
            "  \"showtype\": \"SHOW\",\n" +
            "  \"sourcetype\": \"\",\n" +
            "  \"startdate\": \"2022-08-17\",\n" +
            "  \"starthour\": \"0\",\n" +
            "  \"statisticsym\": \"2022-08-16\",\n" +
            "  \"statquantity\": \"0.00\",\n" +
            "  \"statunitcode\": \"\",\n" +
            "  \"statunitname\": \"\",\n" +
            "  \"sumamount\": \"53000000.00\",\n" +
            "  \"suminsured\": \"0\",\n" +
            "  \"sumnotaxpremium\": \"47166.11\",\n" +
            "  \"sumpremium\": \"49996.08\",\n" +
            "  \"sumquantity\": \"1.0\",\n" +
            "  \"sumtaxfee\": \"2829.97\",\n" +
            "  \"taxfree\": \"480.82\",\n" +
            "  \"taxrate\": \"3.0000\",\n" +
            "  \"tcoinsDetailOutList\": [],\n" +
            "  \"tcoinsOutList\": [],\n" +
            "  \"prptnamelist\": [],\n" +
            "  \"transfertaxexp\": \"0.00\"\n" +
            "}";

    public static void main(String[] args) {
        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONArray jsonArray = jsonObject.getJSONArray("prptnamelist");
        if(jsonArray != null){
            List<Map> list= jsonArray.toJavaList(Map.class);
            System.out.println(
                    123
            );
        } else {
            System.out.println(
                    456
            );
        }

    }


}
