package a2340.uberofcleanwater;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import a2340.uberofcleanwater.database.DbHelper;
import a2340.uberofcleanwater.model.AccountType;
import a2340.uberofcleanwater.model.RegistrationData;
import a2340.uberofcleanwater.model.User;

/**
 * JUnit test for userExists method in RegistrationData.java.
 * @author Seth Triplett
 * @version 1.0
 * @since  4-5-17
 */
public class UserExistsTest {
    private SQLiteDatabase db;
    private DbHelper mDbHelper;
    private User testUser1;
    private User testUser2;

    @Before
    public void setup() {
        mDbHelper = new DbHelper(InstrumentationRegistry.getTargetContext());
        db = mDbHelper.getWritableDatabase();
        //No one will ever, ever have usernames like these.
        testUser1 = new User("18hpiqufhnaipueds flakj", AccountType.Admin, "b123", "pass",
                "bob@mail.com", "123 House Drive", "Mr.");
        testUser2 = new User("apdsoihfpaosdifhjpo", AccountType.Admin, "j456", "pass",
                "jill@mail.com", "87 Home Lane", "Mrs.");
        //Makes sure users are not already in the table.
        RegistrationData.removeUser(db, testUser1.getUserName());
        RegistrationData.removeUser(db, testUser2.getUserName());
        //Add first test user to the table.
        RegistrationData.addUser(db, testUser1);
    }

    @After
    public void finish() {
        RegistrationData.removeUser(db, testUser1.getName());
    }

    @Test
    public void testUserInTable() {
        Assert.assertTrue(RegistrationData.userExists(db, testUser1.getUserName()));
    }

    @Test
    public void testUserNotInTable() {
        Assert.assertFalse(RegistrationData.userExists(db, testUser2.getUserName()));
    }
}
