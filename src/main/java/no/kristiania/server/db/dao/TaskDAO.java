package no.kristiania.server.db.dao;

import no.kristiania.server.db.pojo.Task;
import no.kristiania.server.db.pojo.Person;

import java.util.List;

public interface TaskDAO {
    public Task get(String name);
    public Task get(int id);
    public List<Task> getAllTasks();
    public void add(Task task);
    public void update(Task task);
    public void deleteTask(Task task);
    public void assignTo(Person person, Task task);
}
