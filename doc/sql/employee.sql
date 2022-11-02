create TABLE `teamcobi`.`employee` (
  `id` VARCHAR(36) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `delete_flag` CHAR(1) NOT NULL DEFAULT 'F',
  `create_time` DATETIME NOT NULL DEFAULT now(),
  `writer` VARCHAR(45) NULL,
  `wirter_time` DATETIME NOT NULL DEFAULT 'now()',
  `employee_no` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`));