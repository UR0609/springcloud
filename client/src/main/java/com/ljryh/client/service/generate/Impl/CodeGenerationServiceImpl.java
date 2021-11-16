package com.ljryh.client.service.generate.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.ljryh.client.entity.TablesEntity;
import com.ljryh.client.entity.generate.ColumnData;
import com.ljryh.client.entity.generate.TableData;
import com.ljryh.client.mapper.generate.CodeGenerationMapper;
import com.ljryh.client.service.generate.ICodeGenerationService;
import com.ljryh.client.utils.generation.TypeConversionUtils;
import com.ljryh.client.utils.generation.file.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * @author ljryh
 * @date 2021/5/24 16:19
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CodeGenerationServiceImpl extends ServiceImpl<CodeGenerationMapper, TablesEntity> implements ICodeGenerationService {

    private static String packagePath;
    static {
        packagePath = CodeGenerationServiceImpl.class.getPackage().getName().split(".service")[0];
    }




    @Resource
    private CodeGenerationMapper codeGenerationMapper;

    @Override
    public List<TableData> getTablesData(TableData entity) {
        return codeGenerationMapper.getTablesData(entity);
    }

    @Override
    public List<ColumnData> getColumnData(TableData entity) {
        return codeGenerationMapper.getColumnData(entity);
    }

    @Override
    public void generateCode(List<ColumnData> list, String tableName) {


        // 获取用户目录
        String userDir = System.getProperty("user.dir");
//        System.out.println("userDir = " + userDir);

        //表明驼峰转化
        String child = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName);

        // 获取当前时间戳 微秒
        Long date = getmicTime();
        // 获取系统名称
        String os = System.getProperty("os.name");
//        if(os.toLowerCase().startsWith("win")){
//            System.out.println(os + " can't gunzip");
//        }

        File folder = new File(userDir + "\\generateCode");
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }

        String path =userDir + "\\generateCode\\" + child + "-" + date;

        File tableFile = new File(path);
        if (!tableFile.exists() && !tableFile.isDirectory()) {
            tableFile.mkdirs();
        }

        list = TypeConversionUtils.TypeConversion(list);

        // 生成实体
        GenerateEntity generateEntity = new GenerateEntity();
        generateEntity.GenerateEntity(path,child,list,packagePath);
        // 生成java mapper 文件
        GenerateJavaMapper generateJavaMapper = new GenerateJavaMapper();
        generateJavaMapper.GenerateJavaMapperf(path,child,packagePath);
        // 生成XML mapper 文件
        GenerateXmlMapper generateXmlMapper = new GenerateXmlMapper();
        generateXmlMapper.GenerateXmlMapper(path,child,list,packagePath,tableName);
        // 生成java service 文件
        GenerateService generateService = new GenerateService();
        generateService.GenerateService(path,child,packagePath);
        // 生成java service impl 文件
        GenerateServiceImpl generateServiceImpl = new GenerateServiceImpl();
        generateServiceImpl.GenerateServiceImpl(path,child,packagePath);
        // 生成java controller 文件
        GenerateController generateController = new GenerateController();
        generateController.GenerateController(path,child,packagePath);
        // 生成vue .vue 文件
        GenerateVue generateVue = new GenerateVue();
        generateVue.GenerateVue(path,child,list);
    }

    /**
     * @return返回微秒
     */
    public static Long getmicTime() {
        Long cutime = System.currentTimeMillis() * 1000; // 微秒
        Long nanoTime = System.nanoTime(); // 纳秒
        return cutime + (nanoTime - nanoTime / 1000000 * 1000000) / 1000;
    }

}
