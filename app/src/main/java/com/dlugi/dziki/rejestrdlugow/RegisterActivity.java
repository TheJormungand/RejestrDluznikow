package com.dlugi.dziki.rejestrdlugow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText loginField,passwordField,confirmField,emailField;
    //private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginField = findViewById(R.id.Login);
        passwordField = findViewById(R.id.Haslo);
        emailField = findViewById(R.id.Email);
        confirmField = findViewById(R.id.Confirm);

        //registerButton = findViewById(R.id.Zarejestruj);

    }

    public void registerClick(View view){
        String username = loginField.getText().toString();
        String password = passwordField.getText().toString();
        String email = emailField.getText().toString();
        //new LoggingJSON(this).execute(username,password);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
