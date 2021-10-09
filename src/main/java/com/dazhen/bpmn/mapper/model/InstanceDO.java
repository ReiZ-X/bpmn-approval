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
@TableName("bp_instance")
public class InstanceDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("process_id")
    private Integer processId;

    @TableField("process_version")
    private Integer processVersion;

    @TableField("status")
    private Integer status;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_by")
    private String createBy;

}
