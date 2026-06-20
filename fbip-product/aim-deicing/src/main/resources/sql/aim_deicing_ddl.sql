-- ============================================================
-- 机场除冰液回收处置管理系统 - 数据库建表脚本
-- 表名前缀: aim_deicing_
-- 适用数据库: Oracle / MySQL / 达梦
-- ============================================================

-- ============================================================
-- 1. 回收池档案表 aim_deicing_pool
-- ============================================================
CREATE TABLE aim_deicing_pool (
    pk_recycling_pool    VARCHAR(36)    NOT NULL,
    pool_code            VARCHAR(50)    NOT NULL,
    pool_name            VARCHAR(100)   NOT NULL,
    location             VARCHAR(200),
    design_capacity      DECIMAL(20,4),
    current_stock        DECIMAL(20,4)  DEFAULT 0,
    mixed_concentration  DECIMAL(20,4)  DEFAULT 0,
    pool_status          INTEGER        DEFAULT 1,
    remark               VARCHAR(500),
    pk_group             VARCHAR(36),
    pk_org               VARCHAR(36),
    creator              VARCHAR(36),
    creationtime         TIMESTAMP,
    modifier             VARCHAR(36),
    modifiedtime         TIMESTAMP,
    dr                   INTEGER        DEFAULT 0,
    ts                   TIMESTAMP      DEFAULT SYSDATE,
    CONSTRAINT pk_aim_deicing_pool PRIMARY KEY (pk_recycling_pool)
);

COMMENT ON TABLE aim_deicing_pool IS '回收池档案';
COMMENT ON COLUMN aim_deicing_pool.pk_recycling_pool IS '主键';
COMMENT ON COLUMN aim_deicing_pool.pool_code IS '回收池编码';
COMMENT ON COLUMN aim_deicing_pool.pool_name IS '回收池名称';
COMMENT ON COLUMN aim_deicing_pool.location IS '位置';
COMMENT ON COLUMN aim_deicing_pool.design_capacity IS '设计容量(m³)';
COMMENT ON COLUMN aim_deicing_pool.current_stock IS '当前存量(m³)';
COMMENT ON COLUMN aim_deicing_pool.mixed_concentration IS '混合浓度(mg/L)';
COMMENT ON COLUMN aim_deicing_pool.pool_status IS '状态(1-启用 0-停用)';
COMMENT ON COLUMN aim_deicing_pool.remark IS '备注';
COMMENT ON COLUMN aim_deicing_pool.pk_group IS '集团主键';
COMMENT ON COLUMN aim_deicing_pool.pk_org IS '组织主键';
COMMENT ON COLUMN aim_deicing_pool.creator IS '创建人';
COMMENT ON COLUMN aim_deicing_pool.creationtime IS '创建时间';
COMMENT ON COLUMN aim_deicing_pool.modifier IS '修改人';
COMMENT ON COLUMN aim_deicing_pool.modifiedtime IS '修改时间';
COMMENT ON COLUMN aim_deicing_pool.dr IS '删除标记(0-正常 1-删除)';
COMMENT ON COLUMN aim_deicing_pool.ts IS '时间戳';

CREATE UNIQUE INDEX idx_aim_deicing_pool_code ON aim_deicing_pool(pool_code, pk_group, dr);
CREATE INDEX idx_aim_deicing_pool_org ON aim_deicing_pool(pk_org, dr);

-- ============================================================
-- 2. 除冰作业登记表 aim_deicing_record
-- ============================================================
CREATE TABLE aim_deicing_record (
    pk_deicing_record    VARCHAR(36)    NOT NULL,
    bill_no              VARCHAR(50),
    bill_status          INTEGER        DEFAULT 0,
    deicing_date         DATE,
    flight_no            VARCHAR(20),
    flight_sortie        VARCHAR(50),
    aircraft_type        VARCHAR(50),
    aircraft_reg_no      VARCHAR(20),
    pk_apron             VARCHAR(36),
    apron_operator       VARCHAR(36),
    deicing_start_time   TIMESTAMP,
    deicing_end_time     TIMESTAMP,
    deicing_fluid_usage  DECIMAL(20,4),
    is_recycled          INTEGER        NOT NULL,
    recycled_volume      DECIMAL(20,4),
    unrecycled_type      INTEGER,
    unrecycled_reason    VARCHAR(500),
    pk_recycling_pool    VARCHAR(36),
    pool_code            VARCHAR(50),
    record_status        INTEGER        DEFAULT 0,
    remark               VARCHAR(500),
    pk_group             VARCHAR(36),
    pk_org               VARCHAR(36),
    creator              VARCHAR(36),
    creationtime         TIMESTAMP,
    modifier             VARCHAR(36),
    modifiedtime         TIMESTAMP,
    dr                   INTEGER        DEFAULT 0,
    ts                   TIMESTAMP      DEFAULT SYSDATE,
    CONSTRAINT pk_aim_deicing_record PRIMARY KEY (pk_deicing_record)
);

COMMENT ON TABLE aim_deicing_record IS '除冰作业登记';
COMMENT ON COLUMN aim_deicing_record.pk_deicing_record IS '主键';
COMMENT ON COLUMN aim_deicing_record.bill_no IS '单据编号';
COMMENT ON COLUMN aim_deicing_record.bill_status IS '单据状态(0-草稿 1-已登记 2-已检测 3-已确认)';
COMMENT ON COLUMN aim_deicing_record.deicing_date IS '除冰日期';
COMMENT ON COLUMN aim_deicing_record.flight_no IS '航班号';
COMMENT ON COLUMN aim_deicing_record.flight_sortie IS '航班架次';
COMMENT ON COLUMN aim_deicing_record.aircraft_type IS '机型';
COMMENT ON COLUMN aim_deicing_record.aircraft_reg_no IS '飞机注册号';
COMMENT ON COLUMN aim_deicing_record.pk_apron IS '机坪主键';
COMMENT ON COLUMN aim_deicing_record.apron_operator IS '机坪作业人员';
COMMENT ON COLUMN aim_deicing_record.deicing_start_time IS '除冰开始时间';
COMMENT ON COLUMN aim_deicing_record.deicing_end_time IS '除冰结束时间';
COMMENT ON COLUMN aim_deicing_record.deicing_fluid_usage IS '除冰液使用量(L)';
COMMENT ON COLUMN aim_deicing_record.is_recycled IS '是否回收(1-是 0-否)';
COMMENT ON COLUMN aim_deicing_record.recycled_volume IS '回收量(L)';
COMMENT ON COLUMN aim_deicing_record.unrecycled_type IS '未回收原因分类(1-天气 2-机位 3-设备故障)';
COMMENT ON COLUMN aim_deicing_record.unrecycled_reason IS '未回收原因说明';
COMMENT ON COLUMN aim_deicing_record.pk_recycling_pool IS '回收池主键';
COMMENT ON COLUMN aim_deicing_record.pool_code IS '回收池编码';
COMMENT ON COLUMN aim_deicing_record.record_status IS '记录状态(0-待检测 1-检测合格 2-检测超限 3-已转运)';
COMMENT ON COLUMN aim_deicing_record.remark IS '备注';
COMMENT ON COLUMN aim_deicing_record.pk_group IS '集团主键';
COMMENT ON COLUMN aim_deicing_record.pk_org IS '组织主键';
COMMENT ON COLUMN aim_deicing_record.creator IS '创建人';
COMMENT ON COLUMN aim_deicing_record.creationtime IS '创建时间';
COMMENT ON COLUMN aim_deicing_record.modifier IS '修改人';
COMMENT ON COLUMN aim_deicing_record.modifiedtime IS '修改时间';
COMMENT ON COLUMN aim_deicing_record.dr IS '删除标记(0-正常 1-删除)';
COMMENT ON COLUMN aim_deicing_record.ts IS '时间戳';

CREATE UNIQUE INDEX idx_aim_deicing_record_no ON aim_deicing_record(bill_no, pk_group, dr);
CREATE INDEX idx_aim_deicing_record_date ON aim_deicing_record(deicing_date, dr);
CREATE INDEX idx_aim_deicing_record_flight ON aim_deicing_record(flight_no, flight_sortie, dr);
CREATE INDEX idx_aim_deicing_record_pool ON aim_deicing_record(pk_recycling_pool, dr);
CREATE INDEX idx_aim_deicing_record_status ON aim_deicing_record(record_status, dr);

-- ============================================================
-- 3. 浓度检测记录表 aim_deicing_test
-- ============================================================
CREATE TABLE aim_deicing_test (
    pk_concentration_test VARCHAR(36)   NOT NULL,
    pk_deicing_record    VARCHAR(36)    NOT NULL,
    test_no              VARCHAR(50),
    test_time            TIMESTAMP,
    tester               VARCHAR(36),
    concentration_value  DECIMAL(20,4)  NOT NULL,
    is_over_limit        INTEGER        DEFAULT 0,
    test_result          VARCHAR(200),
    test_method          VARCHAR(100),
    sample_point         VARCHAR(100),
    remark               VARCHAR(500),
    pk_group             VARCHAR(36),
    pk_org               VARCHAR(36),
    creator              VARCHAR(36),
    creationtime         TIMESTAMP,
    modifier             VARCHAR(36),
    modifiedtime         TIMESTAMP,
    dr                   INTEGER        DEFAULT 0,
    ts                   TIMESTAMP      DEFAULT SYSDATE,
    CONSTRAINT pk_aim_deicing_test PRIMARY KEY (pk_concentration_test)
);

COMMENT ON TABLE aim_deicing_test IS '浓度检测记录';
COMMENT ON COLUMN aim_deicing_test.pk_concentration_test IS '主键';
COMMENT ON COLUMN aim_deicing_test.pk_deicing_record IS '除冰记录主键';
COMMENT ON COLUMN aim_deicing_test.test_no IS '检测单号';
COMMENT ON COLUMN aim_deicing_test.test_time IS '检测时间';
COMMENT ON COLUMN aim_deicing_test.tester IS '检测人(环保员)';
COMMENT ON COLUMN aim_deicing_test.concentration_value IS '浓度值(mg/L)';
COMMENT ON COLUMN aim_deicing_test.is_over_limit IS '是否超限(1-是 0-否)';
COMMENT ON COLUMN aim_deicing_test.test_result IS '检测结果';
COMMENT ON COLUMN aim_deicing_test.test_method IS '检测方法';
COMMENT ON COLUMN aim_deicing_test.sample_point IS '采样点';
COMMENT ON COLUMN aim_deicing_test.remark IS '备注';
COMMENT ON COLUMN aim_deicing_test.pk_group IS '集团主键';
COMMENT ON COLUMN aim_deicing_test.pk_org IS '组织主键';
COMMENT ON COLUMN aim_deicing_test.creator IS '创建人';
COMMENT ON COLUMN aim_deicing_test.creationtime IS '创建时间';
COMMENT ON COLUMN aim_deicing_test.modifier IS '修改人';
COMMENT ON COLUMN aim_deicing_test.modifiedtime IS '修改时间';
COMMENT ON COLUMN aim_deicing_test.dr IS '删除标记(0-正常 1-删除)';
COMMENT ON COLUMN aim_deicing_test.ts IS '时间戳';

CREATE INDEX idx_aim_deicing_test_record ON aim_deicing_test(pk_deicing_record, dr);
CREATE INDEX idx_aim_deicing_test_time ON aim_deicing_test(test_time, dr);
CREATE INDEX idx_aim_deicing_test_limit ON aim_deicing_test(is_over_limit, dr);

-- ============================================================
-- 4. 外运联单表 aim_deicing_bill
-- ============================================================
CREATE TABLE aim_deicing_bill (
    pk_transport_bill    VARCHAR(36)    NOT NULL,
    pk_deicing_record    VARCHAR(36)    NOT NULL,
    bill_no              VARCHAR(50),
    transport_unit       VARCHAR(100)   NOT NULL,
    disposal_unit        VARCHAR(100),
    transport_date       DATE,
    transport_volume     DECIMAL(20,4)  NOT NULL,
    transport_type       INTEGER        NOT NULL,
    bill_status          INTEGER        DEFAULT 0,
    confirm_person       VARCHAR(36),
    confirm_time         TIMESTAMP,
    disposal_remark      VARCHAR(500),
    is_disposed          INTEGER        DEFAULT 0,
    disposal_time        TIMESTAMP,
    remark               VARCHAR(500),
    pk_group             VARCHAR(36),
    pk_org               VARCHAR(36),
    creator              VARCHAR(36),
    creationtime         TIMESTAMP,
    modifier             VARCHAR(36),
    modifiedtime         TIMESTAMP,
    dr                   INTEGER        DEFAULT 0,
    ts                   TIMESTAMP      DEFAULT SYSDATE,
    CONSTRAINT pk_aim_deicing_bill PRIMARY KEY (pk_transport_bill)
);

COMMENT ON TABLE aim_deicing_bill IS '外运联单';
COMMENT ON COLUMN aim_deicing_bill.pk_transport_bill IS '主键';
COMMENT ON COLUMN aim_deicing_bill.pk_deicing_record IS '除冰记录主键';
COMMENT ON COLUMN aim_deicing_bill.bill_no IS '联单编号';
COMMENT ON COLUMN aim_deicing_bill.transport_unit IS '转运单位';
COMMENT ON COLUMN aim_deicing_bill.disposal_unit IS '处置单位';
COMMENT ON COLUMN aim_deicing_bill.transport_date IS '转运日期';
COMMENT ON COLUMN aim_deicing_bill.transport_volume IS '转运量(L)';
COMMENT ON COLUMN aim_deicing_bill.transport_type IS '外运类型(1-普通废水 2-危废处置 3-再处理)';
COMMENT ON COLUMN aim_deicing_bill.bill_status IS '联单状态(0-待确认 1-已确认 2-已转运 3-已处置)';
COMMENT ON COLUMN aim_deicing_bill.confirm_person IS '确认人(外运单位)';
COMMENT ON COLUMN aim_deicing_bill.confirm_time IS '确认时间';
COMMENT ON COLUMN aim_deicing_bill.disposal_remark IS '处置备注';
COMMENT ON COLUMN aim_deicing_bill.is_disposed IS '是否已处置(1-是 0-否)';
COMMENT ON COLUMN aim_deicing_bill.disposal_time IS '处置时间';
COMMENT ON COLUMN aim_deicing_bill.remark IS '备注';
COMMENT ON COLUMN aim_deicing_bill.pk_group IS '集团主键';
COMMENT ON COLUMN aim_deicing_bill.pk_org IS '组织主键';
COMMENT ON COLUMN aim_deicing_bill.creator IS '创建人';
COMMENT ON COLUMN aim_deicing_bill.creationtime IS '创建时间';
COMMENT ON COLUMN aim_deicing_bill.modifier IS '修改人';
COMMENT ON COLUMN aim_deicing_bill.modifiedtime IS '修改时间';
COMMENT ON COLUMN aim_deicing_bill.dr IS '删除标记(0-正常 1-删除)';
COMMENT ON COLUMN aim_deicing_bill.ts IS '时间戳';

CREATE UNIQUE INDEX idx_aim_deicing_bill_no ON aim_deicing_bill(bill_no, pk_group, dr);
CREATE INDEX idx_aim_deicing_bill_record ON aim_deicing_bill(pk_deicing_record, dr);
CREATE INDEX idx_aim_deicing_bill_status ON aim_deicing_bill(bill_status, dr);
CREATE INDEX idx_aim_deicing_bill_date ON aim_deicing_bill(transport_date, dr);

-- ============================================================
-- 5. 联单来源池明细表 aim_deicing_bill_pool
-- ============================================================
CREATE TABLE aim_deicing_bill_pool (
    pk_bill_pool         VARCHAR(36)    NOT NULL,
    pk_transport_bill    VARCHAR(36)    NOT NULL,
    pk_recycling_pool    VARCHAR(36)    NOT NULL,
    pool_code            VARCHAR(50),
    pool_name            VARCHAR(100),
    pool_volume          DECIMAL(20,4)  NOT NULL,
    pool_concentration   DECIMAL(20,4),
    trace_start_node     VARCHAR(100),
    trace_end_node       VARCHAR(100),
    remark               VARCHAR(500),
    pk_group             VARCHAR(36),
    pk_org               VARCHAR(36),
    creator              VARCHAR(36),
    creationtime         TIMESTAMP,
    modifier             VARCHAR(36),
    modifiedtime         TIMESTAMP,
    dr                   INTEGER        DEFAULT 0,
    ts                   TIMESTAMP      DEFAULT SYSDATE,
    CONSTRAINT pk_aim_deicing_bill_pool PRIMARY KEY (pk_bill_pool)
);

COMMENT ON TABLE aim_deicing_bill_pool IS '联单来源池明细';
COMMENT ON COLUMN aim_deicing_bill_pool.pk_bill_pool IS '主键';
COMMENT ON COLUMN aim_deicing_bill_pool.pk_transport_bill IS '外运联单主键';
COMMENT ON COLUMN aim_deicing_bill_pool.pk_recycling_pool IS '回收池主键';
COMMENT ON COLUMN aim_deicing_bill_pool.pool_code IS '回收池编码';
COMMENT ON COLUMN aim_deicing_bill_pool.pool_name IS '回收池名称';
COMMENT ON COLUMN aim_deicing_bill_pool.pool_volume IS '来源池转运量(L)';
COMMENT ON COLUMN aim_deicing_bill_pool.pool_concentration IS '来源池浓度(mg/L)';
COMMENT ON COLUMN aim_deicing_bill_pool.trace_start_node IS '轨迹起点';
COMMENT ON COLUMN aim_deicing_bill_pool.trace_end_node IS '轨迹终点';
COMMENT ON COLUMN aim_deicing_bill_pool.remark IS '备注';
COMMENT ON COLUMN aim_deicing_bill_pool.pk_group IS '集团主键';
COMMENT ON COLUMN aim_deicing_bill_pool.pk_org IS '组织主键';
COMMENT ON COLUMN aim_deicing_bill_pool.creator IS '创建人';
COMMENT ON COLUMN aim_deicing_bill_pool.creationtime IS '创建时间';
COMMENT ON COLUMN aim_deicing_bill_pool.modifier IS '修改人';
COMMENT ON COLUMN aim_deicing_bill_pool.modifiedtime IS '修改时间';
COMMENT ON COLUMN aim_deicing_bill_pool.dr IS '删除标记(0-正常 1-删除)';
COMMENT ON COLUMN aim_deicing_bill_pool.ts IS '时间戳';

CREATE INDEX idx_aim_deicing_bill_pool_bill ON aim_deicing_bill_pool(pk_transport_bill, dr);
CREATE INDEX idx_aim_deicing_bill_pool_pool ON aim_deicing_bill_pool(pk_recycling_pool, dr);

-- ============================================================
-- 6. 外运轨迹表 aim_deicing_trace
-- ============================================================
CREATE TABLE aim_deicing_trace (
    pk_transport_trace   VARCHAR(36)    NOT NULL,
    pk_transport_bill    VARCHAR(36)    NOT NULL,
    pk_deicing_record    VARCHAR(36),
    trace_node           VARCHAR(100)   NOT NULL,
    trace_order          INTEGER        DEFAULT 1,
    operate_time         TIMESTAMP      NOT NULL,
    operator             VARCHAR(36),
    trace_status         VARCHAR(50),
    trace_remark         VARCHAR(500),
    location             VARCHAR(200),
    pk_group             VARCHAR(36),
    pk_org               VARCHAR(36),
    creator              VARCHAR(36),
    creationtime         TIMESTAMP,
    modifier             VARCHAR(36),
    modifiedtime         TIMESTAMP,
    dr                   INTEGER        DEFAULT 0,
    ts                   TIMESTAMP      DEFAULT SYSDATE,
    CONSTRAINT pk_aim_deicing_trace PRIMARY KEY (pk_transport_trace)
);

COMMENT ON TABLE aim_deicing_trace IS '外运轨迹';
COMMENT ON COLUMN aim_deicing_trace.pk_transport_trace IS '主键';
COMMENT ON COLUMN aim_deicing_trace.pk_transport_bill IS '外运联单主键';
COMMENT ON COLUMN aim_deicing_trace.pk_deicing_record IS '除冰记录主键';
COMMENT ON COLUMN aim_deicing_trace.trace_node IS '轨迹节点';
COMMENT ON COLUMN aim_deicing_trace.trace_order IS '轨迹顺序';
COMMENT ON COLUMN aim_deicing_trace.operate_time IS '操作时间';
COMMENT ON COLUMN aim_deicing_trace.operator IS '操作人';
COMMENT ON COLUMN aim_deicing_trace.trace_status IS '轨迹状态';
COMMENT ON COLUMN aim_deicing_trace.trace_remark IS '轨迹备注';
COMMENT ON COLUMN aim_deicing_trace.location IS '当前位置';
COMMENT ON COLUMN aim_deicing_trace.pk_group IS '集团主键';
COMMENT ON COLUMN aim_deicing_trace.pk_org IS '组织主键';
COMMENT ON COLUMN aim_deicing_trace.creator IS '创建人';
COMMENT ON COLUMN aim_deicing_trace.creationtime IS '创建时间';
COMMENT ON COLUMN aim_deicing_trace.modifier IS '修改人';
COMMENT ON COLUMN aim_deicing_trace.modifiedtime IS '修改时间';
COMMENT ON COLUMN aim_deicing_trace.dr IS '删除标记(0-正常 1-删除)';
COMMENT ON COLUMN aim_deicing_trace.ts IS '时间戳';

CREATE INDEX idx_aim_deicing_trace_bill ON aim_deicing_trace(pk_transport_bill, dr);
CREATE INDEX idx_aim_deicing_trace_record ON aim_deicing_trace(pk_deicing_record, dr);
CREATE INDEX idx_aim_deicing_trace_order ON aim_deicing_trace(pk_transport_bill, trace_order, dr);

-- ============================================================
-- 初始化数据: 回收池档案
-- ============================================================
INSERT INTO aim_deicing_pool (pk_recycling_pool, pool_code, pool_name, location, design_capacity, current_stock, pool_status, pk_group, pk_org, creator, creationtime, modifier, modifiedtime, dr, ts)
VALUES ('POOL001', 'POOL-001', '1号回收池', '东机坪北侧', 500.0000, 0, 1, 'GLOBLE00000000000000', 'ORG0000000000000000001', 'SYSADMIN', SYSDATE, 'SYSADMIN', SYSDATE, 0, SYSDATE);

INSERT INTO aim_deicing_pool (pk_recycling_pool, pool_code, pool_name, location, design_capacity, current_stock, pool_status, pk_group, pk_org, creator, creationtime, modifier, modifiedtime, dr, ts)
VALUES ('POOL002', 'POOL-002', '2号回收池', '西机坪南侧', 800.0000, 0, 1, 'GLOBLE00000000000000', 'ORG0000000000000000001', 'SYSADMIN', SYSDATE, 'SYSADMIN', SYSDATE, 0, SYSDATE);

INSERT INTO aim_deicing_pool (pk_recycling_pool, pool_code, pool_name, location, design_capacity, current_stock, pool_status, pk_group, pk_org, creator, creationtime, modifier, modifiedtime, dr, ts)
VALUES ('POOL003', 'POOL-003', '3号回收池', '北机坪东侧', 600.0000, 0, 1, 'GLOBLE00000000000000', 'ORG0000000000000000001', 'SYSADMIN', SYSDATE, 'SYSADMIN', SYSDATE, 0, SYSDATE);

COMMIT;
