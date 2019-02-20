package no.kristiania.db.dao.implementations;

import no.kristiania.db.dao.UserDAO;
import no.kristiania.db.pojo.Task;
import no.kristiania.db.pojo.Person;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private List<Person> people;
    private DataSource dataSource;

    public UserDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Person> getAllUsers() {
        try (Connection connection = dataSource.getConnection()) {
            // TODO
            ArrayList<Person> people = new ArrayList<>();
            String sql = "SELECT * FROM Person";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        people.add(new Person(rs.getString("name"), rs.getInt("id")));
                    }
                }
            }
            return people;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void addUser(Person person) {
        try (Connection connection = dataSource.getConnection()) {
            // TODO
            String sql = "INSERT INTO Person(name) VALUES(?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, person.getName());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(Person person) {
        {
            try (Connection connection = dataSource.getConnection()) {
                // TODO
                String sql = "UPDATE person SET name = ? WHERE id = ?";
                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, person.getId());
                    stmt.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void deleteUser(Person person) {
        try (Connection connection = dataSource.getConnection()) {
            // TODO
            String sql = "DELETE FROM person WHERE id =?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, person.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void assignTo(Task task, Person person)  {
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
