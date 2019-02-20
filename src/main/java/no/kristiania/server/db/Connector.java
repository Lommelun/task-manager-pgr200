package no.kristiania.server.db;

import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private static Connector connector = new Connector();
    private PGPoolingDataSource dataSource;

    private Connector() {
        Properties prop = new Properties();
        try {
            InputStream propertiesFile = Connector.class.getClassLoader().getResourceAsStream("db.properties");
            if (propertiesFile == null) {
                return;
            }
            prop.load(propertiesFile);

            dataSource = new PGPoolingDataSource();
            dataSource.setUrl(prop.getProperty("url"));
            dataSource.setUser(prop.getProperty("username"));
            dataSource.setPassword(prop.getProperty("password"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connector getConnector() {
        return connector;
    }

    public DataSource getConnection() throws SQLException {
        return dataSource;
    }
}
