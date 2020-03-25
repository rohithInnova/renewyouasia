CREATE DATABASE POC_DB;

DROP TABLE IF EXISTS POC_DB.TBL_ROLE;
  
CREATE TABLE POC_DB.TBL_ROLE (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  role_name VARCHAR(250) NOT NULL
);

DROP TABLE IF EXISTS POC_DB.TBL_USER;
  
CREATE TABLE POC_DB.TBL_USER (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  user_name VARCHAR(25) NOT NULL UNIQUE,
  password VARCHAR(250),
  role_id INT 
);

ALTER TABLE POC_DB.TBL_USER 
   ADD FOREIGN KEY (role_id) REFERENCES POC_DB.TBL_ROLE (id);

