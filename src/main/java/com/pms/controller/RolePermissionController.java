package com.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.common.constants.ResultConstants;
import com.pms.common.result.Result;
import com.pms.exception.ServiceException;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.pms.service.IRolePermissionService;
import com.pms.entity.RolePermission;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * User Role Details Controller
 * @author Sn0w_15
 * @since 2024-07-26
 */
@RestController
@RequestMapping("/role-permission")
public class RolePermissionController {

    @Resource
    private IRolePermissionService rolePermissionService;

    /**
     * Get All RolePermission
     * @return Result
     */
    @GetMapping
    public Result getAllRolePermission() {
        return Result.success(rolePermissionService.list());
    }

    /**
     * Get RolePermission By Id
     * @param id
     * @return Result
     */
    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Integer id) {
        return Result.success(rolePermissionService.getById(id));
    }

    /**
     * Save Or Update RolePermission
     * @param rolePermission
     * @return Result
     */
    @PostMapping
    public Result save(@RequestBody RolePermission rolePermission) {
        try {
            rolePermissionService.save(rolePermission);
            return Result.success(null, "rolePermission Saved Successfully");
        } catch (Exception e) {
            throw new ServiceException(ResultConstants.INTERNAL_SERVER_ERROR, "Server Error, Failed to Save rolePermission. Please Try Again.");
        }
    }

    /**
     * Update RolePermission
     * @param rolePermission
     * @return Result
     */
    @PutMapping
    public Result update(@RequestBody RolePermission rolePermission) {
        if (rolePermissionService.updateById(rolePermission)) {
            return Result.success(null, "RolePermission Updated Successfully");
        }
        throw new ServiceException(ResultConstants.NOT_FOUND, "Update Fail, RolePermission Not Found. Please Try Again.");
    }

    /**
     * Delete Specific RolePermission
     * @param id
     * @return Result
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        if (rolePermissionService.removeById(id)) {
            return Result.success(ResultConstants.OK,"RolePermission Removed Successfully");
        }
        return Result.error(ResultConstants.INTERNAL_SERVER_ERROR,"Server Error, Failed to Delete RolePermission. Please Try Again.");
    }

    /**
     * Batch Delete RolePermission
     * @param ids
     * @return Result
     */
    @DeleteMapping("/del/batch/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        if (rolePermissionService.removeByIds(ids)) {
            return Result.success(ResultConstants.OK,"RolePermissions Removed Successfully");
        }
        return Result.error(ResultConstants.INTERNAL_SERVER_ERROR,"Server Error, Failed to Remove  RolePermissions. Please Try Again.");
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
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(rolePermissionService.page(new Page<>(pageNum,pageSize), queryWrapper));
    }

}

