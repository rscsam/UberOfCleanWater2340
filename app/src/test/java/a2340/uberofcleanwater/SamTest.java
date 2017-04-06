package a2340.uberofcleanwater;

import junit.framework.Assert;
import org.junit.Test;

import a2340.uberofcleanwater.model.AccountType;
import a2340.uberofcleanwater.model.User;

/**
 * Sam's test for M10 on isProfileComplete in User.java
 *
 * @author Sam Costley
 * @version 1.0
 * @since 4/5/2017
 */
public class SamTest {
    private final String DEFAULT_NAME = "name";
    private final String DEFAULT_USERNAME = "user";
    private final String DEFAULT_PASSWORD = "pass";
    private final String DEFAULT_EMAIL = "user@2340.com";
    private final String DEFAULT_ADDRESS = "GT";
    private final String DEFAULT_TITLE = "Zhis";

    @Test
    public void TestAllValid() {
        User u = new User(DEFAULT_NAME, AccountType.User, DEFAULT_USERNAME, DEFAULT_PASSWORD,
                DEFAULT_EMAIL, DEFAULT_ADDRESS, DEFAULT_TITLE);
        Assert.assertTrue(u.isProfileComplete());
    }

    @Test
    public void TestBlankStrings() {
        User u = new User("", AccountType.User, "", "", "", "", "");
        Assert.assertFalse(u.isProfileComplete());
    }

    @Test
    public void TestNameNull() {
        User u = new User(null, AccountType.User, DEFAULT_USERNAME, DEFAULT_PASSWORD,
                DEFAULT_EMAIL, DEFAULT_ADDRESS, DEFAULT_TITLE);
        Assert.assertFalse(u.isProfileComplete());
    }

    @Test
    public void TestEmailNull() {
        User u = new User(DEFAULT_NAME, AccountType.User, DEFAULT_USERNAME, DEFAULT_PASSWORD,
                null, DEFAULT_ADDRESS, DEFAULT_TITLE);
        Assert.assertFalse(u.isProfileComplete());
    }

    @Test
    public void TestAddressNull() {
        User u = new User(DEFAULT_NAME, AccountType.User, DEFAULT_USERNAME, DEFAULT_PASSWORD,
                DEFAULT_EMAIL, null, DEFAULT_TITLE);
        Assert.assertFalse(u.isProfileComplete());
    }

    @Test
    public void TestTitleNull() {
        User u = new User(DEFAULT_NAME, AccountType.User, DEFAULT_USERNAME, DEFAULT_PASSWORD,
                DEFAULT_EMAIL, DEFAULT_ADDRESS, null);
        Assert.assertFalse(u.isProfileComplete());
    }

    @Test
    public void TestMultipleNull() {
        User u = new User(null, AccountType.User, DEFAULT_USERNAME, DEFAULT_PASSWORD,
                DEFAULT_EMAIL, null, DEFAULT_TITLE);
        Assert.assertFalse(u.isProfileComplete());
    }

    @Test
    public void TestAllNull() {
        User u = new User(null, AccountType.User, null, null, null, null, null);
        Assert.assertFalse(u.isProfileComplete());
    }

}