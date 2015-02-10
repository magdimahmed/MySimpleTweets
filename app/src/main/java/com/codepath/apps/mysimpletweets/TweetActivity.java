package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.models.Profile;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class TweetActivity extends ActionBarActivity {
    //User current_user;
    private ImageView imageView;
    private EditText etTweet;
    private TextView name;
    private TwitterClient myclient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        myclient = TwitterApplication.getRestClient();
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setIcon(R.drawable.ic_ticon);
        getProfileImage();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tweet, menu);

        return true;
    }


    public void getProfileImage(){
        myclient.getUserProfile(new JsonHttpResponseHandler() {


            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {

                Log.d("DEBUG", json.toString());
                imageView = (ImageView) findViewById(R.id.ivProfile);
                Picasso.with(getApplicationContext()).load(Profile.fromJSON(json).getProfileImageUrl()).into(imageView);
                name = (TextView) findViewById(R.id.tvName);
                name.setText(Profile.fromJSON(json).getName() + " \n" + "@" + Profile.fromJSON(json).getScreenName());

            }


            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });

    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_tweet) {
            etTweet = (EditText) findViewById(R.id.etTweetText);
            String status = etTweet.getText().toString();
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
            if (status.length() > 140) {
                Toast.makeText(this, "Your tweet is too long.", Toast.LENGTH_SHORT).show();
            } else {
                myclient.postUpdate(status, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Tweet posted_tweet = Tweet.fromJSON(response);
                        Intent data = new Intent();
// Pass relevant data back as a result
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("tweet", posted_tweet.toString());
                        data.putExtras(bundle);
// data.putExtra("tweet", posted_tweet);
// Activity finished ok, return the data
                        setResult(RESULT_OK, data); // set result code and bundle data for response
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("DEBUG", responseString.toString());
                    }
                });
            }
        }

        return super.onOptionsItemSelected(item);
    }


}
