package com.pms.service.impl;

import com.pms.entity.User;
import com.pms.mapper.UserMapper;
import com.pms.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User Account and Details Service Implementation Class
 * @author Sn0w_15
 * @since 2024-07-26
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
