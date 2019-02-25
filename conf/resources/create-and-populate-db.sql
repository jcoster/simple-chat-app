CREATE TABLE user (
  id INT,
  username TEXT
);

INSERT INTO user VALUES (1111, "John.Smith");
INSERT INTO user VALUES (2222, "Mary.Jane");
INSERT INTO user VALUES (3333, "Peter.Parker");

CREATE TABLE message (
  timestamp TEXT,
  from_user INT,
  to_user INT,
  content TEXT
);