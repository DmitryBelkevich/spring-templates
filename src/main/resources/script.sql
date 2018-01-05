INSERT INTO role VALUES (1, 'ROLE_USER');
INSERT INTO role VALUES (2, 'ROLE_ADMIN');
INSERT INTO role VALUES (3, 'ROLE_ADMIN_DB');

INSERT INTO users (username, password) VALUES ('admin', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');
INSERT INTO users (username, password) VALUES ('admin_db', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');

INSERT INTO user_role VALUES (1, 2);
INSERT INTO user_role VALUES (2, 3);