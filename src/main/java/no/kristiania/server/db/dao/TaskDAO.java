package no.kristiania.server.db.dao;

import no.kristiania.server.db.pojo.Task;
import no.kristiania.server.db.pojo.Contributor;

import java.util.List;

public interface TaskDAO {
    public Task get(String name);
    public Task get(int id);
    public List<Task> getAllTasks();
    public boolean add(Task task);
    public boolean update(Task task);
    public boolean delete(int id);
    public boolean assignTo(Contributor contributor, Task task);
    public List<Contributor> usersAssignedToTask(Task task);
}
