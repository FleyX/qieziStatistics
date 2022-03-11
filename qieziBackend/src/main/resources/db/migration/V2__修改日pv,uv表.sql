drop table host_day;
CREATE TABLE qiezi.host_day (
	hostId int NOT NULL COMMENT 'host表主键',
	dayNum INT NOT NULL COMMENT '日期：20200202',
	uv INT NOT NULL,
	pv int NOT NULL,
	CONSTRAINT host_day_pk PRIMARY KEY (hostId,dayNum)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci
COMMENT='整站，日 uv/pv记录';


CREATE TABLE qiezi.detail_page_day (
	detailPageId int NOT NULL COMMENT 'detail_page主键',
	dayNum INT NOT NULL COMMENT '日期：20200202',
	uv INT NOT NULL,
	pv int NOT NULL,
	CONSTRAINT detail_page_day_pk PRIMARY KEY (detailPageId,dayNum)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci
COMMENT='具体页面，日 uv/pv记录';
