CREATE TABLE likes(
    id UUID PRIMARY KEY,
    post_id UUID NOT NULL,
    user_id UUID NOT NULL,
    UNIQUE (post_id, user_id)
);

