/**
 * Vaibhavsinh Vaghela
 * July 17th, 2017
 * --
 * TestNG class designed to test the functionality of all
 * methods in the Selenium SampleAutomationCode.java program.
 */

package com.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import com.qmetry.SampleAutomationCode;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SampleAutomationCodeTest {
    
    /**
     * Tests that Google chrome browser opens up 'google.com'
     */
    @Test
    public void navigateToGoogle() {
        
        try {
            String pageTitle = SampleAutomationCode.navigateToQMetry();
            assertEquals(pageTitle, "QMetry Test Management Tool for Agile Testing");
        } catch (InterruptedException e) {
        	e.printStackTrace();
        }
        
    }
    
    /**
     * Tests that you can search for 'QMetry' in google
     */
    @Test 
    public void queryForAutomationFrameworkText() {
        
        try {
            SampleAutomationCode.queryText("QMetry Automation Framework");
            
        } catch (InterruptedException e) {
        	e.printStackTrace();
        } finally {
        	SampleAutomationCode.browser.close();
        }
        
    }
    
}
