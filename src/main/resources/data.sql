DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id INT PRIMARY KEY,
  USER_NAME VARCHAR(250) NOT NULL,
  PASSWORD VARCHAR(250) NOT NULL,
  EMAIL VARCHAR(250) NOT NULL
);

INSERT INTO users (ID, USER_NAME, PASSWORD, EMAIL) VALUES
  (1, 'first', 'last 1', 'abc1@gmail.com'),
  (2, 'first', 'last 2', 'abc2@gmail.com'),
  (3, 'first', 'last 3', 'abc3@gmail.com');