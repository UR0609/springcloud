package com.ljryh.client.mapper;

import com.ljryh.client.entity.Children;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljryh
 * @since 2020-02-13
 */
public interface ChildrenMapper extends BaseMapper<Children> {

    void updateEntityByMap(@Param("params") Map<String, Object> params);

    void updateTest(Map<String, Object> map);

    void updateTest2(Map<String, Object> map);
}
