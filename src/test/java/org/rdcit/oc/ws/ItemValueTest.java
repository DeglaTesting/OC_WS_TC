/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rdcit.oc.ws;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author sa841
 */
@RunWith(MockitoJUnitRunner.class)
public class ItemValueTest {

    @Mock
    ItemValue mockItemValue;

    @Test
    public void testNormalBehaviourOfGetValue() {
        //given
        doReturn("value").when(mockItemValue).getValue(anyString(), anyString());
        //when         //then 
        assertTrue(mockItemValue.getValue("studyName", "subjectID").length() > 1);
        verify(mockItemValue).getValue("studyName", "subjectID");
    }
    
    @Test
    public void testGetValueBehaviourWithNullParam() {
        //given 
        doThrow(new RuntimeException()).when(mockItemValue).getValue(anyString(), eq(""));
        //when
        mockItemValue.getValue("studyName", null);
        //then
       verify(mockItemValue).getValue("studyName", null);
    }
    
    @Test
    public void testGetValueBehaviourWithNullParams(){
        //given
        doThrow(new RuntimeException()).when(mockItemValue).getValue(eq(""), eq(""));
        //when
        mockItemValue.getValue(null, null);
        //then
        verify(mockItemValue).getValue(null, null);
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ItemValueTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.println("Test was successful " + result.wasSuccessful());

    }

}
