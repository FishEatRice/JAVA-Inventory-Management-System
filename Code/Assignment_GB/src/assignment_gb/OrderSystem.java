package assignment_gb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author Galaxy_Brain
 * ON YUEN SHERN
 * ONG JIT SIANG
 * SOO WEN WEI
 * YAP YONG QUAN
 * 
 */

public class OrderSystem {

    //untuk item
    private String[] itemIDs;
    private String[] itemNames;
    private double[] prices;
    private int itemCount;

    //untuk order
    private int[] Order_no;
    private String[] Order_ID;
    private String[] Supplier_ID;
    private String[] itemIDsOrder;
    private int[] Quantity;
    private double[] orderPrices;
    private Timestamp[] orderDates;
    private String[] Staff_ID;
    private int orderCount;

    //item
    public OrderSystem(String staffID) {
        //all 100
        itemIDs = new String[100];
        itemNames = new String[100];
        prices = new double[10000];
        itemCount = 0;

        Connection conn = XAMPP_Connect.Connecter();
        String query = "SELECT Item_ID, Item_Name, Item_Price FROM item";

        try {
            //PreparedStatement is java sql interface
            //ResultSet is a java sql, check the datbase 
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if (itemCount < itemIDs.length) {
                    itemIDs[itemCount] = resultSet.getString("item_id");
                    itemNames[itemCount] = resultSet.getString("item_name");
                    prices[itemCount] = resultSet.getDouble("item_price");
                    itemCount++;
                }
            }
            resultSet.close();
            statement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //order
    public void DetailsOrder() {
        Order_no = new int[100];
        Order_ID = new String[100];
        Supplier_ID = new String[100];
        itemIDsOrder = new String[100];
        Quantity = new int[100];
        orderPrices = new double[100];
        orderDates = new Timestamp[100];
        Staff_ID = new String[100];
        orderCount = 0;

        Connection conn = XAMPP_Connect.Connecter();
        String query = "SELECT Order_No, Order_ID, Supplier_ID, Item_ID, Quantity, Price, Order_Datetime, Staff_ID FROM `order`";

        try (PreparedStatement statement = conn.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next() && orderCount < Order_no.length) {
                Order_no[orderCount] = resultSet.getInt("Order_No");
                Order_ID[orderCount] = resultSet.getString("Order_ID");
                Supplier_ID[orderCount] = resultSet.getString("Supplier_ID");
                itemIDsOrder[orderCount] = resultSet.getString("Item_ID");
                Quantity[orderCount] = resultSet.getInt("Quantity");
                orderPrices[orderCount] = resultSet.getDouble("Price");
                orderDates[orderCount] = resultSet.getTimestamp("Order_Datetime");
                Staff_ID[orderCount] = resultSet.getString("Staff_ID");
                orderCount++;
            }
        } catch (SQLException e) {
            System.out.println("SQL error while loading orders.");
            e.printStackTrace();
        }
    }

    public int[] getOrderNos() {
        return Order_no;
    }

    public String[] getOrderIDs() {
        return Order_ID;
    }

    public String[] getSupplierIDs() {
        return Supplier_ID;
    }

    public String[] getItemIDsOrder() {
        return itemIDsOrder;
    }

    public int[] getQuantities() {
        return Quantity;
    }

    public double[] getOrderPrices() {
        return orderPrices;
    }

    public Timestamp[] getOrderDates() {
        return orderDates;
    }

    public String[] getStaffIDs() {
        return Staff_ID;
    }

    public String[] getItemIDs() {
        return itemIDs;
    }

    public String[] getItemNames() {
        return itemNames;
    }

    public double[] getPrices() {
        return prices;
    }

    public int getItemCount() {
        return itemCount;
    }

    public static double getItemPrice(Connection conn, String itemId) throws SQLException {
        double price = 0.0;
        String query = "SELECT Item_Price FROM item WHERE Item_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, itemId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    price = rs.getDouble("Item_Price");
                } else {
                    System.out.println("Item not found: " + itemId);
                }
            }
        }
        return price;
    }

    //order_no part get new order
    public static String generateNewOrderNo(Connection conn) {
        int newOrderNo = 1; // Default value if no orders are found

        String query = "SELECT MAX(Order_No) AS maxOrderNo FROM `order`"; // set the maximum Order_No

        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int maxOrderNo = rs.getInt("maxOrderNo");
                newOrderNo = maxOrderNo + 1; // Increment the maximum Order_No
            }

        } catch (SQLException e) {
            System.out.printf("Error in SQL");
        }

        // Format the order number to ensure it has two digits
        return String.format("%02d", newOrderNo); // Returns a string with leading zero if necessary
    }

    public static String generateNewOrderID(Connection conn) {
        String newOrderID = "S1";

        String query = "SELECT * FROM `order` ORDER BY CAST(SUBSTRING(Order_ID, 2) AS UNSIGNED) DESC LIMIT 1;";

        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String lastOrderID = rs.getString("Order_ID");
                int orderNumber = Integer.parseInt(lastOrderID.substring(1));
                orderNumber++;
                newOrderID = String.format("S%d", orderNumber); //two digit
            }

        } catch (SQLException e) {
            System.out.printf("sql error."); //
        }

        return newOrderID;
    }

}
