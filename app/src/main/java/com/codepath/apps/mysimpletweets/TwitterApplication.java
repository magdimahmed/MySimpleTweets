package com.codepath.apps.mysimpletweets;

import android.content.Context;

import com.codepath.apps.mysimpletweets.models.User;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     RestClient client = RestApplication.getRestClient();
 *     // use client to send requests to API
 *
 */
public class TwitterApplication extends com.activeandroid.app.Application {
	private static Context context;
    private static User user;
	@Override
	public void onCreate() {
		super.onCreate();
		TwitterApplication.context = this;
	}

	public static TwitterClient getRestClient() {
		return (TwitterClient) TwitterClient.getInstance(TwitterClient.class, TwitterApplication.context);
	}
    public static User getCurrentUser() {
        return user;
    }
    public static void setCurrentUser(User user) {
        TwitterApplication.user = user;
    }
}