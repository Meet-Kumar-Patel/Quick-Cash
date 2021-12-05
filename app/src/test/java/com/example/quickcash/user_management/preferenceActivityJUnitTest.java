package com.example.quickcash.user_management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
public class preferenceActivityJUnitTest {

    static PreferenceActivity preferenceActivity1;

    @BeforeClass
    public static void setup() {
        preferenceActivity1 = new PreferenceActivity();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void checkIfDurationEmpty() {
        assertTrue(preferenceActivity1.isEmptyDuration(""));
    }


    @Test
    public void checkIfWageEmpty() {
        assertTrue(preferenceActivity1.isEmptyWage(""));
    }

    @Test
    public void checkIfDurationLessThanone() {
        assertTrue(preferenceActivity1.isDurationLessThanOne(0));
    }

    @Test
    public void checkIfWageLessThan15() {
        assertTrue(preferenceActivity1.isWageLessThan15(0));
    }
}
