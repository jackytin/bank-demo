
drop table  if exists platform_data;
create table platform_data (
  id varchar(100),
  name varchar(100),
  founded varchar(100),
  contact_number bigint,
  version int,
  deleted bool,
  primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE = utf8mb4_unicode_ci;

create index idx_id on platform_data(id);

drop table  if exists order_type_data;
create table order_type_data (
  id varchar(100),
  name varchar(100),
  code varchar(100),
  platform varchar(100),
  version int,
  deleted bool,
  primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE = utf8mb4_unicode_ci;

create index idx_id on order_type_data(id);
create index idx_platform on order_type_data(platform);
insert into order_type_data values
    ('huge','大订单','huge','P00000001','1',true),
    ('small','小订单','small','P00000001','1',true)
;

