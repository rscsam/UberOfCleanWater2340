package a2340.uberofcleanwater.database;

/**
 * Contract class which defines the database and tables it can hold
 *
 * @author Ryan Anderson
 * @version 1.0
 * @since 2017-02-23
 */
public final class UserContract {
    private UserContract() {}

    /*Inner class which defines the table contents*/
    public static class UserEntry implements android.provider.BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_ACCOUNTTYPE = "accountType";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_TITLE = "title";

    }

}
