package com.dlugi.dziki.rejestrdlugow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dlugi.dziki.rejestrdlugow.JSON.GetGroupsJSON;
import com.dlugi.dziki.rejestrdlugow.JSON.InsertIntoGroupJSON;
import com.dlugi.dziki.rejestrdlugow.JSON.JoinGroupDialogJSON;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class MenuActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    private static String idFromCookie;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        sharedPref = getSharedPreferences("cookies", Context.MODE_PRIVATE);
        idFromCookie = sharedPref.getString("id", "Brak cookie");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_activity_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static class GroupActivityFragment extends Fragment implements View.OnClickListener{
        private static final String ARG_SECTION_NUMBER = "section_number";
        private AlertDialog alert=null;
        private ArrayList<ArrayList<String>> UserGroupList;
        private ArrayList<String> UserGroupListCount;
        private ListView groupListView ;
        private ArrayAdapter<String> adapter;
        public GroupActivityFragment() {
        }


        public static GroupActivityFragment newInstance(int sectionNumber) {
            GroupActivityFragment fragment = new GroupActivityFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_group, container, false);
            //Obsługa guzików w activity group
            Button addGroupButton = rootView.findViewById(R.id.addgroup);
            addGroupButton.setOnClickListener(this);
            Button joinGroupButton = rootView.findViewById(R.id.joingroup);
            joinGroupButton.setOnClickListener(this);

            //ListView w Groups tab
            groupListView=rootView.findViewById(R.id.GroupView);
            new GetGroupsJSON(getActivity(),UserGroupList,UserGroupListCount).execute(idFromCookie);
            if(UserGroupList!=null) {
                adapter = new GroupListAdapter(getActivity(), UserGroupList, UserGroupListCount);
                groupListView.setAdapter(adapter);
            }else{
                //TODO whole "else" for testing etc. in final build replace with "No groups" info
                String testString = "testgroup";
                ArrayList<String> testListCount = new ArrayList<String>();
                ArrayList<ArrayList<String>> testArray = new ArrayList<>();
                for(int i=0;i<10;i++) {
                    ArrayList<String> testList = new ArrayList<String>();
                    testListCount.add(testString);
                    testList.add(testString);
                    testList.add(testString);
                    testList.add(testString);
                    testArray.add(testList);
                }
                adapter = new GroupListAdapter(getActivity(), testArray, testListCount);
                groupListView.setAdapter(adapter);
            }//TODO end of test "else", delete in final buld!
            return rootView;
        }

        //implementacja obsługi guzików z activity group
        @Override
        public void onClick(View v) {
            EditText GroupnameLabel=null;
            RadioGroup grouplist = null;
            RadioGroup.LayoutParams rprms;
            LayoutInflater inflater = getLayoutInflater();
            View joinGroupDialog=inflater.inflate(R.layout.joingroup_dialog, null);
            Button okbutton = joinGroupDialog.findViewById(R.id.okbutton);
            Button cancelbutton = joinGroupDialog.findViewById(R.id.cancelbutton);
            ImageButton searchbutton = joinGroupDialog.findViewById(R.id.searchbutton);
            ArrayList<ArrayList<String>> groups=null;
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
            switch (v.getId()) {
                case R.id.addgroup:{

                }
                break;
                case R.id.joingroup:{
                    okbutton.setEnabled(false);
                    alertBuilder.setView(joinGroupDialog);
                    cancelbutton.setOnClickListener(this);
                    okbutton.setOnClickListener(this);
                    searchbutton.setOnClickListener(this);
                    grouplist = joinGroupDialog.findViewById(R.id.GroupList);
                    alert = alertBuilder.show();
                }break;
                case R.id.cancelbutton:{
                    alert.dismiss();
                }break;
                case R.id.okbutton:{
                        Integer index = grouplist.getCheckedRadioButtonId();
                        if (index != null) {
                            new InsertIntoGroupJSON(getActivity()).execute(idFromCookie, groups.get(index).get(0));
                            alert.dismiss();
                            Toast.makeText(getActivity(), "Request sent", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Select a group first", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("userid", idFromCookie);//TODO temporary, delete when search working

                }break;
                case R.id.searchbutton:{
                    GroupnameLabel = joinGroupDialog.findViewById(R.id.EnterGroupName);
                    String GroupName = GroupnameLabel.getText().toString();
                    //new JoinGroupDialogJSON(getActivity(),groups).execute(GroupName,idFromCookie);
                    if(groups==null || groups.isEmpty()){
                        Toast.makeText(getActivity(),"No Groups Found",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        for(ArrayList group: groups) {
                            RadioButton radioButton = new RadioButton(getActivity());
                            radioButton.setText(group.get(1).toString());
                            rprms = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                            grouplist.addView(radioButton, rprms);
                        }
                        grouplist.check(0);
                        okbutton.setEnabled(true);
                    }
                }break;
            }
        }

    }

    public static class DebtActivityFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public DebtActivityFragment() {
        }

        public static DebtActivityFragment newInstance(int sectionNumber) {
            DebtActivityFragment fragment = new DebtActivityFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_debt, container, false);
           // TextView textView = (TextView) rootView.findViewById(R.id.section_label);
           // textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public static class UserActivityFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public UserActivityFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static UserActivityFragment newInstance(int sectionNumber) {
            UserActivityFragment fragment = new UserActivityFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_user, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    // Top Rated fragment activity
                    return new DebtActivityFragment();
                case 1:
                    // Games fragment activity
                    return new GroupActivityFragment();
                case 2:
                    // Movies fragment activity
                    return new UserActivityFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
