package assignment_gb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
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

public class inventory {

    public static void Inventory_mainpage() {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 2; i++) {
            // Make it loop
            i--;

            Format.header(); // Display the header
            Format.page_type('t');

            System.out.printf("<1> Display All List \n");
            System.out.printf("<2> Search Inventory\n");
            System.out.printf("<0> Back \n");
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
                        inventory.displayAllList();
                        break;
                    case 2:
                        inventory.searchInventory();
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

    public static void displayAllList() {
        Scanner scanner = new Scanner(System.in);
        Connection conn = XAMPP_Connect.Connecter();

        for (int i = 0; i < 2; i++) {
            // Make it loop
            i--;

            Format.header();
            Format.page_type('t');
            // Define the SQL query
            System.out.printf("%-10s | %-10s | %-3s | %-13s | %-10s \n", "Store_ID", "Item_ID", "Quantity", "Expiry_Date", "Status");
            System.out.printf("-----------------------------------------------------------------------------------\n");

            String query = "SELECT * FROM store ORDER BY CAST(SUBSTRING(Store_ID, 3) AS UNSIGNED);";

            try {
                PreparedStatement stmt = conn.prepareStatement(query);

                ResultSet resultSet = stmt.executeQuery();

                LocalDate currentDate = LocalDate.now();

                while (resultSet.next()) {

                    // Convert SQL Date to LocalDate
                    LocalDate Expiry_Date = resultSet.getDate("Expiry_Date").toLocalDate();
                    // Compare expiry date with current date to determine status
                    String status = Expiry_Date.compareTo(currentDate) < 0 ? "EXPIRED" : "VALID";

                    System.out.printf("%-10s | %-10s | %-8s | %-13s | %-10s \n", resultSet.getString("Store_ID"), resultSet.getString("Item_ID"), resultSet.getInt("Quantity"), resultSet.getString("Expiry_Date"), status);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.printf("\n");
            System.out.printf("<1> Search Inventory\n");
            System.out.printf("<2> Delete Inventory\n");
            System.out.printf("<0> Back \n");
            System.out.printf("\n> ");
            try {
                int choice1 = scanner.nextInt();
                scanner.nextLine();

                switch (choice1) {
                    case 0:
                        return;

                    case 1:
                        inventory.searchInventory();
                        break;
                    case 2:
                        inventory.deleteInventory();
                        break;
                    default:
                        System.out.printf("Please choose the required number.\n");
                        System.out.printf("Press Enter to continue.");
                        scanner.nextLine();
                        break;
                }
            } catch (InputMismatchException ex) {
                System.out.printf("Please choose the required number.\n");
                System.out.printf("Press Enter to continue.");
                scanner.nextLine(); // replace for int
                scanner.nextLine();
            }
        }
    }

    public static void searchInventory() {
        Scanner scanner = new Scanner(System.in);
        Connection conn = XAMPP_Connect.Connecter();

        boolean exitSearch = false;

        System.out.printf("Enter the Store_ID to search: ");
        String Store_ID = scanner.nextLine();

        while (!exitSearch) {

            Format.header();
            Format.page_type('t', 's');

            System.out.printf("%-10s | %-10s | %-3s | %-13s | %-10s \n", "Store_ID", "Item_ID", "Quantity", "Expiry_Date", "Status");
            System.out.printf("-----------------------------------------------------------------------------------\n");

            String query = "SELECT * FROM STORE WHERE STORE_ID = ?";

            try {
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, Store_ID);

                ResultSet resultSet = stmt.executeQuery();

                LocalDate currentDate = LocalDate.now();

                // Check if any results were returned
                boolean found = false;
                while (resultSet.next()) {

                    // Convert SQL Date to LocalDate
                    LocalDate Expiry_Date = resultSet.getDate("Expiry_Date").toLocalDate();
                    // Compare expiry date with current date to determine status
                    String status = Expiry_Date.compareTo(currentDate) < 0 ? "EXPIRED" : "VALID";
                    // Print the details of the item
                    System.out.printf("%-10s | %-10s | %-8s | %-13s | %-10s \n", resultSet.getString("Store_ID"), resultSet.getString("Item_ID"), resultSet.getInt("Quantity"), resultSet.getString("Expiry_Date"), status);

                    found = true;
                }

                // Inform the user if no items were found
                if (!found) {
                    System.out.println("No item found with Store_ID " + Store_ID + ".");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.printf("\n");
            System.out.printf("<1> Search Again\n");
            System.out.printf("<0> Back \n");
            System.out.printf("\n> ");
            try {
                int choice2 = scanner.nextInt();
                scanner.nextLine();

                switch (choice2) {
                    case 0:
                        // set it to exit search loop
                        exitSearch = true;
                        break;
                    case 1:
                        exitSearch = true;
                        inventory.searchInventory();
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

    public static void deleteInventory() {
        Scanner scanner = new Scanner(System.in);
        Connection conn = XAMPP_Connect.Connecter();
        Format.header();
        Format.page_type('t', 'd');

        System.out.printf("Enter the Store_ID to delete (Enter // to exit): ");
        String Store_ID = scanner.nextLine();

        if ("//".equals(Store_ID)) {

            // If exit go back previous page
        } else {

            //double confirm
            System.out.printf("Are You Sure To Delete Store_ID " + Store_ID + "?\n\nType 'YES' to confirm and 'NO' to cancel: ");
            String confirm = scanner.nextLine();

            //check if user confirmed or not
            if (confirm.equals("YES")) {
                String query = "DELETE FROM STORE WHERE STORE_ID = ?";

                try {

                    PreparedStatement stmt = conn.prepareStatement(query);

                    stmt.setString(1, Store_ID);

                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {

                        System.out.println("\nItem with Store_ID " + Store_ID + " has been deleted successfully.");
                        System.out.print("Press Enter to Continue.");

                    } else {
                        System.out.println("\nNo item found with Store_ID " + Store_ID + ".");
                        System.out.print("Press Enter to Continue.");

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (confirm.equals("NO")) {
                System.out.println("Deletion cancelled.");
                System.out.print("Press Enter to Continue.");
                scanner.nextLine();
                return; // Exit the method without deleting
            } else {
                System.out.print("Press Enter to Back.");

            }
            scanner.nextLine();
        }
    }
}
