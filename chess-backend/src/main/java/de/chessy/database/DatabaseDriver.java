package de.chessy.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseDriver {

    private static final String JDBC_DRIVER = "org.postgresql.Driver";

    private static final String username =
            "postgres";


    private static final String password =
            "password";

    private static final int port = 5432;

    private static final String host =
            "chess-db";

    private static final String dbName =
            "postgres";

    private static final String DB_URL = "jdbc:postgresql://" + host + ":" + port + "/" + dbName;

    private static Connection connection;

    private DatabaseDriver() {

    }

    public static void connect() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
