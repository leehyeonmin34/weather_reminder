--
--CREATE TABLE `BATCH_JOB_EXECUTION` (
--  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
--  `VERSION` bigint(20) DEFAULT NULL,
--  `JOB_INSTANCE_ID` bigint(20) NOT NULL,
--  `CREATE_TIME` datetime(6) NOT NULL,
--  `START_TIME` datetime(6) DEFAULT NULL,
--  `END_TIME` datetime(6) DEFAULT NULL,
--  `STATUS` varchar(10) DEFAULT NULL,
--  `EXIT_CODE` varchar(2500) DEFAULT NULL,
--  `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
--  `LAST_UPDATED` datetime(6) DEFAULT NULL,
--  `JOB_CONFIGURATION_LOCATION` varchar(2500) DEFAULT NULL,
--  PRIMARY KEY (`JOB_EXECUTION_ID`),
--  KEY `JOB_INST_EXEC_FK` (`JOB_INSTANCE_ID`),
--  CONSTRAINT `JOB_INST_EXEC_FK` FOREIGN KEY (`JOB_INSTANCE_ID`) REFERENCES `BATCH_JOB_INSTANCE` (`JOB_INSTANCE_ID`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--CREATE TABLE `BATCH_JOB_EXECUTION_CONTEXT` (
--  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
--  `SHORT_CONTEXT` varchar(2500) NOT NULL,
--  `SERIALIZED_CONTEXT` text,
--  PRIMARY KEY (`JOB_EXECUTION_ID`),
--  CONSTRAINT `JOB_EXEC_CTX_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--CREATE TABLE `BATCH_JOB_EXECUTION_PARAMS` (
--  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
--  `TYPE_CD` varchar(6) NOT NULL,
--  `KEY_NAME` varchar(100) NOT NULL,
--  `STRING_VAL` varchar(250) DEFAULT NULL,
--  `DATE_VAL` datetime(6) DEFAULT NULL,
--  `LONG_VAL` bigint(20) DEFAULT NULL,
--  `DOUBLE_VAL` double DEFAULT NULL,
--  `IDENTIFYING` char(1) NOT NULL,
--  KEY `JOB_EXEC_PARAMS_FK` (`JOB_EXECUTION_ID`),
--  CONSTRAINT `JOB_EXEC_PARAMS_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--CREATE TABLE `BATCH_JOB_EXECUTION_SEQ` (
--  `ID` bigint(20) NOT NULL,
--  `UNIQUE_KEY` char(1) NOT NULL,
--  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--CREATE TABLE `BATCH_JOB_INSTANCE` (
--  `JOB_INSTANCE_ID` bigint(20) NOT NULL,
--  `VERSION` bigint(20) DEFAULT NULL,
--  `JOB_NAME` varchar(100) NOT NULL,
--  `JOB_KEY` varchar(32) NOT NULL,
--  PRIMARY KEY (`JOB_INSTANCE_ID`),
--  UNIQUE KEY `JOB_INST_UN` (`JOB_NAME`,`JOB_KEY`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--CREATE TABLE `BATCH_JOB_SEQ` (
--  `ID` bigint(20) NOT NULL,
--  `UNIQUE_KEY` char(1) NOT NULL,
--  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--CREATE TABLE `BATCH_STEP_EXECUTION` (
--  `STEP_EXECUTION_ID` bigint(20) NOT NULL,
--  `VERSION` bigint(20) NOT NULL,
--  `STEP_NAME` varchar(100) NOT NULL,
--  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
--  `START_TIME` datetime(6) NOT NULL,
--  `END_TIME` datetime(6) DEFAULT NULL,
--  `STATUS` varchar(10) DEFAULT NULL,
--  `COMMIT_COUNT` bigint(20) DEFAULT NULL,
--  `READ_COUNT` bigint(20) DEFAULT NULL,
--  `FILTER_COUNT` bigint(20) DEFAULT NULL,
--  `WRITE_COUNT` bigint(20) DEFAULT NULL,
--  `READ_SKIP_COUNT` bigint(20) DEFAULT NULL,
--  `WRITE_SKIP_COUNT` bigint(20) DEFAULT NULL,
--  `PROCESS_SKIP_COUNT` bigint(20) DEFAULT NULL,
--  `ROLLBACK_COUNT` bigint(20) DEFAULT NULL,
--  `EXIT_CODE` varchar(2500) DEFAULT NULL,
--  `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
--  `LAST_UPDATED` datetime(6) DEFAULT NULL,
--  PRIMARY KEY (`STEP_EXECUTION_ID`),
--  KEY `JOB_EXEC_STEP_FK` (`JOB_EXECUTION_ID`),
--  CONSTRAINT `JOB_EXEC_STEP_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--CREATE TABLE `BATCH_STEP_EXECUTION_CONTEXT` (
--  `STEP_EXECUTION_ID` bigint(20) NOT NULL,
--  `SHORT_CONTEXT` varchar(2500) NOT NULL,
--  `SERIALIZED_CONTEXT` text,
--  PRIMARY KEY (`STEP_EXECUTION_ID`),
--  CONSTRAINT `STEP_EXEC_CTX_FK` FOREIGN KEY (`STEP_EXECUTION_ID`) REFERENCES `BATCH_STEP_EXECUTION` (`STEP_EXECUTION_ID`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--CREATE TABLE `BATCH_STEP_EXECUTION_SEQ` (
--  `ID` bigint(20) NOT NULL,
--  `UNIQUE_KEY` char(1) NOT NULL,
--  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--CREATE TABLE `dust_info` (
--  `id` bigint(20) NOT NULL,
--  PRIMARY KEY (`id`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--CREATE TABLE `hibernate_sequence` (
--  `next_val` bigint(20) DEFAULT NULL
--) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--CREATE TABLE `notification` (
--  `id` bigint(20) NOT NULL,
--  `msg` varchar(255) DEFAULT NULL,
--  `user_id` bigint(20) DEFAULT NULL,
--  PRIMARY KEY (`id`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--CREATE TABLE `session_info` (
--  `access_token` varchar(255) NOT NULL,
--  `expiration_time` datetime DEFAULT NULL,
--  `user_agent` varchar(255) DEFAULT NULL,
--  `user_id` bigint(20) NOT NULL,
--  PRIMARY KEY (`access_token`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--CREATE TABLE `test_domain` (
--  `id` bigint(20) NOT NULL AUTO_INCREMENT,
--  `name` varchar(255) DEFAULT NULL,
--  `time` int(11) DEFAULT NULL,
--  `column2` int(11) DEFAULT NULL,
--  `column3` int(11) DEFAULT NULL,
--  `column4` int(11) DEFAULT NULL,
--  `column5` int(11) DEFAULT NULL,
--  `column6` int(11) DEFAULT NULL,
--  `column7` int(11) DEFAULT NULL,
--  PRIMARY KEY (`id`)
--) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;
--
--CREATE TABLE `user` (
--  `id` bigint(20) NOT NULL,
--  `cold_noti_condition_ceclius` tinyint(4) DEFAULT NULL,
--  `created_at` datetime NOT NULL,
--  `hot_noti_condition_ceclius` tinyint(4) DEFAULT NULL,
--  `pause_until` datetime DEFAULT NULL,
--  `rain_noti_condition_time` tinyint(4) DEFAULT NULL,
--  `region_list` varchar(255) DEFAULT NULL,
--  `snow_noti_condition_time` tinyint(4) DEFAULT NULL,
--  `updated_at` datetime NOT NULL,
--  `noti_time` varchar(255) DEFAULT NULL,
--  PRIMARY KEY (`id`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--CREATE TABLE `weather_info` (
--  `id` bigint(20) NOT NULL AUTO_INCREMENT,
--  `base_time` varchar(255) NOT NULL,
--  `fcst_time` varchar(255) NOT NULL,
--  `value` float NOT NULL,
--  `weather_data_type` varchar(255) NOT NULL,
--  `weather_region` varchar(255) NOT NULL,
--  `unit` varchar(255) NOT NULL,
--  PRIMARY KEY (`id`)
--) ENGINE=InnoDB AUTO_INCREMENT=4339 DEFAULT CHARSET=utf8;
