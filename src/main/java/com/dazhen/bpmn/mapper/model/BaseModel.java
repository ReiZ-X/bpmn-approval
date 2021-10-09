package com.dazhen.bpmn.mapper.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @author junke
 */
@Data
public class BaseModel {
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    @TableField("create_by")
    private String createBy;
    @TableField("update_by")
    private String updateBy;
}
