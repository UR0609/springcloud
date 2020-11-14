package com.ljryh.client.service.impl;

import com.ljryh.client.entity.OperationLog;
import com.ljryh.client.mapper.OperationLogMapper;
import com.ljryh.client.service.IOperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ljryh
 * @since 2020-11-03
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements IOperationLogService {

}
