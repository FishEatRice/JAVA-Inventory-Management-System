/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_gb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class Admin {

    public static void Admin_Login_Page() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            Connection conn = XAMPP_Connect.Connecter();
            Format.header(); // Display the header

            Format.page_type('a', 'l');
            System.out.println("Enter \"/exit/\" to exit");

            System.out.print("Admin ID: ");
            String Admin_ID = scanner.nextLine();
            // Exit Process
            if (Admin_ID.equals("/exit/")) {
                break;
            }

            System.out.print("Password: ");
            String Admin_Password = scanner.nextLine();
            // Exit Process
            if (Admin_Password.equals("/exit/")) {
                break;
            }

            if (CheckAdminAccount(conn, Admin_ID, Admin_Password)) {
                Admin_MainPage(Admin_ID);
                break;
            } else {
                System.out.printf("Invalid ID or Password. Please try again.");
                scanner.nextLine();
            }
        }
    }

    // Read Data from XAMPP and check for
    private static boolean CheckAdminAccount(Connection conn, String Staff_ID, String Staff_Password) {
        String query = "SELECT Staff_ID, Staff_Password FROM staff WHERE Position = 'A' AND Staff_ID = ? AND Staff_Password = ?";
        try {
            // Format
            PreparedStatement stmt = conn.prepareStatement(query);

            // Set Staff_ID
            stmt.setString(1, Staff_ID);

            // Set Staff_Password
            stmt.setString(2, Staff_Password);

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

    private static String GetAdminName(String Staff_ID) {
        Connection conn = XAMPP_Connect.Connecter();
        String query = "SELECT Staff_Name FROM staff WHERE Position = 'A' AND Staff_ID = ?";
        try {
            // Format
            PreparedStatement stmt = conn.prepareStatement(query);

            // Set Staff_ID
            stmt.setString(1, Staff_ID);

            // Checking
            ResultSet rs = stmt.executeQuery();

            // If Yes
            if (rs.next()) {
                return rs.getString("Staff_Name");
            }

        } catch (Exception e) {
            // If Error, print error
            e.printStackTrace();
        }

        // If No
        return null;
    }

    private static void Admin_MainPage(String Staff_ID) {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 2; i++) {
            // Make it loop
            i--;

            String Staff_Name = GetAdminName(Staff_ID);
            Format.header(); // Display the header
            Format.page_type('a');

            System.out.printf("Welcome Staff, %s!\n", Staff_Name);

            System.out.printf("<1> Invoice Manager \n");
            System.out.printf("<2> Order Stock \n");
            System.out.printf("<3> Check Supplier \n");
            System.out.printf("<4> Check Inventory \n");
            System.out.printf("<5> Staff Management \n");
            System.out.printf("<6> Account Details \n");
            System.out.printf("<0> Log Out \n");
            System.out.printf("\n> ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 0:
                        // Make Loop Break
                        i += 2;
                        break;

                    case 1:
                        Invoice.Invoice_MainPage(Staff_ID);
                        break;
                    case 2:
                        Order.Order_Mainpage(Staff_ID);
                        break;
                    case 3:
                        Supplier.supplier_staff_page(Staff_ID);
                        break;
                    case 4:
                        inventory.Inventory_mainpage();
                        break;
                    case 5:
                        StaffManagement.Staff_Management_MainPage(Staff_ID);
                        break;
                    case 6:
                        while (true) {
                            Format.header(); // Display the header
                            Format.page_type('a');

                            System.out.println("Account Details:\n");

                            System.out.printf("Admin ID : %s\n", Staff_ID);
                            System.out.printf("Admin Name : %s\n", Staff.GetStaffName(Staff_ID));
                            System.out.printf("ADmin Password : %s\n\n", Staff.GetStaffPassword(Staff_ID));

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

                                StaffManagement.Staff_Change_Name(New_Name, Staff_ID);

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
                                    StaffManagement.Staff_Reset_Password(New_Password_2, Staff_ID);

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

}
