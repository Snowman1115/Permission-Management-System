package com.pms.service.impl;

import com.pms.entity.Role;
import com.pms.mapper.RoleMapper;
import com.pms.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Role Details Service Implementation Class
 * @author Sn0w_15
 * @since 2024-07-26
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
