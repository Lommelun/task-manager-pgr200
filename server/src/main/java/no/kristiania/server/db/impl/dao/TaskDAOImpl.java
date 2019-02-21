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
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new Task(rs.getString("name"), rs.getInt("status"), rs.getInt("id"));
                } else return null;
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
                ResultSet rs = stmt.executeQuery();
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

            try (ResultSet rs = connection.createStatement().executeQuery(sql)) {
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
    public int add(Task task) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO Task(name,status) VALUES(?,?)";

            try (PreparedStatement stmt = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, task.getName());
                stmt.setInt(2, task.getStatus());
                stmt.executeUpdate();
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    task.setId(generatedKeys.getInt(1));
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("No ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean update(Task task) {
        if (task.getId() == -1) return false;
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
            String sql = "INSERT INTO UserTask (user_id, task_id) VALUES(?,?);";
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
        return usersAssignedToTask(task.getId());
    }

    @Override
    public List<Contributor> usersAssignedToTask(int id) {
        try (Connection connection = dataSource.getConnection()) {
            ArrayList<Contributor> people = new ArrayList<>();
            String sql = "SELECT Contributor.id, NAME FROM Contributor INNER JOIN usertask ON usertask.user_id = contributor.id WHERE usertask.task_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
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


