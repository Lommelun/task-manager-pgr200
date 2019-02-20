package no.kristiania.server.db.dao;

import no.kristiania.server.db.pojo.Task;
import no.kristiania.server.db.pojo.Person;

import java.util.List;

public interface TaskDAO {
    public Task get(String name);
    public Task get(int id);
    public List<Task> getAllTasks();
    public boolean add(Task task);
    public boolean update(Task task);
    public boolean delete(int id);
    public boolean assignTo(Person person, Task task);
    public List<Person> usersAssignedToTask(Task task);
}
