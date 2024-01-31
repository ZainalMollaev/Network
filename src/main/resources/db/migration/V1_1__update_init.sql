CREATE TYPE roles AS ENUM ('user', 'admin');

ALTER TABLE tbl_subscribers
    ADD CONSTRAINT uc_43ad8907655a8e852d9c1afbe UNIQUE (user_id, subscriber_id);