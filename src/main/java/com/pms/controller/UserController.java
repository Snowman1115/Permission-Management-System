package com.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.common.config.redis.RedisService;
import com.pms.common.constants.ResultConstants;
import com.pms.common.result.Result;
import com.pms.common.utils.JwtUtil;
import com.pms.common.utils.MenuTreeUtil;
import com.pms.entity.Permission;
import com.pms.entity.Router;
import com.pms.entity.UserInfo;
import com.pms.exception.ServiceException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Resource
    private RedisService redisService;

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
        String token = request.getHeader("token");
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
        Claims claims = JwtUtil.getClaimsFromToken(token);
        if (!ObjectUtils.isEmpty(claims)) {
            newToken = jwtUtil.refreshToken(token);
        }
        String oldTokenKey = "token_" + token;
        redisService.del(oldTokenKey);
        String newTokenKey = "token_" + newToken;
        redisService.set(newTokenKey, newToken, claims.getExpiration().getTime());
        return Result.success(newToken);
    }

    @GetMapping("/getInfo")
    public Result getInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return Result.error(ResultConstants.INTERNAL_SERVER_ERROR, "User Info Query Fail");
        }
        User user = (User) authentication.getPrincipal();
        List<Permission> permissionList = user.getPermissionList();
        Object[] roles =
                permissionList.stream()
                        .filter(Objects::nonNull)
                        .map(Permission::getCode).toArray();
        UserInfo userInfo = new UserInfo(user.getId(),user.getNickname(),user.getAvatar(),null,roles);
        return Result.success(userInfo);
    }

    @GetMapping("/getMenus")
    public Result getMenuList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<Permission> permissions = user.getPermissionList();
        List<Permission> collect = permissions.stream()
                .filter(item -> item != null && item.getType() != 2)
                .collect(Collectors.toList());
        // Generate Router
        List<Router> routers = MenuTreeUtil.genRouter(collect, 0L);
        return Result.success(routers);
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("token");
        if (ObjectUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request,response,authentication);
            redisService.del("token_" + token);
        }
        return Result.success("", "Logout Successful.");
    }

}

