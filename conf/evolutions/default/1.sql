# DC schema
 
# --- !Ups

CREATE TABLE `user` (
  `user_id` VARCHAR(8) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  `update_datetime` DATETIME NULL,
  PRIMARY KEY (`user_id`)
);

 
# --- !Downs

DROP TABLE user;
