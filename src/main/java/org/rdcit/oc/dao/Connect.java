/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rdcit.oc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author sa841
 */
public class Connect {

     static Connection connection;
    
  
    public static Connection openConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://openclinica-testing.medschl.cam.ac.uk:5432/ocplay", "postgres", "oc33ca");
            if (connection != null) {
                System.out.println("You made it, take control your database now!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            Connect.connection.close();
                            System.out.println("Connection closed !");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    } 

}
