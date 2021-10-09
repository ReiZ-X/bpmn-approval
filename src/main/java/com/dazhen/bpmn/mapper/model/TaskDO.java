package com.dazhen.bpmn.mapper.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author junke
 */
@Data
@TableName("bp_task")
public class TaskDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("instance_id")
    private Long instanceId;
    @TableField("user_task_id")
    private String userTaskId;
    @TableField("assignee")
    private String assignee;
    @TableField("assignee_role")
    private String assigneeRole;
    @TableField("candidates")
    private String candidates;
    @TableField("status")
    private Integer status;
    @TableField("comment")
    private String comment;
    @TableField("create_time")
    private Date createTime;
    @TableField("create_by")
    private String createBy;
}
