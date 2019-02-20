package no.kristiania.server.db.impl.dao;

import no.kristiania.server.db.dao.TaskDAO;
import no.kristiania.server.db.pojo.Task;
import no.kristiania.server.db.pojo.Person;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAOImpl implements TaskDAO {
    private DataSource dataSource;

    public TaskDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Task get(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, name, status FROM TASK WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try(ResultSet rs = stmt.executeQuery()){

                    return new Task(rs.getString("name"),rs.getInt("status"),rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Task get(String name) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, name, status FROM TASK WHERE name = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, name);
                try(ResultSet rs = stmt.executeQuery()){
                    return new Task(rs.getString("name"),rs.getInt("status"),rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public List<Task> getAllTasks() {
        try (Connection connection = dataSource.getConnection()) {
            // TODO
            ArrayList<Task> tasks = new ArrayList<>();
            String sql = "SELECT * FROM Task";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        tasks.add(new Task(rs.getString("name"), rs.getInt("status"), rs.getInt("id")));
                    }
                }
            }
            return tasks;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void add(Task task) {
        try (Connection connection = dataSource.getConnection()) {
            // TODO
            String sql = "INSERT INTO Task(name,status) VALUES(?,?)";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, task.getName());
                stmt.setInt(2, task.getStatus());
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Task task) {
        try (Connection connection = dataSource.getConnection()) {
            // TODO
            String sql = "UPDATE Task SET NAME = ?, status = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, task.getName());
                stmt.setInt(2, task.getStatus());
                stmt.setInt(3, task.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTask(Task task) {
        try (Connection connection = dataSource.getConnection()) {
            // TODO
            String sql = "DELETE FROM Task WHERE id =?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, task.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void assignTo(Person person, Task task) {
        try (Connection connection = dataSource.getConnection()) {
            // TODO
            String sql = "INSERT INTO UserTask (User_id, Task_id) VALUES(?,?);";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, person.getId());
                stmt.setInt(2, task.getId());
                stmt.executeUpdate();
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

}
