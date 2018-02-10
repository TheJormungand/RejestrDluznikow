package com.dlugi.dziki.rejestrdlugow.JSON;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dlugi.dziki.rejestrdlugow.GroupListAdapter;
import com.dlugi.dziki.rejestrdlugow.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetGroupsJSON extends AsyncTask<String,Integer,String> {
    private Activity context;
    private View rootView;
    private ListView groupListView ;
    private ArrayList<ArrayList<String>> UserGroupList = new ArrayList<>();
    private ArrayList<String> UserGroupListCount= new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private JSONArray groups = null;
    private static final String TAG_PARAMS = "params";
    private static final String TAG_CERROR = "connectionError";
    private static final String TAG_QERROR = "queryError";
    private static final String TAG_GROUPS = "groups";
    private static final String TAG_GROUPNAME = "nazwa";
    private static final String TAG_IDGROUP = "id";
    private static final String TAG_GROUPADMIN = "admin";

    public GetGroupsJSON(Activity context, View rootView) {
        this.context = context;
        this.rootView=rootView;
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
        try{
            String iduser = arg0[0];
            Log.d("IDUSER",iduser);
            List<NameValuePair> params = new ArrayList<>();
            ParserJSON jParser = new ParserJSON(context);
            String link = "http://46.242.178.181/rejestr/getGroups.php";//TODO change to new php file
            params.add(new BasicNameValuePair("id", iduser));
            JSONObject json = jParser.makeHttpRequest(link, "POST", params);
            groups = json.getJSONArray(TAG_GROUPS);

            for (int i = 0; i < groups.length(); i++) {
                ArrayList<String> groupparams = new ArrayList<>();
                JSONObject c = groups.getJSONObject(i);
                String name = c.getString(TAG_GROUPNAME);
                String id = c.getString(TAG_IDGROUP);
                String groupadmin= c.getString(TAG_GROUPADMIN);
                groupparams.add(id);
                groupparams.add(name);
                groupparams.add(groupadmin);
                UserGroupList.add(groupparams);
                UserGroupListCount.add("LubiePlacki");
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
        groupListView=rootView.findViewById(R.id.GroupView);
        if(UserGroupList!=null) {
            adapter = new GroupListAdapter(context, UserGroupList, UserGroupListCount);
            groupListView.setAdapter(adapter);
        }else {
            Toast.makeText(context, "GroupList Is Null", Toast.LENGTH_SHORT).show();
        }
    }
}
