package com.example.quickcash.UserManagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
public class preferencePageJUnitTest {

    static preferencePage preferencePage1;

    @BeforeClass
    public static void setup() {
        preferencePage1 = new preferencePage();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void checkIfDurationEmpty() {
        assertTrue(preferencePage1.isEmptyDuration(""));
    }


    @Test
    public void checkIfWageEmpty() {
        assertTrue(preferencePage1.isEmptyWage(""));
    }

    @Test
    public void checkIfDurationLessThanone() {
        assertTrue(preferencePage1.isDurationLessThanone(0));
    }

    @Test
    public void checkIfWageLessThan15() {
        assertTrue(preferencePage1.isWageLessThan15(0));
    }
}
