-- ----------------------------
-- table structure for qrtz_job_details
-- ----------------------------
drop table if exists `qrtz_job_details`;
create table `qrtz_job_details`  (
  `sched_name` varchar(120) character set utf8 collate utf8_general_ci not null,
  `job_name` varchar(200) character set utf8 collate utf8_general_ci not null,
  `job_group` varchar(200) character set utf8 collate utf8_general_ci not null,
  `description` varchar(250) character set utf8 collate utf8_general_ci null default null,
  `job_class_name` varchar(250) character set utf8 collate utf8_general_ci not null,
  `is_durable` varchar(1) character set utf8 collate utf8_general_ci not null,
  `is_nonconcurrent` varchar(1) character set utf8 collate utf8_general_ci not null,
  `is_update_data` varchar(1) character set utf8 collate utf8_general_ci not null,
  `requests_recovery` varchar(1) character set utf8 collate utf8_general_ci not null,
  `job_data` blob null,
  primary key (`sched_name`, `job_name`, `job_group`) using btree
) engine = innodb character set = utf8 collate = utf8_general_ci row_format = dynamic;

-- ----------------------------
-- table structure for qrtz_triggers
-- ----------------------------
drop table if exists `qrtz_triggers`;
create table `qrtz_triggers`  (
  `sched_name` varchar(120) character set utf8 collate utf8_general_ci not null,
  `trigger_name` varchar(200) character set utf8 collate utf8_general_ci not null,
  `trigger_group` varchar(200) character set utf8 collate utf8_general_ci not null,
  `job_name` varchar(200) character set utf8 collate utf8_general_ci not null,
  `job_group` varchar(200) character set utf8 collate utf8_general_ci not null,
  `description` varchar(250) character set utf8 collate utf8_general_ci null default null,
  `next_fire_time` bigint(13) null default null,
  `prev_fire_time` bigint(13) null default null,
  `priority` int(11) null default null,
  `trigger_state` varchar(16) character set utf8 collate utf8_general_ci not null,
  `trigger_type` varchar(8) character set utf8 collate utf8_general_ci not null,
  `start_time` bigint(13) not null,
  `end_time` bigint(13) null default null,
  `calendar_name` varchar(200) character set utf8 collate utf8_general_ci null default null,
  `misfire_instr` smallint(2) null default null,
  `job_data` blob null,
  primary key (`sched_name`, `trigger_name`, `trigger_group`) using btree,
  index `sched_name`(`sched_name`, `job_name`, `job_group`) using btree,
  constraint `qrtz_triggers_ibfk_1` foreign key (`sched_name`, `job_name`, `job_group`) references `qrtz_job_details` (`sched_name`, `job_name`, `job_group`) on delete restrict on update restrict
) engine = innodb character set = utf8 collate = utf8_general_ci comment = '' row_format = dynamic;

-- ----------------------------
-- table structure for qrtz_blob_triggers
-- ----------------------------
drop table if exists `qrtz_blob_triggers`;
create table `qrtz_blob_triggers`  (
  `sched_name` varchar(120) character set utf8 collate utf8_general_ci not null,
  `trigger_name` varchar(200) character set utf8 collate utf8_general_ci not null,
  `trigger_group` varchar(200) character set utf8 collate utf8_general_ci not null,
  `blob_data` blob null,
  primary key (`sched_name`, `trigger_name`, `trigger_group`) using btree,
  constraint `qrtz_blob_triggers_ibfk_1` foreign key (`sched_name`, `trigger_name`, `trigger_group`) references `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) on delete restrict on update restrict
) engine = innodb character set = utf8 collate = utf8_general_ci comment = '' row_format = dynamic;

-- ----------------------------
-- table structure for qrtz_calendars
-- ----------------------------
drop table if exists `qrtz_calendars`;
create table `qrtz_calendars`  (
  `sched_name` varchar(120) character set utf8 collate utf8_general_ci not null,
  `calendar_name` varchar(200) character set utf8 collate utf8_general_ci not null,
  `calendar` blob not null,
  primary key (`sched_name`, `calendar_name`) using btree
) engine = innodb character set = utf8 collate = utf8_general_ci row_format = dynamic;

-- ----------------------------
-- table structure for qrtz_cron_triggers
-- ----------------------------
drop table if exists `qrtz_cron_triggers`;
create table `qrtz_cron_triggers`  (
  `sched_name` varchar(120) character set utf8 collate utf8_general_ci not null,
  `trigger_name` varchar(200) character set utf8 collate utf8_general_ci not null,
  `trigger_group` varchar(200) character set utf8 collate utf8_general_ci not null,
  `cron_expression` varchar(200) character set utf8 collate utf8_general_ci not null,
  `time_zone_id` varchar(80) character set utf8 collate utf8_general_ci null default null,
  primary key (`sched_name`, `trigger_name`, `trigger_group`) using btree,
  constraint `qrtz_cron_triggers_ibfk_1` foreign key (`sched_name`, `trigger_name`, `trigger_group`) references `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) on delete restrict on update restrict
) engine = innodb character set = utf8 collate = utf8_general_ci comment = '' row_format = dynamic;


-- ----------------------------
-- table structure for qrtz_fired_triggers
-- ----------------------------
drop table if exists `qrtz_fired_triggers`;
create table `qrtz_fired_triggers`  (
  `sched_name` varchar(120) character set utf8 collate utf8_general_ci not null,
  `entry_id` varchar(95) character set utf8 collate utf8_general_ci not null,
  `trigger_name` varchar(200) character set utf8 collate utf8_general_ci not null,
  `trigger_group` varchar(200) character set utf8 collate utf8_general_ci not null,
  `instance_name` varchar(200) character set utf8 collate utf8_general_ci not null,
  `fired_time` bigint(13) not null,
  `sched_time` bigint(13) not null,
  `priority` int(11) not null,
  `state` varchar(16) character set utf8 collate utf8_general_ci not null,
  `job_name` varchar(200) character set utf8 collate utf8_general_ci null default null,
  `job_group` varchar(200) character set utf8 collate utf8_general_ci null default null,
  `is_nonconcurrent` varchar(1) character set utf8 collate utf8_general_ci null default null,
  `requests_recovery` varchar(1) character set utf8 collate utf8_general_ci null default null,
  primary key (`sched_name`, `entry_id`) using btree
) engine = innodb character set = utf8 collate = utf8_general_ci row_format = dynamic;

-- ----------------------------
-- table structure for qrtz_locks
-- ----------------------------
drop table if exists `qrtz_locks`;
create table `qrtz_locks`  (
  `sched_name` varchar(120) character set utf8 collate utf8_general_ci not null,
  `lock_name` varchar(40) character set utf8 collate utf8_general_ci not null,
  primary key (`sched_name`, `lock_name`) using btree
) engine = innodb character set = utf8 collate = utf8_general_ci row_format = dynamic;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('quartzScheduler', 'TRIGGER_ACCESS');


-- ----------------------------
-- table structure for qrtz_paused_trigger_grps
-- ----------------------------
drop table if exists `qrtz_paused_trigger_grps`;
create table `qrtz_paused_trigger_grps`  (
  `sched_name` varchar(120) character set utf8 collate utf8_general_ci not null,
  `trigger_group` varchar(200) character set utf8 collate utf8_general_ci not null,
  primary key (`sched_name`, `trigger_group`) using btree
) engine = innodb character set = utf8 collate = utf8_general_ci row_format = dynamic;

-- ----------------------------
-- table structure for qrtz_scheduler_state
-- ----------------------------
drop table if exists `qrtz_scheduler_state`;
create table `qrtz_scheduler_state`  (
  `sched_name` varchar(120) character set utf8 collate utf8_general_ci not null,
  `instance_name` varchar(200) character set utf8 collate utf8_general_ci not null,
  `last_checkin_time` bigint(13) not null,
  `checkin_interval` bigint(13) not null,
  primary key (`sched_name`, `instance_name`) using btree
) engine = innodb character set = utf8 collate = utf8_general_ci row_format = dynamic;

-- ----------------------------
-- table structure for qrtz_simple_triggers
-- ----------------------------
drop table if exists `qrtz_simple_triggers`;
create table `qrtz_simple_triggers`  (
  `sched_name` varchar(120) character set utf8 collate utf8_general_ci not null,
  `trigger_name` varchar(200) character set utf8 collate utf8_general_ci not null,
  `trigger_group` varchar(200) character set utf8 collate utf8_general_ci not null,
  `repeat_count` bigint(7) not null,
  `repeat_interval` bigint(12) not null,
  `times_triggered` bigint(10) not null,
  primary key (`sched_name`, `trigger_name`, `trigger_group`) using btree,
  constraint `qrtz_simple_triggers_ibfk_1` foreign key (`sched_name`, `trigger_name`, `trigger_group`) references `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) on delete restrict on update restrict
) engine = innodb character set = utf8 collate = utf8_general_ci comment = '' row_format = dynamic;

-- ----------------------------
-- table structure for qrtz_simprop_triggers
-- ----------------------------
drop table if exists `qrtz_simprop_triggers`;
create table `qrtz_simprop_triggers`  (
  `sched_name` varchar(120) character set utf8 collate utf8_general_ci not null,
  `trigger_name` varchar(200) character set utf8 collate utf8_general_ci not null,
  `trigger_group` varchar(200) character set utf8 collate utf8_general_ci not null,
  `str_prop_1` varchar(512) character set utf8 collate utf8_general_ci null default null,
  `str_prop_2` varchar(512) character set utf8 collate utf8_general_ci null default null,
  `str_prop_3` varchar(512) character set utf8 collate utf8_general_ci null default null,
  `int_prop_1` int(11) null default null,
  `int_prop_2` int(11) null default null,
  `long_prop_1` bigint(20) null default null,
  `long_prop_2` bigint(20) null default null,
  `dec_prop_1` decimal(13, 4) null default null,
  `dec_prop_2` decimal(13, 4) null default null,
  `bool_prop_1` varchar(1) character set utf8 collate utf8_general_ci null default null,
  `bool_prop_2` varchar(1) character set utf8 collate utf8_general_ci null default null,
  primary key (`sched_name`, `trigger_name`, `trigger_group`) using btree,
  constraint `qrtz_simprop_triggers_ibfk_1` foreign key (`sched_name`, `trigger_name`, `trigger_group`) references `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) on delete restrict on update restrict
) engine = innodb character set = utf8 collate = utf8_general_ci comment = '' row_format = dynamic;




