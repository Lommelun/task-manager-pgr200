package no.kristiania.server.db.dao;

import no.kristiania.server.db.pojo.Task;
import no.kristiania.server.db.pojo.Contributor;

import java.util.List;

public interface TaskDAO {
    Task get(String name);
    Task get(int id);
    List<Task> getAllTasks();
    int add(Task task);
    boolean update(Task task);
    boolean delete(int id);
    boolean assignTo(Contributor contributor, Task task);
    List<Contributor> usersAssignedToTask(Task task);
    List<Contributor> usersAssignedToTask(int id);
}
