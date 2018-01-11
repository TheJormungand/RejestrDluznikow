package com.dlugi.dziki.rejestrdlugow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_group, container, false);
            //Obsługa guzików w activity group
            Button addGroupButton = rootView.findViewById(R.id.addgroup);
            addGroupButton.setOnClickListener(this);
            Button joinGroupButton = rootView.findViewById(R.id.joingroup);
            joinGroupButton.setOnClickListener(this);

            return rootView;
        }

        //implementacja obsługi guzików z activity group
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.addgroup:{
                    Toast.makeText(getActivity(),"Test",Toast.LENGTH_SHORT).show();
                }
                break;
                case R.id.joingroup:{
                    final EditText Groupname;
                    LayoutInflater inflater = getLayoutInflater();
                    View dialoglayout = inflater.inflate(R.layout.joingroup_dialog, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setView(dialoglayout);
                    Groupname = dialoglayout.findViewById(R.id.EnterGroupName) ;
                    Button cancelbutton = dialoglayout.findViewById(R.id.addgroup);
                    cancelbutton.setOnClickListener(this);
                    Button okbutton = dialoglayout.findViewById(R.id.joingroup);
                    okbutton.setOnClickListener(this);

                    alert.show();

                }
                break;
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
