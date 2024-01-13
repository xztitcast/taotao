--postgresql 数据库纯数字字符串和字符串之间隐式转换
CREATE CAST (INTEGER AS VARCHAR) WITH INOUT AS IMPLICIT;
CREATE CAST (VARCHAR AS INTEGER) WITH INOUT AS IMPLICIT;
CREATE CAST (BIGINT AS VARCHAR) WITH INOUT AS IMPLICIT;
CREATE CAST (VARCHAR AS BIGINT) WITH INOUT AS IMPLICIT;

--删掉隐式转换
-- DROP CAST (varchar as bigint);
-- DROP CAST (bigint as varchar);

-- 菜单
CREATE TABLE tb_sys_menu (
     id bigserial NOT NULL,
     parent_id int8 DEFAULT 0 ,
     name varchar(30),
     url varchar(100),
     perms varchar(255),
     type smallint,
     status int2 NOT NULL DEFAULT 0,
     icon varchar(50),
     sorted smallint DEFAULT 0,
     PRIMARY KEY (id)
);

COMMENT ON TABLE tb_sys_menu IS '菜单管理';
COMMENT ON COLUMN tb_sys_menu.id IS '主键ID';
COMMENT ON COLUMN tb_sys_menu.parent_id IS '上级ID，一级菜单为0';
COMMENT ON COLUMN tb_sys_menu.name IS '名称';
COMMENT ON COLUMN tb_sys_menu.url IS '菜单URL';
COMMENT ON COLUMN tb_sys_menu.perms IS '授权(多个用逗号分隔，如：sys:user:list,sys:user:save)';
COMMENT ON COLUMN tb_sys_menu.type IS '类型 0：菜单 1：按钮';
COMMENT ON COLUMN tb_sys_menu.status IS '状态 是否能被删除 0：否 1：是';
COMMENT ON COLUMN tb_sys_menu.icon IS '菜单图标';
COMMENT ON COLUMN tb_sys_menu.sorted IS '排序';

-- 系统用户
CREATE TABLE tb_sys_user (
     id bigserial,
     username varchar(50) NOT NULL,
     password varchar(100),
     salt varchar(20),
     status smallint,
     locked timestamp(6) DEFAULT NOW(),
     avatar varchar(255),
     created timestamp(6),
     updated timestamp(6),
     creator int8,
     tisid int8,
     tisname varchar(50),
     PRIMARY KEY (id),
     UNIQUE (username)
);

COMMENT ON TABLE tb_sys_user IS '用户管理';
COMMENT ON COLUMN tb_sys_user.id IS '主键ID';
COMMENT ON COLUMN tb_sys_user.username IS '用户名';
COMMENT ON COLUMN tb_sys_user.password IS '密码';
COMMENT ON COLUMN tb_sys_user.status IS '状态  0：停用 1：正常';
COMMENT ON COLUMN tb_sys_user.locked IS '锁定时间';
COMMENT ON COLUMN tb_sys_user.avatar IS '头像';
COMMENT ON COLUMN tb_sys_user.created IS '创建时间';
COMMENT ON COLUMN tb_sys_user.updated IS '更新时间';
COMMENT ON COLUMN tb_sys_user.creator IS '创建者ID';
COMMENT ON COLUMN tb_sys_user.tisid IS '机构ID(冗余字段)';
COMMENT ON COLUMN tb_sys_user.tisname IS '机构名称(冗余字段)';

-- 角色
CREATE TABLE tb_sys_role (
     id bigserial NOT NULL,
     name varchar(50),
     remark varchar(100),
     created timestamp(6),
     updated timestamp(6),
     creator int8,
     PRIMARY KEY (id)
);

COMMENT ON TABLE tb_sys_role IS '角色管理';
COMMENT ON COLUMN tb_sys_role.id IS '主键ID';
COMMENT ON COLUMN tb_sys_role.name IS '角色名称';
COMMENT ON COLUMN tb_sys_role.remark IS '备注';
COMMENT ON COLUMN tb_sys_role.created IS '创建时间';
COMMENT ON COLUMN tb_sys_role.updated IS '更新时间';
COMMENT ON COLUMN tb_sys_role.creator IS '创建者ID';

-- 用户与角色对应关系
CREATE TABLE tb_sys_user_role (
    id bigserial NOT NULL,
    user_id int8,
    role_id int8,
    PRIMARY KEY (id)
);

COMMENT ON TABLE tb_sys_user_role IS '角色用户关系';
COMMENT ON COLUMN tb_sys_user_role.role_id IS '角色ID';
COMMENT ON COLUMN tb_sys_user_role.user_id IS '用户ID';

-- 角色与菜单对应关系
CREATE TABLE tb_sys_role_menu (
    id bigserial NOT NULL,
    role_id int8,
    menu_id int8,
    PRIMARY KEY (id)
);

COMMENT ON TABLE tb_sys_role_menu IS '角色菜单关系';
COMMENT ON COLUMN tb_sys_role_menu.role_id IS '角色ID';
COMMENT ON COLUMN tb_sys_role_menu.menu_id IS '菜单ID';

-- 系统日志
CREATE TABLE tb_sys_log (
    id bigserial NOT NULL,
    username varchar(50),
    operation varchar(50),
    method varchar(255),
    params text,
    time int8 NOT NULL,
    ip varchar(64),
    created timestamp(6),
    PRIMARY KEY (id)
);

COMMENT ON TABLE tb_sys_log IS '系统日志表';
COMMENT ON COLUMN tb_sys_log.id IS '主键ID';
COMMENT ON COLUMN tb_sys_log.username IS '用户名';
COMMENT ON COLUMN tb_sys_log.operation IS '用户操作';
COMMENT ON COLUMN tb_sys_log.method IS '请求方法';
COMMENT ON COLUMN tb_sys_log.params IS '请求参数';
COMMENT ON COLUMN tb_sys_log.ip IS 'IP地址';
COMMENT ON COLUMN tb_sys_log.created IS '创建时间';

CREATE TABLE tb_sys_dict (
     id serial NOT NULL,
     key varchar(50) NOT NULL UNIQUE,
     value varchar(255) NOT NULL,
     remark varchar(50),
     created timestamp(6) DEFAULT NULL,
     updated timestamp(6) DEFAULT NULL,
     PRIMARY KEY (id)
);

COMMENT ON TABLE tb_sys_dict IS '字典信息表';
COMMENT ON COLUMN tb_sys_dict.id IS '主键ID';
COMMENT ON COLUMN tb_sys_dict.key IS '字典KEY';
COMMENT ON COLUMN tb_sys_dict.value IS '字典KEY -> VALUE';
COMMENT ON COLUMN tb_sys_dict.remark IS '备注';
COMMENT ON COLUMN tb_sys_dict.created IS '创建时间';
COMMENT ON COLUMN tb_sys_dict.updated IS '更新时间';

INSERT INTO tb_sys_user (id, username, password, salt, status, avatar, created, updated, creator) VALUES ('1', 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', '0', NULL, NOW(), NOW(), '0');

INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (1, 0, '系统管理', NULL, NULL, 0, 0, 'system', 0);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (2, 1, '管理员列表', 'sys/user', NULL, 1, 0, 'admin', 1);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (3, 1, '角色管理', 'sys/role', NULL, 1, 0, 'role', 2);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (4, 1, '菜单管理', 'sys/menu', NULL, 1, 0, 'menu', 3);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (5, 1, '数据字典', 'sys/dict', NULL, 1, 0, 'icon-folder-open', 4);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (6, 1, '机构管理', 'sys/tissue', NULL, 1, 0, 'icon-cloud', 5);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (7, 1, '日志管理', 'sys/log', NULL, 1, 0, 'log', 6);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (8, 2, '查看', NULL, 'sys:user:list,sys:user:info,sys:role:select', 2, 0, 'sousuo', 0);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (9, 2, '新增', NULL, 'sys:user:save', 2, 0, 'add', 1);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (10, 2, '修改', NULL, 'sys:user:update', 2, 0, 'bianji', 2);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (11, 2, '删除', NULL, 'sys:user:delete', 2, 0, 'shanchu', 3);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (12, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, 0, 'sousuo', 0);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (13, 3, '新增', NULL, 'sys:role:save', 2, 0, 'add', 1);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (14, 3, '修改', NULL, 'sys:role:update', 2, 0, 'bianji', 2);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (15, 3, '删除', NULL, 'sys:role:delete', 2, 0, 'shanchu', 3);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (16, 4, '查看', NULL, 'sys:menu:list,sys:menu:info,sys:menu:select', 2, 0, 'sousuo', 0);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (17, 4, '新增', NULL, 'sys:menu:save', 2, 0, 'add', 1);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (18, 4, '修改', NULL, 'sys:menu:update', 2, 0, 'bianji', 2);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (19, 4, '删除', NULL, 'sys:menu:delete', 2, 0, 'shanchu', 3);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (20, 5, '查看', NULL, 'sys:dict:list,sys:dict:info', 2, 0, 'sousuo', 0);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (21, 5, '新增', NULL, 'sys:dict:save', 2, 0, 'add', 1);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (22, 5, '修改', NULL, 'sys:dict:update', 2, 0, 'bianji', 2);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (23, 5, '删除', NULL, 'sys:dict:delete', 2, 0, 'shanchu', 3);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (24, 6, '查看', NULL, 'sys:tissue:list,sys:tissue:info,sys:tissue:wx:config:info', 2, 0, 'sousuo', 0);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (25, 6, '新增', NULL, 'sys:tissue:save,sys:tissue:wx:config:save', 2, 0, 'add', 1);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (26, 6, '修改', NULL, 'sys:tissue:update', 2, 0, 'bianji', 2);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (27, 6, '删除', NULL, 'sys:tissue:delete', 2, 0, 'shanchu', 3);
INSERT INTO tb_sys_menu (id, parent_id, name, url, perms, type, status, icon, sorted) VALUES (28, 7, '查看', NULL, 'sys:log:list', 2, 0, 'sousuo', 0);

alter sequence tb_sys_menu_id_seq restart with 29;
alter sequence tb_sys_user_id_seq restart with 2;

commit;

DROP TABLE IF EXISTS "public"."tb_sys_device";
CREATE TABLE "public"."tb_sys_device"(
     "id" int8 NOT NULL PRIMARY KEY,
     "key" varchar(16) NOT NULL,
     "slat" varchar(16) NOT NULL,
     "type" int2 NOT NULL,
     "tisid" int8 NOT NULL,
     "tisname" varchar(50),
     "created" timestamp(6) DEFAULT NOW(),
     "updated" timestamp(6) DEFAULT NOW()
);

COMMENT ON TABLE "public"."tb_sys_device" IS 'API授权设备表';
COMMENT ON COLUMN "public"."tb_sys_device".id IS '主键ID';
COMMENT ON COLUMN "public"."tb_sys_device".key IS 'AES加密KEY';
COMMENT ON COLUMN "public"."tb_sys_device".slat IS 'AES IV向量';
COMMENT ON COLUMN "public"."tb_sys_device".type IS '类型(1:PC 2:小程序 3:移动APP)';
COMMENT ON COLUMN "public"."tb_sys_device"."tisid" IS '机构号';
COMMENT ON COLUMN "public"."tb_sys_device"."tisname" IS '机构名称';
COMMENT ON COLUMN "public"."tb_sys_device".created IS '创建时间';
COMMENT ON COLUMN "public"."tb_sys_device".updated IS '更新时间';
