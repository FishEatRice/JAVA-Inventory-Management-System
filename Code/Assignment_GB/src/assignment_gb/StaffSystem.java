/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_gb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class StaffSystem {

    public static void Search_Staff_ID(String Staff_ID_Input) {
        Connection conn = XAMPP_Connect.Connecter();

        String query = "SELECT * FROM staff WHERE Position = 'S' AND Staff_ID = ? ORDER BY CAST(SUBSTRING(Staff_ID, 4) AS UNSIGNED);";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, Staff_ID_Input);

            ResultSet resultSet = stmt.executeQuery();

            int Staff_Number = 0;

            // Check if any results were returned
            while (resultSet.next()) {
                if (Staff_Number <= 0) {
                    System.out.println();
                    System.out.printf("%-10s | %-50s |\n", "Staff_ID", "Staff_Name");
                    System.out.printf("-----------------------------------------------------------------\n");
                }
                String Staff_ID_Server = resultSet.getString("Staff_ID");
                String Staff_Name_Server = resultSet.getString("Staff_Name");

                System.out.printf("%-10s | %-50s |\n", Staff_ID_Server, Staff_Name_Server);

                Staff_Number++;
            }

            if (Staff_Number <= 0) {
                System.out.println("Staff_ID " + Staff_ID_Input + " Not Found.");
            }

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void Search_Staff_Name(String Staff_Name_Input) {
        Connection conn = XAMPP_Connect.Connecter();

        String query = "SELECT * FROM staff WHERE Position = 'S' AND Staff_Name LIKE ? ORDER BY CAST(SUBSTRING(Staff_ID, 4) AS UNSIGNED);";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            Staff_Name_Input = "%" + Staff_Name_Input + "%";
            stmt.setString(1, Staff_Name_Input);

            ResultSet resultSet = stmt.executeQuery();

            int Staff_Number = 0;

            // Check if any results were returned
            while (resultSet.next()) {
                if (Staff_Number <= 0) {
                    System.out.println();
                    System.out.printf("%-10s | %-50s |\n", "Staff_ID", "Staff_Name");
                    System.out.printf("-----------------------------------------------------------------\n");
                }
                String Staff_ID_Server = resultSet.getString("Staff_ID");
                String Staff_Name_Server = resultSet.getString("Staff_Name");

                System.out.printf("%-10s | %-50s |\n", Staff_ID_Server, Staff_Name_Server);

                Staff_Number++;
            }

            if (Staff_Number <= 0) {
                System.out.println("Staff " + Staff_Name_Input + " Not Found.");
            }

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void Show_Staff_Password(String Staff_Password_Show) {
        Connection conn = XAMPP_Connect.Connecter();

        String query = "SELECT * FROM staff WHERE Position = 'S' AND Staff_ID = ? ORDER BY CAST(SUBSTRING(Staff_ID, 4) AS UNSIGNED);";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, Staff_Password_Show);

            ResultSet resultSet = stmt.executeQuery();

            int Staff_Number = 0;

            // Check if any results were returned
            while (resultSet.next()) {
                if (Staff_Number <= 0) {
                    System.out.println();
                    System.out.printf("%-10s | %-50s | %-50s | \n", "Staff_ID", "Staff_Name", "Staff_Password");
                    System.out.printf("-------------------------------------------------------------------------------------------------------------------\n");
                }
                String Staff_ID_Server = resultSet.getString("Staff_ID");
                String Staff_Name_Server = resultSet.getString("Staff_Name");
                String Staff_Password_Server = resultSet.getString("Staff_Password");

                System.out.printf("%-10s | %-50s | %-50s |\n", Staff_ID_Server, Staff_Name_Server, Staff_Password_Server);

                Staff_Number++;
            }

            if (Staff_Number <= 0) {
                System.out.println("Staff_ID " + Staff_Password_Show + " Not Found.");
            }

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void Create_New_Staff(String Staff_Name_Create, String Staff_Password_Create) {
        Connection conn = XAMPP_Connect.Connecter();
        Scanner Scanner = new Scanner(System.in);

        String query = "";
        String Staff_ID_Server = "";

        query = "SELECT * FROM staff WHERE Position = 'S' ORDER BY CAST(SUBSTRING(Staff_ID, 4) AS UNSIGNED) DESC LIMIT 1;";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet resultSet = stmt.executeQuery();

            // Check if any results were returned
            if (resultSet.next()) {
                Staff_ID_Server = resultSet.getString("Staff_ID");

                int Staff_No = Integer.parseInt(Staff_ID_Server.substring(3));

                Staff_No++;

                Staff_ID_Server = "STF" + String.format("%d", Staff_No);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        query = "INSERT INTO `staff` (`Staff_ID`, `Staff_Name`, `Staff_Password`, `Position`) VALUES (?, ?, ?, 'S')";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, Staff_ID_Server);
            stmt.setString(2, Staff_Name_Create);
            stmt.setString(3, Staff_Password_Create);

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Delete_Staff_Process(String Staff_ID_Delete) {
        Connection conn = XAMPP_Connect.Connecter();
        Scanner Scanner = new Scanner(System.in);

        String query = "DELETE FROM staff WHERE Staff_ID = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, Staff_ID_Delete);

            int Delete_Result = stmt.executeUpdate();

            if (Delete_Result == 1) {
                System.out.println("\nStaff ID already deleted.");
            } else {
                System.out.println("\nStaff ID not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
