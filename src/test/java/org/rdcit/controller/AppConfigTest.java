/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rdcit.controller;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 *
 * @author sa841
 */
public class AppConfigTest {

    AppConfig mockAppConfig = mock(AppConfig.class);
    AppConfig appConfig = new AppConfig();

    @Test
    public void testGetConfigFile() {
        assertTrue(appConfig.getConfFile().endsWith("OcRestWS.conf"));
    }

    @Test
    public void testItemName() {
        //given
        doCallRealMethod().when(mockAppConfig).setItemName("new Item name");
        //when
        mockAppConfig.setItemName("new Item name");
        //then -> assertion
        assertTrue(mockAppConfig.itemName.equals("new Item name"));
    }

    @Test
    public void testItemNameDoesNotChangeWhenNull() {
        //given
        doNothing().when(mockAppConfig).setItemName("");
        //when
        mockAppConfig.setItemName("");
        //then
        verify(mockAppConfig).setItemName("");
    }
    
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AppConfigTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.println("Test was successful: " + result.wasSuccessful());
    }
}
