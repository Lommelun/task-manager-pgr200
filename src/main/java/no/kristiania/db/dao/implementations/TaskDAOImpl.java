package no.kristiania.db.dao.implementations;

import no.kristiania.db.dao.TaskDAO;
import no.kristiania.db.pojo.Task;
import no.kristiania.db.pojo.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TaskDAOImpl implements TaskDAO {
    private DataSource dataSource;

    public TaskDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Task> getAllTasks() {
        try (Connection connection = dataSource.getConnection()) {
            // TODO
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addTask(Task task) {
        try (Connection connection = dataSource.getConnection()) {
            // TODO
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTask(Task task) {
        try (Connection connection = dataSource.getConnection()) {
            // TODO
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTask(Task task) {
        try (Connection connection = dataSource.getConnection()) {
            // TODO
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void assignTo(User user) {
        try (Connection connection = dataSource.getConnection()) {
            // TODO
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
