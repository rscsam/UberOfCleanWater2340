package a2340.uberofcleanwater;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import a2340.uberofcleanwater.database.DbHelper;
import a2340.uberofcleanwater.model.AccountType;
import a2340.uberofcleanwater.model.User;

import static a2340.uberofcleanwater.model.RegistrationData.addUser;
import static a2340.uberofcleanwater.model.RegistrationData.removeUser;
import static a2340.uberofcleanwater.model.RegistrationData.userExists;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Anna's JUnit tests for addUser method in RegistrationData.java
 * @author Anna Bergstrom
 * @version 1.0
 * @since 4/5/17
 */
public class AddUserTest {

    private User testEmptyPassword;
    private User testEmptyUsername;
    private User testValidUser;
    private SQLiteDatabase mockDB;


    @Before
    public void setup() {
        testEmptyPassword = new User("testEP", AccountType.Worker, "testEP", "", "", "", "");
        testEmptyUsername = new User("testEU", AccountType.Worker, "", "testEU", "", "", "");
        testValidUser = new User("testVU", AccountType.Worker, "testVU", "testVP", "", "", "");
        DbHelper mDbHelper = new DbHelper(InstrumentationRegistry.getTargetContext());
        mockDB = mDbHelper.getWritableDatabase();
    }
    @After
    public void finish() {
        removeUser(mockDB, testValidUser.getName());
    }
    @Test //tests if a user without a valid username or password will not be added to the database
    public void emptyNameOrPW() {
        assertFalse(addUser(mockDB, testEmptyPassword));
        assertFalse(addUser(mockDB, testEmptyUsername));
        assertFalse(userExists(mockDB, "testEP"));
        assertFalse(userExists(mockDB, ""));
    }
    @Test //tests if a valid user is added to the database and if the method returns true if so
    public void testAddValidUser() {
        boolean test = addUser(mockDB, testValidUser);
        assertTrue(test);
        assertTrue(userExists(mockDB, "testVU"));
    }
    @Test //tests if a user will be not be added to the database if they are already in it
    public void testRepeatUser() {
        addUser(mockDB, testValidUser);
        assertFalse(addUser(mockDB, testValidUser));
        assertTrue(userExists(mockDB, "testVU"));
    }
}

