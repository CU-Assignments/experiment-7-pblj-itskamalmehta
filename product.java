package student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class product {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/employee";
    private static final String USER = "kamal";
    private static final String PASS = "P@ssword97";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Connecting to the database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating statement...");
            statement = connection.createStatement();
            String sql = "SELECT EmpID, Name, Salary FROM Employee";
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("Employee Data:");
            while (resultSet.next()) {

                int empID = resultSet.getInt("EmpID");
                String name = resultSet.getString("Name");
                double salary = resultSet.getDouble("Salary");

                System.out.println("EmpID: " + empID + ", Name: " + name + ", Salary: " + salary);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}