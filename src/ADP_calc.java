import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ADP_calc {
    private static final String JDBC_URL = "jdbc:oracle:thin:@//artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    private static final String USERNAME = "dtran39";
    private static final String PASSWORD = "ituptoal";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Fetch data from ROS_2023 table
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("SELECT PLAYER, ROS_ADP FROM ROS_2023");

            // Fetch data from ADP_2024 table
            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery("SELECT PLAYER_NAME, RK, RK AS RANK FROM ADP_2024");

            // Process the results
            while (resultSet1.next() && resultSet2.next()) {
                String player = resultSet1.getString("PLAYER");
                String rosADPStr = resultSet1.getString("ROS_ADP");
                int rosADP = rosADPStr.equals("-") ? -1 : Integer.parseInt(rosADPStr);
                String playerName2024 = resultSet2.getString("PLAYER_NAME");
                int rk2024 = resultSet2.getInt("RK");
                int rank = resultSet2.getInt("RANK");

                // Calculate the difference
                int difference = rosADP == -1 ? -1 : rosADP - rk2024;

                // Print or process the difference along with the player names
                if (rosADP == -1) {
                    System.out.println("Rank: " + String.format("%-4d", rank) + " | Player: " + String.format("%-25s", player) + " | ROS_ADP: " + String.format("%-6s", "N/A") + " | Difference: " + difference);
                } else {
                    System.out.println("Rank: " + String.format("%-4d", rank) + " | Player: " + String.format("%-25s", player) + " | ROS_ADP: " + String.format("%-6s", rosADP) + " | Difference: " + difference);
                }
            }

            // Close result sets and statements
            resultSet1.close();
            resultSet2.close();
            statement1.close();
            statement2.close();
        } catch (SQLException e) {
            System.out.println("Query execution failed!");
            e.printStackTrace();
        }
    }
}

//Rank: This is the rank of the player in the ADP_2024 table.
//Player: This is the name of the player.
//ROS_ADP: This is the ROS_ADP (Rest of Season Average Draft Position) value for the player in the ROS_2023 table. If the ROS_ADP is "-", indicating a null value, it will be displayed as "N/A" in the output.
//Difference: This is the difference between the ROS_ADP and the rank in the ADP_2024 table. If ROS_ADP is "-", the difference will also be shown as "-1".