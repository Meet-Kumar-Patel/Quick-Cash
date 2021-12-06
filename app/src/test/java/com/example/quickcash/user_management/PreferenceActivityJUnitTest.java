package com.example.quickcash.user_management;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
/**
 * this class check if the Preference Activity
 */
public class PreferenceActivityJUnitTest {

    static PreferenceActivity preferenceActivity1;

    @BeforeClass
    public static void setup() {
        preferenceActivity1 = new PreferenceActivity();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }
    /**
     * this test check If Duration is Empty
     */
    @Test
    public void checkIfDurationEmpty() {
        assertTrue(preferenceActivity1.isEmptyDuration(""));
    }

    /**
     * this test check If Wage is Empty
     */
    @Test
    public void checkIfWageEmpty() {
        assertTrue(preferenceActivity1.isEmptyWage(""));
    }
    /**
     * this test check If Duration is less than one
     */
    @Test
    public void checkIfDurationLessThanone() {
        assertTrue(preferenceActivity1.isDurationLessThanOne(0));
    }
    /**
     * this test check If Wage is less than 15
     */
    @Test
    public void checkIfWageLessThan15() {
        assertTrue(preferenceActivity1.isWageLessThan15(0));
    }
}
