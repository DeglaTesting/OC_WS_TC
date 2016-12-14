/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rdcit.oc.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.rdcit.oc.dao.ItemValueDAO;

/**
 *
 * @author sa841
 */
@Path("itemValue/{studyName}/{subjectID}/{itemName}")
public class ItemValue {
    
    
    /**
     * Method handling HTTP GET requests. The returned object will be sent to
     * the client as "text/plain" media type.
     *
     * @param studyName
     * @param subjectID
     * @param itemName
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getValue(@PathParam("studyName") String studyName, @PathParam("subjectID") String subjectID, @PathParam("itemName") String itemName) {
         ItemValueDAO itemValueDao = new ItemValueDAO(studyName, subjectID, itemName);
     //   ItemValueDAO itemValueDao = new ItemValueDAO("Multiple sites test", "SID0000", "First_Name");
        String value = itemValueDao.getStudySubjectItemValue();
        return value;
    }
}
