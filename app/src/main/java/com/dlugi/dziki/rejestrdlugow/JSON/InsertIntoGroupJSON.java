package com.dlugi.dziki.rejestrdlugow.JSON;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class InsertIntoGroupJSON extends AsyncTask<String,Integer,String>{
    private Context context;

    private static final String TAG_PARAMS = "params";
    private static final String TAG_CERROR = "connectionError";
    private static final String TAG_QERROR = "queryError";

    public InsertIntoGroupJSON(Context context) {
        this.context = context;
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
        try{
            String id = arg0[0];
            String groupid = arg0[1];
            List<NameValuePair> params = new ArrayList<>();
            ParserJSON jParser = new ParserJSON(context);
            String link = "http://46.242.178.181/rejestr/joinGroup.php";
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("groupid", groupid));
            JSONObject json = jParser.makeHttpRequest(link, "POST", params);
            Log.d("logs", json.toString());
            if (json.has(TAG_PARAMS)){
                Log.d("tag_params","params ok");
                if(json.has(TAG_CERROR)){
                    Log.d("tag_cerror",json.getString(TAG_CERROR));
                }
                if(json.has(TAG_QERROR)){
                    Log.d("tag_querror",json.getString(TAG_QERROR));
                }
            }
            else{
                Log.d("tag_params","params not ok");
            }
            return json.toString();
        } catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {

    }
}
