package com.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.common.constants.ResultConstants;
import com.pms.common.result.Result;
import com.pms.exception.ServiceException;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.pms.service.IRoleService;
import com.pms.entity.Role;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Role Details Controller
 * @author Sn0w_15
 * @since 2024-07-26
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    /**
     * Get All Role
     * @return Result
     */
    @GetMapping
    public Result getAllRole() {
        return Result.success(roleService.list());
    }

    /**
     * Get Role By Id
     * @param id
     * @return Result
     */
    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Integer id) {
        return Result.success(roleService.getById(id));
    }

    /**
     * Save Or Update Role
     * @param role
     * @return Result
     */
    @PostMapping
    public Result save(@RequestBody Role role) {
        try {
            roleService.save(role);
            return Result.success(null, "role Saved Successfully");
        } catch (Exception e) {
            throw new ServiceException(ResultConstants.INTERNAL_SERVER_ERROR, "Server Error, Failed to Save role. Please Try Again.");
        }
    }

    /**
     * Update Role
     * @param role
     * @return Result
     */
    @PutMapping
    public Result update(@RequestBody Role role) {
        if (roleService.updateById(role)) {
            return Result.success(null, "Role Updated Successfully");
        }
        throw new ServiceException(ResultConstants.NOT_FOUND, "Update Fail, Role Not Found. Please Try Again.");
    }

    /**
     * Delete Specific Role
     * @param id
     * @return Result
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        if (roleService.removeById(id)) {
            return Result.success(ResultConstants.OK,"Role Removed Successfully");
        }
        return Result.error(ResultConstants.INTERNAL_SERVER_ERROR,"Server Error, Failed to Delete Role. Please Try Again.");
    }

    /**
     * Batch Delete Role
     * @param ids
     * @return Result
     */
    @DeleteMapping("/del/batch/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        if (roleService.removeByIds(ids)) {
            return Result.success(ResultConstants.OK,"Roles Removed Successfully");
        }
        return Result.error(ResultConstants.INTERNAL_SERVER_ERROR,"Server Error, Failed to Remove  Roles. Please Try Again.");
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
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(roleService.page(new Page<>(pageNum,pageSize), queryWrapper));
    }

}

