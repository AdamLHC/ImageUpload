CREATE SEQUENCE hibernate_sequence INCREMENT BY 1 START WITH 1;

ALTER TABLE member ADD user_name VARCHAR(255) NULL;

ALTER TABLE member DROP COLUMN name;