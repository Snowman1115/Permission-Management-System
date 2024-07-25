package com.pms.service.impl;

import com.pms.entity.RolePermission;
import com.pms.mapper.RolePermissionMapper;
import com.pms.service.IRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User Role Details Service Implementation Class
 * @author Sn0w_15
 * @since 2024-07-26
 */
@Service
@Transactional
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

}
