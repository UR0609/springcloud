package com.ljryh.client.utils.generation.file;

import com.google.common.base.CaseFormat;
import com.ljryh.client.entity.generate.ColumnData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GenerateEntity {

    public void GenerateEntity(String parent, String child, List<ColumnData> list, String packagePath) {
        File file = new File(parent, child + ".java");  //创建文件对象
        try {
            if (!file.exists()) {//如果文件不存在则新建文件
                file.createNewFile();
            }
            FileOutputStream output = new FileOutputStream(file);
            StringBuilder sb = new StringBuilder();
            StringBuilder jsonDate = new StringBuilder();
            for (ColumnData columnData : list) {
                if(columnData.getDataShow()){
                    String columnType = columnData.getColumnType();
                    String columnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnData.getColumnName());
                    if (columnType.equals("LocalDateTime")) {
                        if ("".equals(jsonDate.toString())) {
                            jsonDate.append("import java.time.LocalDateTime;\n");
                            jsonDate.append("import com.fasterxml.jackson.annotation.JsonFormat;\n");
                            jsonDate.append("import com.fasterxml.jackson.databind.annotation.JsonDeserialize;\n");
                            jsonDate.append("import com.fasterxml.jackson.databind.annotation.JsonSerialize;\n");
                            jsonDate.append("import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;\n");
                            jsonDate.append("import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;\n");
                        }
                        sb.append("\t@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")\n");
                        sb.append("\t@JsonDeserialize(using = LocalDateTimeDeserializer.class)\n");
                        sb.append("\t@JsonSerialize(using = LocalDateTimeSerializer.class)\n");
                        sb.append("\tprivate ").append(columnType).append(" ").append(columnName).append(";\n");
                    } else {
                        sb.append("\tprivate ").append(columnType).append(" ").append(columnName).append(";\n");
                    }
                }
            }
            byte[] startbytes = ("package " + packagePath + ".entity;\n\r" +"import lombok.Data;\n" + jsonDate + "\n\rimport java.util.*;\n\r@Data\npublic class " + child + "{\n\r").getBytes();
            output.write(startbytes);//将头信息数组写入文件中
            byte[] bodybytes = sb.toString().getBytes();
            output.write(bodybytes);//信息数组写入文件中
            byte[] endbytes = ("}").getBytes();
            output.write(endbytes);//将尾信息数组写入文件中
            output.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
