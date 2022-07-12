package com.ax.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName emps
 */
@TableName(value = "emps")
@Data
public class Emps implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private String lastName;

    /**
     *
     */
    private String email;

    /**
     *
     */
    private Integer gender;

    /**
     *
     */
    private Integer deptId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}