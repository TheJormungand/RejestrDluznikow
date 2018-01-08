package com.dlugi.dziki.rejestrdlugow;

import android.content.Context;
import android.os.AsyncTask;

public class RegisterJSON extends AsyncTask<String,Integer,String> {
    private Context context;

    private static final String TAG_PARAMS = "params";
    private static final String TAG_CERROR = "connectionError";
    private static final String TAG_QERROR = "queryError";

    public RegisterJSON(Context context) {
        this.context = context;
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
        try{


        } catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
        return json.toString();
    }

    protected void onPostExecute(String result){

    }
}
