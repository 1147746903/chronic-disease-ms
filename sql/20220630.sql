
ALTER TABLE `t_followup_set`
ADD COLUMN `hospital_id`  bigint(20) NOT NULL DEFAULT -1 COMMENT '医院id' AFTER `update_dt`;

ALTER TABLE `t_follow_main`
ADD COLUMN `hospital_id`  bigint(20) NULL DEFAULT -1 COMMENT '医院id' AFTER `template_id`;

CREATE TABLE `t_data_sync_task` (
  `sid` bigint(20) NOT NULL COMMENT '主键id',
  `member_id` bigint(20) NOT NULL COMMENT '患者id',
  `hospital_id` bigint(20) DEFAULT NULL COMMENT '医院id',
  `doctor_id` bigint(20) DEFAULT NULL COMMENT '医生id',
  `task_type` tinyint(4) NOT NULL COMMENT '数据类型  1检验 2检查 3医嘱',
  `status` tinyint(4) NOT NULL COMMENT '状态 0未执行 1已执行 2失败',
  `insert_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `update_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据同步任务表';



ALTER TABLE `t_member`
ADD COLUMN `social_card`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '社保卡号' AFTER `member_name_pys`;
-- 已执行



