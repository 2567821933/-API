-- 创建库
create database if not exists my_api;

-- 切换库
use my_api;

-- interface_info
-- interface_info
create table if not exists my_api.`interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `name` varchar(256) not null comment '名称',
    `description` varchar(256) null comment '描述',
    `url` varchar(512) not null comment '接口地址',
    'requestParams' text not null comment '请求参数',
    `requestHeader` text null comment '请求头',
    `responseHeader` varchar(256) null comment '响应头',
    `status` int default 0 not null comment '0关闭1开启',
    `method` varchar(256) not null comment '请求类型',
    `userId` bigint not null comment '创建人',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDeleted` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment 'interface_info';



ALTER TABLE `interface_info` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('吴浩宇', '许峻熙', 'www.morgan-mertz.co', '朱鸿涛', '潘峻熙', 0, '曾伟宸', 5042);
insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('范正豪', '彭立果', 'www.lyndia-labadie.net', '姜鑫磊', '梁梓晨', 0, '石子骞', 8);
insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('沈弘文', '龚致远', 'www.bev-mclaughlin.org', '严弘文', '周天磊', 0, '唐昊焱', 975596979);
insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('程明辉', '宋金鑫', 'www.phillip-hintz.co', '刘嘉熙', '董苑博', 0, '陈志泽', 797179);
insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('郑烨华', '洪智宸', 'www.karleen-kozey.name', '郭思聪', '尹绍齐', 0, '孙思聪', 86);
insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('朱鸿涛', '覃绍辉', 'www.tilda-pagac.io', '覃立轩', '龚峻熙', 0, '钟雨泽', 0);
insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('邵黎昕', '孔泽洋', 'www.allegra-reichert.name', '吕思', '万智辉', 0, '姜彬', 7990);
insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('彭思', '薛涛', 'www.shawn-rempel.co', '韦志泽', '梁鑫鹏', 0, '王思', 9912);
insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('史睿渊', '莫致远', 'www.bruno-jast.com', '杨哲瀚', '徐熠彤', 0, '丁懿轩', 764052);
insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('蒋皓轩', '武峻熙', 'www.ileana-hudson.name', '余健雄', '武明杰', 0, '宋风华', 8);
insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('胡文博', '周擎宇', 'www.vicente-mann.net', '卢修洁', '洪修洁', 0, '石浩宇', 63134);
insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('周伟泽', '雷凯瑞', 'www.elda-lueilwitz.com', '傅立辉', '黎昊焱', 0, '杜涛', 625);
insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('田浩轩', '郭昊天', 'www.loyce-conroy.org', '江文昊', '叶明轩', 0, '丁明哲', 9271535053);
insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('廖越泽', '冯天翊', 'www.sanjuana-gibson.com', '邵睿渊', '胡展鹏', 0, '周聪健', 596);
insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('于雨泽', '龚苑博', 'www.darcie-lebsack.net', '夏嘉熙', '洪鹏', 0, '钟志泽', 18);
insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('林浩然', '田思淼', 'www.todd-ritchie.io', '阎航', '冯文轩', 0, '莫君浩', 57);
insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('孔明杰', '龚熠彤', 'www.pei-price.io', '于思源', '谢瑞霖', 0, '邵伟祺', 2251597);
insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('王雪松', '周嘉熙', 'www.dante-braun.biz', '崔晟睿', '江烨华', 0, '郭昊然', 687);
insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('傅聪健', '傅煜祺', 'www.madie-rath.org', '谢子轩', '卢立果', 0, '曾雨泽', 80);
insert into my_api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('宋越彬', '龚黎昕', 'www.dinah-grady.co', '尹思源', '何晓博', 0, '曹晋鹏', 89788);

use my_api;
create table if not exists my_api.`user_interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `userId` bigint not null comment '调用用户id',
    `interfaceInfoId` bigint not null comment '接口id',
    `totalNum` int default 0 not null comment '总调用次数',
    `leftNum` int default 0 not null comment '剩余调用次数',
    `status` int default 0 not null comment '0关闭1开启',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDeleted` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'

) comment 'user_interface_info';


explain select userName from user where id = 1;

-- 切换库
use my_api;
create table if not exists my_api.`user_sign_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `userId` bigint not null comment '签到用户id',
    `signIn` int default 0 not null comment '连续签到天数',
    `integral` int default 0 not null comment '积分数',
    `status` bigint default 0 not null comment '0未签1签到',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDeleted` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'

) comment '用户签到表';


# 接口次数调用
select interfaceInfoId, sum(totalNum) as totalNum
from user_interface_info
group by interfaceInfoId
order by totalNum desc
limit 3;