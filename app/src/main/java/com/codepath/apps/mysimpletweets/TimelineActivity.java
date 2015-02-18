package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.fragments.HomeTimeLineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimeLineFragment;
import com.codepath.apps.mysimpletweets.models.User;

public class TimelineActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setIcon(R.drawable.ic_logo);

        //get view pager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        //set viewpager adapter
        vpPager.setAdapter(new TweetPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(vpPager);

        tabStrip.setBackgroundColor(Color.argb(0xFF, 0xFF, 0xFF, 0xFF));

    }

    public void onProfileView(MenuItem item) {
        Intent in = new Intent(this, ProfileActivity.class);
        startActivity(in);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_tweet) {

            User current_user = TwitterApplication.getCurrentUser();
            Intent productIntent = new Intent(this, TweetActivity.class);
            productIntent.putExtra("current_user", current_user);

            startActivity(productIntent);

        }


        return super.onOptionsItemSelected(item);
    }

    //return order of the fregment in view pager
    public class TweetPagerAdapter extends FragmentPagerAdapter {

        private String tabTitles[] = {"Home", "Mentions"};

        //adapter
        public TweetPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //order of the fragments
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeTimeLineFragment();
            } else if (position == 1) {
                return new MentionsTimeLineFragment();
            } else {
                return null;
            }
        }

        // return tab title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        //return how many fragments
        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
