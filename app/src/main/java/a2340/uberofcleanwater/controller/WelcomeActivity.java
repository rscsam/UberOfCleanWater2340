package a2340.uberofcleanwater.controller;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
            editTV.setText("Your profile is incomplete!  Please click below");
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
        startActivity(submitReport);

    }

    public void viewReportsOnclick(View view) {
        Intent viewReports = new Intent(this, ViewReportsActivity.class);
        startActivity(viewReports);
    }
}
