package com.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.common.constants.ResultConstants;
import com.pms.common.result.Result;
import com.pms.common.utils.JwtUtil;
import com.pms.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import com.pms.service.IUserService;
import com.pms.entity.User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * User Account and Details Controller
 * @author Sn0w_15
 * @since 2024-07-26
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;
    @Resource
    private JwtUtil jwtUtil;

    /**
     * Get All User
     * @return Result
     */
    @GetMapping
    public Result getAllUser() {
        return Result.success(userService.list());
    }

    /**
     * Get User By Id
     * @param id
     * @return Result
     */
    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

    /**
     * Save Or Update User
     * @param user
     * @return Result
     */
    @PostMapping
    public Result save(@RequestBody User user) {
        try {
            userService.save(user);
            return Result.success(null, "user Saved Successfully");
        } catch (Exception e) {
            throw new ServiceException(ResultConstants.INTERNAL_SERVER_ERROR, "Server Error, Failed to Save user. Please Try Again.");
        }
    }

    /**
     * Update User
     * @param user
     * @return Result
     */
    @PutMapping
    public Result update(@RequestBody User user) {
        if (userService.updateById(user)) {
            return Result.success(null, "User Updated Successfully");
        }
        throw new ServiceException(ResultConstants.NOT_FOUND, "Update Fail, User Not Found. Please Try Again.");
    }

    /**
     * Delete Specific User
     * @param id
     * @return Result
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        if (userService.removeById(id)) {
            return Result.success(ResultConstants.OK,"User Removed Successfully");
        }
        return Result.error(ResultConstants.INTERNAL_SERVER_ERROR,"Server Error, Failed to Delete User. Please Try Again.");
    }

    /**
     * Batch Delete User
     * @param ids
     * @return Result
     */
    @DeleteMapping("/del/batch/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        if (userService.removeByIds(ids)) {
            return Result.success(ResultConstants.OK,"Users Removed Successfully");
        }
        return Result.error(ResultConstants.INTERNAL_SERVER_ERROR,"Server Error, Failed to Remove  Users. Please Try Again.");
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
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(userService.page(new Page<>(pageNum,pageSize), queryWrapper));
    }

    /**
     * Refresh Token
     * @param request
     * @return Result
     */
    @PostMapping("/refreshToken")
    public Result refToken(HttpServletRequest request) {
        // Get Token from Header
        String token = request.getRequestURI();
        // Determine header isNull
        if (ObjectUtils.isEmpty(token)) {
            // Get token from parameters
            token = request.getParameter("token");
        }
        // get UserDetails from spring security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Get User Details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // Define Variable store new token
        String newToken = "";
        // Verify Post Token is Legal
        Claims claims = JwtUtil.parseJWT(token);
        if (ObjectUtils.isEmpty(claims)) {
            // newToken = jwtUtil.refreshToken(token);
        }
        return Result.success();
    }

}

