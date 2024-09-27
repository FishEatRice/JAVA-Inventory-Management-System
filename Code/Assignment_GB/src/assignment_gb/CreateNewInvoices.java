/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_gb;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
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

public class CreateNewInvoices {

    public static int CheckItemProcess() {
        Connection conn = XAMPP_Connect.Connecter();

        // Define the SQL query
        String query = "SELECT * FROM item;";

        try {
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                System.out.println("No Store found.");
                return 0;
            } else {
                return 1;
            }
        } catch (Exception e) {
            // If Error, print error
            e.printStackTrace();
        }
        return 0;
    }

    public static void CreateProcess(String Staff_ID) {
        InvoiceSystem InvoiceSystem = new InvoiceSystem(Staff_ID);

        System.out.printf("%-12s (%s) %36s | | %-91s |\n", "Invoice Menu", InvoiceSystem, "", "Store Menu");
        System.out.printf("%-4s | %-50s | %-3s | %-10s | | %-10s | %-50s | %-10s | %-12s |\n", "No.", "Item", "Qty", "Price", "Store Code", "Item Name", "Item Price", "Expired Date");
        System.out.printf("---------------------------------------------------------------------------- | | ------------------------------------------------------------------------------------------- |\n");

        System.out.printf("%-76s | | %-91s |\n", "", "Please Enter '/Item/???' to show item");
    }

    public static void CreateProcess(String Staff_ID, String Input, InvoiceSystem InvoiceSystem) {
        Connection conn = XAMPP_Connect.Connecter();

        Scanner Scanner = new Scanner(System.in);

        String query = "";

        // Check Input is it check Item
        if (Input.startsWith("/1/")) {

            Format.header(); // Display the header
            Format.page_type('i', 's');

            System.out.printf("%-12s (%s) %36s | | %-91s |\n", "Invoice Menu", InvoiceSystem, "", "Store Menu");
            System.out.printf("%-4s | %-50s | %-3s | %-10s | | %-10s | %-50s | %-10s | %-12s |\n", "No.", "Item", "Qty", "Price", "Store Code", "Item Name", "Item Price", "Expired Date");
            System.out.printf("---------------------------------------------------------------------------- | | ------------------------------------------------------------------------------------------- |\n");

            String itemSearch;

            if (Input.contains("%")) {
                itemSearch = Input.substring(3);
            } else {
                itemSearch = "%" + Input.substring(3) + "%";

            }

            query = "SELECT store.Store_ID, item.Item_Name, item.Item_Price, store.Expiry_Date FROM store INNER JOIN item ON store.Item_ID = item.Item_ID WHERE item.Item_Name LIKE ? ORDER BY item.Item_Name;";

            try {
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, itemSearch);
                ResultSet resultSet = stmt.executeQuery();

                // System Check Item
                int ItemFound = 0;

                while (resultSet.next()) {
                    ItemFound++;
                }

                // Get How many Item in Invoice
                int InvoiceItem = InvoiceSystem.getnoOfItem();

                int InvoiceItemShowed = 0;

                if (ItemFound <= 0) {
                    // No Item Found
                    ItemFound += 1;

                    String[] Item_ID = InvoiceSystem.getItem_ID();
                    int[] Item_Qty = InvoiceSystem.getQuantity();

                    do {
                        if (InvoiceItem >= ItemFound) {
                            if (ItemFound >= 1) {
                                System.out.printf("%-4d | %-50s | %-3d | RM %-7.2f | | %-91s |\n",
                                        InvoiceItemShowed + 1, InvoiceSystem.GetItemName(Item_ID[InvoiceItemShowed]), Item_Qty[InvoiceItemShowed], InvoiceSystem.GetItemPrice(Item_ID[InvoiceItemShowed], Item_Qty[InvoiceItemShowed]), "No Store Found");
                                ItemFound--;
                                InvoiceItemShowed++;
                                InvoiceItem -= 1;
                            } else {
                                System.out.printf("%-4d | %-50s | %-3d | RM %-7.2f | | %-91s |\n",
                                        InvoiceItemShowed + 1, InvoiceSystem.GetItemName(Item_ID[InvoiceItemShowed]), Item_Qty[InvoiceItemShowed], InvoiceSystem.GetItemPrice(Item_ID[InvoiceItemShowed], Item_Qty[InvoiceItemShowed]), "");

                                InvoiceItemShowed++;
                                InvoiceItem -= 1;
                            }
                        } else {
                            System.out.printf("%-76s | | %-91s |\n",
                                    "", "No Store Found");
                            ItemFound--;
                        }

                    } while (InvoiceItem > 0);

                } else {
                    // Item Found
                    ResultSet ShowUser = stmt.executeQuery();

                    while (ShowUser.next()) {
                        String store_ID = ShowUser.getString("Store_ID");
                        String item_Name = ShowUser.getString("Item_Name");
                        double item_Price = ShowUser.getDouble("Item_Price");
                        Date exp = ShowUser.getDate("Expiry_Date");

                        String[] Item_ID = InvoiceSystem.getItem_ID();
                        int[] Item_Qty = InvoiceSystem.getQuantity();

                        if (InvoiceItem <= 0) {
                            System.out.printf("%-76s | | %-10s | %-50s | RM %-7.2f | %-12s |\n", "", store_ID, item_Name, item_Price, exp);
                        } else {
                            System.out.printf("%-4d | %-50s | %-3d | RM %-7.2f | | %-10s | %-50s | RM %-7.2f | %-12s |\n",
                                    InvoiceItemShowed + 1, InvoiceSystem.GetItemName(Item_ID[InvoiceItemShowed]), Item_Qty[InvoiceItemShowed], InvoiceSystem.GetItemPrice(Item_ID[InvoiceItemShowed], Item_Qty[InvoiceItemShowed]), store_ID, item_Name, item_Price, exp);

                            InvoiceItemShowed++;
                            InvoiceItem -= 1;
                        }
                    }

                    while (InvoiceItem > 0) {
                        String[] Item_ID = InvoiceSystem.getItem_ID();
                        int[] Item_Qty = InvoiceSystem.getQuantity();

                        System.out.printf("%-4d | %-50s | %-3d | RM %-7.2f | | %-91s |\n",
                                InvoiceItemShowed + 1, InvoiceSystem.GetItemName(Item_ID[InvoiceItemShowed]), Item_Qty[InvoiceItemShowed], InvoiceSystem.GetItemPrice(Item_ID[InvoiceItemShowed], Item_Qty[InvoiceItemShowed]), "");

                        InvoiceItemShowed++;
                        InvoiceItem -= 1;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (Input.startsWith("/2/")) {

            Format.header(); // Display the header
            Format.page_type('i', 's');

            System.out.printf("%-12s (%s) %36s | | %-91s |\n", "Invoice Menu", InvoiceSystem, "", "Store Menu");
            System.out.printf("%-4s | %-50s | %-3s | %-10s | | %-10s | %-50s | %-10s | %-12s |\n", "No.", "Item", "Qty", "Price", "Store Code", "Item Name", "Item Price", "Expired Date");
            System.out.printf("---------------------------------------------------------------------------- | | ------------------------------------------------------------------------------------------- |\n");

            String itemSearch = Input.substring(3);

            query = "SELECT store.Store_ID, item.Item_Name, item.Item_Price, store.Expiry_Date FROM store INNER JOIN item ON store.Item_ID = item.Item_ID WHERE item.Item_ID LIKE ? ORDER BY item.Item_ID;";
            try {
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, itemSearch);
                ResultSet resultSet = stmt.executeQuery();

                // System Check Item
                int ItemFound = 0;

                while (resultSet.next()) {
                    ItemFound++;
                }

                // Get How many Item in Invoice
                int InvoiceItem = InvoiceSystem.getnoOfItem();

                int InvoiceItemShowed = 0;

                if (ItemFound <= 0) {
                    // No Item Found
                    ItemFound += 1;

                    String[] Item_ID = InvoiceSystem.getItem_ID();
                    int[] Item_Qty = InvoiceSystem.getQuantity();

                    do {
                        if (InvoiceItem >= ItemFound) {
                            if (ItemFound >= 1) {
                                System.out.printf("%-4d | %-50s | %-3d | RM %-7.2f | | %-91s |\n",
                                        InvoiceItemShowed + 1, InvoiceSystem.GetItemName(Item_ID[InvoiceItemShowed]), Item_Qty[InvoiceItemShowed], InvoiceSystem.GetItemPrice(Item_ID[InvoiceItemShowed], Item_Qty[InvoiceItemShowed]), "No Store Found");
                                ItemFound--;
                                InvoiceItemShowed++;
                                InvoiceItem -= 1;
                            } else {
                                System.out.printf("%-4d | %-50s | %-3d | RM %-7.2f | | %-91s |\n",
                                        InvoiceItemShowed + 1, InvoiceSystem.GetItemName(Item_ID[InvoiceItemShowed]), Item_Qty[InvoiceItemShowed], InvoiceSystem.GetItemPrice(Item_ID[InvoiceItemShowed], Item_Qty[InvoiceItemShowed]), "");

                                InvoiceItemShowed++;
                                InvoiceItem -= 1;
                            }
                        } else {
                            System.out.printf("%-76s | | %-91s |\n",
                                    "", "No Store Found");
                            ItemFound--;
                        }

                    } while (InvoiceItem > 0);

                } else {
                    // Item Found
                    ResultSet ShowUser = stmt.executeQuery();

                    while (ShowUser.next()) {
                        String store_ID = ShowUser.getString("Store_ID");
                        String item_Name = ShowUser.getString("Item_Name");
                        double item_Price = ShowUser.getDouble("Item_Price");
                        Date exp = ShowUser.getDate("Expiry_Date");

                        String[] Item_ID = InvoiceSystem.getItem_ID();
                        int[] Item_Qty = InvoiceSystem.getQuantity();

                        if (InvoiceItem <= 0) {
                            System.out.printf("%-76s | | %-10s | %-50s | RM %-7.2f |\n", "", store_ID, item_Name, item_Price);
                        } else {
                            System.out.printf("%-4d | %-50s | %-3d | RM %-7.2f | | %-10s | %-50s | RM %-7.2f | $-12s |\n",
                                    InvoiceItemShowed + 1, InvoiceSystem.GetItemName(Item_ID[InvoiceItemShowed]), Item_Qty[InvoiceItemShowed], InvoiceSystem.GetItemPrice(Item_ID[InvoiceItemShowed], Item_Qty[InvoiceItemShowed]), store_ID, item_Name, item_Price, exp);

                            InvoiceItemShowed++;
                            InvoiceItem -= 1;
                        }
                    }

                    while (InvoiceItem > 0) {
                        String[] Item_ID = InvoiceSystem.getItem_ID();
                        int[] Item_Qty = InvoiceSystem.getQuantity();

                        System.out.printf("%-4d | %-50s | %-3d | RM %-7.2f | | %-91s |\n",
                                InvoiceItemShowed + 1, InvoiceSystem.GetItemName(Item_ID[InvoiceItemShowed]), Item_Qty[InvoiceItemShowed], InvoiceSystem.GetItemPrice(Item_ID[InvoiceItemShowed], Item_Qty[InvoiceItemShowed]), "");

                        InvoiceItemShowed++;
                        InvoiceItem -= 1;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (Input.startsWith("/3/")) {

            String itemSearch = Input.substring(3);

            query = "SELECT Store_ID FROM store WHERE Store_ID LIKE ? ORDER BY Store_ID DESC LIMIT 1;";
            try {
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, itemSearch);
                ResultSet resultSet = stmt.executeQuery();

                if (!resultSet.next()) {
                    System.out.print("Store ID not found! \nPress Enter to continue.");
                    Scanner.nextLine();
                    CreateNewInvoices.CreateProcess(Staff_ID, "/1/" + Input, InvoiceSystem);

                } else {
                    String confirm_Store_ID = resultSet.getString("Store_ID");
                    System.out.printf("\nConfirm Add \"%s\" to invoice? ", confirm_Store_ID);
                    System.out.printf("Still have %d stock.\n", StoreSystem.checkQTYStore(itemSearch));
                    System.out.print("If Yes, please enter quantity (1 - 999)\n");
                    System.out.print("If No, please enter 0\n> ");

                    try {
                        int QTY = Scanner.nextInt();
                        Scanner.nextLine();

                        if (QTY > 0 && QTY <= 999) {
                            if (QTY <= StoreSystem.checkQTYStore(confirm_Store_ID)) {

                                InvoiceSystem.addItems(confirm_Store_ID, QTY);

                                System.out.print("Item added! \nPress Enter to continue.");

                                StoreSystem.TakeStoreForInvoice(confirm_Store_ID, QTY);

                                Scanner.nextLine();

                                CreateNewInvoices.CreateProcess(Staff_ID, "/1/", InvoiceSystem);
                            } else {
                                System.out.printf("Out of stock, %d left. \nPress Enter to continue.", StoreSystem.checkQTYStore(itemSearch));
                                Scanner.nextLine();
                                CreateNewInvoices.CreateProcess(Staff_ID, "/1/", InvoiceSystem);
                            }
                        } else if (QTY > 999) {
                            System.out.print("Item over quantity!\n");
                            Scanner.nextLine();
                            CreateNewInvoices.CreateProcess(Staff_ID, "/1/", InvoiceSystem);
                        } else {
                            CreateNewInvoices.CreateProcess(Staff_ID, "/1/", InvoiceSystem);
                        }
                    } catch (InputMismatchException ex) {
                        Scanner.nextLine(); // replace for int
                        System.out.print("Please key in number!\n");
                        Scanner.nextLine();
                        CreateNewInvoices.CreateProcess(Staff_ID, "/1/", InvoiceSystem);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (Input.startsWith("/4/")) {

            String itemSearch = Input.substring(3);

            int ItemWantDelete;
            
            try{
                ItemWantDelete = Integer.parseInt(itemSearch);
            }catch(NumberFormatException exp){
                ItemWantDelete = 0;
            }
            

            System.out.printf("Enter '1' to confirm remove No.%s invoice item.\n> ", itemSearch);
            String answer = Scanner.nextLine();

            if (answer.equals("1")) {

                int InvoiceItem = InvoiceSystem.getnoOfItem();

                if (ItemWantDelete > 0 && ItemWantDelete <= InvoiceItem) {

                    String[] Item_ID = InvoiceSystem.getItem_ID();
                    String[] Store_ID = InvoiceSystem.getStore_ID();
                    int[] Item_Qty = InvoiceSystem.getQuantity();

                    String[] Item_ID_Process = new String[100];
                    String[] Store_ID_Process = new String[100];
                    int[] Item_Qty_Process = new int[100];

                    int newNo = 0;

                    for (int x = 0; x < InvoiceItem; x++) {
                        if (x != ItemWantDelete - 1) {
                            Item_ID_Process[newNo] = Item_ID[x];
                            Store_ID_Process[newNo] = Store_ID[x];
                            Item_Qty_Process[newNo] = Item_Qty[x];
                            newNo++;
                        } else {
                            InvoiceSystem.ReturnInvoiceItem(Store_ID[x], Item_Qty[x]);
                        }
                    }

                    InvoiceSystem.setItem_ID(Item_ID_Process);
                    InvoiceSystem.setStore_ID(Store_ID_Process);
                    InvoiceSystem.setQuantity(Item_Qty_Process);

                    System.out.print("Success Removed.\nPlease Enter to continue");
                    Scanner.nextLine();
                    CreateNewInvoices.CreateProcess(Staff_ID, "/1/", InvoiceSystem);

                } else {
                    System.out.print("No Data Found\nPlease Enter to continue");
                    Scanner.nextLine();
                    CreateNewInvoices.CreateProcess(Staff_ID, "/1/", InvoiceSystem);
                }

            } else {
                CreateNewInvoices.CreateProcess(Staff_ID, "/1/", InvoiceSystem);
            }

        } else if (Input.startsWith("/5/*")) {

            System.out.print("Enter '1' to confirm remove all invoice item.\n> ");
            String answer = Scanner.nextLine();

            if (answer.equals("1")) {

                int InvoiceItem = InvoiceSystem.getnoOfItem();

                String[] Store_ID = InvoiceSystem.getStore_ID();
                int[] Item_Qty = InvoiceSystem.getQuantity();

                String[] Item_ID_Process = new String[100];
                String[] Store_ID_Process = new String[100];
                int[] Item_Qty_Process = new int[100];

                for (int x = 0; x < InvoiceItem; x++) {
                    InvoiceSystem.ReturnInvoiceItem(Store_ID[x], Item_Qty[x]);
                }

                InvoiceSystem.setItem_ID(Item_ID_Process);
                InvoiceSystem.setStore_ID(Store_ID_Process);
                InvoiceSystem.setQuantity(Item_Qty_Process);

                System.out.print("Success Removed.\nPlease Enter to continue");
                Scanner.nextLine();
                CreateNewInvoices.CreateProcess(Staff_ID, "/1/", InvoiceSystem);
            } else {
                CreateNewInvoices.CreateProcess(Staff_ID, "/1/", InvoiceSystem);
            }
        } else {

            System.out.printf("Please choose the required options.\n");
            System.out.printf("Press Enter to continue.");
            Scanner.nextLine();

            CreateNewInvoices.CreateProcess(Staff_ID, "/1/", InvoiceSystem);

        }
    }
}
