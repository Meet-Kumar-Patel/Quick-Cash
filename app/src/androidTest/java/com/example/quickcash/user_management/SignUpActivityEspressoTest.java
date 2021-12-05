package com.example.quickcash.user_management;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.quickcash.R;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)

/**
 * this class is responsible for testing all the signup espresso tests
 * */
public class SignUpActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<SignUpActivity> myRule = new ActivityScenarioRule<>(SignUpActivity.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    /**
     * this test check if it is the correct activity or not
     */
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.quickcash", appContext.getPackageName());
    }

    /**
     * this test check if Registration Page is visible
     */
    @Test
    public void checkIfRegistrationPageIsVisible() {
        onView(ViewMatchers.withId(R.id.txtFirstName)).check(matches(withText("")));
        onView(withId(R.id.txtLastName)).check(matches(withText("")));
        onView(withId(R.id.txtEmail)).check(matches(withText("")));
        onView(withId(R.id.txtUserEnteredPassword)).check(matches(withText("")));
        onView(withId(R.id.txtLastName)).check(matches(withText("")));
        onView(withId(R.id.txtPhone)).check(matches(withText("")));
    }

    /**
     * this test check if  first name is empty
     */
    @Test
    public void checkIfFirstNameIsEmpty() {
        onView(withId(R.id.txtFirstName)).perform(typeText("")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtLastName)).perform(typeText("Patel")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("mp@dal.ca")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPhone)).perform(typeText("1234567890")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtUserEnteredPassword)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPassword)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click()).perform(closeSoftKeyboard());
        onView(withId(R.id.statusLabel)).check(matches(withText("Please Enter Your First Name")));
    }

    /**
     * this test check if  last name is empty
     */
    @Test
    public void checkIfLastNameIsEmpty() {
        onView(withId(R.id.txtFirstName)).perform(typeText("Meet")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtLastName)).perform(typeText("")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("mp@dal.ca")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPhone)).perform(typeText("1234567890")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtUserEnteredPassword)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPassword)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click()).perform(closeSoftKeyboard());
        onView(withId(R.id.statusLabel)).check(matches(withText("Please Enter Your Last Name")));
    }

    /**
     * this test check if  email is empty
     */
    @Test
    public void checkIfEmailIsEmpty() {
        onView(withId(R.id.txtFirstName)).perform(typeText("Meet")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtLastName)).perform(typeText("Patel")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPhone)).perform(typeText("1234567890")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtUserEnteredPassword)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPassword)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click()).perform(closeSoftKeyboard());
        onView(withId(R.id.statusLabel)).check(matches(withText("Please Enter your email address")));
    }

    /**
     * this test check if  phone number is empty
     */
    @Test
    public void checkIfPhoneNumberIsEmpty() {
        onView(withId(R.id.txtFirstName)).perform(typeText("Meet")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtLastName)).perform(typeText("Patel")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("mp@dal.ca")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPhone)).perform(typeText("")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtUserEnteredPassword)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPassword)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click()).perform(closeSoftKeyboard());
        onView(withId(R.id.statusLabel)).check(matches(withText("Please enter your phone number")));
    }

    /**
     * this test check if  password is empty
     */
    @Test
    public void checkIfPasswordIsEmpty() {
        onView(withId(R.id.txtFirstName)).perform(typeText("Meet")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtLastName)).perform(typeText("Patel")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("mp@dal.ca")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPhone)).perform(typeText("1234567890")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtUserEnteredPassword)).perform(typeText("")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPassword)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click()).perform(closeSoftKeyboard());
        onView(withId(R.id.statusLabel)).check(matches(withText("Please Enter your password")));
    }

    /**
     * this test check if  confirm password is empty
     */
    @Test
    public void checkIfConfirmPasswordIsEmpty() {
        onView(withId(R.id.txtFirstName)).perform(typeText("Meet")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtLastName)).perform(typeText("Patel")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("mp@dal.ca")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPhone)).perform(typeText("1234567890")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtUserEnteredPassword)).perform(typeText("Mp@2001#")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPassword)).perform(typeText("")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click()).perform(closeSoftKeyboard());
        onView(withId(R.id.statusLabel)).check(matches(withText("Please Enter to confirm your password")));
    }

    /**
     * this test check if  RadioButton is selected
     */
    @Test
    public void checkIfRadioButtonIsSelected() {
        onView(withId(R.id.txtFirstName)).perform(typeText("Meet")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtLastName)).perform(typeText("Patel")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("mp@dal.ca")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPhone)).perform(typeText("1234567890")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtUserEnteredPassword)).perform(typeText("Mp@2001#")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPassword)).perform(typeText("Mp@2001#")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click()).perform(closeSoftKeyboard());
        onView(withId(R.id.statusLabel)).check(matches(withText("Please Select one of the two given options")));
    }

    /**
     * this test check if password is valid
     */
    @Test
    public void checkIfPasswordIsValid() {


        onView(withId(R.id.txtFirstName)).perform(typeText("Raham")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtLastName)).perform(typeText("moghaddam")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("abc.123@dal.ca")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPhone)).perform(typeText("1234567890")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtUserEnteredPassword)).perform(typeText("AAAAAAAAAAAAA")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPassword)).perform(typeText("AAAAAAAAAAAAA")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click()).perform(closeSoftKeyboard());
        onView(withId(R.id.statusLabel)).check(matches(withText("Please Enter valid Password")));
    }

    /**
     * this test check if passwords(password and confirm) are matching
     */
    @Test
    public void checkIfPasswordIsMatching() {
        onView(withId(R.id.txtFirstName)).perform(typeText("Raham")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtLastName)).perform(typeText("moghaddam")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("abc.123@dal.ca")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPhone)).perform(typeText("1234567890")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtUserEnteredPassword)).perform(typeText("Ab1!azasb2#2121")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPassword)).perform(typeText("Ab1!azasb2#21212")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click()).perform(closeSoftKeyboard());
        onView(withId(R.id.statusLabel)).check(matches(withText("Passwords don't match. Please Enter again")));
    }

    /**
     * this test check if phone number is valid
     */
    @Test
    public void checkIfPhoneNumberIsValid() {
        onView(withId(R.id.txtFirstName)).perform(typeText("Meet")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtLastName)).perform(typeText("Patel")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("mp@dal.ca")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPhone)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtUserEnteredPassword)).perform(typeText("Mp@2001#")).perform(closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPassword)).perform(typeText("Mp@2001#")).perform(closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click()).perform(closeSoftKeyboard());
        onView(withId(R.id.statusLabel)).check(matches(withText("Phone number should be atleast 10 digits")));
    }

    /**
     * this test check if the login button moves to login page
     */
    @Test
    public void moveToLogin() {
        onView(withId(R.id.buttonLogin)).perform(closeSoftKeyboard()).perform(click());
        intended(hasComponent(LogInActivity.class.getName()));
    }


}