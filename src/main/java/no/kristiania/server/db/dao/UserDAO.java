package no.kristiania.server.db.dao;

import no.kristiania.server.db.pojo.Task;
import no.kristiania.server.db.pojo.Person;

import java.util.List;

public interface UserDAO {

    public Person get(String name);
    public Person get(int id);
    public List<Person> getAllUsers();
    public boolean add(Person person);
    public boolean update(Person person);
    public boolean delete(int id);
    public boolean assignTo(Task task, Person person);
    public List<Task> tasksAssignedToUser(Person person);

}

