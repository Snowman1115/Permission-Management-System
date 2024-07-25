package com.pms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Permission Details
 * @author Sn0w_15
 * @since 2024-07-26
 */
@Getter
@Setter
@TableName("pms_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Permission ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Permission Label
     */
    private String label;

    /**
     * Parent Permission ID
     */
    private Long pid;

    /**
     * Parent Permission Name
     */
    private String pName;

    /**
     * Permission Authorization Code
     */
    private String code;

    /**
     * Router Path
     */
    private String path;

    /**
     * Router Name
     */
    private String name;

    /**
     * Authority Path
     */
    private String url;

    /**
     * Permission Type (0 = Directory : 1 = Menu : 2 = Button)
     */
    private Integer type;

    /**
     * Icon
     */
    private String icon;

    /**
     * Sorting Order
     */
    private Integer orderNumber;

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
