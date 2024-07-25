package com.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.common.constants.ResultConstants;
import com.pms.common.result.Result;
import com.pms.exception.ServiceException;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.pms.service.IPermissionService;
import com.pms.entity.Permission;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Permission Details Controller
 * @author Sn0w_15
 * @since 2024-07-26
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    private IPermissionService permissionService;

    /**
     * Get All Permission
     * @return Result
     */
    @GetMapping
    public Result getAllPermission() {
        return Result.success(permissionService.list());
    }

    /**
     * Get Permission By Id
     * @param id
     * @return Result
     */
    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Integer id) {
        return Result.success(permissionService.getById(id));
    }

    /**
     * Save Or Update Permission
     * @param permission
     * @return Result
     */
    @PostMapping
    public Result save(@RequestBody Permission permission) {
        try {
            permissionService.save(permission);
            return Result.success(null, "permission Saved Successfully");
        } catch (Exception e) {
            throw new ServiceException(ResultConstants.INTERNAL_SERVER_ERROR, "Server Error, Failed to Save permission. Please Try Again.");
        }
    }

    /**
     * Update Permission
     * @param permission
     * @return Result
     */
    @PutMapping
    public Result update(@RequestBody Permission permission) {
        if (permissionService.updateById(permission)) {
            return Result.success(null, "Permission Updated Successfully");
        }
        throw new ServiceException(ResultConstants.NOT_FOUND, "Update Fail, Permission Not Found. Please Try Again.");
    }

    /**
     * Delete Specific Permission
     * @param id
     * @return Result
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        if (permissionService.removeById(id)) {
            return Result.success(ResultConstants.OK,"Permission Removed Successfully");
        }
        return Result.error(ResultConstants.INTERNAL_SERVER_ERROR,"Server Error, Failed to Delete Permission. Please Try Again.");
    }

    /**
     * Batch Delete Permission
     * @param ids
     * @return Result
     */
    @DeleteMapping("/del/batch/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        if (permissionService.removeByIds(ids)) {
            return Result.success(ResultConstants.OK,"Permissions Removed Successfully");
        }
        return Result.error(ResultConstants.INTERNAL_SERVER_ERROR,"Server Error, Failed to Remove  Permissions. Please Try Again.");
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
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(permissionService.page(new Page<>(pageNum,pageSize), queryWrapper));
    }

}

