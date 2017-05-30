# LoadBalancer
CS223 Project

Server:
ServerID, IP, Port, Request number, Average Response Time

Request
requestId, params, processed server, process time, status.


1. zookeeper install
    https://zookeeper.apache.org/doc/r3.1.2/zookeeperStarted.html
    bin/zkServer.sh start

2. Curator usage.


3. MySQL Data Model
1. mysql.server start

2.  create database loadB;

3. create request table, and request failure table. tb_request is used to store request information and record
the latest status of a request. tb_failure only records the

~~~
CREATE TABLE `tb_request` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
    `ip` VARCHAR(20) NOT NULL DEFAULT '' COMMENT 'ip address',
    `port` INT(11) NOT NULL  COMMENT 'port number',
    `type` tinyint(4) NOT NULL  COMMENT 'request type',
    `invoke_type` tinyint(4) NOT NULL  COMMENT 'invoke type',
    `path` VARCHAR(100) NOT NULL DEFAULT '' COMMENT 'request url path',
    `params` VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'request params json string',
    `retry_times` int(11) NOT NULL DEFAULT 1 COMMENT 'retry times for a request',
    `remark` VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'remark',
    `status` TINYINT(4) NOT NULL DEFAULT '1' COMMENT 'latest request process status',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'updated time',
    PRIMARY KEY (`id`),
    KEY `ix_ip` (`ip`),
    KEY `ix_port` (`port`),
    KEY `ix_created_at` (`created_at`),
    KEY `ix_updated_at` (`updated_at`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='request info';




CREATE TABLE `tb_request_failure` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
    `request_id` BIGINT(20) NOT NULL DEFAULT 0  COMMENT 'request id',
	`ip` VARCHAR(20) NOT NULL DEFAULT '' COMMENT 'ip address',
    `port` INT(11) NOT NULL  COMMENT 'port number',
    `remark` VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'fail reasons',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'updated time',
    PRIMARY KEY (`id`),
    KEY `ix_ip` (`ip`),
    KEY `ix_created_at` (`created_at`),
    KEY `ix_updated_at` (`updated_at`),
	FOREIGN KEY (`request_id`) REFERENCES tb_request(`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='request failure history';

~~~


Test case:

