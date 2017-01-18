/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rdcit.oc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    Connect connect;
    Connection connection;

    public ItemValueDAO(String studyName, String subjectID) {
        studyName = studyName;
        subjectID = subjectID;
        AppConfig appConfig = AppConfig.getAppConfig();
        itemName = appConfig.getItemName();
        connect = new Connect();
        connection = connect.openConnection();
    }

    public String getStudySubjectItemValue() {
        String value = "";
        JSONObject joResponse = new JSONObject();
        joResponse.put("Service", "RDCIT");
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT value FROM item_data "
                    + "INNER JOIN item_form_metadata ON item_data.item_id = item_form_metadata.item_id "
                    + "INNER JOIN study ON study.owner_id = item_data.owner_id "
                    + "INNER JOIN study_subject ON study_subject.study_id =  study.study_id"
                    + " WHERE study.name = '" + studyName
                    + "' AND study_subject.label = '" + subjectID
                    + "' AND item_form_metadata.left_item_text = '" + itemName + "';",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            rs.last();
            int countRS = rs.getRow();
            switch (countRS) {
                case 0:
                    if (checkItemName()) {
                        joResponse.put("ErrCode", "1");
                        joResponse.put("Response", "null");
                    } else {
                        joResponse.put("ErrCode", "3");
                        joResponse.put("Response", "null");
                    }
                    break;
                case 1:
                    joResponse.put("ErrCode", "0");
                    joResponse.put("Response", rs.getString("value"));
                    break;
                default:
                    rs.beforeFirst();
                    joResponse.put("ErrCode", "2");
                    jaItemValues = new JSONArray();
                    while (rs.next()) {
                        JSONObject joValue = new JSONObject();
                        joValue.put("ItemValue", rs.getString("value"));
                        jaItemValues.put(joValue);
                    }
                    joResponse.put("Response", jaItemValues);
                    break;
            }
            value = joResponse.toString();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        connect.closeConnection();
        return value;
    }

    public boolean checkItemName() {
        boolean exist = false;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT left_item_text FROM item_form_metadata "
                    + "INNER JOIN item_data ON item_data.item_id = item_form_metadata.item_id "
                    + "INNER JOIN study ON study.owner_id = item_data.owner_id "
                    + "INNER JOIN study_subject ON study_subject.study_id =  study.study_id"
                    + " WHERE study.name = '" + studyName
                    + "' AND study_subject.label = '" + subjectID + "';");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("left_item_text").equals(itemName)) {
                    exist = true;
                    break;
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return exist;
    }

    /*   public static void main(String[] args) {
        ItemValueDAO itemValue = new ItemValueDAO("Multiple sites test", "SID0000");
        System.out.println(itemValue.getStudySubjectItemValue());
    }*/
}
