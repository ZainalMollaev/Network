CREATE TYPE gender_type AS ENUM ('male', 'female');

create table if not exists users(
    id uuid primary key,
    email text not null unique,
    password text not null unique,
    refresh_token text unique
);

create table if not exists user_profile(
    id uuid primary key,
    name text not null,
    last_name text not null,
    birth_date date not null,
    gender gender_type not null,
    title text,
    company text,
    location text,
    phone_number text unique not null,
    user_id uuid,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

create table if not exists post(
    id uuid primary key,
    title text,
    description text,
    location text,
    profile_id uuid not null,
    FOREIGN KEY (profile_id) REFERENCES user_profile (id)
);

create table if not exists language(
    id int primary key,
    name text unique not null
);

create table if not exists user_profile_language(
    profile_id uuid,
    language_id smallint,
    FOREIGN KEY (profile_id) REFERENCES user_profile (id),
    FOREIGN KEY (language_id) REFERENCES language (id),
    unique(profile_id, language_id)
);

create table if not exists tbl_subscribers(
    profile_id uuid,
    subscriber_id uuid,
    FOREIGN KEY (profile_id) REFERENCES user_profile (id),
    FOREIGN KEY (subscriber_id) REFERENCES user_profile (id),
    unique(profile_id, subscriber_id)
);