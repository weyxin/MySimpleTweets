package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{

    private List<Tweet> mTweets;
    //private TimelineActivity.ClickListener heartListener;
    Context context;
    //pass in the Tweets array into the constructor
    public TweetAdapter(List<Tweet> tweets) {
        mTweets = tweets;
    }

    //for each row, inflate the layout and cache references into ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvTime.setText(tweet.time);
        holder.tvHandle.setText("@" + tweet.user.getScreenName());
        holder.favoriteCount.setText(Long.toString(tweet.getFavorites()));
        holder.retweetCount.setText(Long.toString(tweet.getRetweets()));
        Glide.with(context)
                .load(tweet.user.profileImageURL)
                .into(holder.ivProfileImage);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    //create the ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
        @BindView(R.id.tvUserName) TextView tvUsername;
        @BindView(R.id.tvBody) TextView tvBody;
        @BindView(R.id.tvTime) TextView tvTime;
        @BindView(R.id.tvHandle) TextView tvHandle;
        @BindView(R.id.favoriteCount) TextView favoriteCount;
        @BindView(R.id.retweetCount) TextView retweetCount;
        @BindView(R.id.reply_button) ImageButton replyButton;
        @BindView(R.id.heart_button) ImageButton heartButton;

        public ViewHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            replyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        Tweet tweet = mTweets.get(position);
                        Intent intent = new Intent(context, ComposeActivity.class);
                        intent.putExtra("compose", "@" + tweet.user.screenName);
                        ((Activity) context).startActivityForResult(intent, 404);

                    }
                }
            });
            heartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        Tweet tweet = mTweets.get(position);
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
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Tweet tweet = mTweets.get(position);
                Intent intent = new Intent(context, TweetDetailsActivity.class);
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                context.startActivity(intent);
            }
        }

    }

    public void clearTweetsList() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    public void addTweetsToList(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }

}
