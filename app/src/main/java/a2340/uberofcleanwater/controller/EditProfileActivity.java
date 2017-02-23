package a2340.uberofcleanwater.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import a2340.uberofcleanwater.R;
import a2340.uberofcleanwater.model.RegistrationData;
import a2340.uberofcleanwater.model.User;

/**
 * Activity which allows users to edit their profiles
 *
 * @author Sam Costley
 * @version 1.0
 * @since 2017-02-21
 */
public class EditProfileActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setTitle("Edit Profile");
        user = RegistrationData.getUserByUsername(getIntent().getStringExtra("username"));
        final EditText nameET = (EditText) findViewById(R.id.name_et);
        final EditText titleET = (EditText) findViewById(R.id.title_et);
        final EditText emailET = (EditText) findViewById(R.id.email_et);
        final EditText addressET = (EditText) findViewById(R.id.address_et);
        if (user.getName() != null)
            nameET.setText(user.getName());
        if (user.getTitle() != null)
            titleET.setText(user.getTitle());
        if (user.getEmailAddress() != null)
            emailET.setText(user.getEmailAddress());
        if (user.getHomeAddress() != null)
            addressET.setText(user.getHomeAddress());
    }

    /**
     * saves all the changes to the user object and then exits the activity
     * @param view the button clicked
     */
    public void saveOnClick(View view) {
        final EditText nameET = (EditText) findViewById(R.id.name_et);
        final EditText titleET = (EditText) findViewById(R.id.title_et);
        final EditText emailET = (EditText) findViewById(R.id.email_et);
        final EditText addressET = (EditText) findViewById(R.id.address_et);
        user.setName(nameET.getText().toString());
        user.setTitle(titleET.getText().toString());
        user.setEmailAddress(emailET.getText().toString());
        user.setHomeAddress(addressET.getText().toString());
        finish();
    }
}
