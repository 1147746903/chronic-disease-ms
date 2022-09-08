-- 未执行
ALTER TABLE `t_user_double_factor`
    ADD COLUMN `user_type` tinyint(2) NOT NULL DEFAULT 1 COMMENT '用户类型 1 医生  2 后台管理员' ;

ALTER TABLE `t_user`
    ADD COLUMN `login_fail_times` int(8) NOT NULL DEFAULT 0 COMMENT '登录失败次数' AFTER `open_id`,
ADD COLUMN `last_update_password_dt` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上次修改密码时间' AFTER `login_fail_times`,
ADD COLUMN `lock_dt` datetime(0) NULL COMMENT '账号锁定时间' AFTER `last_update_password_dt`;

ALTER TABLE `t_admin`
    ADD COLUMN `login_fail_times` int(8) NOT NULL DEFAULT 0 COMMENT '登录失败次数' ,
ADD COLUMN `last_update_password_dt` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上次修改密码时间',
ADD COLUMN `lock_dt` datetime(0) NULL COMMENT '账号锁定时间';