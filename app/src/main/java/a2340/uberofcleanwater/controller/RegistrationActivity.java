package a2340.uberofcleanwater.controller;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import a2340.uberofcleanwater.R;
import a2340.uberofcleanwater.database.DbHelper;
import a2340.uberofcleanwater.model.AccountType;
import a2340.uberofcleanwater.model.RegistrationData;
import a2340.uberofcleanwater.model.User;

/**
 * Activity which handles registration
 *
 * @author Sam Costley
 * @author Ryan Anderson
 * @version 1.0
 * @since 2017-02-22
 */
public class RegistrationActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Register");
        setContentView(R.layout.activity_registration);
        DbHelper mDbHelper = new DbHelper(this);
        db = mDbHelper.getWritableDatabase();
    }

    /**
     * Attempts to register the user based on the provided data
     * @param view the button being pressed
     */
    public void registerOnClick(View view) {
        final EditText usernameET = (EditText) findViewById(R.id.username_et);
        final EditText passwordET = (EditText) findViewById(R.id.password_et);
        final RadioGroup rg = (RadioGroup) findViewById(R.id.account_type_rg);
        int selection = rg.getCheckedRadioButtonId();
        AccountType accountType;
        if (selection == 0)
            accountType = AccountType.User;
        else if (selection == 1)
            accountType = AccountType.Worker;
        else if (selection == 2)
            accountType = AccountType.Manager;
        else
            accountType = AccountType.Admin;

        if (RegistrationData.addUser(db, new User(null, accountType, usernameET.getText().toString(),
                passwordET.getText().toString(), null, null, null))) {
            Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show();
            finish();
        } else if (usernameET.getText().toString().isEmpty() || passwordET.getText().toString().isEmpty()) {
            Toast.makeText(this, "Username or password is blank", Toast.LENGTH_LONG).show();
            passwordET.setText("");
        } else {
            Toast.makeText(this, "User already exists", Toast.LENGTH_LONG).show();
            passwordET.setText("");
        }
    }

    /**
     * exits the activity and returns to login screen
     * @param view the TextView being pressed
     */
    public void returnOnClick(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
