package com.dlugi.dziki.rejestrdlugow;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class JoinGroupDialogJSON extends AsyncTask<String,Integer,String> {
    private Context context;

    JSONArray groups = null;
    private ArrayList<ArrayList<String>> groupNameArray;

    private static final String TAG_PARAMS = "params";
    private static final String TAG_CERROR = "connectionError";
    private static final String TAG_QERROR = "queryError";
    private static final String TAG_GROUPS = "groups";
    private static final String TAG_GROUPNAME = "groupname";
    private static final String TAG_IDGROUP = "idgroup";
    private static final String TAG_GROUPADMIN = "groupadmin";

    public JoinGroupDialogJSON(Context context, ArrayList<ArrayList<String>> groupNamesArray) {
        this.context = context;
        this.groupNameArray = groupNamesArray;
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
        try{
            String groupname = arg0[0];
            List<NameValuePair> params = new ArrayList<>();
            ParserJSON jParser = new ParserJSON(context);
            String link = "http://46.242.178.181/rejestr/logging.php";//TODO change to new php file
            params.add(new BasicNameValuePair("groupname", groupname));
            JSONObject json = jParser.makeHttpRequest(link, "GET", params);
            Log.d("logs", json.toString());
            groups = json.getJSONArray(TAG_GROUPS);
            ArrayList groupparams = new ArrayList<String>();
            for (int i = 0; i < groups.length(); i++) {
                JSONObject c = groups.getJSONObject(i);
                String name = c.getString(TAG_GROUPNAME);
                String id = c.getString(TAG_IDGROUP);
                String groupadmin= c.getString(TAG_GROUPADMIN);
                groupparams.add(id);
                groupparams.add(name);
                groupparams.add(groupadmin);
                groupNameArray.add(groupparams);
            }

            if (json.has(TAG_PARAMS)){
                Log.d("tag_params","logowanie udane");
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
