package com.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.common.constants.ResultConstants;
import com.pms.common.result.Result;
import com.pms.exception.ServiceException;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.pms.service.IUserRoleService;
import com.pms.entity.UserRole;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * User Role Details Controller
 * @author Sn0w_15
 * @since 2024-07-26
 */
@RestController
@RequestMapping("/user-role")
public class UserRoleController {

    @Resource
    private IUserRoleService userRoleService;

    /**
     * Get All UserRole
     * @return Result
     */
    @GetMapping
    public Result getAllUserRole() {
        return Result.success(userRoleService.list());
    }

    /**
     * Get UserRole By Id
     * @param id
     * @return Result
     */
    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Integer id) {
        return Result.success(userRoleService.getById(id));
    }

    /**
     * Save Or Update UserRole
     * @param userRole
     * @return Result
     */
    @PostMapping
    public Result save(@RequestBody UserRole userRole) {
        try {
            userRoleService.save(userRole);
            return Result.success(null, "userRole Saved Successfully");
        } catch (Exception e) {
            throw new ServiceException(ResultConstants.INTERNAL_SERVER_ERROR, "Server Error, Failed to Save userRole. Please Try Again.");
        }
    }

    /**
     * Update UserRole
     * @param userRole
     * @return Result
     */
    @PutMapping
    public Result update(@RequestBody UserRole userRole) {
        if (userRoleService.updateById(userRole)) {
            return Result.success(null, "UserRole Updated Successfully");
        }
        throw new ServiceException(ResultConstants.NOT_FOUND, "Update Fail, UserRole Not Found. Please Try Again.");
    }

    /**
     * Delete Specific UserRole
     * @param id
     * @return Result
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        if (userRoleService.removeById(id)) {
            return Result.success(ResultConstants.OK,"UserRole Removed Successfully");
        }
        return Result.error(ResultConstants.INTERNAL_SERVER_ERROR,"Server Error, Failed to Delete UserRole. Please Try Again.");
    }

    /**
     * Batch Delete UserRole
     * @param ids
     * @return Result
     */
    @DeleteMapping("/del/batch/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        if (userRoleService.removeByIds(ids)) {
            return Result.success(ResultConstants.OK,"UserRoles Removed Successfully");
        }
        return Result.error(ResultConstants.INTERNAL_SERVER_ERROR,"Server Error, Failed to Remove  UserRoles. Please Try Again.");
    }

    /**
     * Page Query
     * @param pageNum
     * @param pageSize
     * @return Result
     */
    @GetMapping("/page")
    public Result getPage(@RequestParam Integer pageNum,
                          @RequestParam Integer pageSize) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(userRoleService.page(new Page<>(pageNum,pageSize), queryWrapper));
    }

}

