package com.dlugi.dziki.rejestrdlugow;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.dlugi.dziki.rejestrdlugow.JSON.LoginJSON;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameField,passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameField = findViewById(R.id.editText1);
        passwordField = findViewById(R.id.editText2);
    }

    public void login(View view){
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        new LoginJSON(this).execute(username,password);
    }

    public void register(View view){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
}