package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TimelineActivity extends ActionBarActivity {
     private ArrayList<Tweet> tweets;
    private TweetArrayAdapter atweet;
    private TwitterClient myclient;
    ListView lvTweet;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        lvTweet = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<Tweet>();
        atweet = new TweetArrayAdapter(this,tweets);
        lvTweet.setAdapter(atweet);
        myclient = TwitterApplication.getRestClient();
        ActionBar ab =getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setIcon(R.drawable.ic_ticon);
        populateTimeLine();
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

        swipeContainer.setRefreshing(false);

                populateTimeLine();
            }
        });
// Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    private void populateTimeLine() {
        myclient.getHomeTimeLine(new JsonHttpResponseHandler(){


            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                Log.d("DEBUG",json.toString() );
                atweet.addAll(Tweet.formJSONArray(json));
                swipeContainer.setRefreshing(false);
            }


            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG",errorResponse.toString() );
            }
        });

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
            //Start Product Activity
            startActivity(productIntent);
        }


        return super.onOptionsItemSelected(item);
    }





}
