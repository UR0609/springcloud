package com.ljryh.client.utils.generation.file;

import com.google.common.base.CaseFormat;
import com.ljryh.client.entity.generate.ColumnData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GenerateXmlMapper {

    public void GenerateXmlMapper(String parent, String child, List<ColumnData> list, String packagePath, String tableName) {
        File file = new File(parent, child + "Mapper.xml");  //创建文件对象

        try {
            if (!file.exists()) {//如果文件不存在则新建文件
                file.createNewFile();
            }
            FileOutputStream output = new FileOutputStream(file);

            byte[] startbytes = ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n\r" +
                    "<mapper namespace=\"" + packagePath + ".mapper." + child + "Mapper\">\n\r").getBytes();

            output.write(startbytes);//将头信息数组写入文件中

            String result = child + "Result";

            StringBuilder sb = new StringBuilder();

            // 定义实体
            sb.append("\t<parameterMap id=\"").append(child).append("\" type=\"").append(packagePath).append(".entity.").append(child).append("\"/>\n\r");
            // 定义返回实体
            sb.append("\t<resultMap id=\"").append(result).append("\" type=\"").append(packagePath).append(".entity.").append(child).append("\">\n");
            for (ColumnData columnData : list) {
                if (columnData.getDataShow()) {
                    String columnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnData.getColumnName());
                    sb.append("\t\t<result property=\"").append(columnName).append("\" column=\"").append(columnName).append("\"/>\n");
                }
            }
            sb.append("\t</resultMap>\n\r");

            // 定义查询
            sb.append("\t<select id=\"selectPage\" parameterMap=\"").append(child).append("\" resultMap=\"").append(result).append("\">\n");
            sb.append("\t\tSELECT\n");
            sb.append("\t\t\t<trim prefix=\"\" suffixOverrides=\",\" suffix=\"\">\n");
            for (ColumnData columnData : list) {
                if (columnData.getDataShow()) {
                    String columnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnData.getColumnName());
                    sb.append("\t\t\t\t").append(columnData.getColumnName()).append(" as \"").append(columnName).append("\",\n");
                }
            }
            sb.append("\t\t\t</trim>\n");
            sb.append("\t\tFROM ").append(tableName).append(" where 1 = 1\n");
            for (ColumnData columnData : list) {
                if (columnData.getDataQuery()) {
                    sb.append("\t\t").append(this.typeJudgeSel(columnData)).append("\n");
                    sb.append("\t\t\tand ").append(columnData.getColumnName()).append(" = #{ew.entity.").append(columnData.getColumnName()).append("}\n");
                    sb.append("\t\t</if>\n");
                }
            }
            sb.append("\t</select>\n\r");

            // 定义插入
            sb.append("\t<insert id=\"insert\" parameterMap=\"").append(child).append("\">\n");
            sb.append("\t\tinsert into ").append(tableName).append("\n");
            sb.append("\t\t<trim prefix=\"(\" prefixOverrides=\",\" suffix=\")\">\n");
            for (ColumnData columnData : list) {
                if (columnData.getDataShow() && !columnData.getColumnName().equals("id")) {
                    sb.append("\t\t\t").append(this.typeJudge(columnData)).append(",").append(columnData.getColumnName()).append("</if>\n");
                }
            }
            sb.append("\t\t</trim>\n");
            sb.append("\t\tvalues\n");
            sb.append("\t\t<trim prefix=\"(\" prefixOverrides=\",\" suffix=\")\">\n");
            for (ColumnData columnData : list) {
                if (columnData.getDataShow() && !columnData.getColumnName().equals("id")) {
                    String columnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnData.getColumnName());
                    sb.append("\t\t\t").append(this.typeJudge(columnData)).append(",#{").append(columnName).append("}</if>\n");
                }
            }
            sb.append("\t\t</trim>\n");
            sb.append("\t\t<selectKey keyProperty=\"id\" order=\"AFTER\" resultType=\"Long\">\n");
            sb.append("\t\t\tselect LAST_INSERT_ID() as id\n");
            sb.append("\t\t</selectKey>\n");
            sb.append("\t</insert>\n\r");

            // 定义更新
            sb.append("\t<update id=\"updateById\" parameterMap=\"").append(child).append("\">\n");
            sb.append("\t\tupdate ").append(tableName).append("\n");
            sb.append("\t\t<set>\n");
            sb.append("\t\t\t<trim prefixOverrides=\",\">\n");
            for (ColumnData columnData : list) {
                if (columnData.getDataShow() && !columnData.getColumnName().equals("id")) {
                    String columnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnData.getColumnName());
                    sb.append("\t\t\t\t").append(this.typeJudge(columnData)).append(",").append(columnData.getColumnName()).append(" = #{").append(columnName).append("}</if>\n");
                }
            }
            sb.append("\t\t\t</trim>\n");
            sb.append("\t\t</set>\n");
            sb.append("\t\t<where>\n");
            sb.append("\t\t\tID = ${id}\n");
            sb.append("\t\t</where>\n");
            sb.append("\t</update>\n\r");

            // 定义删除
            sb.append("\t<delete id=\"deleteById\" parameterMap=\"").append(child).append("\">\n");
            sb.append("\t\tdelete from ").append(tableName).append(" where ID = ${id}\n");
            sb.append("\t</delete>\n\r");

            // 定义id查询
            sb.append("\t<select id=\"selectById\" parameterMap=\"").append(child).append("\" resultMap=\"").append(result).append("\">\n");
            sb.append("\t\tSELECT\n");
            sb.append("\t\t\t<trim prefix=\"\" suffixOverrides=\",\" suffix=\"\">\n");
            for (ColumnData columnData : list) {
                if (columnData.getDataShow()) {
                    String columnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnData.getColumnName());
                    sb.append("\t\t\t\t").append(columnData.getColumnName()).append(" as \"").append(columnName).append("\",\n");
                }
            }
            sb.append("\t\t\t</trim>\n");
            sb.append("\t\tFROM ").append(tableName).append("\n");
            sb.append("\t\twhere id = ${id}\n");
            sb.append("\t</select>\n");

            byte[] bodybytes = sb.toString().getBytes();
            output.write(bodybytes);//信息数组写入文件中

            byte[] endbytes = ("\n</mapper>").getBytes();

            output.write(endbytes);//将尾信息数组写入文件中

            output.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String typeJudge(ColumnData columnData) {
        String columnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnData.getColumnName());
        return columnData.getColumnType().equals("String") ?
                "<if test=\"" + columnName + " != null and " + columnName + " != ''\">" :
                "<if test=\"" + columnName + " != null \">";
    }

    private String typeJudgeSel(ColumnData columnData) {
        String columnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnData.getColumnName());
        return columnData.getColumnType().equals("String") ?
                "<if test=\"ew.entity." + columnName + " != null and ew.entity." + columnName + " != ''\">" :
                "<if test=\"ew.entity." + columnName + " != null \">";
    }

}
