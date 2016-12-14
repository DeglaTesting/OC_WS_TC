/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rdcit.oc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sa841
 */
public class ItemValueDAO {

    String studyName;
    String subjectID;
    String itemName;
    JSONArray jaItemValues;

    public ItemValueDAO(String studyName, String subjectID, String itemName) {
        this.studyName = studyName;
        this.subjectID = subjectID;
        this.itemName = itemName;
    }

    public String getStudySubjectItemValue() {
        String value = "";
        try {
            Statement stmt = Connect.openConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM item_data "
                    + "INNER JOIN item_form_metadata ON item_data.item_id = item_form_metadata.item_id "
                    + "INNER JOIN study ON study.owner_id = item_data.owner_id "
                    + "INNER JOIN study_subject ON study_subject.study_id =  study.study_id"
                    + " WHERE study.name = '" + this.studyName
                    + "' AND study_subject.label = '" + this.subjectID
                    + "' AND item_form_metadata.left_item_text = '" + this.itemName + "';");
            JSONObject joResponse = new JSONObject();
            joResponse.put("Service", "RDCIT");
            if (!rs.next()) {
                joResponse.put("ErrCode", "1");
                joResponse.put("Response", "Fail");
            } else {
                joResponse.put("ErrCode", "0");
                while (rs.next()) {
                    JSONObject joValue = new JSONObject();
                    joValue.put("ItemValue", rs.getString("value"));
                    jaItemValues = new JSONArray();
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

    public static void main(String[] args) {
        ItemValueDAO itemValue = new ItemValueDAO("Multiple sites test", "SID0000", "FirstName");
        System.out.println( itemValue.getStudySubjectItemValue());

    }
}
