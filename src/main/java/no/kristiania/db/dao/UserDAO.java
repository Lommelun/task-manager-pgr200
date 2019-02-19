package no.kristiania.db.dao;

import no.kristiania.db.pojo.Task;
import no.kristiania.db.pojo.User;

import java.util.List;

public interface UserDAO {
    public List<User> getAllUsers();
    public void addUser(User user);
    public void updateUser(User user);
    public void deleteUser(User user);
    public void assignTo(Task task);
}
