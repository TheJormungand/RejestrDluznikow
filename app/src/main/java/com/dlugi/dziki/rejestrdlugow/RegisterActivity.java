package com.dlugi.dziki.rejestrdlugow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import JSON.RegisterJSON;

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


    }

    public void registerClick(View view){
        String username = loginField.getText().toString();
        String password = passwordField.getText().toString();
        String email = emailField.getText().toString();
        new RegisterJSON(this).execute(username,password,email);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

}
