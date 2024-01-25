CREATE TABLE files (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  file_path VARCHAR(255),
  create_at VARCHAR(255),
  updated_at VARCHAR(255),
  status  enum('ACTIVE', 'DELETED')

);

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    status  enum('ACTIVE', 'DELETED')
  );

CREATE TABLE events (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id int,
  file_id int,
  status  enum('ACTIVE', 'DELETED'),
  CONSTRAINT fk_user
  FOREIGN KEY (user_id) REFERENCES user (id),
  CONSTRAINT fk_event_file
  FOREIGN KEY (file_id) REFERENCES files (id)
);
