CREATE TABLE IF NOT EXISTS Task
(
  id     SERIAL PRIMARY KEY,
  name   VARCHAR NOT NULL,
  status VARCHAR NOT NULL
);
CREATE TABLE IF NOT EXISTS Contributor
(
  id   SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS UserTask
(
  id      SERIAL PRIMARY KEY,
  user_id INTEGER REFERENCES Contributor (id) ON DELETE CASCADE,
  task_id INTEGER REFERENCES Task (id) ON DELETE CASCADE
);