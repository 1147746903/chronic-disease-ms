--已执行

CREATE TABLE `t_member_committee_relation` (
  `sid` bigint(20) NOT NULL,
  `member_id` bigint(20) NOT NULL COMMENT '医生id',
  `committee_id` bigint(20) NOT NULL COMMENT '社区id',
  `committee_name` varchar(20) DEFAULT NULL COMMENT '社区名',
  `insert_dt` datetime NOT NULL COMMENT '插入时间',
  `modify_dt` datetime NOT NULL COMMENT '修改时间',
  `is_valid` tinyint(2) NOT NULL DEFAULT '1',
  PRIMARY KEY (`sid`),
  KEY `idx_tmcr_1` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='患者社区关联表';




