package com.ljryh.client.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljryh.client.entity.TablesEntity;
import com.ljryh.client.mapper.CodeGenerationMapper;
import com.ljryh.client.service.ICodeGenerationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ljryh
 * @date 2021/5/24 16:19
 */
@Slf4j
@Service
@Transactional(rollbackFor=Exception.class)
public class CodeGenerationServiceImpl extends ServiceImpl<CodeGenerationMapper, TablesEntity> implements ICodeGenerationService {



}
