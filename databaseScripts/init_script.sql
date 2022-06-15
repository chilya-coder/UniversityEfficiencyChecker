CREATE TABLE `university_efficiency_db`.`users`
(
    `user_id`             INT         NOT NULL AUTO_INCREMENT,
    `first_name`          VARCHAR(45) NOT NULL,
    `last_name`           VARCHAR(45) NOT NULL,
    `patronymic`          VARCHAR(45) NOT NULL,
    `department_id`       INT         NOT NULL,
    `email`               VARCHAR(45) NOT NULL,
    `position_id`         INT         NOT NULL,
    `scientific_title_id` INT         NOT NULL,
    `degree_id`           INT         NOT NULL,
    PRIMARY KEY (`user_id`)
);

CREATE TABLE `university_efficiency_db`.`positions`
(
    `position_id` INT         NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(45) NOT NULL,
    PRIMARY KEY (`position_id`)
);

CREATE TABLE `university_efficiency_db`.`specialties`
(
    `specialty_id`    INT         NOT NULL AUTO_INCREMENT,
    `name`            VARCHAR(45) NOT NULL,
    `code`            VARCHAR(45) NOT NULL,
    `specialty_alias` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`specialty_id`)
);

CREATE TABLE `university_efficiency_db`.`departments`
(
    `department_id` INT         NOT NULL AUTO_INCREMENT,
    `name`          VARCHAR(45) NOT NULL,
    `faculty_id`    INT         NOT NULL,
    PRIMARY KEY (`department_id`)
);

CREATE TABLE `university_efficiency_db`.`faculties`
(
    `faculty_id` INT          NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(100) NOT NULL,
    PRIMARY KEY (`faculty_id`)
);

CREATE TABLE `university_efficiency_db`.`user_credentials`
(
    `user_cred_id`    INT         NOT NULL AUTO_INCREMENT,
    `login`           VARCHAR(45) NOT NULL,
    `password`        VARCHAR(45) NOT NULL,
    `user_id`         INT         NOT NULL,
    `user_role_id`    INT         NOT NULL,
    `is_user_enabled` TINYINT     NOT NULL,
    PRIMARY KEY (`user_cred_id`)
);

CREATE TABLE `university_efficiency_db`.`user_met_requirements`
(
    `user_met_req_id` INT NOT NULL AUTO_INCREMENT,
    `user_id`         INT NOT NULL,
    `specialty_id`    INT NOT NULL,
    `year`            INT NOT NULL,
    PRIMARY KEY (`user_met_req_id`)
);

CREATE TABLE `university_efficiency_db`.`req_time_data`
(
    `req_time_data_id` INT  NOT NULL AUTO_INCREMENT,
    `sysdate`          DATE NOT NULL,
    `date_in`          DATE NOT NULL,
    `date_off`         DATE NULL,
    `user_met_req_id`  INT  NOT NULL,
    PRIMARY KEY (`req_time_data_id`)
);

CREATE TABLE `university_efficiency_db`.`degrees`
(
    `degree_id` INT         NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(45) NOT NULL,
    PRIMARY KEY (`degree_id`)
);

CREATE TABLE `university_efficiency_db`.`scientific_titles`
(
    `scientific_title_id` INT         NOT NULL AUTO_INCREMENT,
    `name`                VARCHAR(45) NOT NULL,
    PRIMARY KEY (`scientific_title_id`)
);

CREATE TABLE `university_efficiency_db`.`science_works`
(
    `science_work_id`     INT          NOT NULL AUTO_INCREMENT,
    `name`                VARCHAR(250) NOT NULL,
    `char_of_work_id`     INT          NOT NULL,
    `date_of_publication` DATE         NOT NULL,
    `type_of_work_id`     INT NULL,
    `size`                INT NULL,
    `has_ext_authors`     TINYINT NULL,
    `has_ext_stud`        TINYINT NULL,
    `output_data`         VARCHAR(2000) NULL,
    `specialty_id`        INT NULL,
    PRIMARY KEY (`science_work_id`)
);
CREATE TABLE `university_efficiency_db`.`requirements_types`
(
    `req_type_id` INT          NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(500) NOT NULL,
    `req_number`  INT          NOT NULL,
    `condition`   VARCHAR(100) NOT NULL,
    PRIMARY KEY (`req_type_id`)
);

CREATE TABLE `university_efficiency_db`.`character_of_work`
(
    `char_of_work_id` INT         NOT NULL AUTO_INCREMENT,
    `name`            VARCHAR(45) NOT NULL,
    PRIMARY KEY (`char_of_work_id`)
);

CREATE TABLE `university_efficiency_db`.`type_of_work`
(
    `type_of_work_id` INT         NOT NULL AUTO_INCREMENT,
    `name`            VARCHAR(45) NOT NULL,
    PRIMARY KEY (`type_of_work_id`)
);

CREATE TABLE `university_efficiency_db`.`authors_science_works`
(
    `auth_science_work_id` INT NOT NULL AUTO_INCREMENT,
    `science_work_id`      INT NOT NULL,
    `user_id`              INT NOT NULL,
    PRIMARY KEY (`auth_science_work_id`)
);

CREATE TABLE `university_efficiency_db`.`ext_auth_science_works`
(
    `ext_auth_science_work_id` INT NOT NULL AUTO_INCREMENT,
    `ext_author_id`            INT NOT NULL,
    `science_work_id`          INT NOT NULL,
    PRIMARY KEY (`ext_auth_science_work_id`)
);

CREATE TABLE `university_efficiency_db`.`external_authors`
(
    `ext_author_id` INT         NOT NULL AUTO_INCREMENT,
    `name`          VARCHAR(45) NOT NULL,
    `patronymic`    VARCHAR(45) NOT NULL,
    `surname`       VARCHAR(45) NOT NULL,
    PRIMARY KEY (`ext_author_id`)
);

CREATE TABLE `university_efficiency_db`.`external_students`
(
    `ext_student_id` INT         NOT NULL AUTO_INCREMENT,
    `name`           VARCHAR(45) NOT NULL,
    `surname`        VARCHAR(45) NOT NULL,
    `patronymic`     VARCHAR(45) NOT NULL,
    `group_id`       VARCHAR(45),
    PRIMARY KEY (`ext_student_id`)
);

CREATE TABLE `university_efficiency_db`.`ext_stud_science_work`
(
    `ext_student_science_work_id` INT NOT NULL AUTO_INCREMENT,
    `ext_student_id`              INT NOT NULL,
    `science_work_id`             INT NOT NULL,
    PRIMARY KEY (`ext_student_science_work_id`)
);

CREATE TABLE `university_efficiency_db`.`contracts`
(
    `contract_id`  INT  NOT NULL AUTO_INCREMENT,
    `date_start`   DATE NOT NULL,
    `date_end`     DATE NOT NULL,
    `user_id`      INT  NOT NULL,
    `specialty_id` INT,
    PRIMARY KEY (`contract_id`),
    INDEX          `user_contract_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `user_contract`
        FOREIGN KEY (`user_id`)
            REFERENCES `university_efficiency_db`.`users` (`user_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);



ALTER TABLE `university_efficiency_db`.`users`
    ADD INDEX `user_department_idx` (`department_id` ASC) VISIBLE;
ALTER TABLE `university_efficiency_db`.`users`
    ADD CONSTRAINT `user_department`
        FOREIGN KEY (`department_id`)
            REFERENCES `university_efficiency_db`.`departments` (`department_id`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE;

ALTER TABLE `university_efficiency_db`.`users`
    ADD INDEX `user_position_idx` (`position_id` ASC) VISIBLE;
ALTER TABLE `university_efficiency_db`.`users`
    ADD CONSTRAINT `user_position`
        FOREIGN KEY (`position_id`)
            REFERENCES `university_efficiency_db`.`positions` (`position_id`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE;

ALTER TABLE `university_efficiency_db`.`user_credentials`
    ADD INDEX `user_cred_user_idx` (`user_id` ASC) VISIBLE;

ALTER TABLE `university_efficiency_db`.`user_credentials`
    ADD CONSTRAINT `user_cred_user`
        FOREIGN KEY (`user_id`)
            REFERENCES `university_efficiency_db`.`users` (`user_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE;

ALTER TABLE `university_efficiency_db`.`departments`
    ADD INDEX `dep_fac_idx` (`faculty_id` ASC) VISIBLE;

ALTER TABLE `university_efficiency_db`.`users`
    ADD CONSTRAINT `user_scientific_title`
        FOREIGN KEY (`scientific_title_id`)
            REFERENCES `university_efficiency_db`.`scientific_titles` (`scientific_title_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    ADD CONSTRAINT `user_degree`
      FOREIGN KEY (`degree_id`)
      REFERENCES `university_efficiency_db`.`degrees` (`degree_id`)
      ON
DELETE
CASCADE
      ON
UPDATE CASCADE;

ADD INDEX `user_scientific_title_idx` (`scientific_title_id` ASC) VISIBLE,
ADD INDEX `user_degree_idx` (`degree_id` ASC) VISIBLE;
ALTER TABLE `university_efficiency_db`.`departments`
    ADD CONSTRAINT `dep_fac`
        FOREIGN KEY (`faculty_id`)
            REFERENCES `university_efficiency_db`.`faculties` (`faculty_id`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE;


ALTER TABLE `university_efficiency_db`.`users`
    ADD CONSTRAINT `user_scientific_title`
        FOREIGN KEY (`scientific_title_id`)
            REFERENCES `university_efficiency_db`.`scientific_titles` (`scientific_title_id`)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT,
    ADD CONSTRAINT `user_degree`
      FOREIGN KEY (`degree_id`)
      REFERENCES `university_efficiency_db`.`degrees` (`degree_id`)
      ON
DELETE
RESTRICT
      ON
UPDATE RESTRICT;

ADD INDEX `specialties_science_works_idx` (`specialty_id` ASC) VISIBLE;
ALTER TABLE `university_efficiency_db`.`science_works`
    ADD CONSTRAINT `specialties_science_works`
        FOREIGN KEY (`specialty_id`)
            REFERENCES `university_efficiency_db`.`specialties` (`specialty_id`)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT;

ADD INDEX `contract_specialty_idx` (`specialty_id` ASC) VISIBLE;
ALTER TABLE `university_efficiency_db`.`contracts`
    ADD CONSTRAINT `contract_specialty`
        FOREIGN KEY (`specialty_id`)
            REFERENCES `university_efficiency_db`.`specialties` (`specialty_id`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE;