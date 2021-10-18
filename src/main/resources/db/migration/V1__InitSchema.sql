CREATE TABLE member (
  id INT NOT NULL,
  name VARCHAR(255) NULL,
  nick_name VARCHAR(255) NULL,
  notes VARCHAR(255) NULL,
  CONSTRAINT pk_member PRIMARY KEY (id)
);

CREATE TABLE submission (
  id INT NOT NULL,
  file_key VARCHAR(255) NULL,
  title VARCHAR(255) NULL,
  descriptive VARCHAR(255) NULL,
  upload_date datetime NULL,
  member_id INT NOT NULL,
  CONSTRAINT pk_submission PRIMARY KEY (id)
);

ALTER TABLE submission ADD CONSTRAINT FK_SUBMISSION_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (id);