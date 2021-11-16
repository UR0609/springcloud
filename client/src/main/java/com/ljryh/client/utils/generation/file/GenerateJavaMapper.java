package com.ljryh.client.utils.generation.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateJavaMapper {

    public void GenerateJavaMapperf(String parent, String child, String packagePath) {

        File file = new File(parent, child + "Mapper.java");  //创建文件对象

        try {
            if (!file.exists()) {//如果文件不存在则新建文件
                file.createNewFile();
            }
            FileOutputStream output = new FileOutputStream(file);

            byte[] startbytes = ("package " + packagePath + ".mapper;\n\r" +
                    "import " + packagePath + ".entity." + child +";\n"+
                    "import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;\n" +
                    "import com.baomidou.mybatisplus.core.mapper.BaseMapper;\n" +
                    "import com.baomidou.mybatisplus.core.metadata.IPage;\n" +
                    "import org.apache.ibatis.annotations.Param;\n\r" +
                    "public interface " + child + "Mapper extends BaseMapper<"+child+">{\n\r").getBytes();

            output.write(startbytes);//将头信息数组写入文件中

            // 分页查询
            String sb = "\tIPage<" + child + "> selectPage(IPage<" + child + "> page, @Param(\"ew\") QueryWrapper<" + child + "> wrapper);\n\r" +
                    // 插入
                    "\tint insert(" + child + " entity);\n\r" +
                    // 更新
                    "\tint updateById(" + child + " entity);\n\r" +
                    // 删除
                    "\tint deleteById(@Param(\"id\") Long id);\n\r" +
                    // 子查
                    "\t" + child + " selectById(@Param(\"id\") Long id);\n\r";

            byte[] bodybytes = sb.getBytes();
            output.write(bodybytes);//信息数组写入文件中

            byte[] endbytes = ("\n}").getBytes();

            output.write(endbytes);//将尾信息数组写入文件中

            output.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
