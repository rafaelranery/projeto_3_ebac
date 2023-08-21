package br.com.rnery.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static Connection c;

    private ConnectionFactory() {};

    public static Connection getConnetion() throws SQLException {
        if(c == null) {
            c = init();
        } else if(c != null && c.isClosed()) {
            c = init();
        }
        return c;
    }

    private static Connection init() {
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:54320/ebac_projeto_3", "user", "admin"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
