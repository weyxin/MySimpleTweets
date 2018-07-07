package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.AsyncHttpClient;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetDetailsActivity extends AppCompatActivity {
    Tweet tweet;
    // the view objects
    @BindView(R.id.tvBody) TextView tvBody;
    @BindView(R.id.tvUserName) TextView tvUserName;
    @BindView(R.id.ivProfileImage) ImageView background;
    @BindView(R.id.tvHandle) TextView tvHandle;
    @BindView(R.id.retweetCount) TextView retweetCount;
    @BindView(R.id.favoriteCount) TextView favoriteCount;
    @BindView(R.id.heart_button) ImageButton heartButton;
    @BindView(R.id.reply_button) ImageButton replyButton;
    @BindView(R.id.retweet_button) ImageButton retweetButton;
    int composeCode = 20;
    AsyncHttpClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);
        client = new AsyncHttpClient();
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        ButterKnife.bind(this);
        Log.d("details", Long.toString(tweet.getFavorites()));
        tvBody.setText(tweet.getBody());
        tvUserName.setText(tweet.getUser().getName());
        tvHandle.setText("@" + tweet.getUser().getScreenName());
        retweetCount.setText(Long.toString(tweet.getRetweets()));
        favoriteCount.setText(Long.toString(tweet.getFavorites()));
        Glide.with(getApplicationContext())
                .load(tweet.user.profileImageURL)
                .into(background);

        heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heartButton.setSelected(!heartButton.isSelected());
                if(heartButton.isSelected()) {
                    tweet.favorites += 1;
                    favoriteCount.setText(Long.toString(tweet.favorites));
                    heartButton.setBackgroundResource(R.drawable.ic_vector_heart);
                }
                else {
                    tweet.favorites -= 1;
                    favoriteCount.setText(Long.toString(tweet.favorites));
                    heartButton.setBackgroundResource(R.drawable.ic_vector_heart_stroke);
                }
            }
        });
        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TweetDetailsActivity.this, ComposeActivity.class);
                TextView handle = findViewById(R.id.tvHandle);
                String handleName = handle.getText().toString();
                intent.putExtra("compose", handleName);
                startActivityForResult(intent, composeCode);
            }
        });
        retweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retweetButton.setSelected(!heartButton.isSelected());
                if(retweetButton.isSelected()) {
                    tweet.retweets += 1;
                    retweetCount.setText(Long.toString(tweet.retweets));
                    retweetButton.setBackgroundResource(R.drawable.ic_vector_retweet);
                }
                else {
                    tweet.retweets -= 1;
                    retweetCount.setText(Long.toString(tweet.retweets));
                    retweetButton.setBackgroundResource(R.drawable.ic_vector_retweet_stroke);
                }
            }
        });
    }


}
