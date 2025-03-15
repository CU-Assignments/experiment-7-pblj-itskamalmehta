package exp7b;

import java.sql.*;
import java.util.Scanner;

public class Product {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Product";
    private static final String USER = "kamal"; 
    private static final String PASS = "P@ssword97";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.setAutoCommit(false);
            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Create Product");
                System.out.println("2. Read Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter Product Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Price: ");
                        double price = scanner.nextDouble();
                        System.out.print("Enter Quantity: ");
                        int quantity = scanner.nextInt();
                        createProduct(connection, name, price, quantity);
                        break;

                    case 2:
                        readProducts(connection);
                        break;

                    case 3:
                        System.out.print("Enter Product ID to update: ");
                        int productIdToUpdate = scanner.nextInt();
                        System.out.print("Enter new Product Name: ");
                        String newName = scanner.next();
                        System.out.print("Enter new Price: ");
                        double newPrice = scanner.nextDouble();
                        System.out.print("Enter new Quantity: ");
                        int newQuantity = scanner.nextInt();
                        updateProduct(connection, productIdToUpdate, newName, newPrice, newQuantity);
                        break;

                    case 4:
                        System.out.print("Enter Product ID to delete: ");
                        int productIdToDelete = scanner.nextInt();
                        deleteProduct(connection, productIdToDelete);
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

    private static void deleteProduct(Connection connection, int productIdToDelete) {
		
	}
	private static void createProduct(Connection connection, String name, double price, int quantity) throws SQLException {
        String sql = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
            connection.commit();
            System.out.println("Product created successfully.");
        }
    }

    private static void readProducts(Connection connection) throws SQLException {
        String sql = "SELECT * FROM Product";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Product List:");
            while (rs.next()) {
                int id = rs.getInt("ProductID");
                String name = rs.getString("ProductName");
                double price = rs.getDouble("Price");
                int quantity = rs.getInt("Quantity");
                System.out.printf("ID: %d, Name: %s, Price: %.2f, Quantity: %d%n", id, name, price, quantity);
            }
        }
    }

    private static void updateProduct(Connection connection, int productId, String name, double price, int quantity) throws SQLException {
        String sql = "UPDATE Product SET ProductName = ?, Price = ?, Quantity = ? WHERE ProductID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, quantity);
            pstmt.setInt(4, productId);
            int rowsAffected = pstmt.executeUpdate();
            connection.commit();
            if (rowsAffected > 0) {
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("Product not found.");
            }
        }
    }
 }
