create table jobs
(
    id           int auto_increment
        primary key,
    related_id   varchar(50)   null comment '外部关联id',
    job_name     varchar(50)   null comment '任务名称',
    params_json  varchar(500)  null comment '任务参数',
    execute_time datetime      null comment '任务执行时间',
    class_name   varchar(100)  null comment 'job类名称',
    is_deleted   int default 0 null comment '逻辑删除 0未删除 1已删除'
);

