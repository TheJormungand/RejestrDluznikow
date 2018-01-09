package com.dlugi.dziki.rejestrdlugow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    private EditText usernameField,passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameField = findViewById(R.id.editText1);
        passwordField = findViewById(R.id.editText2);

        SharedPreferences sharedPref = getSharedPreferences("cookies",Context.MODE_PRIVATE);
        String cookie = sharedPref.getString("id", "Brak cookie");
        Log.d("Stan cookie", cookie);
    }

    public void login(View view){
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        new LoginJSON(this).execute(username,password);
        SharedPreferences sharedPref = getSharedPreferences("cookies",Context.MODE_PRIVATE);
        String cookie = sharedPref.getString("id", "Brak cookie");
        Log.d("Stan cookie", cookie);
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }

    public void register(View view){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
}