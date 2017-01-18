/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rdcit.oc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.rdcit.controller.AppConfig;

/**
 *
 * @author sa841
 */
public class Connect {

    Connection connection;

    public Connection openConnection() {
        AppConfig appConfig = AppConfig.getAppConfig();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://" + appConfig.hostAddress + ":" + appConfig.hostPort + "/" + appConfig.dbName, appConfig.dbUserName, appConfig.dbUserPwd);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
