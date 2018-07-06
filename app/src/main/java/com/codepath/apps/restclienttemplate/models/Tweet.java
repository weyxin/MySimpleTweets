package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Parcel
public class Tweet{
    //list out the attributes
    public String body;
    public long uid;
    public User user;
    public String createdAt;
    public String time;
    public Boolean isReplying;
    public long favorites;
    public Boolean isFavorited;
    public long retweets;

    //serialize JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException{
        Tweet tweet = new Tweet();
        //extract values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        tweet.time = Tweet.getRelativeTimeAgo(jsonObject.getString("created_at"));
        tweet.isReplying = false;
        tweet.isFavorited = jsonObject.getBoolean("favorited");
        tweet.favorites = jsonObject.getLong("favorite_count");
        tweet.retweets = jsonObject.getLong("retweet_count");
        return tweet;
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getTime() {
        return time;
    }

    public Boolean getReplying() {
        return isReplying;
    }

    public long getFavorites() {
        return favorites;
    }

    public Boolean getFavorited() {
        return isFavorited;
    }

    public long getRetweets() {
        return retweets;
    }
}
