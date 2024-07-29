package com.pms.service.impl;

import com.pms.entity.Permission;
import com.pms.mapper.PermissionMapper;
import com.pms.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Permission Details Service Implementation Class
 * @author Sn0w_15
 * @since 2024-07-26
 */
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    /**
     * Get User's Permission List By Id
     * @param userId
     * @return List<Permission>
     */
    @Override
    public List<Permission> getPermissionListByUserId(Long userId) {
        return baseMapper.getPermissionListByUserId(userId);
    }

}
