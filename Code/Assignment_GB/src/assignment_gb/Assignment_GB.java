/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment_gb;

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

public class Assignment_GB {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 2; i++) {
            // Make it loop
            i--;

            // HomePages
            XAMPP_Connect.Connecter();
            Format.header();
            Format.page_type('m');

            System.out.printf("<1> Staff Login \n");
            System.out.printf("<2> Admin Login \n");
            System.out.printf("<3> Supplier Login \n");
            System.out.printf("<0> Exit \n");
            System.out.printf("\n> ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 0:
                        System.out.printf("Thank You, See you next time.\n");
                        System.exit(0);

                    case 1:
                        Staff.Staff_Login_Page();
                        break;

                    case 2:
                        Admin.Admin_Login_Page();
                        break;
                        
                    case 3:
                        Supplier.supplier_login_page();
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
