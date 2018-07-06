package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    Button tweetButton;
    EditText composeText;
    private TwitterClient client;
    int characters;
    TextView wordCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        wordCount = (TextView) findViewById(R.id.wordCount);
        wordCount.setText(String.valueOf(140));
        client = TwitterApp.getRestClient(this);
        tweetButton = (Button) findViewById(R.id.Tweet);
        composeText = (EditText) findViewById(R.id.composeText);
        Intent intent = getIntent();
        String handle = intent.getExtras().getString("compose");
        if(handle != "") {
            composeText.setText(handle);
        }

        composeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (String.valueOf(composeText.length()) != null) {
                    wordCount.setText(String.valueOf(140 - composeText.length()));
                }
            }
        });
        tweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tweet", "clicked tweet button");
                composeText = (EditText) findViewById(R.id.composeText);
                String text = composeText.getText().toString();
                //send network request
                networkRequest(text);
                Log.d("tweet", "network request done");
            }
        });
    }

    private void networkRequest(String text) {
        client.sendTweet(text, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());
                try {
                    Tweet tweet = Tweet.fromJSON(response);
                    Log.d("***", "about to print the body");
                    Intent data = new Intent(ComposeActivity.this, TimelineActivity.class);
                    data.putExtra("tweet", Parcels.wrap(tweet));
                    setResult(RESULT_OK, data);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("TwitterClient", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }
        });
    }
}
