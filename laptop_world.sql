create database laptop_world;
use laptop_world;
drop database laptop_world;

INSERT INTO `roles` (`name`) VALUES ('USER');
INSERT INTO `roles` (`name`) VALUES ('ADMIN');
delete from products where product_name='Laptop Lenovo Ideapad Gaming 3';
update users_roles set role_id=2 where user_id=1;
Alter table products drop foreign key `FKl2cyj2st6mjygl2pgwd057ivu`;
Alter table products drop brand_id;
show create table orders;