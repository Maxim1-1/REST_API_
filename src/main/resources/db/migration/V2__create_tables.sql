CREATE TABLE files (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  filePath VARCHAR(255),
  create_at VARCHAR(255)
);

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
  );

CREATE TABLE events (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id int,
  CONSTRAINT fk_user
  FOREIGN KEY (user_id) REFERENCES user (id),
);


CREATE TABLE events_files (
  event_id INT,
  file_id INT,
  CONSTRAINT fk_event
  FOREIGN KEY (event_id) REFERENCES events (id),
  CONSTRAINT fk_file
  FOREIGN KEY (file_id) REFERENCES files (id)
);