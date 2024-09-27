/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_gb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Scanner;

/**
 *
 * @author Galaxy_Brain
 * ON YUEN SHERN
 * ONG JIT SIANG
 * SOO WEN WEI
 * YAP YONG QUAN
 * 
 */

public class SupplierSystem {

    public static void addItem(String addItemName, double addItemPrice, String Supplier_ID) {
        Connection conn = XAMPP_Connect.Connecter();
        String newItemId = generateNewItemID(conn);
        String query = "INSERT INTO item (Item_ID, Item_Name, Item_Price, Supplier_ID) VALUES (?, ?, ?, ?);";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, newItemId);
            stmt.setString(2, addItemName);
            stmt.setDouble(3, addItemPrice);
            stmt.setString(4, Supplier_ID);

            stmt.executeUpdate();

            System.out.println("\nItem has been successfully added.");

        } catch (Exception e) {
            System.out.println("Please try again!");
            e.printStackTrace();
        }

    }

    public static String generateNewItemID(Connection conn) {
        String newItemID = "I1";

        String query = "SELECT Item_ID FROM item ORDER BY CAST(SUBSTRING(Item_ID, 2) AS UNSIGNED) DESC LIMIT 1;";

        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String lastItemID = rs.getString("Item_ID");
                int ItemIDNumber = Integer.parseInt(lastItemID.substring(1));
                ItemIDNumber++;
                newItemID = String.format("I%d", ItemIDNumber); //two digit
            }

        } catch (Exception e) {
            System.out.printf("Error occurs, Please try again!"); //
        }

        return newItemID;
    }

    public static void searchItemName(String searchItemName, String Supplier_ID) {
        Connection conn = XAMPP_Connect.Connecter();
        String query = "SELECT * FROM item WHERE Supplier_ID = ? AND Item_Name LIKE ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, Supplier_ID);
            stmt.setString(2, "%" + searchItemName + "%");

            ResultSet rs = stmt.executeQuery();

            int found_supplier_item = 0;

            boolean found = false;
            while (rs.next()) {
                if (found_supplier_item <= 0) {
                    System.out.println("Search Result: ");
                    found_supplier_item++;
                    System.out.printf("%-10s | %-15s | %-10s | \n", "Item ID", "Item Name", "Price (RM)");
                    System.out.printf("-------------------------------------------\n");
                }
                String itemID = rs.getString("Item_ID");
                String itemName = rs.getString("Item_Name");
                double itemPrice = rs.getDouble("Item_Price");

                System.out.printf("%-10s | %-15s | %10.2f | \n", itemID, itemName, itemPrice);
                found = true;

            }
            if (!found) {
                System.out.println("No item found with the name: " + searchItemName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void searchItemID(String searchItemID, String Supplier_ID) {
        Connection conn = XAMPP_Connect.Connecter();
        String query = "SELECT * FROM item WHERE Supplier_ID = ? AND Item_ID LIKE ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, Supplier_ID);
            stmt.setString(2, searchItemID);

            ResultSet rs = stmt.executeQuery();

            int found_supplier_item = 0;

            boolean found = false;
            while (rs.next()) {
                if (found_supplier_item <= 0) {
                    System.out.println("Search Result: ");
                    found_supplier_item++;
                    System.out.printf("%-10s | %-15s | %-10s | \n", "Item ID", "Item Name", "Price (RM)");
                    System.out.printf("-------------------------------------------\n");
                }

                String itemID = rs.getString("Item_ID");
                String itemName = rs.getString("Item_Name");
                double itemPrice = rs.getDouble("Item_Price");

                System.out.printf("%-10s | %-15s | %10.2f | \n", itemID, itemName, itemPrice);
                found = true;

            }
            if (!found) {
                System.out.println("No item found with the name: " + searchItemID);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void deleteItem(String deleteItem, String Supplier_ID) {
        Connection conn = XAMPP_Connect.Connecter();
        String query = "DELETE FROM item WHERE Supplier_ID = ? AND Item_ID LIKE ?";

        try {

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, Supplier_ID);
            stmt.setString(2, deleteItem);

            int success_delete = stmt.executeUpdate();

            if (success_delete > 0) {
                System.out.println("\nItem " + deleteItem + " successfully deleted.");
            } else {
                System.out.println("\nItem " + deleteItem + " not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int check_before_update(String updateItemID, String Supplier_ID) {
        Connection conn = XAMPP_Connect.Connecter();
        String query = "SELECT * FROM item WHERE Supplier_ID = ? AND Item_ID = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, Supplier_ID);
            stmt.setString(2, updateItemID);

            // Checking
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            System.out.print("Error occurs! Please Try Again!");
            e.printStackTrace();
        }
        return 0;
    }

    public static void updateItem(String updateItemID, String updateItemName, double updateItemPrice, String Supplier_ID) {
        Connection conn = XAMPP_Connect.Connecter();
        String query = "UPDATE item SET Item_Name = ?, Item_Price = ? WHERE Supplier_ID = ? AND Item_ID = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, updateItemName);
            stmt.setDouble(2, updateItemPrice);
            stmt.setString(3, Supplier_ID);
            stmt.setString(4, updateItemID);

            int success_update = stmt.executeUpdate();

            if (success_update > 0) {

                System.out.println("\nItem " + updateItemID + " successfully edited.");

            } else {
                System.out.println("\nItem " + updateItemID + " not found.");
            }

        } catch (Exception e) {
            System.out.print("Error occurs! Please Try Again!");
            e.printStackTrace();
        }
    }

    public static void searchSupplierName() {
        Scanner scanner = new Scanner(System.in);
        Connection conn = XAMPP_Connect.Connecter();
        String query = "SELECT * FROM supplier WHERE Supplier_Name LIKE ?";

        System.out.print("Enter the Supplier Name to search: ");
        String nameToSearch = scanner.nextLine();

        try {
            // Format
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + nameToSearch + "%");
            // Checking
            ResultSet rs = stmt.executeQuery();

            int found_supplier = 0;

            boolean found = false;
            while (rs.next()) {
                String id = rs.getString("Supplier_ID");
                String name = rs.getString("Supplier_Name");
                String phone = rs.getString("Supplier_Phone");

                if (found_supplier <= 0) {
                    System.out.println("\nSearch Result: ");
                    System.out.printf("%-10s | %-10s | %-10s | \n", "Supplier ID", "Supplier Name", "Phone Number");
                    System.out.printf("--------------------------------------------\n");
                    found_supplier++;
                }
                System.out.printf("%-11s | %-13s | %-12s | \n", id, name, phone);
                found = true;

            }
            if (!found) {
                System.out.println("\nNo suppliers found with the name: " + nameToSearch);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void searchSupplierById() {
        Scanner scanner = new Scanner(System.in);
        Connection conn = XAMPP_Connect.Connecter();
        String query = "SELECT * FROM supplier WHERE Supplier_ID = ?";

        System.out.print("Enter the Supplier ID to search: ");
        String nameToSearchID = scanner.nextLine();

        try {
            // Format
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, nameToSearchID);
            // Checking
            ResultSet rs = stmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                String id = rs.getString("Supplier_ID");
                String name = rs.getString("Supplier_Name");
                String phone = rs.getString("Supplier_Phone");

                System.out.println("\nSearch Result: ");
                System.out.printf("%-11s | %-13s | %-12s |\n", id, name, phone);
                found = true;
            }
            if (!found) {
                System.out.println("\nNo suppliers found with the name: " + nameToSearchID);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Supplier_Reset_Password(String NewPassword, String Supplier_ID) {
        Connection conn = XAMPP_Connect.Connecter();

        String query = "UPDATE `supplier` SET `Supplier_Password` = ? WHERE `supplier`.`Supplier_ID` = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, NewPassword);
            stmt.setString(2, Supplier_ID);

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Supplier_Change_Name(String NewName, String Supplier_ID) {
        Connection conn = XAMPP_Connect.Connecter();

        String query = "UPDATE `supplier` SET `Supplier_Name` = ? WHERE `supplier`.`Supplier_ID` = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, NewName);
            stmt.setString(2, Supplier_ID);

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Supplier_Ship_Record(String Supplier_ID) {
        Connection conn = XAMPP_Connect.Connecter();

        try {
            String query = "SELECT * FROM `order` WHERE `order`.`Store_ID` IS NULL AND Supplier_ID = ?;";
            PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(1, Supplier_ID);

            ResultSet resultSet = statement.executeQuery();

            int recordfind = 0;

            while (resultSet.next()) {
                if (recordfind <= 0) {
                    System.out.printf("%-10s | %-10s | %-10s | %-10s | %-10s | %-20s  | %-10s |\n", "Order_ID", "Supplier_ID", "Item_ID", "Quantity", "Price (RM)", "Order_Datetime", "Staff_ID");
                    System.out.println("------------------------------------------------------------------------------------------------------");
                }
                String orderID = resultSet.getString("Order_ID");
                String supplierID = resultSet.getString("Supplier_ID");
                String itemID = resultSet.getString("Item_ID");
                int quantity = resultSet.getInt("Quantity");
                double price = resultSet.getDouble("Price");
                Timestamp orderDateTime = resultSet.getTimestamp("Order_Datetime");
                String staffID = resultSet.getString("Staff_ID");

                System.out.printf("%-10s | %-10s  | %-10s | %-10d | %-10.2f | %-20s | %-10s |\n",
                        orderID, supplierID, itemID, quantity, price, orderDateTime, staffID);

                recordfind++;
            }

            if (recordfind == 0) {
                System.out.println("Currently No Order");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean Check_Supplier_Record(String Supplier_ID, String Order_ID) {
        Connection conn = XAMPP_Connect.Connecter();

        try {
            String query = "SELECT * FROM `order` WHERE `order`.`Store_ID` IS NULL AND supplier_id = ? AND order_id = ? LIMIT 1;";
            PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(1, Supplier_ID);
            statement.setString(2, Order_ID);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean In_Stock_System(String Supplier_ID, String Order_ID, String Store_ID, String Item_ID, int Quantity, LocalDate Expire_Date) {
        Connection conn = XAMPP_Connect.Connecter();

        String query = "INSERT INTO `store` (`Store_ID`, `Item_ID`, `Quantity`, `Expiry_Date`) VALUES (?,?,?,?);";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, Store_ID);
            stmt.setString(2, Item_ID);
            stmt.setInt(3, Quantity);
            stmt.setDate(4, java.sql.Date.valueOf(Expire_Date));

            stmt.executeUpdate();

            Order_Put_Store_ID(Order_ID, Store_ID);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void Order_Put_Store_ID(String Order_ID, String Store_ID) {
        Connection conn = XAMPP_Connect.Connecter();

        String query = "UPDATE `order` SET `Store_ID` = ? WHERE `order`.`Order_ID` = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, Store_ID);
            stmt.setString(2, Order_ID);

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
