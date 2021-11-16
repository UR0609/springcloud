package com.ljryh.client.utils.generation.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateService {

    public void GenerateService(String parent, String child, String packagePath) {
        File file = new File(parent, "I" + child + "Service.java");  //创建文件对象

        try {
            if (!file.exists()) {//如果文件不存在则新建文件
                file.createNewFile();
            }
            FileOutputStream output = new FileOutputStream(file);

            char[] name = child.toCharArray();
            name[0] += 32;
            String smart = String.valueOf(name);

            String re = smart + "Mapper";

            byte[] startbytes = ("package " + packagePath + ".service;\n\r" +
                    "import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;\n" +
                    "import com.baomidou.mybatisplus.core.metadata.IPage;\n" +
                    "import com.baomidou.mybatisplus.extension.service.IService;\n" +
                    "import " + packagePath + ".entity." + child + ";\n\r" +
                    "public interface I" + child + "Service extends IService<" + child + "> {\n\r").getBytes();

            output.write(startbytes);//将头信息数组写入文件中

            // 分页查询
            String sb = "\tIPage<" + child + "> page(IPage<" + child + "> page, QueryWrapper<" + child + "> wrapper);\n\r" +
                    // 插入
                    "\tboolean save(" + child + " entity);\n\r" +
                    // 更新
                    "\tboolean updateById(" + child + " entity);\n\r" +
                    // 删除
                    "\tboolean removeById(Long id);\n\r" +
                    // 子查
                    "\t" + child + " getById(Long id);\n\r";

            byte[] bodybytes = sb.getBytes();
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
