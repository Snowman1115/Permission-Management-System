package com.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.common.constants.ResultConstants;
import com.pms.common.result.Result;
import com.pms.exception.ServiceException;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.pms.service.IDepartmentService;
import com.pms.entity.Department;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * All Department Details Controller
 * @author Sn0w_15
 * @since 2024-07-26
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Resource
    private IDepartmentService departmentService;

    /**
     * Get All Department
     * @return Result
     */
    @GetMapping
    public Result getAllDepartment() {
        return Result.success(departmentService.list());
    }

    /**
     * Get Department By Id
     * @param id
     * @return Result
     */
    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Integer id) {
        return Result.success(departmentService.getById(id));
    }

    /**
     * Save Or Update Department
     * @param department
     * @return Result
     */
    @PostMapping
    public Result save(@RequestBody Department department) {
        try {
            departmentService.save(department);
            return Result.success(null, "department Saved Successfully");
        } catch (Exception e) {
            throw new ServiceException(ResultConstants.INTERNAL_SERVER_ERROR, "Server Error, Failed to Save department. Please Try Again.");
        }
    }

    /**
     * Update Department
     * @param department
     * @return Result
     */
    @PutMapping
    public Result update(@RequestBody Department department) {
        if (departmentService.updateById(department)) {
            return Result.success(null, "Department Updated Successfully");
        }
        throw new ServiceException(ResultConstants.NOT_FOUND, "Update Fail, Department Not Found. Please Try Again.");
    }

    /**
     * Delete Specific Department
     * @param id
     * @return Result
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        if (departmentService.removeById(id)) {
            return Result.success(ResultConstants.OK,"Department Removed Successfully");
        }
        return Result.error(ResultConstants.INTERNAL_SERVER_ERROR,"Server Error, Failed to Delete Department. Please Try Again.");
    }

    /**
     * Batch Delete Department
     * @param ids
     * @return Result
     */
    @DeleteMapping("/del/batch/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        if (departmentService.removeByIds(ids)) {
            return Result.success(ResultConstants.OK,"Departments Removed Successfully");
        }
        return Result.error(ResultConstants.INTERNAL_SERVER_ERROR,"Server Error, Failed to Remove  Departments. Please Try Again.");
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
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(departmentService.page(new Page<>(pageNum,pageSize), queryWrapper));
    }

}

