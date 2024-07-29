package com.pms.mapper;

import com.pms.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Permission Details Mapper
 * @author Sn0w_15
 * @since 2024-07-26
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {


    /**
     * Get User's Permission List By Id
     * @param userId
     * @return List<Permission>
     */
    List<Permission> getPermissionListByUserId(Long userId);

}
