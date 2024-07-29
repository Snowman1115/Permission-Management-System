package com.pms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User Class
 * @author Sn0w_15
 * @since 2024-07-26
 */
@Getter
@Setter
@Data
@TableName("pms_user")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * User ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Account Username
     */
    private String username;

    /**
     * Account Password
     */
    private String password;

    /**
     * Account isExpired (0:NO, 1:YES)
     */
    private boolean isAccountNonExpired = true;

    /**
     * Account isLock (0:NO, 1:YES)
     */
    private boolean isAccountNonLocked = true;

    /**
     * Password isExpired (0:NO, 1:YES)
     */
    private boolean isCredentialsNonExpired = true;

    /**
     * Account isEnable (0:YES, 1:NO)
     */
    private boolean isEnabled = true;

    /**
     * User First Name
     */
    private String firstName;

    /**
     * User Last Name
     */
    private String lastName;

    /**
     * User Nickname
     */
    private String nickname;

    /**
     * Department ID
     */
    private Long departmentId;

    /**
     * Department Name
     */
    private String departmentName;

    /**
     *  Email
     */
    private String email;

    /**
     * Mobile Number
     */
    private String phone;

    /**
     * 0 = MALE : 1 = FEMALE
     */
    private Integer gender;

    /**
     * User Avatar Url
     */
    private String avatar;

    /**
     * 0 = NO : 1 = YES (SUPER_ADMIN)
     */
    private Integer isAdmin;

    /**
     * Last Login Date
     */
    private Date loginDate;

    /**
     * 0 = isActive : 1 isDeleted
     */
    private Integer isDelete;

    /**
     * Remarks
     */
    private String remark;

    /**
     * Create Time
     */
    private Date createTime;

    /**
     * Update Time
     */
    private Date updateTime;

    /**
     * Permission List
     */
    @TableField(exist = false)
    Collection<? extends GrantedAuthority> authorities;

    /**
     * Query User Permission List
     */
    @TableField(exist = false)
    private List<Permission> permissionList;

}
