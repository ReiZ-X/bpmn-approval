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
@TableName("bp_process")
public class ProcessDO {
    @TableId(type = IdType.AUTO)
    @TableField("id")
    private Integer id;
    @TableField("bpmn_xml")
    private String bpmnXml;
    @TableField("create_by")
    private String createBy;
    @TableField("create_time")
    private Date createTime;
    @TableField("name")
    private String name;
    @TableField("version")
    private Integer version;
}
