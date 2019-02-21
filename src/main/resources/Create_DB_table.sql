create table if not exists Task (id SERIAL primary key, name varchar not null, status varchar not null);

create table if not exists Contributor (id SERIAL primary key, name varchar not null);

create table if not exists UserTask (id SERIAL PRIMARY KEY, user_id INTEGER REFERENCES Contributor(id) ON DELETE CASCADE,
Task_id INTEGER REFERENCES Task(id) ON DELETE CASCADE );