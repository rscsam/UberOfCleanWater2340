package a2340.uberofcleanwater.controller;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import a2340.uberofcleanwater.R;
import a2340.uberofcleanwater.database.UserDbHelper;
import a2340.uberofcleanwater.model.RegistrationData;

/**
 * Activity which handles login
 *
 * @author Sam Costley
 * @author Ryan Anderson
 * @version 1.0
 * @since 2017-02-12
 */
public class LoginActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Login");
        setContentView(R.layout.activity_login);

        UserDbHelper mDbHelper = new UserDbHelper(this);
        db = mDbHelper.getWritableDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final EditText usernameET= (EditText) findViewById(R.id.username_et);
        final EditText passwordET = (EditText) findViewById(R.id.password_et);
        usernameET.setText("");
        passwordET.setText("");
        usernameET.requestFocus();
    }

    /**
     * starts registration activity
     * @param view the textview that was clicked
     */
    public void registerOnClick(View view) {
        Intent registrationActivity = new Intent(this, RegistrationActivity.class);
        startActivity(registrationActivity);
    }

    /**
     * When "Login" button is pressed, attempt to login is made
     * @param view the button pressed
     */
    public void loginOnClick(View view) {
        final EditText usernameET= (EditText) findViewById(R.id.username_et);
        final EditText passwordET = (EditText) findViewById(R.id.password_et);
        String user = usernameET.getText().toString();
        String pass = passwordET.getText().toString();
        if (RegistrationData.userExists(db, user)) {
            if (RegistrationData.checkPassword(db, user, pass)) {
                startWelcomeActivity();
            } else {
                Toast.makeText(this, "Incorrect Password", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "User Doesn't Exist", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Starts welcome activity upon successful login
     */
    private void startWelcomeActivity() {
        final EditText usernameET= (EditText) findViewById(R.id.username_et);
        Intent welcomeActivity = new Intent(this, WelcomeActivity.class);
        welcomeActivity.putExtra("username", usernameET.getText().toString());
        startActivity(welcomeActivity);
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
