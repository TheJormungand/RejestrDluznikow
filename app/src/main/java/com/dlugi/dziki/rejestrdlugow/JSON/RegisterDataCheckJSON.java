package com.dlugi.dziki.rejestrdlugow.JSON;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dlugi.dziki.rejestrdlugow.Activities.MainActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegisterDataCheckJSON extends AsyncTask<String,Integer,String> {
    private Context context;
    private boolean userFlag;
    private boolean emailFlag;
    private String username;
    private String password;
    private String email;

    private static final String TAG_PARAMS = "params";
    private static final String TAG_CERROR = "connectionError";
    private static final String TAG_QERROR = "queryError";
    private static final String TAG_LOGINOCCUPIED = "loginOccupied";
    private static final String TAG_EMAILOCCUPIED = "emailOccupied";



    public RegisterDataCheckJSON(Context context,String username,String password,String email) {
        this.context = context;
        this.username=username;
        this.password=password;
        this.email=email;
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
        try{
            String username = arg0[0];
            String email = arg0[1];
            List<NameValuePair> params = new ArrayList<>();
            ParserJSON jParser = new ParserJSON(context);
            String link = "http://46.242.178.181/rejestr/registerDataCheck.php";
            params.add(new BasicNameValuePair("login", username));
            params.add(new BasicNameValuePair("email", email));
            JSONObject json = jParser.makeHttpRequest(link, "POST", params);
            Log.d("logs", json.toString());
            if (json.has(TAG_PARAMS)){
                Log.d("tag_params","params not ok");
                if(json.has(TAG_CERROR)){
                    Log.d("tag_cerror",json.getString(TAG_CERROR));
                }
                if(json.has(TAG_QERROR)){
                    Log.d("tag_querror",json.getString(TAG_QERROR));
                }
                if(json.has(TAG_EMAILOCCUPIED)){
                    emailFlag=true;
                }
                if(json.has(TAG_LOGINOCCUPIED)){
                    userFlag=true;
                }
            }
            else{
                Log.d("tag_params","params ok");
            }
            return json.toString();
        } catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    protected void onPostExecute(String result){
        if(userFlag){
            Toast.makeText(context, "Username already taken", Toast.LENGTH_SHORT).show();
        }else if(emailFlag){
            Toast.makeText(context, "Email already taken", Toast.LENGTH_SHORT).show();
        }else {
            new RegisterJSON(context).execute(username, password, email);
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
    }
}
