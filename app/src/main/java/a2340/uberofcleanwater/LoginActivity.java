package a2340.uberofcleanwater;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void registerOnClick(View view) {

        Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show();
    }

    public void loginOnClick(View view) {

    }

    private void startWelcomeActivity() {
        Intent welcomeActivity = new Intent(this, WelcomeActivity.class);
        startActivity(welcomeActivity);
    }
}
