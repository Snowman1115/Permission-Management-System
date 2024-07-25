package com.pms.service.impl;

import com.pms.entity.UserRole;
import com.pms.mapper.UserRoleMapper;
import com.pms.service.IUserRoleService;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
