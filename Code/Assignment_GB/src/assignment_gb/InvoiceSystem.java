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

public class InvoiceSystem {

    private String Invoice_ID;
    private String[] Item_ID;
    private int[] Quantity;
    private String[] Store_ID;
    private static double total_invoice = 0.0;
    private String Staff_ID;

    private static int noOfItems = 0;

    public InvoiceSystem(String Staff_ID) {
        Connection conn = XAMPP_Connect.Connecter();
        // Limit 1 = only get one row
        String query = "SELECT Invoice_ID FROM Invoice ORDER BY CAST(Invoice_ID AS UNSIGNED) DESC LIMIT 1;";

        try {
            // Format
            PreparedStatement stmt = conn.prepareStatement(query);

            // Checking
            ResultSet rs = stmt.executeQuery();

            // If Yes
            if (rs.next()) {
                String lastInvoiceID = rs.getString("Invoice_ID");

                // PrarseInt (change string to int) subString (remove N in Invoice_ID)
                int Invoice_Number = Integer.parseInt(lastInvoiceID.substring(1));

                Invoice_Number += 1;

                this.Invoice_ID = "N" + String.format("%d", Invoice_Number);

            } else {
                this.Invoice_ID = "N1";
            }

            this.noOfItems = 0;

            //1 - 100 (0 - 99)
            this.Item_ID = new String[100];
            this.Store_ID = new String[100];
            this.Quantity = new int[100];

            this.Staff_ID = Staff_ID;

        } catch (Exception e) {
            // If Error, print error
            e.printStackTrace();
        }

    }

    // Method Get And Set
    public String getInvoice_ID() {
        return this.Invoice_ID;
    }

    public void setInvoice_ID(String Invoice_ID) {
        this.Invoice_ID = Invoice_ID;
    }

    // Method Get[] and Set[]
    public String[] getItem_ID() {
        return Item_ID;
    }

    public void setItem_ID(String[] Item_ID_Process) {
        this.Item_ID = Item_ID_Process;
    }

    // Method Get[] and Set[]
    public String[] getStore_ID() {
        return Store_ID;
    }

    public void setStore_ID(String[] Store_ID_Process) {
        this.Store_ID = Store_ID_Process;
    }

    // Method Get[] and Set[]
    public int[] getQuantity() {
        return Quantity;
    }

    public void setQuantity(int[] Item_Qty_Process) {
        this.Quantity = Item_Qty_Process;
    }

    // Method Get And Set
    public String getStaff_ID() {
        return this.Staff_ID;
    }

    public void setStaff_ID(String Staff_ID) {
        this.Staff_ID = Staff_ID;
    }

    // Method Get
    public static int getnoOfItem() {
        return noOfItems;
    }

    // Add New Items
    public void addItems(String RStore_ID, int Qty) {

        if (noOfItems < 100) {
            Connection conn = XAMPP_Connect.Connecter();

            String query = "SELECT item.Item_ID FROM store INNER JOIN item ON store.Item_ID = item.Item_ID WHERE store.Store_ID LIKE ? ORDER BY store.Store_ID;";
            try {
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, RStore_ID);
                ResultSet resultSet = stmt.executeQuery();

                if (resultSet.next()) {
                    Item_ID[noOfItems] = resultSet.getString("Item_ID");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Store_ID[noOfItems] = RStore_ID;

            Quantity[noOfItems] = Qty;

            noOfItems++;
        } else {
            System.out.println("Invoice is full.");
        }
    }

    //Get Item Name
    public String GetItemName(String Item_ID) {
        Connection conn = XAMPP_Connect.Connecter();

        String query = "SELECT Item_Name FROM item WHERE Item_ID LIKE ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, Item_ID);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                return resultSet.getString("Item_Name");
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get Total Price
    public double GetItemPrice(String Item_ID, int Qty) {
        Connection conn = XAMPP_Connect.Connecter();

        String query = "SELECT Item_Price FROM item WHERE Item_ID LIKE ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, Item_ID);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                return resultSet.getDouble("Item_Price") * Qty;
            }

            return 0.0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public void ReturnInvoiceItem(String Store_ID, int Qty) {
        Connection conn = XAMPP_Connect.Connecter();

        int current_QTY = 0;

        String query = "SELECT Quantity FROM store WHERE Store_ID LIKE ?;";
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

        current_QTY = current_QTY + Qty;

        String update_query = "UPDATE store SET Quantity = ? WHERE Store_ID LIKE ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(update_query);
            stmt.setInt(1, current_QTY);
            stmt.setString(2, Store_ID);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        noOfItems--;
    }

    // Total Invoice
    public double gettotal_invoice() {

        total_invoice = 0.0;

        int system_number = 0;

        for (int x = 0; x <= noOfItems; x++) {
            total_invoice = total_invoice + GetItemPrice(Item_ID[system_number], Quantity[system_number]);
            system_number++;
        }

        return total_invoice;
    }

    public int Current_InvoiceNo() {
        Connection conn = XAMPP_Connect.Connecter();

        String query = "SELECT Invoice_No FROM invoice ORDER BY Invoice_No DESC LIMIT 1;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("Invoice_No") + 1;
            }else{
                return 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void CreateInvoice(String Staff_ID) {
        Connection conn = XAMPP_Connect.Connecter();

        int system_number = 0;
        for (int x = 0; x < noOfItems; x++) {
            String query = "INSERT INTO `invoice` (`Invoice_No`, `Invoice_ID`, `Item_ID`, `Quantity`, `Price`, `Invoice_Datetime`, `Staff_ID`) VALUES (?, ?, ?, ?, ?, ?, ?);";
            try {
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, Current_InvoiceNo());
                stmt.setString(2, Invoice_ID);
                stmt.setString(3, Item_ID[system_number]);
                stmt.setInt(4, Quantity[system_number]);
                stmt.setDouble(5, GetItemPrice(Item_ID[system_number], 1));
                stmt.setTimestamp(6, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
                stmt.setString(7, Staff_ID);

                stmt.executeUpdate();
                system_number++;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return String.format(" Invoice_ID: %-10s ", Invoice_ID);
    }

}
