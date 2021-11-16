package com.ljryh.client.utils.generation.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateController {
    public void GenerateController(String parent, String child, String packagePath) {
        File file = new File(parent, child + "Controller.java");  //创建文件对象

        try {
            if (!file.exists()) {//如果文件不存在则新建文件
                file.createNewFile();
            }
            FileOutputStream output = new FileOutputStream(file);

            char[] name = child.toCharArray();
            name[0] += 32;
            String smart = String.valueOf(name);

            byte[] startbytes = ("package " + packagePath + ".controller;\n\r" +
                    "import " + packagePath + ".entity." + child + ";\n" +
                    "import " + packagePath + ".service.I" + child + "Service;\n" +
                    "import com.ljryh.common.entity.CallResult;\n" +
                    "import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;\n" +
                    "import com.baomidou.mybatisplus.core.metadata.IPage;\n" +
                    "import com.baomidou.mybatisplus.extension.plugins.pagination.Page;\n" +
                    "import org.springframework.web.bind.annotation.RequestBody;\n" +
                    "import org.springframework.web.bind.annotation.RequestMapping;\n" +
                    "import org.springframework.web.bind.annotation.RequestMethod;\n" +
                    "import org.springframework.web.bind.annotation.RestController;\n\n" +
                    "import javax.annotation.Resource;\n\n" +
                    "@RestController\n" +
                    "@RequestMapping(\"/" + smart + "\")\n" +
                    "public class " + child + "Controller {\n\r" +
                    "\t@Resource\n" +
                    "\tprivate I" + child + "Service " + smart + "Service;\n\r").getBytes();

            output.write(startbytes);//将头信息数组写入文件中

            // 分页查询

            String sb = "\t@RequestMapping(value = \"/list\", method = RequestMethod.POST)\n" +
                    "\tpublic Object findAll(@RequestBody " + child + " entity) {\n" +
                    "\t\tIPage<" + child + "> page = new Page<>(entity.getPageNo(), entity.getPageSize());\n" +
                    "\t\tQueryWrapper<" + child + "> wrapper = new QueryWrapper<>();\n" +
                    "\t\twrapper.setEntity(entity);\n" +
                    "\t\treturn CallResult.success(" + smart + "Service.page(page, wrapper));\n" +
                    "\t}\n\r" +

                    // 添加
                    "\t@RequestMapping(value = \"/add\", method = RequestMethod.POST)\n" +
                    "\tpublic Object add(@RequestBody " + child + " entity) {\n" +
                    "\t\tboolean judge = " + smart + "Service.save(entity);\n" +
                    "\t\treturn judge ? CallResult.success() : CallResult.fail();\n" +
                    "\t}\n\r" +

                    // 修改
                    "\t@RequestMapping(value = \"/mod\", method = RequestMethod.POST)\n" +
                    "\tpublic Object mod(@RequestBody " + child + " entity) {\n" +
                    "\t\tboolean judge = " + smart + "Service.updateById(entity);\n" +
                    "\t\treturn judge ? CallResult.success() : CallResult.fail();\n" +
                    "\t}\n\r" +

                    // 删除
                    "\t@RequestMapping(value = \"/del\", method = RequestMethod.POST)\n" +
                    "\tpublic Object del(@RequestBody " + child + " entity) {\n" +
                    "\t\tboolean judge = " + smart + "Service.removeById(entity.getId());\n" +
                    "\t\treturn judge ? CallResult.success() : CallResult.fail();\n" +
                    "\t}\n\r" +

                    // 查单条
                    "\t@RequestMapping(value = \"/sel\", method = RequestMethod.POST)\n" +
                    "\tpublic Object sel(@RequestBody " + child + " entity) {\n" +
                    "\t\t" + child + " result = " + smart + "Service.getById(entity.getId());\n" +
                    "\t\treturn result != null ? CallResult.success(result) : CallResult.fail();\n" +
                    "\t}\n\r";

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
