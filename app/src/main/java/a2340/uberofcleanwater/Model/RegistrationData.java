package a2340.uberofcleanwater.model;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.security.SecureRandom;

import a2340.uberofcleanwater.database.UserContract;
import a2340.uberofcleanwater.database.UserDbHelper;

/**
 * Singleton which holds user registration data
 *
 * @author Ryan Anderson
 * @version 1.0
 * @since 2017-02-12
 */

public class RegistrationData {

    /**
     * Adds a new user to the RegData. Checks that no user with the name already exists.
     * @param db - The database being accessed
     * @param user - User to be added.
     * @return - true if the user is successfully added, false if otherwise.
     */
    public static boolean addUser(SQLiteDatabase db, User user) {
        if (user.getUserName().isEmpty() || user.getPassword().isEmpty()) {
            return false;
        }

        if(userExists(db, user.getUserName())) {
            return false;
        } else {
            ContentValues values = new ContentValues();
            values.put(UserContract.UserEntry.COLUMN_NAME_USERNAME, user.getUserName());
            values.put(UserContract.UserEntry.COLUMN_NAME_ACCOUNTTYPE, user.getType().ordinal());
            values.put(UserContract.UserEntry.COLUMN_NAME_NAME, user.getName());
            values.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, user.getPassword());
            values.put(UserContract.UserEntry.COLUMN_NAME_EMAIL, user.getEmailAddress());
            values.put(UserContract.UserEntry.COLUMN_NAME_ADDRESS, user.getHomeAddress());
            values.put(UserContract.UserEntry.COLUMN_NAME_TITLE, user.getTitle());

            db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
        }
        return true;
    }

    /**
     * Removes a user based on the name of the user.
     * @param db - The database being accessed
     * @param userName - Username of the user being removed.
     * @return - true if the user is successfully removed, false if otherwise.
     */
    public static boolean removeUser(SQLiteDatabase db, String userName) {
        String selection = UserContract.UserEntry.COLUMN_NAME_USERNAME + " LIKE ?";
        String[] selectionArgs = {userName};
        int success = db.delete(UserContract.UserEntry.TABLE_NAME, selection, selectionArgs);

        return (success > 0);
    }

    /**
     * Checks if a user with the given name exists.
     * @param db - The database being accessed
     * @param userName - Username of the user being checked.
     * @return - true if a user with the given name is in the database, false if not.
     */
    public static boolean userExists(SQLiteDatabase db, String userName) {

        String[] projection = {
                UserContract.UserEntry.COLUMN_NAME_USERNAME,
        };

        String selection = UserContract.UserEntry.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = {userName};

        Cursor c = db.query(
                UserContract.UserEntry.TABLE_NAME,  // The table to query
                projection,                         // The columns to return
                selection,                          // The columns for the where clause
                selectionArgs,                      // The values for the where clause
                null,                               // don't group the rows
                null,                               // don't filter by row groups
                null                                // the sort order
        );
        String name = null;
        if(c.moveToFirst()) {
            name = c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_USERNAME));
        }
        c.close();

        if (name == null) {
            return false;
        } else if (userName.equals(name)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the given username/password combination exists in the database.
     * @param userName - Username of the user being checked for.
     * @param pass - password of the user being checked for.
     * @return - true if the given username/password combination exists, false if not.
     */
    public static boolean checkPassword(SQLiteDatabase db, String userName, String pass) {
        String[] projection = {
                UserContract.UserEntry._ID,
                UserContract.UserEntry.COLUMN_NAME_USERNAME,
                UserContract.UserEntry.COLUMN_NAME_PASSWORD
        };

        String selection = UserContract.UserEntry.COLUMN_NAME_USERNAME + " = ? AND " +
                UserContract.UserEntry.COLUMN_NAME_PASSWORD + " =?";
        String[] selectionArgs = {userName, pass};

        Cursor c = db.query(
                UserContract.UserEntry.TABLE_NAME,  // The table to query
                projection,                         // The columns to return
                selection,                          // The columns for the where clause
                selectionArgs,                      // The values for the where clause
                null,                               // don't group the rows
                null,                               // don't filter by row groups
                null                                // the sort order
        );
        String name = null;
        String password = null;
        if(c.moveToFirst()) {
            name = c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_USERNAME));
            password = c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_PASSWORD));
        }
        c.close();

        return (name != null && password != null);

    }

    /**
     * Gets a user object from its username
     * @param username The username used to identify the user
     * @return the userobject with username or null if user does not exist
     */
    public static User getUserByUsername(SQLiteDatabase db, String username) {
        String[] projection = {
                UserContract.UserEntry._ID,
                UserContract.UserEntry.COLUMN_NAME_USERNAME,
                UserContract.UserEntry.COLUMN_NAME_PASSWORD,
                UserContract.UserEntry.COLUMN_NAME_ACCOUNTTYPE,
                UserContract.UserEntry.COLUMN_NAME_NAME,
                UserContract.UserEntry.COLUMN_NAME_EMAIL,
                UserContract.UserEntry.COLUMN_NAME_ADDRESS,
                UserContract.UserEntry.COLUMN_NAME_TITLE
        };

        String selection = UserContract.UserEntry.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor c = db.query(
                UserContract.UserEntry.TABLE_NAME,  // The table to query
                projection,                         // The columns to return
                selection,                          // The columns for the where clause
                selectionArgs,                      // The values for the where clause
                null,                               // don't group the rows
                null,                               // don't filter by row groups
                null                                // the sort order
        );
        String user, pass, name, email, address, title;
        AccountType type = null;
        if(c.moveToFirst()) {
            user = c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_USERNAME));
            pass = c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_PASSWORD));
            type = AccountType.values()[c.getInt(c.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_ACCOUNTTYPE))];
            name = c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_NAME));
            email = c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_EMAIL));
            address = c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_ADDRESS));
            title = c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_TITLE));
        } else {
            c.close();
            return null;
        }
        c.close();

        return (new User(name, type, user, pass, email, address, title));

    }

    /**
     * Edits a user's data
     * @param db - The database being accessed
     * @param user - The user being edited
     * @return a boolean, true if the user was successfully edited, otherwise false
     */
    public static boolean editUserData(SQLiteDatabase db, User user) {
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME_USERNAME, user.getUserName());
        values.put(UserContract.UserEntry.COLUMN_NAME_ACCOUNTTYPE, user.getType().ordinal());
        values.put(UserContract.UserEntry.COLUMN_NAME_NAME, user.getName());
        values.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, user.getPassword());
        values.put(UserContract.UserEntry.COLUMN_NAME_EMAIL, user.getEmailAddress());
        values.put(UserContract.UserEntry.COLUMN_NAME_ADDRESS, user.getHomeAddress());
        values.put(UserContract.UserEntry.COLUMN_NAME_TITLE, user.getTitle());

        String[] arg = {user.getUserName()};
        int success = db.update(UserContract.UserEntry.TABLE_NAME, values, UserContract.UserEntry.COLUMN_NAME_USERNAME + " = ?", arg);

        return (success > 0);
    }

    /**
     * Returns an instance of the class
     *
     * @return an instance of the RegistrationData class
     */
    public static RegistrationData getInstance() {
        return new RegistrationData();
    }

}
