DROP TABLE IF EXISTS contract;

CREATE TABLE contract (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  main VARCHAR(250),
  details VARCHAR(250),
  status VARCHAR(250),
  num INT
);