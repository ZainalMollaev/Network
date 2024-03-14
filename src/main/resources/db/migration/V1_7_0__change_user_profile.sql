ALTER TABLE user_profile
ADD COLUMN birth_privacy privacy;

ALTER TABLE user_profile
    ADD COLUMN job_privacy privacy;

ALTER TABLE user_profile
    ADD COLUMN education_privacy privacy;

ALTER TABLE user_profile
    ADD COLUMN location_privacy privacy;