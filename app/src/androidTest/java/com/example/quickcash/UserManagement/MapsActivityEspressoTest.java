package com.example.quickcash.UserManagement;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import com.example.quickcash.MainActivity;
import com.example.quickcash.R;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * This class is responsible for checking the implementation of the Map Acitivity.
 */
public class MapsActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<MapsActivity> MapsRule = new ActivityScenarioRule<MapsActivity>(MapsActivity.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    /**
     * This test is to check if the search by reference button is properly connecting with the designated pages or not.
     * @throws NoMatchingViewException Indicates that a given matcher did not match any elements in the view hierarchy
     */
    @Test
    public void checkIfSearchByPreferenceIsConnecting() throws NoMatchingViewException{
        try {
            onView(withId(R.id.Search_Preference)).perform(closeSoftKeyboard()).perform(click());
            // The LoginActivity should be changed to something else page.
            intended(hasComponent(MainActivity.class.getName()));
        } catch (NoMatchingViewException e) {
            onView(withId(R.id.create_Tasks)).perform(closeSoftKeyboard()).perform(click());
            // The LoginActivity should be changed to something else page.
            intended(hasComponent(LogInActivity.class.getName()));
        }
    }

//    @Test
//    public void checkIfSearchByManualIsConnecting() throws NoMatchingViewException{
//
//        try {
//
////            onView(withId(R.id.Search_Preference)).perform(closeSoftKeyboard()).perform(click());
////            intended(hasComponent(MainActivity.class.getName()));
//
//            onView(withId(R.id.search_Manual)).perform(closeSoftKeyboard()).perform(click());
//            intended(hasComponent(MainActivity.class.getName()));
//
//        } catch (NoMatchingViewException e) {
//
//            onView(withId(R.id.create_Tasks)).perform(closeSoftKeyboard()).perform(click());
//
//            // The MainActivity should be changed to Preference page.
//            intended(hasComponent(LogInActivity.class.getName()));
//
//        }
//
//
//    }

}
