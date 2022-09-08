-- 已执行
ALTER TABLE `t_inspection`
ADD COLUMN `review_status` tinyint NOT NULL DEFAULT 1 COMMENT '审核状态 0 未审核 1 已审核 ';


/*异常信息大屏删除文案字段  -- 已执行*/
ALTER TABLE `t_member_exception_record`
DROP COLUMN `detail`;

/*修改大屏唯一索引  -- 已执行 */
ALTER TABLE `t_member_exception_record`
DROP INDEX `idx_tmer_1` ,
ADD UNIQUE INDEX `idx_tmer_1` (`member_id`, `record_dt`, `hospital_id`) USING BTREE ;

