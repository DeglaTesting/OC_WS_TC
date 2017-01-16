/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rdcit.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 *
 * @author sa841
 */
@ManagedBean(name = "AppConfig")
public class AppConfig {

    @Context
 HttpServletRequest request;

    private final File confFile;
    String itemName;
    String dbCredentials;

    public AppConfig() {
        String sFilePath = "";
        URL location = AppConfig.class.getProtectionDomain().getCodeSource().getLocation();
        sFilePath = location.getFile();
        sFilePath = sFilePath.replace("AppConfig.class", "");
        confFile = new File(sFilePath + "OcRestWS.conf");
    }

    public String getConfFile() {
        return confFile.getAbsolutePath();
    }

    public String getItemName() {
        BufferedReader br = null;
        String lItemName = "";
        try {
            String sCurrentLine;
            br = new BufferedReader(new java.io.FileReader(this.confFile));
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
        System.out.println(itemName);
        return itemName;
    }

    public void setItemName(String newItemName) {
        if (newItemName.length() > 1) {
            this.itemName = newItemName;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error", "Item name cannot be empty"));
        }
    }

    public void changeItemName(ActionEvent event) {
        try {
            File tmp = File.createTempFile("OcRestWSTMP", "txt");
            BufferedReader br = new BufferedReader(new FileReader(this.confFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tmp));
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.startsWith("itemName")) {
                    sCurrentLine = "itemName=".concat(itemName);
                    bw.write(String.format("%s%n", sCurrentLine));
                    // break;
                } else {
                    bw.write(String.format("%s%n", sCurrentLine));
                }
            }
            br.close();
            bw.close();
            File oldFile = new File(this.confFile.getAbsolutePath());
            if (oldFile.delete()) {
                tmp.renameTo(oldFile);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String getdbCredentials() {
       BufferedReader br = null;
        String dbCredentials = "";
        try {
            String sCurrentLine;
            br = new BufferedReader(new java.io.FileReader(this.confFile));
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
        System.out.println(dbCredentials);
        return dbCredentials;
    }

}
