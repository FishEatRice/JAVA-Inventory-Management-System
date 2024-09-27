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

public class StaffManagement {

    public static void Staff_Management_MainPage(String Staff_ID) {
        Scanner Scanner = new Scanner(System.in);

        while (true) {
            Format.header(); // Display the header
            Format.page_type('a');

            String query = "SELECT * FROM staff WHERE Position = 'S' ORDER BY CAST(SUBSTRING(Staff_ID, 4) AS UNSIGNED);";

            Connection conn = XAMPP_Connect.Connecter();

            try {
                PreparedStatement stmt = conn.prepareStatement(query);

                ResultSet resultSet = stmt.executeQuery();

                int Staff_Number = 0;

                // Check if any results were returned
                while (resultSet.next()) {
                    if (Staff_Number <= 0) {
                        System.out.printf("%-10s | %-50s |\n", "Staff_ID", "Staff_Name");
                        System.out.printf("-----------------------------------------------------------------\n");
                    }
                    String Staff_ID_Server = resultSet.getString("Staff_ID");
                    String Staff_Name_Server = resultSet.getString("Staff_Name");

                    System.out.printf("%-10s | %-50s |\n", Staff_ID_Server, Staff_Name_Server);

                    Staff_Number++;
                }

                if (Staff_Number <= 0) {
                    System.out.printf("No staff account.\n");
                }

                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.printf("<1> Search Staff using Staff_ID \n");
            System.out.printf("<2> Search Staff using Staff_Name \n");
            System.out.printf("<3> Show Staff Password \n");
            System.out.printf("<4> Create new Staff Account \n");
            System.out.printf("<5> Delete Staff Account \n");
            System.out.printf("<0> Back to previous page \n");

            System.out.printf("\n> ");

            try {
                int choice = Scanner.nextInt();
                Scanner.nextLine();

                switch (choice) {
                    case 0:
                        // Show which error
                        break;
                    case 1:
                        Format.header(); // Display the header
                        Format.page_type('a');

                        System.out.printf("Enter Staff_ID : ");
                        String Staff_ID_Search = Scanner.nextLine();

                        StaffSystem.Search_Staff_ID(Staff_ID_Search);

                        System.out.print("Press Enter to continue. ");
                        Scanner.nextLine();
                        break;
                    case 2:
                        Format.header(); // Display the header
                        Format.page_type('a');

                        System.out.printf("Enter Staff_Name : ");
                        String Staff_Name_Search = Scanner.nextLine();

                        StaffSystem.Search_Staff_Name(Staff_Name_Search);

                        System.out.print("Press Enter to continue. ");
                        Scanner.nextLine();
                        break;
                    case 3:
                        Format.header(); // Display the header
                        Format.page_type('a');

                        System.out.printf("Enter Staff_ID : ");
                        String Staff_Password_Show = Scanner.nextLine();

                        StaffSystem.Show_Staff_Password(Staff_Password_Show);

                        System.out.print("Press Enter to continue. ");
                        Scanner.nextLine();
                        break;
                    case 4:
                        Format.header(); // Display the header
                        Format.page_type('a');

                        System.out.printf("Enter Staff_Name ('/exit/' to exit) : ");
                        String Staff_Name_Create = Scanner.nextLine();
                        if (Staff_Name_Create.equals("/exit/")) {
                            break;
                        }
                        if (Staff_Name_Create.isEmpty()) {
                            break;
                        }

                        System.out.printf("\nEnter Staff_Password ('/exit/' to exit) : ");
                        String Staff_Password_Create = Scanner.nextLine();
                        if (Staff_Password_Create.equals("/exit/")) {
                            break;
                        }

                        StaffSystem.Create_New_Staff(Staff_Name_Create, Staff_Password_Create);

                        System.out.println("\nNew Staff Account Created");
                        System.out.print("Press Enter to continue. ");
                        Scanner.nextLine();
                        break;
                    case 5:
                        Format.header(); // Display the header
                        Format.page_type('a');

                        query = "SELECT * FROM staff WHERE Position = 'S' ORDER BY CAST(SUBSTRING(Staff_ID, 4) AS UNSIGNED);";

                        try {
                            PreparedStatement stmt = conn.prepareStatement(query);

                            ResultSet resultSet = stmt.executeQuery();

                            int Staff_Number = 0;

                            // Check if any results were returned
                            while (resultSet.next()) {
                                if (Staff_Number <= 0) {
                                    System.out.printf("%-10s | %-50s |\n", "Staff_ID", "Staff_Name");
                                    System.out.printf("-----------------------------------------------------------------\n");
                                }
                                String Staff_ID_Server = resultSet.getString("Staff_ID");
                                String Staff_Name_Server = resultSet.getString("Staff_Name");

                                System.out.printf("%-10s | %-50s |\n", Staff_ID_Server, Staff_Name_Server);

                                Staff_Number++;
                            }

                            if (Staff_Number <= 0) {
                                System.out.printf("No staff account.\n");
                            }

                            System.out.println();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        System.out.printf("Enter Staff_ID ('//' to exit) : ");
                        String Staff_ID_Delete = Scanner.nextLine();
                        if (Staff_ID_Delete.equals("//")) {
                            break;
                        }

                        System.out.print("Enter '1' to confirm delete staff_id : ");
                        String confirm_delete = Scanner.nextLine();

                        if (confirm_delete.equals("1")) {
                            StaffSystem.Delete_Staff_Process(Staff_ID_Delete);
                        } else {
                            System.out.print("\nDelete process stop. ");
                        }

                        System.out.print("Press Enter to continue. ");
                        Scanner.nextLine();
                        break;
                    default:
                        System.out.printf("Please choose the required number.\n");
                        System.out.printf("Press Enter to continue.");
                        Scanner.nextLine();
                        break;
                }

                if (choice == 0) {
                    // Break loop
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.printf("Please choose the required number.\n");
                System.out.printf("Press Enter to continue.");
                Scanner.nextLine(); // replace for int
                Scanner.nextLine();
            }
        }
    }

    public static void Staff_Reset_Password(String NewPassword, String Staff_ID) {
        Connection conn = XAMPP_Connect.Connecter();

        String query = "UPDATE `staff` SET `Staff_Password` = ? WHERE `staff`.`Staff_ID` = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, NewPassword);
            stmt.setString(2, Staff_ID);

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Staff_Change_Name(String NewName, String Staff_ID) {
        Connection conn = XAMPP_Connect.Connecter();

        String query = "UPDATE `staff` SET `Staff_Name` = ? WHERE `staff`.`Staff_ID` = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, NewName);
            stmt.setString(2, Staff_ID);

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
