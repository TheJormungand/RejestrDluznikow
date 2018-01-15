package com.dlugi.dziki.rejestrdlugow.JSON;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class JoinGroupDialogJSON extends AsyncTask<String,Integer,String> {
    private Context context;
    private RadioGroup grouplist = null;


    private JSONArray groupsarray = null;
    private ArrayList<ArrayList<String>> groupNameArray= new ArrayList<>();

    private static final String TAG_PARAMS = "params";
    private static final String TAG_CERROR = "connectionError";
    private static final String TAG_QERROR = "queryError";
    private static final String TAG_GROUPS = "groups";
    private static final String TAG_GROUPNAME = "nazwa";
    private static final String TAG_IDGROUP = "id";
    private static final String TAG_GROUPADMIN = "admin";

    public JoinGroupDialogJSON(Context context, RadioGroup grouplist, ArrayList<ArrayList<String>> groupNamesArray) {
        this.context = context;
        this.grouplist=grouplist;
        this.groupNameArray = groupNamesArray;
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
        try{
            String groupname = arg0[0];
            String iduser = arg0[1];
            List<NameValuePair> params = new ArrayList<>();
            ParserJSON jParser = new ParserJSON(context);
            String link = "http://46.242.178.181/rejestr/searchGroups.php";//TODO change to new php file
            params.add(new BasicNameValuePair("id", iduser));
            params.add(new BasicNameValuePair("name", groupname));
            JSONObject json = jParser.makeHttpRequest(link, "GET", params);
            groupsarray = json.getJSONArray(TAG_GROUPS);
            for (int i = 0; i < groupsarray.length(); i++) {
                ArrayList<String> groupparams = new ArrayList<>();
                JSONObject c = groupsarray.getJSONObject(i);
                String name = c.getString(TAG_GROUPNAME);
                String id = c.getString(TAG_IDGROUP);
                String groupadmin= c.getString(TAG_GROUPADMIN);
                groupparams.add(id);
                groupparams.add(name);
                groupparams.add(groupadmin);
                groupNameArray.add(groupparams);
            }

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
        RadioGroup.LayoutParams rprms;
        if (groupNameArray == null) {
            Toast.makeText(context, "No Groups Found", Toast.LENGTH_SHORT).show();
        } else {
            for (ArrayList group : groupNameArray) {
                RadioButton radioButton = new RadioButton(context);
                radioButton.setText(group.get(1).toString());
                rprms = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                grouplist.addView(radioButton, rprms);
            }
            grouplist.check(0);
            //okbutton.setEnabled(true);
        }
    }
}
