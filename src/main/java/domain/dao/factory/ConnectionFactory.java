package domain.dao.factory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static String url;
    private static String user;
    private static String password;

    static {
        try {
            Properties dbProperties = new Properties();
            dbProperties.load(ConnectionFactory.class.getClassLoader().getResourceAsStream("jdbc.properties"));
            url = dbProperties.getProperty("url");
            user = dbProperties.getProperty("user");
            password = dbProperties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}