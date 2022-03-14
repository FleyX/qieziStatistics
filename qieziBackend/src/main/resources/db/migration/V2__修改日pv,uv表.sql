drop table host_day;
CREATE TABLE qiezi.host_day (
    id     INT auto_increment NOT NULL,
	hostId int NOT NULL COMMENT 'host表主键',
	dayNum INT NOT NULL COMMENT '日期：20200202',
	uv INT NOT NULL,
	pv int NOT NULL,
	CONSTRAINT host_day_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci
COMMENT='整站，日 uv/pv记录';
CREATE UNIQUE INDEX host_day_key_IDX USING BTREE ON qiezi.host_day (hostId,dayNum);


CREATE TABLE qiezi.detail_page_day (
    id     INT auto_increment NOT NULL,
	detailPageId int NOT NULL COMMENT 'detail_page主键',
	dayNum INT NOT NULL COMMENT '日期：20200202',
	uv INT NOT NULL,
	pv int NOT NULL,
	CONSTRAINT detail_page_day_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci
COMMENT='具体页面，日 uv/pv记录';
CREATE UNIQUE INDEX detail_page_day_key_IDX USING BTREE ON qiezi.detail_page_day(detailPageId,dayNum);
