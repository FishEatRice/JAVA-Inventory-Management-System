/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_gb;

import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Galaxy_Brain
 * ON YUEN SHERN
 * ONG JIT SIANG
 * SOO WEN WEI
 * YAP YONG QUAN
 * 
 */

public class XAMPP_Connect {

    public static java.sql.Connection Connecter() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String XAMPP = "jdbc:mysql://localhost/galaxy_brain";
            String Name = "root";
            String Password = "";

            // XAMPP database name
            java.sql.Connection conn = DriverManager.getConnection(XAMPP, Name, Password);

            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

            System.out.println("Loss Connect To LocalHost");

            System.exit(0);

            return null;
        }
    }
}
