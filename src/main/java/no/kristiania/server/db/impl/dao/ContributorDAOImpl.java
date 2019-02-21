package no.kristiania.server.db.impl.dao;

import no.kristiania.server.db.dao.ContributorDAO;
import no.kristiania.server.db.pojo.Task;
import no.kristiania.server.db.pojo.Contributor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContributorDAOImpl implements ContributorDAO {
    private List<Contributor> people;
    private DataSource dataSource;

    public ContributorDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Contributor get(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, name, status FROM TASK WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new Contributor(rs.getString("name"), rs.getInt("id"));
                } else return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Contributor get(String name) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, name FROM CONTRIBUTOR WHERE name = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, name);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new Contributor(rs.getString("name"), rs.getInt("id"));
                } else return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Contributor> getAllUsers() {
        try (Connection connection = dataSource.getConnection()) {
            ArrayList<Contributor> people = new ArrayList<>();
            String sql = "SELECT * FROM Contributor";

            try (ResultSet rs = connection.createStatement().executeQuery(sql)) {
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


    @Override
    public int add(Contributor person) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO Contributor(name) VALUES(?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, person.getName());
                stmt.executeUpdate();
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    person.setId(generatedKeys.getInt(1));
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
    public boolean update(Contributor contributor) {
        if (contributor.getId() == -1) return false;
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE contributor SET name = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, contributor.getName());
                stmt.setInt(2, contributor.getId());
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
            String sql = "DELETE FROM contributor WHERE id =?";
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
    public boolean assignTo(Task task, Contributor contributor) {
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
    public List<Task> tasksAssignedToUser(Contributor contributor) {
        try (Connection connection = dataSource.getConnection()) {
            ArrayList<Task> tasks = new ArrayList<>();
            String sql = "SELECT Task.id, NAME FROM Task INNER JOIN usertask ON usertask.task_id = task.id WHERE usertask.user_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, contributor.getId());
                ResultSet rs = stmt.executeQuery();
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
}

