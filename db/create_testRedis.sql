
 DROP TABLE IF EXISTS `test_redis`;

 CREATE TABLE `test_redis` (
 `id` int(11) PRIMARY KEY auto_increment,
 `name` VARCHAR(255),
 `password` VARCHAR(255)
 ) engine = InnoDB default charset = utf8 comment = '测试redis表';

