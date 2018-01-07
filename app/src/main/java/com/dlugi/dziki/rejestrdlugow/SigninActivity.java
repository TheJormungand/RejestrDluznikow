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

public class SigninActivity extends AsyncTask<String,Integer,String>{
    private TextView statusField,roleField;
    private Context context;

    private static final String TAG_PARAMS = "params";
    private static final String TAG_CERROR = "connectionerror";
    private static final String TAG_QERROR = "queryerror";
    private static final String TAG_LOGGED = "logged";


    public SigninActivity(Context context,TextView statusField,TextView roleField) {
        this.context = context;
        this.statusField = statusField;
        this.roleField = roleField;
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
            success = json.getBoolean(TAG_PARAMS);
            if (success){
                Log.d("tag_params","logowanie udane");
                success = json.getBoolean(TAG_LOGGED);
                if(success){
                    Log.d("tag_logged","login ok");
                }
                else{
                    Log.d("tag_logged","login nie ok");
                }
                error=json.getString(TAG_CERROR);
                if(error.equals("OK")){
                    Log.d("tag_cerror",error);
                }
                error=json.getString(TAG_QERROR);
                if(error.equals("OK")){
                    Log.d("tag_querror",error);
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
        this.statusField.setText("Login Successful");
        this.roleField.setText(result);
    }
}