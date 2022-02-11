CREATE TABLE qiezi.host (
	id INT auto_increment NOT NULL,
	`key` CHAR(32) NOT NULL COMMENT 'key，用于标识',
	secret char(32) NOT NULL COMMENT '密钥',
	name varchar(100) NOT NULL COMMENT '网站名',
	host varchar(100) NOT NULL COMMENT '网站域名（不含http前缀以及路径）',
	pv INT UNSIGNED DEFAULT 0 NOT NULL,
	uv INT UNSIGNED DEFAULT 0 NOT NULL,
	CONSTRAINT host_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci
COMMENT='host表，记录某个站点总的pv,uv数据';
CREATE UNIQUE INDEX host_key_IDX USING BTREE ON qiezi.host (`key`);

CREATE TABLE qiezi.host_day(
	id INT auto_increment NOT NULL,
	hostId INT NOT NULL COMMENT 'hostId',
	dateNum INT NOT NULL COMMENT '日期比如20200202',
	pv INT UNSIGNED DEFAULT 0 NOT NULL,
	uv INT UNSIGNED DEFAULT 0 NOT NULL,
	CONSTRAINT detail_page_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci
COMMENT='记录域名日pv/uv';
CREATE INDEX detail_page_host_id_date_IDX USING BTREE ON qiezi.host_day(`hostId`,`dateNum`);




CREATE TABLE qiezi.detail_page(
	id INT auto_increment NOT NULL,
	hostId INT NOT NULL COMMENT 'hostId',
	pv INT UNSIGNED DEFAULT 0 NOT NULL,
	uv INT UNSIGNED DEFAULT 0 NOT NULL,
	CONSTRAINT detail_page_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci
COMMENT='detail表，记录细分页面pv/uv';
CREATE INDEX detail_page_host_id_IDX USING BTREE ON qiezi.detail_page(`hostId`);

