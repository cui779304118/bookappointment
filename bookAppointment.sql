CREATE DATABASE IF NOT EXISTS appointment DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
-- 创建图书表
CREATE TABLE `book` ( 
  `bookId` INT(11) NOT NULL AUTO_INCREMENT COMMENT '图书ID',
  `name` VARCHAR(100) NOT NULL COMMENT '图书名称',
  `introdction` VARCHAR(1000) NOT NULL COMMENT '简介',
  `number` INT(11) NOT NULL COMMENT '馆藏数量',
  PRIMARY KEY (`bookId`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='图书表';

-- 初始化图书数据
INSERT INTO `book` (`bookId`, `name`, `introdction`,`number`)
VALUES
	(1000, 'Java程序设计', 'Java程序设计是一门balbal',10),
	(1001, '数据结构','数据结构是计算机存储、组织数据的方式。数据结构是指相互之间存在一种或多种特定关系的数据元素的集合。', 10),
	(1002, '设计模式','设计模式（Design Pattern）是一套被反复使用、多数人知晓的、经过分类的、代码设计经验的总结。',10),
	(1003, '编译原理','编译原理是计算机专业的一门重要专业课，旨在介绍编译程序构造的一般原理和基本方法。',10),
	(1004,'大学语文','基于长期的教学实践和学科思考，我们编写了这本《大学语文》教材balbal',10),
	(1005,'计算方法','计算方法又称“数值分析”。是为各种数学问题的数值解答研究提供最有效的算法。',10),
	(1006,'高等数学','广义地说，初等数学之外的数学都是高等数学，也有将中学较深入的代数、几何以及简单的集合论初步balbal',10);

-- 创建预约图书表
CREATE TABLE `appointment` (
  `id` INT(11) NOT NULL COMMENT 'ID',
  `bookId` INT(11) NOT NULL COMMENT '图书ID',
  `studentId` INT(11) NOT NULL COMMENT '学号',
  `appointTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '预约时间' ,
  PRIMARY KEY (`bookId`, `studentId`),
  INDEX `idx_appoint_time` (`appointTime`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='预约图书表';
-- 创建学生数据表
CREATE TABLE `student`(
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
	`studentNum` VARCHAR(20) NOT NULL COMMENT '学生ID',
	`studentInfoId` INT(11) NOT NULL COMMENT '学生详细信息ID',
	`password`  VARCHAR(20) NOT NULL COMMENT '密码',
	PRIMARY KEY(`id`),
	INDEX `idx_studentNum` (`studentNum`)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='学生统计表';