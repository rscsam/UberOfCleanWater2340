package a2340.uberofcleanwater.database;

/**
 * Contract class which defines the database and tables it can hold
 *
 * @author Ryan Anderson
 * @version 1.0
 * @since 2017-02-28
 */
public final class DbContract {
    private DbContract() {}

    /**
     * Inner class which defines the contents of table "users"
     *
     * @author Ryan Anderson
     * @version 1.0
     * @since 2017-02-28
     */
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

    /**
     * Inner class which defines the contents of table "waterReports"
     *
     * @author Ryan Anderson
     * @version 1.0
     * @since 2017-02-28
     */
    public static class WaterReportEntry implements android.provider.BaseColumns {
        public static final String TABLE_NAME = "waterReports";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_LONG = "longitude";
        public static final String COLUMN_NAME_LAT = "latitude";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_CONDITION = "condition";
    }

    /**
     * Inner class which defines the contents of table "purityReports"
     *
     * @author Ryan Anderson
     * @version 1.0
     * @since 2017-03-09
     */
    public static class PurityEntry implements android.provider.BaseColumns {
        public static final String TABLE_NAME = "purityReports";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_LONG = "longitude";
        public static final String COLUMN_NAME_LAT = "latitude";
        public static final String COLUMN_NAME_CONDITION = "condition";
        public static final String COLUMN_NAME_CONTAMINANT = "contaminantPPM";
        public static final String COLUMN_NAME_VIRUS = "virusPPM";
        public static final String COLUMN_NAME_DATE = "date";
    }

}
