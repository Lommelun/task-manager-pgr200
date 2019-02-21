package no.kristiania.db.test.util;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtils {
    public static void resetTestDB(DataSource dataSource) {
        try (Connection con = dataSource.getConnection()) {
            Statement stmt = con.createStatement();
            String sql = "DROP TABLE task, usertask, contributor";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static JdbcDataSource initializeTestDb() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
        dataSource.setUser("sa");
        try (Connection con = dataSource.getConnection()) {
            Statement stmt = con.createStatement();
            String sql = "create table if not exists Task (id SERIAL primary key, NAME varchar(10) not null, STATUS varchar(10) not null); " +
                    "create table if not exists Contributor (id SERIAL primary key, NAME VARCHAR(10) not null ); " +
                    "create table if not exists UserTask (id SERIAL PRIMARY KEY, User_id INTEGER REFERENCES Contributor(id) ON DELETE CASCADE, " +
                    "Task_id INTEGER REFERENCES Task(id) ON DELETE CASCADE );";
            stmt.executeUpdate(sql);
            return dataSource;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
