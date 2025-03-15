package Exp7c;

import java.sql.*;
import java.util.Scanner;

public class Student {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/Student";
    private static final String USER = "kamal"; 
    private static final String PASS = "P@ssword97";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
			Connection connection = null;
   
			try {
			    connection = DriverManager.getConnection(DB_URL, USER, PASS);
			    connection.setAutoCommit(false); 

			    while (true) {
			        System.out.println("\nMenu:");
			        System.out.println("1. Create Student");
			        System.out.println("2. Read Students");
			        System.out.println("3. Update Student");
			        System.out.println("4. Delete Student");
			        System.out.println("5. Exit");
			        System.out.print("Choose an option: ");
			        int choice = scanner.nextInt();
			        scanner.nextLine(); 

			        switch (choice) {
			            case 1:
			                System.out.print("Enter Student Name: ");
			                String name = scanner.nextLine();
			                System.out.print("Enter Age: ");
			                int age = scanner.nextInt();
			                scanner.nextLine(); 
			                System.out.print("Enter Major: ");
			                String major = scanner.nextLine();
			                createStudent(connection, name, age, major);
			                break;

			            case 2:
			                readStudents(connection);
			                break;

			            case 3:
			                System.out.print("Enter Student ID to update: ");
			                int studentIdToUpdate = scanner.nextInt();
			                scanner.nextLine(); 
			                System.out.print("Enter new Student Name: ");
			                String newName = scanner.nextLine();
			                System.out.print("Enter new Age: ");
			                int newAge = scanner.nextInt();
			                scanner.nextLine(); 
			                System.out.print("Enter new Major: ");
			                String newMajor = scanner.nextLine();
			                updateStudent(connection, studentIdToUpdate, newName, newAge, newMajor);
			                break;

			            case 4:
			                System.out.print("Enter Student ID to delete: ");
			                int studentIdToDelete = scanner.nextInt();
			                deleteStudent(connection, studentIdToDelete);
			                break;

			            case 5:
			                System.out.println("Exiting...");
			                connection.commit();
			                connection.close();
			                scanner.close();
			                return;

			            default:
			                System.out.println("Invalid choice. Please try again.");
			        }
			    }
			
			} catch (SQLException e) {
			    e.printStackTrace();
			    try {
			        if (connection != null) {
			            connection.rollback(); 
			        }
			    } catch (SQLException rollbackEx) {
			        rollbackEx.printStackTrace();
			    }
			}
		}
    }

    private static void createStudent(Connection connection, String name, int age, String major) throws SQLException {
        String sql = "INSERT INTO Student (StudentName, Age, Major) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, major);
            pstmt.executeUpdate();
            connection.commit();
            System.out.println("Student created successfully.");
        }
    }

    private static void readStudents(Connection connection) throws SQLException {
        String sql = "SELECT * FROM Student";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Student List:");
            while (rs.next()) {
                int id = rs.getInt("StudentID");
                String name = rs.getString("StudentName");
                int age = rs.getInt("Age");
                ResultSet major = rs;
                        String major1 = rs.getString("Major");
                        System.out.printf("ID: %d, Name: %s, Age: %d, Major: %s%n", id, name, age, major1);
                    }
                }
            }

            private static void updateStudent(Connection connection, int studentId, String name, int age, String major) throws SQLException {
                String sql = "UPDATE Student SET StudentName = ?, Age = ?, Major = ? WHERE StudentID = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, name);
                    pstmt.setInt(2, age);
                    pstmt.setString(3, major);
                    pstmt.setInt(4, studentId);
                    int rowsAffected = pstmt.executeUpdate();
                    connection.commit();
                    if (rowsAffected > 0) {
                        System.out.println("Student updated successfully.");
                    } else {
                        System.out.println("Student not found.");
                    }
                }
            }

            private static void deleteStudent(Connection connection, int studentId) throws SQLException {
                String sql = "DELETE FROM Student WHERE StudentID = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setInt(1, studentId);
                    int rowsAffected = pstmt.executeUpdate();
                    connection.commit();
                    if (rowsAffected > 0) {
                        System.out.println("Student deleted successfully.");
                    } else {
                        System.out.println("Student not found.");
                    }
                }
            }
        }
