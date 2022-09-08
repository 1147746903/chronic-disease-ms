insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(1 ,1 ,'您的食欲怎么样呢？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(1 ,1 ,1 ,' 食欲正常 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(2 ,1 ,2 ,' 食欲减退 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(3 ,1 ,3 ,' 食欲亢进（经常吃得很饱很撑） ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(4 ,1 ,4 ,' 吃完容易饿 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(5 ,1 ,5 ,' 觉得饿但不想吃 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(2 ,2 ,'您的饮食习惯是怎样的？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(6 ,2 ,1 ,' 一日三餐，规律饮食 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(7 ,2 ,2 ,' 饮食不规律 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(8 ,2 ,3 ,' 经常不吃早餐 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(9 ,2 ,4 ,' 经常不吃晚餐 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(10 ,2 ,5 ,' 经常吃夜宵 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(3 ,3 ,'您的饮食结构是？ (单选)' ,1 ,1 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(11 ,3 ,1 ,' 荤素比例差不多 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(12 ,3 ,2 ,' 以肉食为主 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(13 ,3 ,3 ,' 以素食为主 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(4 ,4 ,'您的饮食口味是？（口味轻重以外出就餐餐厅口味为参考） (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(14 ,4 ,1 ,' 口味清淡，喜食蒸煮类食物，少油少盐 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(15 ,4 ,2 ,' 口味偏重、多油多盐 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(16 ,4 ,3 ,' 爱吃甜的或油腻的 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(17 ,4 ,4 ,' 爱吃辛辣或煎炸类食物 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(18 ,4 ,5 ,' 高嘌呤饮食（反复烧开的汤及菌菇、豆类、海鲜、动物内脏等） ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(19 ,4 ,6 ,' 爱吃生冷的食物 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(20 ,4 ,7 ,' 爱吃滚烫的食物 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(5 ,5 ,'您近来的睡眠情况怎么样？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(21 ,5 ,1 ,' 按时入睡，梦不多，一觉到天亮 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(22 ,5 ,2 ,' 睡眠轻浅容易醒 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(23 ,5 ,3 ,' 睡醒后依然觉得很累 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(24 ,5 ,4 ,' 睡觉时多梦 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(25 ,5 ,5 ,' 难以入眠 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(26 ,5 ,6 ,' 经常觉得困，但睡不着 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(27 ,5 ,7 ,' 经常觉得困倦，随时都想睡觉 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(6 ,6 ,'您大便情况怎么样？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(28 ,6 ,1 ,' 每1-2天1次，大便成形，排便顺畅 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(29 ,6 ,2 ,' 每天1次，大便稀 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(30 ,6 ,3 ,' 每天多次，大便不成形，甚至稀薄 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(31 ,6 ,4 ,'  3天甚至更久一次 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(32 ,6 ,5 ,' 大便时干时稀 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(33 ,6 ,6 ,' 大便干硬，或排便困难 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(34 ,6 ,7 ,' 大便粘滞感，排出不畅 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(35 ,6 ,8 ,' 大便夹杂血液 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(7 ,7 ,'您的小便情况怎么样？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(36 ,7 ,1 ,' 小便顺畅，色淡，无特殊气味 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(37 ,7 ,2 ,' 小便量多色清 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(38 ,7 ,3 ,' 小便色比正常黄，尿量减少 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(39 ,7 ,4 ,' 小便浑浊不清 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(40 ,7 ,5 ,' 夜间小便次数增多（大于2次） ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(41 ,7 ,6 ,' 小便排出不畅 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(42 ,7 ,7 ,' 小便有疼痛感 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(8 ,8 ,'您平常怕冷还是怕热吗？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(43 ,8 ,1 ,' 既不怕冷也不怕热 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(44 ,8 ,2 ,' 日常比较怕冷 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(45 ,8 ,3 ,' 平时比较怕热 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(46 ,8 ,4 ,' 有怕风的感觉 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(47 ,8 ,5 ,' 一会儿发冷一会儿发热 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(48 ,8 ,6 ,' 经常感觉一阵子发热 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(49 ,8 ,7 ,' 很有规律的按时发热 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(9 ,9 ,'您经常感到疲乏无力吗？ (单选)' ,1 ,1 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(50 ,9 ,1 ,' 不会，感觉精力充沛 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(51 ,9 ,2 ,' 会，但稍活动后会缓解 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(52 ,9 ,3 ,' 会，而且稍活动后就加重 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(10 ,10 ,'您喝水情况怎么样？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(53 ,10 ,1 ,' 不容易感到口渴 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(54 ,10 ,2 ,' 容易口渴且饮水很多 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(55 ,10 ,3 ,' 容易口渴但饮水不多 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(56 ,10 ,4 ,' 容易口渴但不想喝水 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(57 ,10 ,5 ,' 喝水温度：冷水热水都可以 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(58 ,10 ,6 ,' 平常喜欢喝冷水 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(59 ,10 ,7 ,' 平常喜欢喝热水 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(11 ,11 ,'您的头部有无不适？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(60 ,11 ,1 ,' 没有头部不适 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(61 ,11 ,2 ,' 单侧/两侧头痛 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(62 ,11 ,3 ,' 头顶头痛 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(63 ,11 ,4 ,' 头后及脖子疼痛 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(64 ,11 ,5 ,' 整个头都疼痛 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(65 ,11 ,6 ,' 感觉头晕 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(66 ,11 ,7 ,' 经常脱发 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(12 ,12 ,'您的眼睛有无不适？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(67 ,12 ,1 ,' 眼睛没有感觉不适 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(68 ,12 ,2 ,' 眼睛干涩 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(69 ,12 ,3 ,' 眼睛痒 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(70 ,12 ,4 ,' 眼睛痛 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(71 ,12 ,5 ,' 视物模糊不清（非近视或远视导致） ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(72 ,12 ,6 ,' 眼白有血丝 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(13 ,13 ,'您的鼻子有无不适？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(73 ,13 ,1 ,' 鼻子没有感觉不适 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(74 ,13 ,2 ,' 经常鼻塞 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(75 ,13 ,3 ,' 经常流鼻涕（清稀） ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(76 ,13 ,4 ,' 经常流鼻涕（浓稠） ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(77 ,13 ,5 ,' 经常鼻子痒 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(78 ,13 ,6 ,' 经常打喷嚏 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(79 ,13 ,7 ,' 经常流鼻血 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(14 ,14 ,'您的耳朵有无不适？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(80 ,14 ,1 ,'耳朵没有感觉不适' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(81 ,14 ,2 ,' 经常耳鸣，声音如蝉 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(82 ,14 ,3 ,' 耳鸣突发，声音如潮水 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(83 ,14 ,4 ,' 听力逐渐减退，甚至听不见 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(84 ,14 ,5 ,' 听力突然下降，甚至听不见 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(85 ,14 ,6 ,' 耳道内瘙痒 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(86 ,14 ,7 ,' 耳道流脓 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(15 ,15 ,'您的口腔有无不适？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(87 ,15 ,1 ,' 口腔没有不适感 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(88 ,15 ,2 ,' 口腔感觉寡淡无味 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(89 ,15 ,3 ,' 口腔感觉苦味 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(90 ,15 ,4 ,' 口腔感觉甜味 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(91 ,15 ,5 ,' 口腔感觉酸味 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(92 ,15 ,6 ,' 口有咸味 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(93 ,15 ,7 ,' 口腔有涩涩的感觉 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(94 ,15 ,8 ,' 口腔溃疡 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(95 ,15 ,9 ,' 口有粘腻感 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(96 ,15 ,10 ,' 口臭 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(97 ,15 ,11 ,' 口唇干燥 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(98 ,15 ,12 ,' 牙龈出血 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(99 ,15 ,13 ,' 牙齿松动，甚至脱落 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(16 ,16 ,'您的咽喉有无不适？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(100 ,16 ,1 ,' 咽喉部没有感觉不适 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(101 ,16 ,2 ,' 咽干 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(102 ,16 ,3 ,' 咽喉疼痛 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(103 ,16 ,4 ,' 咽中有异物感 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(104 ,16 ,5 ,' 吞咽困难 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(105 ,16 ,6 ,' 近日突然的声音沙哑 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(106 ,16 ,7 ,' 经常声音沙哑 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(17 ,17 ,'您有咳嗽的症状吗？ (多选) ' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(107 ,17 ,1 ,' 没有咳嗽 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(108 ,17 ,2 ,' 长期反复咳嗽 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(109 ,17 ,3 ,' 近期偶然有些咳嗽 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(110 ,17 ,4 ,' 咳嗽发生在夜间为主 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(111 ,17 ,5 ,'近期有剧烈咳嗽' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(18 ,18 ,'您咳嗽的时候有痰吗？ (多选) ' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(112 ,18 ,1 ,' 没有痰 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(113 ,18 ,2 ,' 咳嗽伴少量痰 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(114 ,18 ,3 ,' 咳嗽伴很多痰 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(115 ,18 ,4 ,' 痰清稀 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(116 ,18 ,5 ,' 痰浓稠 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(117 ,18 ,6 ,' 痰黄色 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(118 ,18 ,7 ,' 痰白色 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(119 ,18 ,8 ,' 痰色黄白相间 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(120 ,18 ,9 ,' 咳嗽有泡沫样痰 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(121 ,18 ,10 ,' 有痰但难以咳出 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(19 ,19 ,'您平时有痰吗？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(122 ,19 ,1 ,' 没有咳痰 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(123 ,19 ,2 ,' 经常有少量痰 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(124 ,19 ,3 ,' 经常有很多痰 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(125 ,19 ,4 ,' 痰清稀 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(126 ,19 ,5 ,' 痰浓稠 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(127 ,19 ,6 ,' 痰黄色 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(128 ,19 ,7 ,' 痰白色 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(129 ,19 ,8 ,' 痰色黄白相间 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(130 ,19 ,9 ,' 咳有泡沫样痰 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(131 ,19 ,10 ,' 有痰但难以咳出 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(132 ,19 ,11 ,'仅干咳' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(20 ,20 ,'您经常觉得心慌或不自主地心跳加快吗？ (单选)' ,1 ,1 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(133 ,20 ,1 ,'没有' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(134 ,20 ,2 ,'有的' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(21 ,21 ,'您的胸部有无不适？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(135 ,21 ,1 ,' 胸部没有感觉不适 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(136 ,21 ,2 ,' 近来气喘 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(137 ,21 ,3 ,' 长期气喘 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(138 ,21 ,4 ,' 胸闷 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(139 ,21 ,5 ,' 胸部疼痛 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(140 ,21 ,6 ,' 两胁疼痛 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(22 ,22 ,'您的腰、腹部有无不适？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(141 ,22 ,1 ,' 腰、腹部没有感觉不适 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(142 ,22 ,2 ,' 腰酸 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(143 ,22 ,3 ,' 胃胀 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(144 ,22 ,4 ,' 胃痛，进食后减轻 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(145 ,22 ,5 ,' 胃痛，进食后增加 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(146 ,22 ,6 ,' 小肚子痛 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(147 ,22 ,7 ,' 肚脐周围疼痛 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(23 ,23 ,'您的四肢有无不适？ (多选) ' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(148 ,23 ,1 ,' 四肢没有感觉不适 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(149 ,23 ,2 ,' 四肢冰冷 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(150 ,23 ,3 ,' 四肢酸软无力 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(151 ,23 ,4 ,' 四肢麻木 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(152 ,23 ,5 ,' 四肢水肿 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(153 ,23 ,6 ,' 关节疼痛 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(154 ,23 ,7 ,' 一侧肢体不能正常活动 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(24 ,24 ,'您是否存在汗出异常？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(155 ,24 ,1 ,' 没有异常出汗 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(156 ,24 ,2 ,' 手脚心汗多 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(157 ,24 ,3 ,' 睡觉时出汗，醒的时候汗就没有 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(158 ,24 ,4 ,' 醒着时容易出汗，动则更甚 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(159 ,24 ,5 ,' 先感到一阵热而汗出热退 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(160 ,24 ,6 ,' 半身汗出（左右或上下） ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(25 ,25 ,'哪些情绪容易引起身体不适？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(161 ,25 ,1 ,' 与情绪没有明显相关 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(162 ,25 ,2 ,' 兴奋 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(163 ,25 ,3 ,' 生气 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(164 ,25 ,4 ,' 忧愁 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(165 ,25 ,5 ,' 思虑 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(166 ,25 ,6 ,' 悲伤 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(167 ,25 ,7 ,' 恐惧 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(168 ,25 ,8 ,' 惊吓 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(26 ,26 ,'目前的情绪有没有异常？ (单选)' ,1 ,1 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(169 ,26 ,1 ,' 情绪稳定，没有异常 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(170 ,26 ,2 ,' 很兴奋，止不住语言高亢 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(171 ,26 ,3 ,' 感觉心烦，躁动 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(172 ,26 ,4 ,' 情绪低落，甚至有悲伤欲哭的感觉 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(173 ,26 ,5 ,' 感觉紧张恐惧，甚至惶惶终日的感觉 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(27 ,27 ,'哪些环境特点容易引起身体不适？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(174 ,27 ,1 ,' 与环境特点没有明显关系 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(175 ,27 ,2 ,' 潮湿 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(176 ,27 ,3 ,' 寒冷 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(177 ,27 ,4 ,' 干燥 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(178 ,27 ,5 ,' 炎热 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(28 ,28 ,'哪些季节容易引起身体不适？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(179 ,28 ,1 ,' 与季节没有明显关系 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(180 ,28 ,2 ,' 春天 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(181 ,28 ,3 ,' 夏天 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(182 ,28 ,4 ,' 秋天 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(183 ,28 ,5 ,' 冬天 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(184 ,28 ,6 ,' 性欲正常 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(185 ,28 ,7 ,' 性欲亢进 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(186 ,28 ,8 ,' 性欲减退 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(29 ,29 ,'您月经情况如何？ (多选)' ,3 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(187 ,29 ,1 ,' 月经正常 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(188 ,29 ,2 ,' 闭经 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(189 ,29 ,3 ,' 已绝经 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(190 ,29 ,4 ,' 月经先后不定期 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(191 ,29 ,5 ,' 月经推后7天以上 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(192 ,29 ,6 ,' 月经提前7天以上 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(193 ,29 ,7 ,' 月经量明显增多 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(194 ,29 ,8 ,' 月经量明显减少 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(195 ,29 ,9 ,' 痛经 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(196 ,29 ,10 ,' 月经有血块 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(197 ,29 ,11 ,' 月经色淡 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(30 ,30 ,'您平时运动的频率如何？ (单选)' ,1 ,1 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(198 ,30 ,1 ,' 每天坚持 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(199 ,30 ,2 ,' 每周几次 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(200 ,30 ,3 ,' 每月几次 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(201 ,30 ,4 ,' 从不运动 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(31 ,31 ,'您是否抽烟喝酒？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(202 ,31 ,1 ,' 不抽烟、不喝酒 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(203 ,31 ,2 ,' 偶尔抽烟 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(204 ,31 ,3 ,' 烟瘾很重 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(205 ,31 ,4 ,' 偶尔喝酒 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(206 ,31 ,5 ,' 经常大量饮酒 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(32 ,32 ,'您的起居习惯是？ (多选)' ,1 ,2 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(207 ,32 ,1 ,' 按时起居，很规律 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(208 ,32 ,2 ,' 夜猫子型，黑白颠倒 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(209 ,32 ,3 ,' 经常加班熬夜，睡眠不足 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(210 ,32 ,4 ,' 偶尔会午休 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(211 ,32 ,5 ,' 有经常午休的习惯 ' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(212 ,32 ,6 ,' 没有午休的习惯 ' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(33 ,33 ,'您的收缩压水平？（mmHg）' ,1 ,1 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(213 ,33 ,1 ,'＜110' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(214 ,33 ,2 ,'110-119' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(215 ,33 ,3 ,'120-129' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(216 ,33 ,4 ,'130-139' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(217 ,33 ,5 ,'140-149' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(218 ,33 ,6 ,'150-159' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(219 ,33 ,7 ,'160-179' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(220 ,33 ,8 ,'≥180' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(221 ,33 ,9 ,'不知道' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(34 ,34 ,'您的腰围有？（cm）' ,2 ,1 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(222 ,34 ,1 ,'＜75' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(223 ,34 ,2 ,'75-79.9' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(224 ,34 ,3 ,'80-84.9' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(225 ,34 ,4 ,'85-89.9' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(226 ,34 ,5 ,'90-94.9' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(227 ,34 ,6 ,'≥95' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(228 ,34 ,7 ,'不知道' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(35 ,35 ,'您的腰围有？（cm）' ,3 ,1 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(229 ,35 ,1 ,'＜70' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(230 ,35 ,2 ,'70-74.9' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(231 ,35 ,3 ,'75-79.9' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(232 ,35 ,4 ,'80-84.9' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(233 ,35 ,5 ,'85-89.9' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(234 ,35 ,6 ,'≥90' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(235 ,35 ,7 ,'不知道' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(36 ,36 ,'您的胆固醇水平？(mmol/L)' ,1 ,1 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(236 ,36 ,1 ,'＜5.2' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(237 ,36 ,2 ,'≥5.2' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(238 ,36 ,3 ,'不知道' ,now() ,now() ,1);
insert into t_tcm_collect_que(sid ,que_index,que_topic,que_type,ans_type,insert_dt,update_dt,valid)
value(37 ,37 ,'您是否有糖尿病家族史（父母、同胞、子女）' ,1 ,1 ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(239 ,37 ,1 ,'无' ,now() ,now() ,1);
insert into t_tcm_collect_que_ans (sid ,que_id ,ans_index ,ans_desc ,insert_dt ,update_dt ,valid)
values(240 ,37 ,2 ,'有' ,now() ,now() ,1);