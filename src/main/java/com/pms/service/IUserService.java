package com.pms.service;

import com.pms.entity.Permission;
import com.pms.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * User Account and Details Service Class
 * @author Sn0w_15
 * @since 2024-07-26
 */
public interface IUserService extends IService<User> {

    /**
     * Find User Details By Username
     * @param username
     * @return User
     */
    User findUserByUsername(String username);

}
