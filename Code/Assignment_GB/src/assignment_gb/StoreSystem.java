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

public class StoreSystem {

    public static int checkQTYStore(String Store_ID) {

        Connection conn = XAMPP_Connect.Connecter();

        String query = "SELECT Quantity FROM store WHERE Store_ID LIKE ? ORDER BY Store_ID DESC LIMIT 1;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, Store_ID);
            ResultSet resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                return 0;
            } else {
                return resultSet.getInt("Quantity");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void TakeStoreForInvoice(String Store_ID, int QTY) {
        Connection conn = XAMPP_Connect.Connecter();

        int current_QTY = 0;

        String query = "SELECT Quantity FROM store WHERE Store_ID LIKE ? ORDER BY Store_ID DESC LIMIT 1;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, Store_ID);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                current_QTY = resultSet.getInt("Quantity");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        current_QTY = current_QTY - QTY;

        String update_query = "UPDATE store SET Quantity = ? WHERE Store_ID LIKE ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(update_query);
            stmt.setInt(1, current_QTY);
            stmt.setString(2, Store_ID);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void removezerostock() {

        Connection conn = XAMPP_Connect.Connecter();

        String update_query = "DELETE FROM store WHERE Quantity <= 0;";
        try {
            PreparedStatement stmt = conn.prepareStatement(update_query);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String GetStoreID() {
        String query = "SELECT Store_ID FROM `store` ORDER BY CAST(SUBSTRING(Store_ID, 3) AS UNSIGNED) DESC LIMIT 1;";

        Connection conn = XAMPP_Connect.Connecter();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet resultSet = stmt.executeQuery();

            String Store_ID_Data = null;

            if (resultSet.next()) {
                Store_ID_Data = resultSet.getString("Store_ID");
            }

            if (Store_ID_Data == null) {
                return "ST1";
            }

            int Store_No = Integer.parseInt(Store_ID_Data.substring(2));

            Store_No++;

            String Store_ID = "ST" + Store_No;

            return Store_ID;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String GetItemID_Supplier(String Order_ID) {
        String query = "SELECT Item_ID FROM `order` WHERE Order_ID = ?;";
        String itemId = null;

        try (Connection conn = XAMPP_Connect.Connecter(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, Order_ID);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    itemId = resultSet.getString("Item_ID");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemId;
    }

    public static int GetQuantity_Supplier(String Order_ID) {
        String query = "SELECT Quantity FROM `order` WHERE Order_ID = ?;";
        int Quantity = 0;

        try (Connection conn = XAMPP_Connect.Connecter(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, Order_ID);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Quantity = resultSet.getInt("Quantity");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Quantity;
    }

}
