package com.iotaconcepts.aurum;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String user_name, user_sex_age, user_age, user_sex, user_lat, user_lon;

    SessionManager name_sex_session;
    LocationSessionManager location_session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        // these session managers have users input data!
        name_sex_session = new SessionManager(getApplicationContext());
        location_session = new LocationSessionManager(getApplicationContext());

        // check whethe ruser has logged in or filled details
        name_sex_session.checkLogin();  // if he hasnt, user will be automatically redirected to login activity
        location_session.checkLogin();

        // get user data from SessionManager : data : name and sex_age
        HashMap<String, String> user = name_sex_session.getUserDetails();
        user_name = user.get(SessionManager.KEY_NAME);
        user_sex_age = user.get(SessionManager.KEY_EMAIL);

        // get user data from LocationSessionManager : data : lat and lon
        HashMap<String, String> user_2 = location_session.getUserDetails();
        user_lat = user_2.get(LocationSessionManager.KEY_NAME);
        user_lon = user_2.get(LocationSessionManager.KEY_EMAIL);


        Toast.makeText(MainActivity.this, "Welcome " + user_name, Toast.LENGTH_SHORT).show();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFrangment(), "Symptoms");
        adapter.addFragment(new TwoFragment(), "Diagnose");
        adapter.addFragment(new TreeFragment(), "Hospitals");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // In case you have an item
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();


        if(id == R.id.action_logout)  //Logout
        {
            name_sex_session.logoutUser();
            location_session.logoutUser();
        }

        if(id == R.id.action_feedback)  //Feedback
        {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }

        if(id == R.id.action_share)  //Share
        {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }

        if(id == R.id.action_about)  //About
        {
            //Intent i = new Intent(MainActivity.this, About.class);
            //startActivity(i);
        }

        if (id == R.id.action_disclaimer)  //Disclaimer
        {
            //Intent i = new Intent(MainActivity.this, Disclaimer.class);
            //startActivity(i);
        }


        return super.onOptionsItemSelected(item);
    }
















}