package no.kristiania.server.db.impl.dao;

import no.kristiania.server.db.dao.TaskDAO;
import no.kristiania.server.db.pojo.Task;
import no.kristiania.server.db.pojo.Contributor;

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
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                stmt.setInt(1, id);
                if (rs.next()) {
                    return new Task(rs.getString("name"), rs.getInt("status"), rs.getInt("id"));
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
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                stmt.setString(1, name);
                if (rs.next()) {
                    return new Task(rs.getString("NAME"), rs.getInt("STATUS"), rs.getInt("ID"));
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
            ArrayList<Task> tasks = new ArrayList<>();
            String sql = "SELECT * FROM Task";

            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add(new Task(rs.getString("name"), rs.getInt("status"), rs.getInt("id")));
                }
            }
            return tasks;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean add(Task task) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO Task(name,status) VALUES(?,?)";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, task.getName());
                stmt.setInt(2, task.getStatus());
                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Task task) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE Task SET NAME = ?, status = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, task.getName());
                stmt.setInt(2, task.getStatus());
                stmt.setInt(3, task.getId());
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM Task WHERE id =?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean assignTo(Contributor contributor, Task task) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO UserTask (User_id, Task_id) VALUES(?,?);";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, contributor.getId());
                stmt.setInt(2, task.getId());
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Contributor> usersAssignedToTask(Task task) {
        try (Connection connection = dataSource.getConnection()) {
            ArrayList<Contributor> people = new ArrayList<>();
            String sql = "SELECT Person.id, NAME FROM Person INNER JOIN usertask ON usertask.user_id = person.id WHERE usertask.task_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                stmt.setInt(1, task.getId());
                while (rs.next()) {
                    people.add(new Contributor(rs.getString("name"), rs.getInt("id")));
                }
            }
            return people;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}


