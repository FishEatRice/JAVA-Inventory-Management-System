/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_gb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Galaxy_Brain
 * ON YUEN SHERN
 * ONG JIT SIANG
 * SOO WEN WEI
 * YAP YONG QUAN
 * 
 */

public class Item {

    public static String getSupplierID(String ItemID) {
        Connection conn = XAMPP_Connect.Connecter();

        String query = "SELECT * FROM Item WHERE Item_ID = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, ItemID);

            ResultSet resultSet = stmt.executeQuery();

            // Check if any results were returned
            if (resultSet.next()) {
                return resultSet.getString("Supplier_ID");
            } else {
                return "/exit/";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/exit/";
    }
}
