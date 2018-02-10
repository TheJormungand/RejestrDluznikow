package com.dlugi.dziki.rejestrdlugow.JSON;

import com.dlugi.dziki.rejestrdlugow.Activities.MenuActivity;
import com.dlugi.dziki.rejestrdlugow.Activities.LoginActivity;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class LoginCheckJSON extends AsyncTask<String,Integer,String>{
    private Context context;
    private boolean loginOk = false;

    private static final String TAG_PARAMS = "params";
    private static final String TAG_CERROR = "connectionError";
    private static final String TAG_QERROR = "queryError";
    private static final String TAG_LOGGED = "logged";

    public LoginCheckJSON(Context context) {
        this.context = context;
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
        try{
            String id = arg0[0];
            List<NameValuePair> params = new ArrayList<>();
            ParserJSON jParser = new ParserJSON(context);
            String link = "http://46.242.178.181/rejestr/loginCheck.php";
            params.add(new BasicNameValuePair("id", id));
            JSONObject json = jParser.makeHttpRequest(link, "POST", params);
            Log.d("logs", json.toString());
            if (json.has(TAG_PARAMS)){
                Log.d("tag_params","logowanie udane");
                if(json.has(TAG_LOGGED)){
                    Log.d("tag_logged","login ok");
                    loginOk = true;
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
    protected void onPostExecute(String result){
        if(loginOk){
            Intent intent = new Intent(context, MenuActivity.class);
            context.startActivity(intent);
        }
        else{
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }
    }
}