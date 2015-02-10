package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by magdi on 2/5/15.
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetArrayAdapter(Context context, List<Tweet> tweet)  {
        super(context, 0,tweet);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
            ImageView ivprofileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
            TextView tvbody = (TextView) convertView.findViewById(R.id.tvBody);
            TextView tvts = (TextView) convertView.findViewById(R.id.tvTs);
        String formattedName = "<b>" + tweet.getUser().getName() + "</b>" +
                " <small><font color='#777777'>@" + tweet.getUser().getScreenName() + "</font></small>";
        tvName.setText(Html.fromHtml(formattedName));
           // tvName.setText(tweet.getUser().getScreenName());
            tvbody.setText(tweet.getBody());
             tvts.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
            Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivprofileImage);
            return convertView;
        }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
            relativeDate = relativeDate.replaceAll("minutes ago", "m");
            relativeDate = relativeDate.replaceAll("seconds ago", "s");
            relativeDate = relativeDate.replaceAll("hour ago", "h");
            relativeDate = relativeDate.replaceAll("hours ago", "h");



        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }


}
