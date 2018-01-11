package com.dlugi.dziki.rejestrdlugow.JSON;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.dlugi.dziki.rejestrdlugow.MenuActivity;

public class LoginJSON extends AsyncTask<String,Integer,String>{
    private Context context;
    private boolean loginOK = false;

    private static final String TAG_PARAMS = "params";
    private static final String TAG_CERROR = "connectionError";
    private static final String TAG_QERROR = "queryError";
    private static final String TAG_LOGGED = "logged";

    public LoginJSON(Context context) {
        this.context = context;
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
        try{
            String username = arg0[0];
            String password = arg0[1];
            List<NameValuePair> params = new ArrayList<>();
            ParserJSON jParser = new ParserJSON(context);
            String link = "http://46.242.178.181/rejestr/logging.php";
            params.add(new BasicNameValuePair("login", username));
            params.add(new BasicNameValuePair("password", password));
            JSONObject json = jParser.makeHttpRequest(link, "GET", params);
            Log.d("logs", json.toString());
            if (json.has(TAG_PARAMS)){
                Log.d("tag_params","logowanie udane");
                if(json.has(TAG_LOGGED)){
                    Log.d("tag_logged","login ok");
                    loginOK = true;
                }
                else{
                    Log.d("tag_logged","login nie ok");
                }
                if(json.has(TAG_CERROR)){
                    Log.d("tag_cerror",json.getString(TAG_CERROR));
                }
                if(json.has(TAG_QERROR)){
                    Log.d("tag_querror",json.getString(TAG_QERROR));
                }
            }
            else{
                Log.d("tag_params","logowanie nieudane");
            }
            return json.toString();
        } catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (loginOK) {
            Intent intent = new Intent(context, MenuActivity.class);
            context.startActivity(intent);
        }
        else{
            //TODO: Informacja o błędnych danych logowania
            SharedPreferences sharedPref = context.getSharedPreferences("cookies", Context.MODE_PRIVATE);
            sharedPref.edit().clear().apply();
        }
    }
}