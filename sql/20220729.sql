--已执行  0816

ALTER TABLE `t_doctor`
ADD COLUMN `doctor_level`  tinyint(4) NULL DEFAULT NULL COMMENT '医生级别  1县级 2乡镇级 3村级' AFTER `union_id`;


CREATE TABLE `t_doctor_committee_relation` (
  `sid` bigint(20) NOT NULL,
  `doctor_id` bigint(20) NOT NULL COMMENT '医生id',
  `committee_id` bigint(20) NOT NULL COMMENT '社区id',
  `insert_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_valid` tinyint(2) NOT NULL DEFAULT '1',
  PRIMARY KEY (`sid`),
  KEY `idx_tdr_1` (`doctor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医生村所关联表';


INSERT INTO `chronic_disease_ms`.`t_dict` (`dict_id`, `dict_code`, `dict_value`, `dict_desc`, `parent_code`, `insert_dt`, `update_dt`, `valid`) VALUES ('98975053021970565', 'WX_QUICK_LOGIN', '0', '微信小程序一键登录', 'WX_QUICK_LOGIN', '2022-08-09 09:40:24', '2022-08-09 09:40:24', '1');


ALTER TABLE `t_follow_diabetes`
ADD COLUMN `xltz`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '心理调整  JG01良好   JG02一般  JG03差' AFTER `yqddmb`,
ADD COLUMN `zyxw`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '遵医行为  JG01良好   JG02一般  JG03差' AFTER `xltz`;


ALTER TABLE `t_hospital_committee`
ADD COLUMN `town_name`  varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '所属乡镇名' AFTER `valid`;



UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='熊山街道' WHERE (`id`='307');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='熊山街道' WHERE (`id`='308');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='熊山街道' WHERE (`id`='309');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='熊山街道' WHERE (`id`='310');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='熊山街道' WHERE (`id`='311');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='熊山街道' WHERE (`id`='312');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='熊山街道' WHERE (`id`='313');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='熊山街道' WHERE (`id`='314');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='熊山街道' WHERE (`id`='315');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='铁山镇' WHERE (`id`='316');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='铁山镇' WHERE (`id`='317');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='铁山镇' WHERE (`id`='318');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='铁山镇' WHERE (`id`='319');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='铁山镇' WHERE (`id`='320');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='铁山镇' WHERE (`id`='321');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='铁山镇' WHERE (`id`='322');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='铁山镇' WHERE (`id`='323');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='铁山镇' WHERE (`id`='324');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='铁山镇' WHERE (`id`='325');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='铁山镇' WHERE (`id`='326');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='铁山镇' WHERE (`id`='327');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='铁山镇' WHERE (`id`='328');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='铁山镇' WHERE (`id`='329');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='东平镇' WHERE (`id`='330');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='东平镇' WHERE (`id`='331');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='东平镇' WHERE (`id`='332');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='东平镇' WHERE (`id`='333');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='东平镇' WHERE (`id`='334');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='东平镇' WHERE (`id`='335');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='东平镇' WHERE (`id`='336');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='东平镇' WHERE (`id`='337');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='东平镇' WHERE (`id`='338');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='东平镇' WHERE (`id`='339');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='东平镇' WHERE (`id`='340');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='东平镇' WHERE (`id`='341');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='东平镇' WHERE (`id`='342');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='石屯镇' WHERE (`id`='343');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='石屯镇' WHERE (`id`='344');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='石屯镇' WHERE (`id`='345');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='石屯镇' WHERE (`id`='346');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='石屯镇' WHERE (`id`='347');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='石屯镇' WHERE (`id`='348');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='石屯镇' WHERE (`id`='349');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='石屯镇' WHERE (`id`='350');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='352');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='353');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='354');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='355');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='356');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='357');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='358');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='359');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='360');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='361');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='362');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='363');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='364');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='365');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='366');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='367');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='368');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='369');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='370');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='371');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='372');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='镇前镇' WHERE (`id`='373');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='星溪乡' WHERE (`id`='374');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='星溪乡' WHERE (`id`='375');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='星溪乡' WHERE (`id`='376');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='星溪乡' WHERE (`id`='377');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='星溪乡' WHERE (`id`='378');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='星溪乡' WHERE (`id`='379');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='星溪乡' WHERE (`id`='380');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='星溪乡' WHERE (`id`='381');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='星溪乡' WHERE (`id`='382');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='星溪乡' WHERE (`id`='383');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='星溪乡' WHERE (`id`='384');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='外屯乡' WHERE (`id`='385');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='外屯乡' WHERE (`id`='386');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='外屯乡' WHERE (`id`='387');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='外屯乡' WHERE (`id`='388');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='外屯乡' WHERE (`id`='389');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='外屯乡' WHERE (`id`='390');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='外屯乡' WHERE (`id`='391');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='外屯乡' WHERE (`id`='392');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='外屯乡' WHERE (`id`='393');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='杨源乡' WHERE (`id`='394');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='杨源乡' WHERE (`id`='395');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='杨源乡' WHERE (`id`='396');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='杨源乡' WHERE (`id`='397');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='杨源乡' WHERE (`id`='398');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='杨源乡' WHERE (`id`='399');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='杨源乡' WHERE (`id`='400');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='杨源乡' WHERE (`id`='401');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='杨源乡' WHERE (`id`='402');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='杨源乡' WHERE (`id`='403');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='杨源乡' WHERE (`id`='404');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='杨源乡' WHERE (`id`='405');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='杨源乡' WHERE (`id`='406');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='杨源乡' WHERE (`id`='407');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='杨源乡' WHERE (`id`='408');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='409');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='410');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='411');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='412');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='413');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='414');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='415');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='416');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='417');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='418');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='419');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='420');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='421');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='422');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='423');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='424');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='425');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='426');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='427');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='428');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='429');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='430');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='岭腰乡' WHERE (`id`='431');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='岭腰乡' WHERE (`id`='432');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='岭腰乡' WHERE (`id`='433');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='岭腰乡' WHERE (`id`='434');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='岭腰乡' WHERE (`id`='435');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='岭腰乡' WHERE (`id`='436');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='岭腰乡' WHERE (`id`='437');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='熊山街道' WHERE (`id`='2207');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='熊山街道' WHERE (`id`='2267');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='2367');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='澄源乡' WHERE (`id`='2368');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='星溪乡' WHERE (`id`='3107');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='石屯镇' WHERE (`id`='3307');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='熊山街道' WHERE (`id`='3807');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='熊山街道' WHERE (`id`='3808');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='熊山街道' WHERE (`id`='3809');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='熊山街道' WHERE (`id`='3827');
UPDATE `chronic_disease_ms`.`t_hospital_committee` SET `town_name`='熊山街道' WHERE (`id`='3828');
