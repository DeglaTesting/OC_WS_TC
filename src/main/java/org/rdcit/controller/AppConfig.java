/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rdcit.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author sa841
 */
public class AppConfig {

    // For the server
    //private final String confFilePath = AppConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("AppConfig.class", "OcRestWS.conf") ;
    private final String confFilePath = AppConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "OcRestWS.conf";
    private File confFile = new File(confFilePath);
    String itemName;
    String dbCredentials;
    public String hostAddress;
    public String hostPort;
    public String dbName;
    public String dbUserName;
    public String dbUserPwd;

    private static AppConfig appConfig = null;

    private AppConfig() {
    }

    public static AppConfig getAppConfig() {
        if (appConfig == null) {
            appConfig = new AppConfig();
            appConfig.setdbCredentials();
        }
        return appConfig;
    }

    public String getConfFilePath() {
        return confFilePath;
    }

    public String getItemName() {
        BufferedReader br = null;
        String lItemName = "";
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(confFile));
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.startsWith("itemName")) {
                    lItemName = sCurrentLine;
                    break;
                }
            }
            br.close();
            String[] arIteName = lItemName.split("=");
            itemName = arIteName[1];
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return itemName;
    }

    public void setdbCredentials() {
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new java.io.FileReader(confFile));
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.startsWith("OC_DB")) {
                    dbCredentials = sCurrentLine;
                    break;
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        String[] arrDbCredentials = dbCredentials.split("\t");
        hostAddress = arrDbCredentials[1];
        hostPort = arrDbCredentials[2];
        dbName = arrDbCredentials[3];
        dbUserName = arrDbCredentials[4];
        dbUserPwd = arrDbCredentials[5];
    }
}
