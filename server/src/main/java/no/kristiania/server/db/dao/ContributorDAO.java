package no.kristiania.server.db.dao;

import no.kristiania.server.db.pojo.Task;
import no.kristiania.server.db.pojo.Contributor;

import java.util.List;

public interface ContributorDAO {

    Contributor get(String name);
    Contributor get(int id);
    List<Contributor> getAllUsers();
    int add(Contributor contributor);
    boolean update(Contributor contributor);
    boolean delete(int id);
    boolean assignTo(Task task, Contributor contributor);
    boolean assignTo(int task, int owner);
    List<Task> tasksAssignedToUser(Contributor person);
    List<Task> tasksAssignedToUser(int id);
}

