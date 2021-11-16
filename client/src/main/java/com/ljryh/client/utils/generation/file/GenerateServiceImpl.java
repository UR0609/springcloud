package com.ljryh.client.utils.generation.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateServiceImpl {

    public void GenerateServiceImpl(String parent, String child, String packagePath) {
        File file = new File(parent, child + "ServiceImpl.java");  //创建文件对象

        try {
            if (!file.exists()) {//如果文件不存在则新建文件
                file.createNewFile();
            }
            FileOutputStream output = new FileOutputStream(file);

            char[] name = child.toCharArray();
            name[0]+=32;
            String smart = String.valueOf(name);

            String re = smart + "Mapper";

            byte[] startbytes = ("package " + packagePath + ".service.impl;\n\r" +
                    "import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;\n" +
                    "import com.baomidou.mybatisplus.core.metadata.IPage;\n" +
                    "import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;\n"+
                    "import org.springframework.stereotype.Service;\n"+
                    "import " + packagePath + ".entity." + child + ";\n" +
                    "import " + packagePath + ".mapper." + child + "Mapper;\n" +
                    "import " + packagePath + ".service.I" + child + "Service;\n\r" +
                    "@Service\n"+
                    "public class "+child+"ServiceImpl extends ServiceImpl<"+child+"Mapper, "+child+"> implements I"+child+"Service {\n\r").getBytes();

            output.write(startbytes);//将头信息数组写入文件中

            // 分页

            String sb = "\t@Override\n" +
                    "\tpublic IPage<" + child + "> page(IPage<" + child + "> page, QueryWrapper<" + child + "> wrapper) {\n" +
                    "\t\treturn this.baseMapper.selectPage(page, wrapper);\n" +
                    "\t}\n" +

                    // 保存
                    "\t@Override\n" +
                    "\tpublic boolean save(" + child + " entity) {\n" +
                    "\t\tint result = this.baseMapper.insert(entity);\n" +
                    "\t\treturn result == 1;\n" +
                    "\t}\n" +

                    // 跟新
                    "\t@Override\n" +
                    "\tpublic boolean updateById(" + child + " entity) {\n" +
                    "\t\tint result = this.baseMapper.updateById(entity);\n" +
                    "\t\treturn result == 1;\n" +
                    "\t}\n" +

                    // 删除
                    "\t@Override\n" +
                    "\tpublic boolean removeById(Long id) {\n" +
                    "\t\tint result = this.baseMapper.deleteById(id);\n" +
                    "\t\treturn result == 1;\n" +
                    "\t}\n" +

                    // 查单条
                    "\t@Override\n" +
                    "\tpublic " + child + " getById(Long id) {\n" +
                    "\t\treturn this.baseMapper.selectById(id);\n" +
                    "\t}\n";

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
