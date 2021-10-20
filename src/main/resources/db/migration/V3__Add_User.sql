CREATE TABLE user (
  id BIGINT NOT NULL,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NULL,
  CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE user ADD CONSTRAINT uc_user_username UNIQUE (username);