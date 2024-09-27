/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_gb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
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

public class Invoice {

    public static void Invoice_MainPage(String Staff_ID) {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 2; i++) {
            // Make it loop
            i--;

            Format.header(); // Display the header
            Format.page_type('i', 's');

            System.out.printf("<1> Create New Invoice \n");
            System.out.printf("<2> Check Invoice History \n");
            System.out.printf("<0> Back to previous page \n");
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
                        Format.header(); // Display the header
                        Format.page_type('i', 's');

                        // Check is there have any items
                        int results = CreateNewInvoices.CheckItemProcess();
                        if (results != 1) {
                            scanner.nextLine();
                            break;
                        }

                        // Show user how to create invoice
                        CreateNewInvoices.CreateProcess(Staff_ID);

                        InvoiceSystem InvoiceSystem = new InvoiceSystem(Staff_ID);

                        while (true) {
                            System.out.printf("%-54s | Total: | RM %-7.2f |\n", "", InvoiceSystem.gettotal_invoice());
                            System.out.println("");
                            System.out.println("Please Enter '/1/a' to search item name, '/1/' only to show all");
                            System.out.println("Please Enter '/2/I1' to search item by using Item ID");
                            System.out.println("Please Enter '/3/ST1' to add item that entered Code to invoice");
                            System.out.println("Please Enter '/4/1' to delete item that in No. from invoice");
                            System.out.println("Please Enter '/5/*' to delete all item from invoice");
                            System.out.println("Please Enter '/6/' to create this invoice");
                            System.out.println("Please Enter '/exit/' to exit without save");
                            System.out.printf("\n> ");
                            String Input = scanner.nextLine();

                            if (Input.equals("/exit/")) {
                                System.out.println("Enter '1' confirm exit? Process will not save.");
                                System.out.printf("\n> ");
                                try {
                                    int ConfrimExit = scanner.nextInt();
                                    scanner.nextLine();
                                    if (ConfrimExit == 1) {

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

                                        break;
                                    } else {
                                        Format.header(); // Display the header
                                        Format.page_type('i', 's');

                                        CreateNewInvoices.CreateProcess(Staff_ID, Input, InvoiceSystem);
                                    }
                                } catch (InputMismatchException ex) {
                                    scanner.nextLine(); // replace for int
                                    Format.header(); // Display the header
                                    Format.page_type('i', 's');

                                    CreateNewInvoices.CreateProcess(Staff_ID, Input, InvoiceSystem);
                                }

                            } else if (Input.startsWith("/6/")) {

                                int InvoiceItem = InvoiceSystem.getnoOfItem();
                                if (InvoiceItem <= 0) {
                                    System.out.print("No Data Found. \nPlease Enter to continue");
                                    scanner.nextLine();
                                    CreateNewInvoices.CreateProcess(Staff_ID, "/1/", InvoiceSystem);
                                } else {

                                    System.out.print("Enter '1' to confirm create this invoice.\n> ");
                                    String answer = scanner.nextLine();

                                    if (answer.equals("1")) {

                                        StoreSystem.removezerostock();

                                        InvoiceSystem.CreateInvoice(Staff_ID);

                                        System.out.print("Success Created. \nPress enter to continue.");
                                        scanner.nextLine();

                                        break;

                                    } else {
                                        CreateNewInvoices.CreateProcess(Staff_ID, "/1/", InvoiceSystem);
                                    }
                                }
                            } else {

                                CreateNewInvoices.CreateProcess(Staff_ID, Input, InvoiceSystem);

                            }

                        }

                        break;

                    case 2:
                        Format.header(); // Display the header
                        Format.page_type('i', 's');

                        Connection conn = XAMPP_Connect.Connecter();

                        System.out.printf("%-10s | %-10s | %-3s | %-10s | %-19s | %-13s \n", "Invoice ID", "Item_ID", "Qty", "  Price", "Invoice DateTime", "Staff_ID");
                        System.out.printf("---------------------------------------------------------------------------------\n");
                        String query = "SELECT * FROM invoice ORDER BY Invoice_Datetime DESC;";
                        try {
                            PreparedStatement stmt = conn.prepareStatement(query);

                            ResultSet resultSet = stmt.executeQuery();

                            while (resultSet.next()) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String Invoice_DateTime = dateFormat.format(resultSet.getTimestamp("Invoice_Datetime"));

                                System.out.printf("%-10s | %-10s | %3d | RM %7.2f | %-19s | %-13s \n",
                                        resultSet.getString("Invoice_ID"),
                                        resultSet.getString("Item_ID"),
                                        resultSet.getInt("Quantity"),
                                        resultSet.getDouble("Price"),
                                        Invoice_DateTime,
                                        resultSet.getString("Staff_ID"));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        System.out.printf("\nPress Enter back to previous page.");
                        scanner.nextLine();
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
