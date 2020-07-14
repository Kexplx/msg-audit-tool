-- -----------------------------------------------------
-- Schema msg_audit_database
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema msg_audit_database
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `msg_audit_database` ;
USE `msg_audit_database` ;

-- -----------------------------------------------------
-- Drop tables
-- -----------------------------------------------------
SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `msg_audit_database`.`contact_person` ;
DROP TABLE IF EXISTS `msg_audit_database`.`audit` ;
DROP TABLE IF EXISTS `msg_audit_database`.`audit_contact_person` ;
DROP TABLE IF EXISTS `msg_audit_database`.`fac_crit` ;
DROP TABLE IF EXISTS `msg_audit_database`.`question` ;
DROP TABLE IF EXISTS `msg_audit_database`.`interview` ;
DROP TABLE IF EXISTS `msg_audit_database`.`answer` ;
DROP TABLE IF EXISTS `msg_audit_database`.`interview_contact_person` ;
DROP TABLE IF EXISTS `msg_audit_database`.`scope` ;
SET foreign_key_checks = 1;

-- -----------------------------------------------------
-- Table `msg_audit_database`.`contact_person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `msg_audit_database`.`contact_person` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `salutation` ENUM('HERR', 'FRAU', 'DIVERS') NULL,
  `title` VARCHAR(256) NULL,
  `forename` VARCHAR(256) NOT NULL,
  `surname` VARCHAR(256) NOT NULL,
  `contact_information` VARCHAR(256) NULL,
  `company_name` VARCHAR(256) NOT NULL,
  `department` VARCHAR(256) NULL,
  `sector` VARCHAR(256) NULL,
  `corporate_division` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `contact_person_id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `msg_audit_database`.`audit`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `msg_audit_database`.`audit` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256) NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NULL,
  `cancellation_date` DATE NULL,
  `cancellation_reason` VARCHAR(256) NULL,
  `cancellation_contact_person` INT NULL,
  `status` ENUM('OPEN', 'ACTIVE', 'FINISHED', 'CANCELED') NOT NULL,
  `creation_date` TIMESTAMP(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_audit_cancellation_contact_person_idx` (`cancellation_contact_person` ASC),
  CONSTRAINT `fk_audit_cancellation_contact_person`
    FOREIGN KEY (`cancellation_contact_person`)
    REFERENCES `msg_audit_database`.`contact_person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `msg_audit_database`.`audit_contact_person`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `msg_audit_database`.`audit_contact_person` (
  `audit_id` INT NOT NULL,
  `contact_person_id` INT NOT NULL,
  PRIMARY KEY (`audit_id`, `contact_person_id`),
  INDEX `fk_auditcontactperson_contactperson_id_idx` (`contact_person_id` ASC),
  CONSTRAINT `fk_auditcontactperson_audit_id`
    FOREIGN KEY (`audit_id`)
    REFERENCES `msg_audit_database`.`audit` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_auditcontactperson_contactperson_id`
    FOREIGN KEY (`contact_person_id`)
    REFERENCES `msg_audit_database`.`contact_person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `msg_audit_database`.`fac_crit`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `msg_audit_database`.`fac_crit` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `reference_id` INT NULL,
  `name` VARCHAR(256) NOT NULL,
  `goal` VARCHAR(2048) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `faccrit_id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `faccrit_name_UNIQUE` (`name` ASC),
  INDEX `fk_faccrit_faccrit_id_idx` (`reference_id` ASC),
  CONSTRAINT `fk_faccrit_reference_id`
    FOREIGN KEY (`reference_id`)
    REFERENCES `msg_audit_database`.`fac_crit` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `msg_audit_database`.`question`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `msg_audit_database`.`question` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `faccrit_id` INT NOT NULL,
  `text_de` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `question_id_UNIQUE` (`id` ASC),
  INDEX `fk_question_faccrit_id_idx` (`faccrit_id` ASC),
  CONSTRAINT `fk_question_faccrit_id`
    FOREIGN KEY (`faccrit_id`)
    REFERENCES `msg_audit_database`.`fac_crit` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `msg_audit_database`.`interview`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `msg_audit_database`.`interview` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `audit_id` INT NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NULL,
  `status` ENUM('ACTIVE', 'FINISHED') NOT NULL,
  `note` VARCHAR(1024) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `interview_id_UNIQUE` (`id` ASC),
  INDEX `fk_interview_audit_id_idx` (`audit_id` ASC),
  CONSTRAINT `fk_interview_audit_id`
    FOREIGN KEY (`audit_id`)
    REFERENCES `msg_audit_database`.`audit` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `msg_audit_database`.`answer`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `msg_audit_database`.`answer` (
  `question_id` INT NOT NULL,
  `interview_id` INT NOT NULL,
  `faccrit_id` INT NOT NULL,
  `result` TINYINT NULL,
  `responsible` TINYINT NULL,
  `documentation` TINYINT NULL,
  `procedure` TINYINT NULL,
  `reason` VARCHAR(1024) NULL,
  `proof` VARCHAR(1024) NULL,
  `annotation` VARCHAR(1024) NULL,
  PRIMARY KEY (`question_id`, `interview_id`),
  INDEX `fk_answer_question_id_idx` (`question_id` ASC),
  INDEX `fk_answer_interview_id_idx` (`interview_id` ASC),
  INDEX `fk_answer_faccrit_id_idx` (`faccrit_id` ASC),
  CONSTRAINT `fk_answer_question_id`
    FOREIGN KEY (`question_id`)
    REFERENCES `msg_audit_database`.`question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_answer_interview_id`
    FOREIGN KEY (`interview_id`)
    REFERENCES `msg_audit_database`.`interview` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_answer_faccrit_id`
    FOREIGN KEY (`faccrit_id`)
    REFERENCES `msg_audit_database`.`fac_crit` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `msg_audit_database`.`interview_contact_person`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `msg_audit_database`.`interview_contact_person` (
  `interview_id` INT NOT NULL,
  `contact_person_id` INT NOT NULL,
  `role` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`interview_id`, `contact_person_id`),
  INDEX `fk_interviewcontactperson_contactperson_id_idx` (`contact_person_id` ASC),
  CONSTRAINT `fk_interviewcontactperson_interview_id`
    FOREIGN KEY (`interview_id`)
    REFERENCES `msg_audit_database`.`interview` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_interviewcontactperson_contactperson_id`
    FOREIGN KEY (`contact_person_id`)
    REFERENCES `msg_audit_database`.`contact_person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `msg_audit_database`.`scope`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `msg_audit_database`.`scope` (
  `audit_id` INT NOT NULL,
  `faccrit_id` INT NOT NULL,
  `change_note` VARCHAR(1024) NULL,
  `removed` TINYINT NULL,
  `note` VARCHAR(8096) NULL,
  PRIMARY KEY (`audit_id`, `faccrit_id`),
  INDEX `fk_scope_faccrit_id_idx` (`faccrit_id` ASC),
  CONSTRAINT `fk_scope_audit_id`
    FOREIGN KEY (`audit_id`)
    REFERENCES `msg_audit_database`.`audit` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_scope_faccrit_id`
    FOREIGN KEY (`faccrit_id`)
    REFERENCES `msg_audit_database`.`fac_crit` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
