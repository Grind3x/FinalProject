package domain.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseTest {

    public Connection getH2Connection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:mem:");
    }
}
