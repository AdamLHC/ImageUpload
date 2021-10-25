CREATE TABLE member_attribute (
  id BIGINT NOT NULL,
  name VARCHAR(255) NULL,
  value VARCHAR(255) NULL,
  member_id INT NULL,
  CONSTRAINT pk_memberattribute PRIMARY KEY (id)
);

CREATE TABLE member_subscribed_tags (
  member_id INT NOT NULL,
  subscribed_tags_id BIGINT NOT NULL
);

CREATE TABLE submission_tags (submission_id INT NOT NULL, tags_id BIGINT NOT NULL);

CREATE TABLE tag (
  id BIGINT NOT NULL,
  name VARCHAR(255) NULL,
  CONSTRAINT pk_tag PRIMARY KEY (id)
);

CREATE TABLE user_roles (
  id BIGINT NOT NULL,
  name VARCHAR(255) NULL,
  user_id BIGINT NULL,
  CONSTRAINT pk_userroles PRIMARY KEY (id)
);

ALTER TABLE user ADD account_enabled BIT(1) NULL;

ALTER TABLE user ADD account_expired BIT(1) NULL;

ALTER TABLE user ADD account_locked BIT(1) NULL;

ALTER TABLE user ADD credentials_expired BIT(1) NULL;

ALTER TABLE member ADD introduction VARCHAR(255) NULL;

ALTER TABLE member_subscribed_tags ADD CONSTRAINT uc_member_subscribed_tags_subscribedtags UNIQUE (subscribed_tags_id);

ALTER TABLE submission_tags ADD CONSTRAINT uc_submission_tags_tags UNIQUE (tags_id);

ALTER TABLE member_attribute ADD CONSTRAINT FK_MEMBERATTRIBUTE_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE user_roles ADD CONSTRAINT FK_USERROLES_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE member_subscribed_tags ADD CONSTRAINT fk_memsubtag_on_member FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE member_subscribed_tags ADD CONSTRAINT fk_memsubtag_on_tag FOREIGN KEY (subscribed_tags_id) REFERENCES tag (id);

ALTER TABLE submission_tags ADD CONSTRAINT fk_subtag_on_submission FOREIGN KEY (submission_id) REFERENCES submission (id);

ALTER TABLE submission_tags ADD CONSTRAINT fk_subtag_on_tag FOREIGN KEY (tags_id) REFERENCES tag (id);

ALTER TABLE member DROP COLUMN notes;