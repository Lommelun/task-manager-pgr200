package no.kristiania.server.db.dao;

import no.kristiania.server.db.pojo.Task;
import no.kristiania.server.db.pojo.Contributor;

import java.util.List;

public interface ContributorDAO {

    public Contributor get(String name);
    public Contributor get(int id);
    public List<Contributor> getAllUsers();
    public int add(Contributor contributor);
    public boolean update(Contributor contributor);
    public boolean delete(int id);
    public boolean assignTo(Task task, Contributor contributor);

    List<Task> tasksAssignedToUser(Contributor person);
}

