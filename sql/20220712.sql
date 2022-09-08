-- 已执行
ALTER TABLE `t_hospital`
DROP COLUMN `town_id`,
DROP COLUMN `town_name`;

-- 已执行
ALTER TABLE `t_member_level`
ADD COLUMN `confirm_dt`  datetime NULL DEFAULT NULL COMMENT '确认时间' AFTER `modify_dt`;

INSERT INTO `chronic_disease_ms`.`t_authority` (`sid`, `permission`, `description`, `insert_dt`, `available`, `pid`, `a_type`, `show_status`) VALUES ('120003', 'wz-hzgl-mbhzsc', '慢病患者删除', '2022-03-02 15:06:12', '1', '100002', '10', '1');

-- 已执行 0725
DELETE FROM t_hospital_exception_item WHERE item_code in ('today_visit','year_check');

ALTER TABLE `t_doctor`
ADD COLUMN `union_id`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开放平台id' AFTER `is_agent_doctor`;

ALTER TABLE `t_member_level`
ADD COLUMN `change_reason`  varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '改变理由' AFTER `confirm_dt`;

-- 已执行 0726

update t_member
set committee_id = null,set committee_name = null
where committee_id in (select id from t_hospital_committee where committee_name = '流动人口');

delete from t_hospital_committee where committee_name = '流动人口';
