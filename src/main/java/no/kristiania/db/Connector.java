package no.kristiania.db;

import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Connector {
    private static Connector connector = new Connector();
    private PGPoolingDataSource dataSource;

    private Connector() {
        dataSource = new PGPoolingDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost/task_manager_db");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
    }

    public static Connector getConnector() {
        return connector;
    }

    public DataSource getConnection() throws SQLException {
        return dataSource;
    }
}
