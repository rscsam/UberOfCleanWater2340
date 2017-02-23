package a2340.uberofcleanwater.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import a2340.uberofcleanwater.R;
import a2340.uberofcleanwater.model.RegistrationData;
import a2340.uberofcleanwater.model.User;

public class WelcomeActivity extends AppCompatActivity {

    private User currentUser;
    private String currentUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setTitle("Welcome!");
        currentUserName = getIntent().getStringExtra("username");
        currentUser = RegistrationData.getUserByUsername(currentUserName);
        final TextView welcomeTV = (TextView) findViewById(R.id.welcome_tv);
        welcomeTV.setText("Welcome to the Uber of Clean Water " + currentUser.getUserName() + "!");
        checkProfile();
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentUser = RegistrationData.getUserByUsername(currentUserName);
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

    public void editProfileOnClick(View view) {
        Intent editProfile = new Intent(this, EditProfileActivity.class);
        editProfile.putExtra("username", currentUser.getUserName());
        startActivity(editProfile);
    }
}
