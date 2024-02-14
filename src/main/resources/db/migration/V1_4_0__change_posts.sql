CREATE TYPE privacies
    AS ENUM ('all', 'subscribers', 'only_me');

ALTER TABLE post
    ADD COLUMN privacy privacies;