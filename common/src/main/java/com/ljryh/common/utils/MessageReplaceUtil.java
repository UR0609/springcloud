package com.ljryh.common.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class MessageReplaceUtil {

    /**
     * 替换${XXX}内容
     *
     * @param msg
     * @param map
     * @return
     */
    public String MessageReplace(String msg, Map<String, Object> map) {
        try {
            Template template = new Template("strTpl", msg, new Configuration(new Version("2.3.30")));
            StringWriter result = new StringWriter();
            template.process(map, result);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            HashMap map = new HashMap();
            map.put("name", "张三");
            map.put("money", 10.155);
            map.put("point", 10);
            map.put("test", "aaa");
            Template template = new Template("strTpl", "您好${name}，晚上好！您目前余额：${money?string(\"#.##\")}元，积分：${point}", new Configuration(new Version("2.3.30")));
            StringWriter result = new StringWriter();
            template.process(map, result);
            System.out.println(result.toString());
            //您好张三，晚上好！您目前余额：10.16元，积分：10
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
