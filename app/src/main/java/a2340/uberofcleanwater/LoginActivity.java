package a2340.uberofcleanwater;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Model.RegistrationData;

public class LoginActivity extends AppCompatActivity {

    private RegistrationData rd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rd = new RegistrationData();

        setContentView(R.layout.activity_login);
    }

    public void registerOnClick(View view) {
        final EditText usernameET= (EditText) findViewById(R.id.username_et);
        final EditText passwordET = (EditText) findViewById(R.id.password_et);
        if (rd.addUser(usernameET.getText().toString(), passwordET.getText().toString()))
            Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "User already exists", Toast.LENGTH_LONG).show();
    }

    public void loginOnClick(View view) {
        final EditText usernameET= (EditText) findViewById(R.id.username_et);
        final EditText passwordET = (EditText) findViewById(R.id.password_et);
        String user = usernameET.getText().toString();
        String pass = passwordET.getText().toString();
        if (rd.userExists(user)) {
            if (rd.checkPassword(user, pass)) {
                startWelcomeActivity();
            } else {
                Toast.makeText(this, "Incorrect Password", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "User Doesn't Exist", Toast.LENGTH_LONG).show();
        }
    }

    private void startWelcomeActivity() {
        Intent welcomeActivity = new Intent(this, WelcomeActivity.class);
        startActivity(welcomeActivity);
    }
}
