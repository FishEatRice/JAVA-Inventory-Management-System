/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_gb;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Galaxy_Brain
 * ON YUEN SHERN
 * ONG JIT SIANG
 * SOO WEN WEI
 * YAP YONG QUAN
 * 
 */

public class Format {

    public static void cls() {
        // Not Working In IDE
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void header() {
        LocalDateTime Header_DateTime = LocalDateTime.now();

        DateTimeFormatter Format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String HeaderFormat = Header_DateTime.format(Format);

        cls();

        System.out.printf("   ____           _                            ____                              _ \n");
        System.out.printf("  / ___|   __ _  | |   __ _  __  __  _   _    | __ )   _ __    ___    __ _    __| |\n");
        System.out.printf(" | |  _   / _` | | |  / _` | \\ \\/ / | | | |   |  _ \\  | '__|  / _ \\  / _` |  / _` |\n");
        System.out.printf(" | |_| | | (_| | | | | (_| |  >  <  | |_| |   | |_) | | |    |  __/ | (_| | | (_| |\n");
        System.out.printf("  \\____|  \\__,_| |_|  \\__,_| /_/\\_\\  \\__, |   |____/  |_|     \\___|  \\__,_|  \\__,_|\n");
        System.out.printf("                                     |___/                      %s\n", HeaderFormat);

        // If have expired item
        //System.out.println("\u001B[47m" + "\u001B[31m" + " Having " + " Stock Expired!!! " + "\u001B[0m");
    }

    public static void page_type(char Type, char Type2) {
        switch (Type) {
            case 's' -> {
                switch (Type2) {
                    case 'l' ->
                        System.out.println("------------------------------ Staff Login Page -----------------------------------");
                    case 'p' ->
                        System.out.println("----------------------------- Staff Supplier Page ---------------------------------");
                    case 's' ->
                        System.out.println("---------------------------- Supplier Search Page ---------------------------------");
                }
            }
            case 'a' -> {
                switch (Type2) {
                    case 'l' ->
                        System.out.println("------------------------------ Admin Login Page -----------------------------------");
                }
            }
            case 'p' -> {
                switch (Type2) {
                    case 'l' ->
                        System.out.println("----------------------------- Supplier Login Page ----------------------------------");
                    case 'i' ->
                        System.out.println("----------------------------- Supplier Item Page -----------------------------------");
                    case 's' ->
                        System.out.println("-------------------------- Supplier Search Item Page -------------------------------");
                    case 'a' ->
                        System.out.println("---------------------------- Supplier Add Item Page --------------------------------");
                }
            }
            case 'i' -> {
                switch (Type2) {
                    case 's' ->
                        System.out.println("----------------------------- Staff Invoice Page ----------------------------------");
                    case 'a' ->
                        System.out.println("----------------------------- Admin Invoice Page ----------------------------------");
                    case 'h' ->
                        System.out.println("---------------------------- Invoice History Page ---------------------------------");
                }
            }
            case 'o' -> {
                switch (Type2) {
                    case 's' ->
                        System.out.println("------------------------------ Staff Order Page -----------------------------------");
                    case 'a' ->
                        System.out.println("------------------------------ Admin Order Page -----------------------------------");
                    case 'h' ->
                        System.out.println("------------------------------ Order History Page ---------------------------------");
                    case 'd' ->
                        System.out.println("------------------------------ Cancel Order Page ----------------------------------");
                }
            }

            case 't' -> {
                switch (Type2) {
                    case 'd' ->
                        System.out.println("--------------------------- Inventory Delete Page ---------------------------------");
                    case 's' ->
                        System.out.println("--------------------------- Inventory Search Page ---------------------------------");
                }
            }
            default ->
                System.out.println("----------------------------------- NULL ------------------------------------------");
        }
    }

    public static void page_type(char Type) {
        switch (Type) {
            case 'm' ->
                System.out.println("--------------------------------- Main Page ---------------------------------------");
            case 's' ->
                System.out.println("--------------------------------- Staff Page --------------------------------------");
            case 'a' ->
                System.out.println("--------------------------------- Admin Page --------------------------------------");
            case 'p' ->
                System.out.println("-------------------------------- Supplier Page -------------------------------------");
            case 'o' ->
                System.out.println("--------------------------------- Order Page ---------------------------------------");
            case 'i' ->
                System.out.println("-------------------------------- Invoice Page -------------------------------------");
            case 't' ->
                System.out.println("------------------------------- Inventory Page ------------------------------------");
            default ->
                System.out.println("----------------------------------- NULL ------------------------------------------");
        }
    }
}
