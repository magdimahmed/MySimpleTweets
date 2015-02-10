package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by magdi on 2/9/15.
 */
public class Profile {

    private String profileImageUrl;

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    private String name;
    private String screenName;

    public static Profile fromJSON(JSONObject json) {
       Profile p = new Profile();
        try {

            p.profileImageUrl = json.getString("profile_image_url");
            p.name = json.getString("name");
            p.screenName = json.getString("screen_name");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return p;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

}
