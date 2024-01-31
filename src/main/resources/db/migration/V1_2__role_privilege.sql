CREATE TYPE privileges
    AS ENUM ('write', 'read');

CREATE TABLE role(
    id INT PRIMARY KEY,
    name roles not null unique
);

CREATE TABLE privilege(
     id INT PRIMARY KEY,
     name privileges not null unique
);

CREATE TABLE user_profile_roles(
   profile_id uuid,
   role_id INT,
   UNIQUE (profile_id, role_id),
   FOREIGN KEY (profile_id) REFERENCES user_profile,
   FOREIGN KEY (role_id) REFERENCES role
);

CREATE TABLE role_privilege(
   privilege_id INT,
   role_id INT,
   UNIQUE (privilege_id, role_id),
   FOREIGN KEY (privilege_id) REFERENCES privilege,
   FOREIGN KEY (role_id) REFERENCES role
)

