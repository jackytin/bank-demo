
drop table  if exists merchant_data;
create table merchant_data (
  id varchar(100),
  name varchar(100),
  admin varchar(100),
  enabled bool,
  platform varchar(100),
  passwd varchar(128),
  location_longitude double,
  location_latitude double,
  version int,
  deleted bool,
  primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE = utf8mb4_unicode_ci;

create index idx_id on merchant_data(id);
create index idx_platform on merchant_data(platform);
drop table  if exists merchant_order_data;
create table merchant_order_data (
  id varchar(100),
  total_amout double,
  create_date date,
  seller varchar(100),
  buyer varchar(100),
  version int,
  deleted bool,
  primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE = utf8mb4_unicode_ci;

create index idx_id on merchant_order_data(id);
create index idx_seller on merchant_order_data(seller);
create index idx_buyer on merchant_order_data(buyer);


