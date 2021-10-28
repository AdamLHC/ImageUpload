CREATE TABLE member_attribute (
  member_id INT NOT NULL,
  name VARCHAR(255) NULL,
  value VARCHAR(255) NULL
);

CREATE TABLE submission_tags (submission_id INT NOT NULL, tag VARCHAR(255) NULL);

CREATE TABLE subscriber (
  id BIGINT NOT NULL,
  username VARCHAR(255) NULL,
  CONSTRAINT pk_subscriber PRIMARY KEY (id)
);

CREATE TABLE subscriber_subscribed_tags (subscriber_id BIGINT NOT NULL, tag VARCHAR(255) NULL);

CREATE TABLE user_roles (
  id BIGINT NOT NULL,
  name VARCHAR(255) NULL,
  user_id BIGINT NULL,
  CONSTRAINT pk_user_roles PRIMARY KEY (id)
);

ALTER TABLE user ADD account_enabled BIT(1) NULL;

ALTER TABLE user ADD account_expired BIT(1) NULL;

ALTER TABLE user ADD account_locked BIT(1) NULL;

ALTER TABLE user ADD credentials_expired BIT(1) NULL;

ALTER TABLE member ADD introduction VARCHAR(255) NULL;

ALTER TABLE user_roles ADD CONSTRAINT FK_USER_ROLES_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE member_attribute ADD CONSTRAINT fk_member_attribute_on_member FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE submission_tags ADD CONSTRAINT fk_submission_tags_on_submission FOREIGN KEY (submission_id) REFERENCES submission (id);

ALTER TABLE subscriber_subscribed_tags ADD CONSTRAINT fk_subscriber_subscribed_tags_on_subscriber FOREIGN KEY (subscriber_id) REFERENCES subscriber (id);

ALTER TABLE member DROP COLUMN notes;