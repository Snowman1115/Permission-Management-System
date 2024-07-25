package com.pms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Role Details
 * @author Sn0w_15
 * @since 2024-07-26
 */
@Getter
@Setter
@TableName("pms_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Role ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Role Code
     */
    private String roleCode;

    /**
     * Role Name
     */
    private String roleName;

    /**
     * Creator
     */
    private Long createUser;

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

}
