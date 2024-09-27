/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_gb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
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

public class Supplier {

    public static void supplier_login_page() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            Connection conn = XAMPP_Connect.Connecter();
            Format.header(); // Display the header

            Format.page_type('p', 'l');
            System.out.println("Enter \"/exit/\" to exit");

            System.out.print("Supplier ID : ");
            String Supplier_ID = scanner.nextLine();
            // Exit Process
            if (Supplier_ID.equals("/exit/")) {
                break;
            }

            System.out.print("Password: ");
            String Supplier_Password = scanner.nextLine();
            // Exit Process
            if (Supplier_Password.equals("/exit/")) {
                break;
            }

            if (CheckSupplierAccount(conn, Supplier_ID, Supplier_Password)) {
                supplier_mainpage(Supplier_ID);
                break;

            } else {
                System.out.printf("Invalid ID or Password. Please try again.");
                scanner.nextLine();
            }
        }
    }

    private static boolean CheckSupplierAccount(Connection conn, String Supplier_ID, String Supplier_Password) {
        String query = "SELECT Supplier_ID, Supplier_Password FROM supplier WHERE Supplier_ID = ? AND Supplier_Password = ?";
        try {
            // Format
            PreparedStatement stmt = conn.prepareStatement(query);

            // Set Staff_ID
            stmt.setString(1, Supplier_ID);

            // Set Staff_Password
            stmt.setString(2, Supplier_Password);

            // Checking
            ResultSet rs = stmt.executeQuery();

            // If Yes
            if (rs.next()) {
                return true;
            }

        } catch (Exception e) {
            // If Error, print error
            e.printStackTrace();
        }

        // If No
        return false;
    }

    private static String GetSupplierName(String Supplier_ID) {
        Connection conn = XAMPP_Connect.Connecter();
        String query = "SELECT Supplier_Name FROM supplier WHERE Supplier_ID = ?";
        try {
            // Format
            PreparedStatement stmt = conn.prepareStatement(query);

            // Set Staff_ID
            stmt.setString(1, Supplier_ID);

            // Checking
            ResultSet rs = stmt.executeQuery();

            // If Yes
            if (rs.next()) {
                return rs.getString("Supplier_Name");
            }

        } catch (Exception e) {
            // If Error, print error
            e.printStackTrace();
        }

        // If No
        return null;
    }

    private static String GetSupplierPassword(String Supplier_ID) {
        Connection conn = XAMPP_Connect.Connecter();
        String query = "SELECT Supplier_Password FROM supplier WHERE Supplier_ID = ?";
        try {
            // Format
            PreparedStatement stmt = conn.prepareStatement(query);

            // Set Staff_ID
            stmt.setString(1, Supplier_ID);

            // Checking
            ResultSet rs = stmt.executeQuery();

            // If Yes
            if (rs.next()) {
                return rs.getString("Supplier_Password");
            }

        } catch (Exception e) {
            // If Error, print error
            e.printStackTrace();
        }

        // If No
        return null;
    }

    public static void supplier_mainpage(String Supplier_ID) {
        Scanner scanner = new Scanner(System.in);
        Connection conn = XAMPP_Connect.Connecter();

        for (int i = 0; i < 2; i++) {
            // Make it loop
            i--;

            String Supplier_Name = GetSupplierName(Supplier_ID);
            Format.header(); // Display the header
            Format.page_type('p');

            System.out.printf("Welcome Supplier, %s!\n", Supplier_Name);

            System.out.printf("<1> Ship Items \n");
            System.out.printf("<2> Check Items \n");
            System.out.printf("<3> Account Details \n");
            System.out.printf("<0> Log Out \n");
            System.out.printf("\n> ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 0:
                        i += 2;
                        break;

                    case 1:
                        while (true) {
                            Format.header(); // Display the header
                            Format.page_type('p');

                            SupplierSystem.Supplier_Ship_Record(Supplier_ID);

                            System.out.println();

                            System.out.print("<1> Enter Stock \n");
                            System.out.print("<0> Back to previous page \n");
                            System.out.print("\n> ");

                            String input_supplier_stock = scanner.nextLine();

                            switch (input_supplier_stock) {
                                case "0":
                                    break;
                                case "1":
                                    System.out.print("\nEnter Order_ID : ");
                                    String Order_ID_Supplier_Add = scanner.nextLine();

                                    if (SupplierSystem.Check_Supplier_Record(Supplier_ID, Order_ID_Supplier_Add)) {
                                        String StoreID = StoreSystem.GetStoreID();

                                        System.out.println("Please put your Item to : Store_ID = " + StoreID);

                                        System.out.println("");

                                        System.out.printf("Expire Date (yyyy-mm-dd) : ");
                                        String Expire_Date_String = scanner.nextLine();

                                        LocalDate LocalExpireDate = null;

                                        while (LocalExpireDate == null) {
                                            try {
                                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                LocalExpireDate = LocalDate.parse(Expire_Date_String, formatter);
                                            } catch (DateTimeParseException e) {
                                                System.out.printf("Wrong Date Format\n");
                                                System.out.printf("Expire Date (yyyy-mm-dd) : ");
                                                Expire_Date_String = scanner.nextLine();
                                            }
                                            if (LocalExpireDate != null) {
                                                LocalDate currentDate = LocalDate.now();
                                                String status = LocalExpireDate.compareTo(currentDate) <= 0 ? "NO" : "YES";

                                                if (status.equals("NO")) {
                                                    System.out.printf("Already Expired Item!\n");
                                                    LocalExpireDate = null;
                                                    System.out.printf("Expire Date (yyyy-mm-dd) : ");
                                                    Expire_Date_String = scanner.nextLine();
                                                }
                                            }
                                        }

                                        if (SupplierSystem.In_Stock_System(Supplier_ID, Order_ID_Supplier_Add, StoreID, StoreSystem.GetItemID_Supplier(Order_ID_Supplier_Add), StoreSystem.GetQuantity_Supplier(Order_ID_Supplier_Add), LocalExpireDate)) {
                                            System.out.println("Thank you for shipping item.");
                                        } else {
                                            System.out.println("Data error! Please contact to staff.");

                                        }

                                    } else {
                                        System.out.printf("Order_ID " + Order_ID_Supplier_Add + " not found.");
                                    }
                                    System.out.printf("\nPress Enter to continue.");
                                    scanner.nextLine();
                                    break;
                                default:
                                    System.out.printf("Please choose the required number.\n");
                                    System.out.printf("Press Enter to continue.");
                                    scanner.nextLine();
                                    break;
                            }

                            if (input_supplier_stock.equals("0")) {
                                break;
                            }
                        }
                        break;

                    case 2:
                        while (true) {
                            Format.header(); // Display the header
                            Format.page_type('p', 'i');

                            System.out.printf("%-10s | %-15s | %-10s | \n", "Item ID", "Item Name", "Price (RM)");
                            System.out.printf("-------------------------------------------\n");

                            String query = "SELECT * FROM item WHERE Supplier_ID = ? ORDER BY CAST(SUBSTRING(Item_ID, 2)AS UNSIGNED);";

                            try {
                                PreparedStatement stmt = conn.prepareStatement(query);
                                stmt.setString(1, Supplier_ID);
                                ResultSet rs = stmt.executeQuery();

                                while (rs.next()) {
                                    String supplierItemId = rs.getString("Item_ID");
                                    String supplierItemName = rs.getString("Item_Name");
                                    double supplierItemPrice = rs.getDouble("Item_Price");

                                    System.out.printf("%-10s | %-15s | %10.2f | \n", supplierItemId, supplierItemName, supplierItemPrice);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            System.out.println();
                            System.out.println("<1> Search item by item name");
                            System.out.println("<2> Search item by item ID");
                            System.out.println("<3> Add new item");
                            System.out.println("<4> Delete item");
                            System.out.println("<5> Edit item details");
                            System.out.println("<0> Back to previous page");

                            System.out.printf("\n> ");
                            String Input = scanner.nextLine();

                            if (Input.equals("1")) {
                                Format.header(); // Display the header
                                Format.page_type('p', 's');

                                System.out.print("Enter your item name: ");
                                String searchItemName = scanner.nextLine();

                                System.out.println();

                                SupplierSystem.searchItemName(searchItemName, Supplier_ID);

                                System.out.printf("Press Enter to continue.");
                                scanner.nextLine();

                            } else if (Input.equals("2")) {
                                Format.header(); // Display the header
                                Format.page_type('p', 's');
                                System.out.print("Enter item ID: ");
                                String searchItemID = scanner.nextLine();

                                System.out.println();

                                SupplierSystem.searchItemID(searchItemID, Supplier_ID);

                                System.out.printf("Press Enter to continue.");
                                scanner.nextLine();

                            } else if (Input.equals("3")) {
                                Format.header(); // Display the header
                                Format.page_type('p', 'a');
                                System.out.printf("New item name: ");
                                String addItemName = scanner.nextLine();

                                double addItemPrice = 0.00;
                                boolean validInput = false;

                                while (!validInput) {
                                    System.out.print("New item price : RM ");
                                    try {
                                        addItemPrice = scanner.nextDouble();
                                        scanner.nextLine();
                                        if (addItemPrice < 0) {
                                            System.out.println("\nThe price cannot be negative! \nPlease enter the price again.\n");
                                        } else {
                                            validInput = true;
                                        }
                                    } catch (InputMismatchException ex) {
                                        System.out.println("\nInvalid input, please enter a valid price.");
                                        scanner.nextLine();
                                    }
                                }

                                SupplierSystem.addItem(addItemName, addItemPrice, Supplier_ID);

                                System.out.printf("Press Enter to continue.");
                                scanner.nextLine();

                            } else if (Input.equals("4")) {
                                System.out.print("Enter item ID that you want to delete: ");
                                String deleteItem = scanner.nextLine();

                                System.out.print("Are you sure to delete? (Y/N)? ");
                                String confirmation = scanner.nextLine();

                                if (confirmation.equals("Y")) {
                                    SupplierSystem.deleteItem(deleteItem, Supplier_ID);
                                } else {
                                    System.out.println("\nYou cancel to delete your item!");
                                }

                                System.out.printf("Press Enter to continue.");
                                scanner.nextLine();

                            } else if (Input.equals("5")) {
                                System.out.print("Enter item ID that you want to edit: ");
                                String updateItemID = scanner.nextLine();

                                if (SupplierSystem.check_before_update(updateItemID, Supplier_ID) == 1) {

                                    System.out.print("Enter New Item name: ");
                                    String updateItemName = scanner.nextLine();

                                    System.out.print("Enter New Item price: ");
                                    double updateItemPrice = scanner.nextDouble();
                                    scanner.nextLine();

                                    SupplierSystem.updateItem(updateItemID, updateItemName, updateItemPrice, Supplier_ID);
                                } else {
                                    System.out.println("\nItem " + updateItemID + " not found.");
                                }

                                System.out.printf("Press Enter to continue.");
                                scanner.nextLine();

                            } else if (Input.equals("0")) {
                                break;
                            } else {
                                System.out.printf("Invalid Input! Please try again!");
                                scanner.nextLine();
                            }
                        }
                        break;

                    case 3:
                        while (true) {
                            Format.header(); // Display the header
                            Format.page_type('p');

                            System.out.println("Account Details:\n");

                            System.out.printf("Supplier ID : %s\n", Supplier_ID);
                            System.out.printf("Supplier Name : %s\n", GetSupplierName(Supplier_ID));
                            System.out.printf("Supplier Password : %s\n\n", GetSupplierPassword(Supplier_ID));

                            System.out.printf("<1> Change Name \n");
                            System.out.printf("<2> Change Password \n");
                            System.out.printf("<0> Back to previous page \n");
                            System.out.printf("\n> ");

                            String Account_Choose = scanner.nextLine();
                            if (Account_Choose.equals("0")) {
                                break;
                            } else if (Account_Choose.equals("1")) {

                                System.out.println();

                                System.out.print("Enter New Name : ");

                                String New_Name = scanner.nextLine();

                                SupplierSystem.Supplier_Change_Name(New_Name, Supplier_ID);

                                System.out.println("\nChange Name Successfully.");
                                System.out.print("Press Enter to continue. ");
                                scanner.nextLine();

                            } else if (Account_Choose.equals("2")) {

                                System.out.println();

                                System.out.print("Enter New Password : ");

                                String New_Password_1 = scanner.nextLine();

                                System.out.print("Enter New Password Again : ");

                                String New_Password_2 = scanner.nextLine();

                                if (New_Password_1.equals(New_Password_2)) {
                                    SupplierSystem.Supplier_Reset_Password(New_Password_2, Supplier_ID);

                                    System.out.println("\nReset Password Successfully.");

                                } else {
                                    System.out.println("\nWrong New password.");
                                }

                                System.out.print("Press Enter to continue. ");
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
            } catch (InputMismatchException e) {
                System.out.printf("Please choose the required number.\n");
                System.out.printf("Press Enter to continue.");
                scanner.nextLine(); // replace for int
                scanner.nextLine();
            }
        }
    }

    public static void supplier_staff_page(String Staff_ID) {
        Scanner scanner = new Scanner(System.in);

        Connection conn = XAMPP_Connect.Connecter();
        while (true) {
            Format.header(); // Display the header
            Format.page_type('s', 'p');

            System.out.printf("<1> Supplier List \n");
            System.out.printf("<0> Back to previous page \n");
            System.out.printf("\n> ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 0:
                        return;

                    case 1:
                        while (true) {
                            Format.header(); // Display the header
                            Format.page_type('s', 'p');

                            System.out.printf("%-10s | %-10s | %-10s | \n", "Supplier ID", "Supplier Name", "Phone Number");
                            System.out.printf("--------------------------------------------\n");
                            String query = "SELECT * FROM supplier";

                            try {

                                PreparedStatement stmt = conn.prepareStatement(query);
                                ResultSet rs = stmt.executeQuery(query);

                                while (rs.next()) {
                                    String id = rs.getString("Supplier_ID");
                                    String name = rs.getString("Supplier_Name");
                                    String phone = rs.getString("Supplier_Phone");

                                    System.out.printf("%-11s | %-13s | %-12s |\n", id, name, phone);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            System.out.println();
                            System.out.println("<1> Search Supplier by using Supplier Name");
                            System.out.println("<2> Search Supplier by using Supplier ID");
                            System.out.println("<0> Back to previous page");

                            System.out.printf("\n> ");
                            String Input = scanner.nextLine();

                            if (Input.equals("0")) {
                                break; //back to previous page

                            } else if (Input.equals("1")) {
                                Format.header(); // Display the header
                                Format.page_type('s', 's');

                                SupplierSystem.searchSupplierName();

                                System.out.printf("\nPress Enter to continue.");
                                scanner.nextLine();

                            } else if (Input.equals("2")) {
                                Format.header(); // Display the header
                                Format.page_type('s', 's');

                                SupplierSystem.searchSupplierById();

                                System.out.printf("\nPress Enter to continue.");
                                scanner.nextLine();
                            } else {
                                System.out.printf("Invalid Input! Please Try Again!");
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
            } catch (InputMismatchException ep) {
                System.out.printf("Please choose the required number.\n");
                System.out.printf("Press Enter to continue.");
                scanner.nextLine(); // replace for int
                scanner.nextLine();
            }

        }
    }
}
