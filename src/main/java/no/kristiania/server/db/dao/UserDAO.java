package no.kristiania.server.db.dao;

import no.kristiania.server.db.pojo.Task;
import no.kristiania.server.db.pojo.Person;

import java.util.List;

public interface UserDAO {
    public List<Person> getAllUsers();
    public void addUser(Person person);
    public void updateUser(Person person);
    public void deleteUser(Person person);
    public void assignTo(Task task, Person person);
}
