package a2340.uberofcleanwater.controller;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import a2340.uberofcleanwater.R;
import a2340.uberofcleanwater.model.AccountType;
import a2340.uberofcleanwater.database.DbHelper;
import a2340.uberofcleanwater.model.RegistrationData;
import a2340.uberofcleanwater.model.User;

/**
 * Activity which handles the welcome screen
 *
 * @author Sam Costley
 * @author Ryan Anderson
 * @author Sylvia Necula
 * @author Anna Bergstrom
 * @version 1.0
 * @since 2017-02-12
 */
public class WelcomeActivity extends AppCompatActivity {

    private User currentUser;
    private String currentUserName;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setTitle("Welcome!");

        DbHelper mDbHelper = new DbHelper(this);
        db = mDbHelper.getWritableDatabase();

        currentUserName = getIntent().getStringExtra("username");
        currentUser = RegistrationData.getUserByUsername(db, currentUserName);
        final TextView welcomeTV = (TextView) findViewById(R.id.welcome_tv);
        welcomeTV.setText("Welcome to the Uber of Clean Water " + currentUser.getUserName() + "!");
        checkProfile();

        checkUserClearance();
    }

    /**
     * Checks the authority level of the user and changes the display as appropriate
     */
    private void checkUserClearance() {
        AccountType clearance = currentUser.getType();
        View submitPurity = findViewById(R.id.submit_purity_bttn);
        View viewPurity = findViewById(R.id.view_purity_reports_bttn);
        View viewHistory = findViewById(R.id.history_graph_bttn);
        if (clearance == AccountType.User) {
            submitPurity.setVisibility(View.GONE);
            viewPurity.setVisibility(View.GONE);
            viewHistory.setVisibility(View.GONE);
        } else if (clearance == AccountType.Worker) {
            viewPurity.setVisibility(View.GONE);
            viewHistory.setVisibility(View.GONE);
        } else {
            submitPurity.setVisibility(View.VISIBLE);
            viewPurity.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentUser = RegistrationData.getUserByUsername(db, currentUserName);
        checkProfile();
    }

    /**
     * checks profile for completeness and alters the text view appropriately
     */
    public void checkProfile() {
        final TextView editTV = (TextView) findViewById(R.id.edit_profile_tv);
        if (!currentUser.isProfileComplete()) {
            editTV.setText(R.string.incomplete_profile_prompt);
        } else {
            editTV.setText(R.string.you_can_edit_your_profile_anytime);
        }
    }

    /**
     * logs the user out
     * @param view the TextView clicked
     */
    public void logoutOnClick(View view) {
        finish();
    }

    /**
     * launches the EditProfileActivity and passes on the username
     * @param view the button being pressed
     */
    public void editProfileOnClick(View view) {
        Intent editProfile = new Intent(this, EditProfileActivity.class);
        editProfile.putExtra("username", currentUser.getUserName());
        startActivity(editProfile);
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    /**
     * launches the SubmitReportActivity
     * @param view the button being pressed
     */
    public void submitReportOnClick(View view) {
        Intent submitReport = new Intent(this, SubmitReportActivity.class);
        submitReport.putExtra("username", currentUser.getUserName());
        startActivity(submitReport);

    }

    /**
     * launches the MapsActivity
     * @param view the button being pressed
     */
    public void mapOnclick(View view) {
        Intent map = new Intent(this, MapsActivity.class);
        map.putExtra("username", currentUser.getUserName());
        startActivity(map);
    }

    /**
     * launches the ViewReportActivity
     * @param view the button being pressed
     */
    public void viewReportsOnclick(View view) {
        Intent viewReports = new Intent(this, ViewReportsActivity.class);
        startActivity(viewReports);
    }

    /**
     * Launches the ViewPurityReportsActivity.
     * Denies the user if they don't have proper authorization though this is a redundant
     * countermeasure as they shouldn't be able to see the button in the first place.
     * @param view the View Purity Reports button
     */
    public void submitPurityReportOnClick(View view) {
        if (currentUser.getType() == AccountType.User) {
            Toast.makeText(this, "ERROR: User does not have proper authority to access this feature.", Toast.LENGTH_LONG).show();
        } else {
            Intent submitPurity = new Intent(this, SubmitPurityReportActivity.class);
            submitPurity.putExtra("username", currentUser.getUserName());
            startActivity(submitPurity);
        }
    }

    /**
     * Launches the ViewPurityReportsActivity.
     * Denies the user if they don't have proper authorization though this is a redundant
     * countermeasure as they shouldn't be able to see the button in the first place.
     * @param view the View Purity Reports button
     */
    public void viewPurityReportsOnClick(View view) {
        /*
        String display = (currentUser.getType().toString());
        String display2 = (currentUser.getUserName().toString());
        Toast.makeText(this, display, Toast.LENGTH_LONG).show();
        Toast.makeText(this, display2, Toast.LENGTH_LONG).show();
        */
        if (currentUser.getType() == AccountType.User) {
            Toast.makeText(this, "ERROR: User does not have proper authority to access this feature.", Toast.LENGTH_LONG).show();
        } else {
            Intent viewPurity = new Intent(this, ViewPurityReportsActivity.class);
            startActivity(viewPurity);
        }
    }

    /**
     * Launches the ViewHistoryGraphActivity
     * Denies the user if they don't have proper authorization though this is a redundant
     * countermeasure as they shouldn't be able to see the button in the first place.
     * @param view the View History Graph Reports button
     */
    public void historyGraphOnClick(View view) {
        if (currentUser.getType() == AccountType.User) {
            Toast.makeText(this, "ERROR: User does not have proper authority to access this feature.",
                    Toast.LENGTH_LONG).show();
        } else {
            Intent viewHistory = new Intent(this, ViewHistoryGraphActivity.class);
            startActivity(viewHistory);
        }
    }
}
