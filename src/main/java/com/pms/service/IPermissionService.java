package com.pms.service;

import com.pms.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Permission Details Service Class
 * @author Sn0w_15
 * @since 2024-07-26
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * Get User's Permission List By Id
     * @param userId
     * @return List<Permission>
     */
    List<Permission> getPermissionListByUserId(Long userId);

}
