package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by magdi on 2/5/15.
 */


public class User implements Serializable {

    private String name;
    private long uid;
    private String sceenname;
    private String profileImageUrl;
    private int numTweets;
    private int followersCount;
    private int friendsCount;
    private String tagline;
    private String profileBgImageUrl;

    public static User fromJSON(JSONObject json) {
        User u = new User();
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.sceenname = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
            u.profileBgImageUrl = json.getString("profile_background_image_url");
            u.numTweets = json.getInt("statuses_count");
            u.followersCount = json.getInt("followers_count");
            u.friendsCount = json.getInt("friends_count");
            u.tagline = json.getString("description");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;
    }

    public String getTagline() {
        return tagline;
    }

    public String getProfileBgImageUrl() {
        return profileBgImageUrl;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getNumTweets() {
        return numTweets;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return sceenname;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }


}
