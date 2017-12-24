DROP TABLE IF EXISTS entity;

CREATE TABLE entity (
  id   BIGSERIAL NOT NULL,
  name VARCHAR(255),
  PRIMARY KEY (id)
);

INSERT INTO entity (name) VALUES ('entity1');
INSERT INTO entity (name) VALUES ('entity2');
INSERT INTO entity (name) VALUES ('entity3');