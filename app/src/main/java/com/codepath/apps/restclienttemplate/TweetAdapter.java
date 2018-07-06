package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{

    private List<Tweet> mTweets;
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


    //bind the values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get the data according to the position
        Tweet tweet = mTweets.get(position);

        //populate the views according to this data
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
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvTime;
        public TextView tvHandle;
        public TextView favoriteCount;
        public TextView retweetCount;

        public ViewHolder (View itemView) {
            super(itemView);
            //perform findViewByID lookups
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvHandle = (TextView) itemView.findViewById(R.id.tvHandle);
            favoriteCount = (TextView) itemView.findViewById(R.id.favoriteCount);
            retweetCount = (TextView) itemView.findViewById(R.id.retweetCount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Tweet tweet = mTweets.get(position);
                Log.d("*", "got tweet position");
                Intent intent = new Intent(context, TweetDetailsActivity.class);
                Log.d("*", "set up intent");
                //serialize the movie using parceler, use its short name as a key
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                Log.d("*", "parceled movie");
                // show the activity
                context.startActivity(intent);
                Log.d("*", "done with click activity");
            }
        }
    }

    // Clean all elements of the recycler
    public void clear() {
        //Log.d("***", "clear start");
        mTweets.clear();
        notifyDataSetChanged();
        //Log.d("***", "clear end");
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        //Log.d("***", "add start");
        mTweets.addAll(list);
        notifyDataSetChanged();
        //Log.d("***", "add end");
    }

}
