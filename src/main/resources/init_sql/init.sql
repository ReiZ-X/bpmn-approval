-- auto-generated definition
create table bp_instance
(
    id              bigint auto_increment
        primary key,
    process_id      int null,
    process_version int null,
    status          int      default 0 null,
    create_time     datetime default CURRENT_TIMESTAMP null,
    create_by       varchar(50) null
);


-- auto-generated definition
create table bp_process
(
    id          int auto_increment
        primary key,
    name        varchar(50) null,
    bpmn_xml    longtext null,
    version     int      default 0 null,
    create_by   varchar(50) null,
    create_time datetime default CURRENT_TIMESTAMP null,
    constraint bp_process_name_uindex
        unique (name)
);

-- auto-generated definition
create table bp_process_his
(
    id          bigint auto_increment
        primary key,
    process_id  int null,
    name        varchar(50) null,
    bpmn_xml    longtext null,
    version     tinyint null,
    create_by   varchar(50) null,
    create_time datetime default CURRENT_TIMESTAMP null,
    constraint bp_process_his_process_id_version_uindex
        unique (process_id, version)
);

-- auto-generated definition
create table bp_task
(
    id            bigint auto_increment
        primary key,
    instance_id   bigint null,
    user_task_id  varchar(50) null,
    assignee      varchar(50) null,
    assignee_role varchar(50) null,
    candidates    varchar(100) null,
    comment       varchar(200) null comment '审批备注',
    status        tinyint  default 0 null comment '0待审批 1通过 2拒绝',
    create_time   datetime default CURRENT_TIMESTAMP null,
    create_by     varchar(50) null
);

