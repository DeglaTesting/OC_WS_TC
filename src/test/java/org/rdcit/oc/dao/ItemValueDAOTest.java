/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rdcit.oc.dao;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author sa841
 */
@RunWith(MockitoJUnitRunner.class)
public class ItemValueDAOTest {

    @Mock
    ItemValueDAO mockItemValueDao;

    @Test
    public void getStudySubjectItemValue() {
        doReturn("itemName").when(mockItemValueDao).getStudySubjectItemValue();
        System.out.println(mockItemValueDao.getStudySubjectItemValue());
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ItemValueDAOTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.println("The test was successful: " +result.wasSuccessful());
    }

}
