CREATE TABLE qiezi.host (
	id INT auto_increment NOT NULL,
	`key` CHAR(32) NOT NULL COMMENT 'key，用于标识',
	secret char(32) NOT NULL COMMENT '密钥',
	name varchar(100) NOT NULL COMMENT '网站名',
	host varchar(100) NOT NULL COMMENT '网站域名（不含http前缀以及路径）',
	pv INT UNSIGNED DEFAULT 0 NOT NULL,
	uv varchar(100) DEFAULT 0 NOT NULL,
	CONSTRAINT host_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci
COMMENT='host表，记录某个站点总的pv,uv数据';

CREATE TABLE qiezi.path_date(
	id INT auto_increment NOT NULL,
	`key` CHAR(32) NOT NULL COMMENT 'key，用于标识',
	secret char(32) NOT NULL COMMENT '密钥',
	name varchar(100) NOT NULL COMMENT '网站名',
	host varchar(100) NOT NULL COMMENT '网站域名（不含http前缀以及路径）',
	pv INT UNSIGNED DEFAULT 0 NOT NULL,
	uv varchar(100) DEFAULT 0 NOT NULL,
	CONSTRAINT host_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci
COMMENT='host表，记录某个站点总的pv,uv数据';
