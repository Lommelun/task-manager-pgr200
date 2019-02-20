package no.kristiania.server.db.dao;

import no.kristiania.server.db.pojo.Task;
import no.kristiania.server.db.pojo.Person;

import java.util.List;

public interface UserDAO {

    public Person get(String name);
    public Person get(int id);
    public List<Person> getAllUsers();
    public void add(Person person);
    public void update(Person person);
    public void delete(Person person);
    public void assignTo(Task task, Person person);
}

