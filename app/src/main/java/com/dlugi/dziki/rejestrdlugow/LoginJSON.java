package com.dlugi.dziki.rejestrdlugow;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class LoginJSON extends AsyncTask<String,Integer,String>{
    private TextView statusField,roleField;
    private Context context;

    private static final String TAG_PARAMS = "params";
    private static final String TAG_CERROR = "connectionerror";
    private static final String TAG_QERROR = "queryerror";
    private static final String TAG_LOGGED = "logged";


    public LoginJSON(Context context) {
        this.context = context;
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
        try{
            boolean success;
            String error;
            String username = arg0[0];
            String password = arg0[1];
            List<NameValuePair> params = new ArrayList<>();
            JSONParser jParser = new JSONParser();
            String link = "http://46.242.178.181/rejestr/logging.php";
            params.add(new BasicNameValuePair("login", username));
            params.add(new BasicNameValuePair("password", password));
            JSONObject json = jParser.makeHttpRequest(link, "GET", params);
            Log.d("logs", json.toString());
            if (json.has(TAG_PARAMS)){
                Log.d("tag_params","logowanie udane");
                if(json.has(TAG_LOGGED)){
                    Log.d("tag_logged","login ok");
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

    }
}