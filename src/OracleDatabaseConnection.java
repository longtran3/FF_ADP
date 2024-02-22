//this class tests the connection of the Oracle database

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class OracleDatabaseConnection {
    private static final String JDBC_URL = "jdbc:oracle:thin:@//artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    private static final String USERNAME = "dtran39";
    private static final String PASSWORD = "ituptoal";

    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            System.out.println("Connected to Oracle database!");

            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}

