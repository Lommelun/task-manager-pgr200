create table if not exists Task (id SERIAL primary key, NAME varchar not null, STATUS varchar not null);

create table if not exists Person (id SERIAL primary key, NAME varchar not null);

create table if not exists UserTasks (id SERIAL PRIMARY KEY, User_id INTEGER REFERENCES Person(id),
Task_id INTEGER REFERENCES Task(id) );
