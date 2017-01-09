/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rdcit.oc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONObject;
import org.rdcit.controller.AppConfig;

/**
 *
 * @author sa841
 */
public class ItemValueDAO {

    String studyName;
    String subjectID;
    String itemName;
    JSONArray jaItemValues;

    public ItemValueDAO(String studyName, String subjectID) {
        this.studyName = studyName;
        this.subjectID = subjectID;
        AppConfig appConfig = new AppConfig();
        this.itemName = appConfig.getItemName();
    }

    public String getStudySubjectItemValue() {
        String value = "";
        JSONObject joResponse = new JSONObject();
        joResponse.put("Service", "RDCIT");
        try {
            PreparedStatement stmt = Connect.openConnection().prepareStatement("SELECT * FROM item_data "
                    + "INNER JOIN item_form_metadata ON item_data.item_id = item_form_metadata.item_id "
                    + "INNER JOIN study ON study.owner_id = item_data.owner_id "
                    + "INNER JOIN study_subject ON study_subject.study_id =  study.study_id"
                    + " WHERE study.name = '" + this.studyName
                    + "' AND study_subject.label = '" + this.subjectID
                    + "' AND item_form_metadata.left_item_text = '" + this.itemName + "';");
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                if (checkItemName()) {
                    joResponse.put("ErrCode", "1");
                    joResponse.put("Response", "null");
                } else {
                    joResponse.put("ErrCode", "2");
                    joResponse.put("Response", "Item name is not valid");
                }
            } else {
                joResponse.put("ErrCode", "0");
                jaItemValues = new JSONArray();
                while (rs.next()) {
                    JSONObject joValue = new JSONObject();
                    joValue.put("ItemValue", rs.getString("value"));
                    jaItemValues.put(joValue);
                }
                joResponse.put("Response", jaItemValues);
            }
            value = joResponse.toString();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return value;
    }

    public boolean checkItemName() {
        boolean exist = false;
        try {
            Statement stmt = Connect.openConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT left_item_text FROM item_form_metadata "
                    + "INNER JOIN item_data ON item_data.item_id = item_form_metadata.item_id "
                    + "INNER JOIN study ON study.owner_id = item_data.owner_id "
                    + "INNER JOIN study_subject ON study_subject.study_id =  study.study_id"
                    + " WHERE study.name = '" + this.studyName
                    + "' AND study_subject.label = '" + this.subjectID + "';");
            while (rs.next()) {
                System.out.println("@crf.left_item_text " + rs.getString("left_item_text"));
                if (rs.getString("left_item_text").equals(this.itemName)) {
                    exist = true;
                    break;
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return exist;
    }

    public static void main(String[] args) {
        ItemValueDAO itemValue = new ItemValueDAO("Multiple sites test", "SID0000");
        System.out.println(itemValue.getStudySubjectItemValue());
    }
}
