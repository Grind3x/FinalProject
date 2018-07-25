package domain.dao.factory;

import domain.dao.BaseTest;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class ConnectionFactoryTest extends BaseTest {

    @Test
    public void getConnection() throws Exception {
        Connection connection = ConnectionFactory.getConnection();
        assertNotNull(connection);
    }
}