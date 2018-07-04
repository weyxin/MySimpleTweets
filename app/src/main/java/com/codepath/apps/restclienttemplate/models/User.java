package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User{
    //list the attributes
    public String name;
    public long uid;
    public String screenName;
    public String profileImageURL;

    //deserialize the JSON
    public static User fromJSON(JSONObject jsonObject) throws JSONException{
        User user = new User();
        //extract and fill in the values
        user.name = jsonObject.getString("name");
        user.uid = jsonObject.getLong("id");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageURL = jsonObject.getString("profile_image_url");
        return user;
    }
}
