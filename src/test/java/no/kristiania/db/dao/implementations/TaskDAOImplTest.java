package no.kristiania.db.dao.implementations;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class TaskDAOImplTest {



    @Before
    public void initiate() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa"); }


    @Test
    public void getAllTasks() {

    }

    @Test
    public void addTask() {
    }

    @Test
    public void updateTask() {
    }

    @Test
    public void deleteTask() {
    }

    @Test
    public void assignTo() {
    }
}