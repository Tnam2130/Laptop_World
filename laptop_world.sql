create database laptop_world;
use laptop_world;
drop database laptop_world;

INSERT INTO `roles` (`name`) VALUES ('USER');
INSERT INTO `roles` (`name`) VALUES ('ADMIN');
update users_roles set role_id=2 where user_id=1;;