package no.kristiania.db.dao.implementations;

import no.kristiania.db.dao.UserDAO;
import no.kristiania.db.pojo.Task;
import no.kristiania.db.pojo.User;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    private List<User> users;

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public void assignTo(Task task) {

    }
}
