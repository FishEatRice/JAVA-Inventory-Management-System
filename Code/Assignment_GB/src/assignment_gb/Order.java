/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_gb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Scanner;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.InputMismatchException;

/**
 *
 * @author Galaxy_Brain
 * ON YUEN SHERN
 * ONG JIT SIANG
 * SOO WEN WEI
 * YAP YONG QUAN
 * 
 */

public class Order {

    public static void Order_Mainpage(String Staff_ID) {

        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 2; i++) {
            i--;

            //header
            Format.header();
            Format.page_type('o', 's');

            System.out.printf("<1> Order New Item \n");
            System.out.printf("<2> Cancel ordering \n");
            System.out.printf("<3> History Order \n");
            System.out.printf("<0> Back to previous page \n");
            System.out.printf("\n> ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 0:
                        i += 2;
                        break;

                    //new Order
                    case 1:
                        while (true) {
                            Format.header();
                            Format.page_type('o');

                            displayItemDetails();

                            // Get Item ID
                            System.out.println();
                            System.out.printf("If you need return, Please key 'exit'\n");
                            System.out.printf("Please enter the Item ID to add new order");
                            System.out.printf("\n> ");

                            String itemId = scanner.nextLine();
                            if (itemId.equals("exit")) {
                                break;
                            }

                            String SupID = Item.getSupplierID(itemId);

                            if ("/exit/".equals(SupID)) {
                                System.out.printf("\nItem not found.");
                                scanner.nextLine();
                            } else {

                                // Get Quantity
                                int quantity = 0;
                                boolean validQuantity = false;
                                System.out.printf("\n");
                                System.out.printf("Please enter the Quantity ( Enter '0' to cancel order )");
                                System.out.printf("\n> ");

                                while (!validQuantity) {
                                    try {
                                        quantity = scanner.nextInt();

                                        if (quantity < 0) {
                                            System.out.println("Quantity must be greater than 0.");
                                            System.out.printf("\n");
                                            System.out.printf("Please enter the Quantity");
                                            System.out.printf("\n> ");
                                        } else {
                                            validQuantity = true; // Valid input
                                        }
                                    } catch (InputMismatchException e) {
                                        scanner.nextLine(); // replace for int
                                        System.out.println("Quantity must be greater than 0.");
                                        System.out.printf("\n");
                                        System.out.printf("Please enter the Quantity");
                                        System.out.printf("\n> ");
                                    }
                                }
                                scanner.nextLine(); // Consume the newline character

                                if (quantity != 0) {

                                    // Get Supplier ID
                                    System.out.printf("\n");

                                    // Insert into Database
                                    try {
                                        Connection conn = XAMPP_Connect.Connecter();
                                        if (conn == null) {
                                            System.out.println("Database connection failed.");
                                            return;
                                        }

                                        String newOrderNo = OrderSystem.generateNewOrderNo(conn);
                                        String newOrderId = OrderSystem.generateNewOrderID(conn);
                                        double itemPrice = OrderSystem.getItemPrice(conn, itemId);

                                        LocalDateTime localDateTime = LocalDateTime.now();
                                        Timestamp orderDateTime = Timestamp.valueOf(localDateTime);

                                        String sql = "INSERT INTO `order` (Order_No, Order_ID, Supplier_ID, Item_ID, Quantity, Price, Order_Datetime, Staff_ID, Store_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, NULL)";
                                        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                                            stmt.setString(1, newOrderNo); // Order_No
                                            stmt.setString(2, newOrderId); // Order_ID
                                            stmt.setString(3, SupID); // Supplier_ID
                                            stmt.setString(4, itemId); // Item_ID
                                            stmt.setInt(5, quantity); // Quantity
                                            stmt.setDouble(6, itemPrice); // Price
                                            stmt.setTimestamp(7, orderDateTime); // Order_Datetime
                                            stmt.setString(8, Staff_ID); // Staff_ID

                                            int rowsAffected = stmt.executeUpdate();
                                            if (rowsAffected > 0) {
                                                System.out.printf("Order successfully added with Order No: " + newOrderNo);
                                            } else {
                                                System.out.printf("Order entry failed, please try again.");
                                            }
                                            scanner.nextLine();
                                        }

                                        conn.close();

                                    } catch (SQLException e) {
                                        System.out.println("SQL error: " + e.getMessage());
                                    }
                                } else {
                                    System.out.printf("Order Canceled.");
                                    scanner.nextLine();
                                }
                            }
                        }
                        break;
                    case 2:
                        while (true) {
                            Format.header();
                            Format.page_type('o', 'd');
                            displayOrderDetails();

                            //key cancel 
                            System.out.printf("\nIf you need return, Please key 'exit'\n");
                            System.out.printf("Please key your need cancel Order ID");
                            System.out.printf("\n> ");
                            String answer = scanner.nextLine();

                            if (answer.equals("exit")) {
                                break;
                            }

                            if (OrderIdValid(answer)) {
                                System.out.printf("\nDouble Confirm to CANCEL THIS ORDER ID %s? (yes/no)\n", answer);
                                System.out.printf("> ");
                                String confirmation = scanner.nextLine();

                                System.out.println();

                                if (confirmation.equals("yes")) {
                                    cancelOrder(answer);
                                } else {
                                    System.out.printf("Canceled delete process");
                                }
                            } else {
                                System.out.printf("Invalid Order ID. Please try again.");
                            }

                            scanner.nextLine();
                        }
                        break;

                    case 3:
                        while (true) {
                            Format.header();
                            Format.page_type('o', 'h');

                            DisplayOrderHistory();

                            System.out.printf("\n<1> Search order by using Date");
                            System.out.printf("\n<2> Search order by using Order_ID ");
                            System.out.printf("\n<0> back to previous page");

                            System.out.println();
                            System.out.printf("\n> ");

                            String search = scanner.nextLine();
                            if (search.equals("0")) {
                                break;

                            } else if (search.equals("1")) {
                                Format.header();
                                Format.page_type('o', 'h');

                                System.out.printf("Please key the date(y-m-d) : \n");
                                System.out.printf("> ");
                                String inputDate = scanner.nextLine();

                                checkdata(inputDate);

                                System.out.printf("\nPress Enter to continue.");
                                scanner.nextLine();
                            } else if (search.equals("2")) {
                                Format.header();
                                Format.page_type('o', 'h');

                                System.out.printf("Please key the Order id : \n");
                                System.out.printf("> ");
                                String inputid = scanner.nextLine();

                                checkOrid(inputid);

                                System.out.printf("\nPress Enter to continue.");
                                scanner.nextLine();
                            } else {
                                System.out.printf("Please choose the required number.\n");
                                System.out.printf("Press Enter to continue.");
                                scanner.nextLine();
                            }
                        }

                        break;

                    default:
                        System.out.printf("Please choose the required number.\n");
                        System.out.printf("Press Enter to continue.");
                        scanner.nextLine();
                        break;
                }
            } catch (InputMismatchException ex) {
                System.out.printf("Please choose the required number.\n");
                System.out.printf("Press Enter to continue.");
                scanner.nextLine(); // replace for int
                scanner.nextLine();
            }
        }

    }

    //display item(private, dont share, hahahaha)
    private static void displayItemDetails() {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = XAMPP_Connect.Connecter();
            String query = "SELECT * FROM Item ORDER BY CAST(SUBSTRING(Item_ID, 2) AS UNSIGNED) ASC;";
            statement = conn.prepareStatement(query);
            resultSet = statement.executeQuery();

            System.out.printf("%-10s | %-20s | %-10s |\n", "Item_ID", "Item Name", "Price (RM)");
            System.out.println("------------------------------------------------");

            while (resultSet.next()) {
                String itemIds = resultSet.getString("Item_ID");
                String itemNames = resultSet.getString("Item_Name");
                double prices = resultSet.getDouble("Item_Price");

                System.out.printf("%-10s | %-20s | %10.2f |\n", itemIds, itemNames, prices);
            }

            //SQLException like check the sql error eg resultSet,statement,null conn
        } catch (SQLException e) {
            System.out.printf("sql error."); //
        } finally {
            // Close resources in finally block
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    //display order(private, dont share, hahahaha)
    private static void displayOrderDetails() {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = XAMPP_Connect.Connecter();
            String query = "SELECT * FROM `order` WHERE `order`.`Store_ID` IS NULL;";
            statement = conn.prepareStatement(query);
            resultSet = statement.executeQuery();

            System.out.printf("%-10s | %-10s | %-10s | %-10s | %-10s | %-20s  | %-10s |\n", "Order_ID", "Supplier_ID", "Item_ID", "Quantity", "Price (RM)", "Order_Datetime", "Staff_ID");
            System.out.println("------------------------------------------------------------------------------------------------------");
            while (resultSet.next()) {
                String orderID = resultSet.getString("Order_ID");
                String supplierID = resultSet.getString("Supplier_ID");
                String itemID = resultSet.getString("Item_ID");
                int quantity = resultSet.getInt("Quantity");
                double price = resultSet.getDouble("Price");
                Timestamp orderDateTime = resultSet.getTimestamp("Order_Datetime");
                String staffID = resultSet.getString("Staff_ID");

                System.out.printf("%-10s | %-10s  | %-10s | %-10d | %-10.2f | %-20s | %-10s |\n",
                        orderID, supplierID, itemID, quantity, price, orderDateTime, staffID);
            }
            //SQLException like check the sql error eg resultSet,statement,null conn
        } catch (SQLException e) {
            System.out.printf("sql error."); //
        } finally {
            // Close resources in finally block
            try {
                if (resultSet != null) {
                    resultSet.close();

                }
                if (statement != null) {
                    statement.close();

                }
                if (conn != null) {
                    conn.close();

                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    private static void DisplayOrderHistory() {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = XAMPP_Connect.Connecter();
            String query = "SELECT * FROM `order`;";
            statement = conn.prepareStatement(query);
            resultSet = statement.executeQuery();

            System.out.printf("%-10s | %-10s | %-10s | %-10s | %-10s | %-20s  | %-10s | %-11s |\n", "Order_ID", "Supplier_ID", "Item_ID", "Quantity", "Price (RM)", "Order_Datetime", "Staff_ID", "Store_ID");
            System.out.println("-------------------------------------------------------------------------------------------------------------------");
            while (resultSet.next()) {
                String orderID = resultSet.getString("Order_ID");
                String supplierID = resultSet.getString("Supplier_ID");
                String itemID = resultSet.getString("Item_ID");
                int quantity = resultSet.getInt("Quantity");
                double price = resultSet.getDouble("Price");
                Timestamp orderDateTime = resultSet.getTimestamp("Order_Datetime");
                String staffID = resultSet.getString("Staff_ID");
                String StoreID = resultSet.getString("Store_ID");

                if (StoreID == null) {
                    StoreID = "Shipping...";
                }

                System.out.printf("%-10s | %-10s  | %-10s | %-10d | %-10.2f | %-20s | %-10s | %-11s |\n",
                        orderID, supplierID, itemID, quantity, price, orderDateTime, staffID, StoreID);
            }
            //SQLException like check the sql error eg resultSet,statement,null conn
        } catch (SQLException e) {
            System.out.printf("sql error."); //
        } finally {
            // Close resources in finally block
            try {
                if (resultSet != null) {
                    resultSet.close();

                }
                if (statement != null) {
                    statement.close();

                }
                if (conn != null) {
                    conn.close();

                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    //check the Order is vaild?
    private static boolean OrderIdValid(String orderId) {
        // Implement this method to check if the order ID exists in the database
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = XAMPP_Connect.Connecter();
            String query = "SELECT COUNT(*) FROM `order` WHERE Order_ID = ? AND Store_ID IS NULL";
            statement = conn.prepareStatement(query);
            statement.setString(1, orderId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources in finally block
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }

        return false;
    }

    // Cancel the order
    private static void cancelOrder(String orderId) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = XAMPP_Connect.Connecter();
            String query = "DELETE FROM `order` WHERE Order_ID = ? AND Store_ID IS NULL;";
            statement = conn.prepareStatement(query);
            statement.setString(1, orderId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.printf("Order ID " + orderId + " has been successfully canceled.");
            } else {
                System.out.printf(orderId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources in finally block
            try {

                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    private static void checkdata(String inputDate) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = XAMPP_Connect.Connecter();
            if (conn == null) {
                System.out.println("Database connection failed.");
                return;
            }

            String query = "SELECT * FROM `order` WHERE DATE(Order_Datetime) = ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, inputDate);

            resultSet = statement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No orders found on this date.");
            } else {
                System.out.println();
                System.out.printf("%-10s | %-10s | %-10s | %-10s | %-10s | %-20s  | %-10s | %-11s |\n", "Order_ID", "Supplier_ID", "Item_ID", "Quantity", "Price (RM)", "Order_Datetime", "Staff_ID", "Store_ID");
                System.out.println("--------------------------------------------------------------------------------------------------------------------");

                while (resultSet.next()) {
                    String orderID = resultSet.getString("Order_ID");
                    String supplierID = resultSet.getString("Supplier_ID");
                    String itemID = resultSet.getString("Item_ID");
                    int quantity = resultSet.getInt("Quantity");
                    double price = resultSet.getDouble("Price");
                    Timestamp orderDateTime = resultSet.getTimestamp("Order_Datetime");
                    String staffID = resultSet.getString("Staff_ID");
                    String StoreID = resultSet.getString("Store_ID");

                    if (StoreID == null) {
                        StoreID = "Shipping...";
                    }

                    System.out.printf("%-10s | %-10s  | %-10s | %-10d | %-10.2f | %-20s | %-10s | %-11s |\n",
                            orderID, supplierID, itemID, quantity, price, orderDateTime, staffID, StoreID);
                }
            }
        } catch (SQLException e) {
            System.out.printf("ERROR in SQL");
        } finally {

            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.printf("Error In SQL");
            }
        }
    }

    private static void checkOrid(String inputid) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = XAMPP_Connect.Connecter();
            if (conn == null) {
                System.out.println("ERROR in SQL");
                return;
            }

            String query = "SELECT * FROM `order` WHERE Order_ID = ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, inputid);

            resultSet = statement.executeQuery();

            //kalao tak ada this id
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No orders found on this id.");
            } else {
                System.out.println();
                System.out.printf("%-10s | %-10s | %-10s | %-10s | %-10s | %-20s  | %-10s | %-11s |\n", "Order_ID", "Supplier_ID", "Item_ID", "Quantity", "Price (RM)", "Order_Datetime", "Staff_ID", "Store_ID");
                System.out.println("--------------------------------------------------------------------------------------------------------------------");

                while (resultSet.next()) {
                    String orderID = resultSet.getString("Order_ID");
                    String supplierID = resultSet.getString("Supplier_ID");
                    String itemID = resultSet.getString("Item_ID");
                    int quantity = resultSet.getInt("Quantity");
                    double price = resultSet.getDouble("Price");
                    Timestamp orderDateTime = resultSet.getTimestamp("Order_Datetime");
                    String staffID = resultSet.getString("Staff_ID");
                    String StoreID = resultSet.getString("Store_ID");

                    if (StoreID == null) {
                        StoreID = "Shipping...";
                    }

                    System.out.printf("%-10s | %-10s  | %-10s | %-10d | %-10.2f | %-20s | %-10s | %-11s |\n",
                            orderID, supplierID, itemID, quantity, price, orderDateTime, staffID, StoreID);
                }
            }
        } catch (SQLException e) {
            System.out.printf("ERROR in SQL");
        } finally {

            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.printf("ERROR in SQL");
            }
        }
    }
}
