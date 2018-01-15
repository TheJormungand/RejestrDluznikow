package com.dlugi.dziki.rejestrdlugow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.dlugi.dziki.rejestrdlugow.JSON.LoginCheckJSON;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getSharedPreferences("cookies",Context.MODE_PRIVATE);
        String idFromCookie = sharedPref.getString("id", "Brak cookie");
        if(idFromCookie.equals("Brak cookie")){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
        else{
            new LoginCheckJSON(this).execute(idFromCookie);
        }
        Log.d("Stan cookie", idFromCookie);
    }

    @Override
    public void onResume(){
        super.onResume();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}