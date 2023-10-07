CREATE TABLE "public"."tb_activity_strategy"(
    "id" int8 NOT NULL PRIMARY KEY ,
    "strategy_id" varchar(50) NOT NULL ,
    "created" timestamp(6) DEFAULT NOW(),
    "updated" timestamp(6) DEFAULT NOW()
);
COMMENT ON TABLE "tb_activity_strategy" IS '活动策略表';
COMMENT ON COLUMN "tb_activity_strategy"."id" IS '主键ID(即活动ID)';
COMMENT ON COLUMN "tb_activity_strategy"."strategy_id" IS '策略ID';
COMMENT ON COLUMN "tb_activity_strategy"."created" IS '创建时间';
COMMENT ON COLUMN "tb_activity_strategy"."updated" IS '更新时间';

CREATE TABLE "public"."tb_strategy"(
    "id" varchar(50) NOT NULL PRIMARY KEY ,
    "name" varchar(30) NOT NULL,
    "status" int2 DEFAULT 1,
    "created" timestamp(6) DEFAULT NOW(),
    "updated" timestamp(6) DEFAULT NOW()
);
COMMENT ON TABLE "tb_strategy" IS '玩法策略表';
COMMENT ON COLUMN "tb_strategy"."id" IS '主键ID(即spring bean name)';
COMMENT ON COLUMN "tb_strategy"."name" IS '策略名称';
COMMENT ON COLUMN "tb_strategy"."status" IS '状态 0:无效 1:有效';
COMMENT ON COLUMN "tb_strategy"."created" IS '创建时间';
COMMENT ON COLUMN "tb_strategy"."updated" IS '更新时间';

CREATE TABLE "public"."tb_activity_auth_strategy"(
    "id" bigserial NOT NULL PRIMARY KEY ,
    "activity_id" int8 NOT NULL UNIQUE ,
    "dis_price" decimal(10, 2) NOT NULL DEFAULT '0.00',
    "cost_price" decimal(10, 2) NOT NULL DEFAULT '0.00',
    "card_type" varchar(30) NOT NULL,
    "created" timestamp(6) DEFAULT NOW(),
    "updated" timestamp(6) DEFAULT NOW()
);
COMMENT ON TABLE "tb_activity_auth_strategy" IS '支付认证策略表';
COMMENT ON COLUMN "tb_activity_auth_strategy"."id" IS '主键ID';
COMMENT ON COLUMN "tb_activity_auth_strategy"."activity_id" IS '活动ID';
COMMENT ON COLUMN "tb_activity_auth_strategy"."dis_price" IS '折扣价';
COMMENT ON COLUMN "tb_activity_auth_strategy"."cost_price" IS '原价';
COMMENT ON COLUMN "tb_activity_auth_strategy"."card_type" IS '卡种';
COMMENT ON COLUMN "tb_activity_auth_strategy"."created" IS '创建时间';
COMMENT ON COLUMN "tb_activity_auth_strategy"."updated" IS '更新时间';
COMMENT ON CONSTRAINT "tb_activity_auth_strategy_activity_id_key" ON "public"."tb_activity_auth_strategy" IS '活动ID唯一索引'

CREATE TABLE "public"."tb_activity_auth_record"(
    "id" bigserial NOT NULL PRIMARY KEY ,
    "activity_id" int8 NOT NULL ,
    "activity_name" varchar(30),
    "user_id" int8 NOT NULL ,
    "mobile" varchar(11),
    "dis_price" decimal(10, 2) NOT NULL,
    "cost_price" decimal(10, 2) NOT NULL,
    "status" int2 NOT NULL DEFAULT 1 ,
    "order_id" varchar(20) NOT NULL ,
    "trx_id" varchar(20) NOT NULL DEFAULT '0' ,
    "card_type" varchar(30) NOT NULL ,
    "tisid" int8 NOT NULL,
    "created" timestamp(6) DEFAULT NOW(),
    "updated" timestamp(6) DEFAULT NOW()
);

CREATE INDEX tb_activity_auth_record_order_id_key ON "public"."tb_activity_auth_record" ("order_id");

COMMENT ON TABLE "tb_activity_auth_record" IS '支付认证策略表';
COMMENT ON COLUMN "tb_activity_auth_record"."id" IS '主键ID';
COMMENT ON COLUMN "tb_activity_auth_record"."activity_id" IS '活动ID';
COMMENT ON COLUMN "tb_activity_auth_record"."activity_name" IS '活动名称(冗余字段)';
COMMENT ON COLUMN "tb_activity_auth_record"."user_id" IS '用户ID';
COMMENT ON COLUMN "tb_activity_auth_record"."mobile" IS '用户手机号(冗余字段)';
COMMENT ON COLUMN "tb_activity_auth_record"."dis_price" IS '折扣价';
COMMENT ON COLUMN "tb_activity_auth_record"."cost_price" IS '原价';
COMMENT ON COLUMN "tb_activity_auth_record"."status" IS '状态 1:待支付 2:已完成 3:失败 4:超时 5:退款';
COMMENT ON COLUMN "tb_activity_auth_record"."order_id" IS '订单号';
COMMENT ON COLUMN "tb_activity_auth_record"."trx_id" IS '流水号';
COMMENT ON COLUMN "tb_activity_auth_record"."card_type" IS '卡种';
COMMENT ON COLUMN "tb_activity_auth_record"."tisid" IS '机构号';
COMMENT ON COLUMN "tb_activity_auth_record"."created" IS '创建时间';
COMMENT ON COLUMN "tb_activity_auth_record"."updated" IS '更新时间';
COMMENT ON INDEX "public"."tb_activity_auth_record_order_id_key" IS '支付认证订单号索引'

