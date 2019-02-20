package no.kristiania.db.dao;

import no.kristiania.db.pojo.Task;
import no.kristiania.db.pojo.Person;

import java.util.List;

public interface TaskDAO {
    public List<Task> getAllTasks();
    public void addTask(Task task);
    public void updateTask(Task task);
    public void deleteTask(Task task);
    public void assignTo(Person person, Task task);
}
