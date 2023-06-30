 CREATE TABLE IF NOT EXISTS `adit`.`favourites` (
     user_id int not null,
     advertisement_id int not null,
     PRIMARY KEY (`user_id`, `advertisement_id`),
     constraint `user_to_favourites_fk`
     FOREIGN KEY (`user_id`)
     REFERENCES `adit`.`user` (`user_id`)
     ON DELETE CASCADE
     ON UPDATE CASCADE,
     constraint `advertisement_to_favourites_fk`
     FOREIGN KEY (`advertisement_id`)
     REFERENCES `adit`.`advertisement` (`ad_id`)
     ON DELETE CASCADE
     ON UPDATE CASCADE
 )
ENGINE = InnoDB;