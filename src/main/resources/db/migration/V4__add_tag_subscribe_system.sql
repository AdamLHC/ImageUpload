CREATE TABLE member_attribute (
  id BIGINT NOT NULL,
  name VARCHAR(255) NULL,
  value VARCHAR(255) NULL,
  member_id INT NOT NULL,
  CONSTRAINT pk_memberattribute PRIMARY KEY (id)
);

CREATE TABLE member_subscribed_tags (
  member_id INT NOT NULL,
  subscribed_tags_id BIGINT NOT NULL,
  CONSTRAINT pk_member_subscribedtags PRIMARY KEY (member_id, subscribed_tags_id)
);

CREATE TABLE submission_submission_tags (
  submission_id INT NOT NULL,
  submission_tags_id BIGINT NOT NULL,
  CONSTRAINT pk_submission_submissiontags PRIMARY KEY (submission_id, submission_tags_id)
);

CREATE TABLE submission_tag (
  id BIGINT NOT NULL,
  name VARCHAR(255) NULL,
  CONSTRAINT pk_submissiontag PRIMARY KEY (id)
);

CREATE TABLE submission_tag_related_submissions (
  submission_tag_id BIGINT NOT NULL,
  related_submissions_id INT NOT NULL,
  CONSTRAINT pk_submissiontag_relatedsubmissions PRIMARY KEY (submission_tag_id, related_submissions_id)
);

CREATE TABLE submission_tag_subscribers (
  submission_tag_id BIGINT NOT NULL,
  subscribers_id INT NOT NULL,
  CONSTRAINT pk_submissiontag_subscribers PRIMARY KEY (submission_tag_id, subscribers_id)
);

ALTER TABLE member ADD introduction VARCHAR(255) NULL;

ALTER TABLE member_attribute ADD CONSTRAINT FK_MEMBERATTRIBUTE_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE member_subscribed_tags ADD CONSTRAINT fk_memsubtag_on_member FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE member_subscribed_tags ADD CONSTRAINT fk_memsubtag_on_submission_tag FOREIGN KEY (subscribed_tags_id) REFERENCES submission_tag (id);

ALTER TABLE submission_submission_tags ADD CONSTRAINT fk_subsubtag_on_submission FOREIGN KEY (submission_id) REFERENCES submission (id);

ALTER TABLE submission_submission_tags ADD CONSTRAINT fk_subsubtag_on_submission_tag FOREIGN KEY (submission_tags_id) REFERENCES submission_tag (id);

ALTER TABLE submission_tag_related_submissions ADD CONSTRAINT fk_subtagrelsub_on_submission FOREIGN KEY (related_submissions_id) REFERENCES submission (id);

ALTER TABLE submission_tag_related_submissions ADD CONSTRAINT fk_subtagrelsub_on_submission_tag FOREIGN KEY (submission_tag_id) REFERENCES submission_tag (id);

ALTER TABLE submission_tag_subscribers ADD CONSTRAINT fk_subtagsub_on_member FOREIGN KEY (subscribers_id) REFERENCES member (id);

ALTER TABLE submission_tag_subscribers ADD CONSTRAINT fk_subtagsub_on_submission_tag FOREIGN KEY (submission_tag_id) REFERENCES submission_tag (id);

ALTER TABLE member DROP COLUMN notes;